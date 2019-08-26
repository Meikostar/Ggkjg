package com.ggkjg.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ggkjg.R;
import com.ggkjg.common.Constants;
import com.jakewharton.rxbinding2.view.RxView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

/**
 * 分享
 * Created by xld021 on 2018/4/17.
 */

public class ShareSdkUtils {

    @SuppressLint("StaticFieldLeak")
    private static ShareSdkUtils shareSdkUtils = new ShareSdkUtils();
    private Activity context;
    private final String TAG = "ShareSdkUtils";
    String title, description, imgUrl, pageUrl;

    public static ShareSdkUtils getInstances() {
        return shareSdkUtils;
    }
    private    Dialog shareDialog;
    public void showShareDialog(Activity context, String title, String description, String imgUrl, String pageUrl) {
        this.context = context;
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.pageUrl = pageUrl;
        shareDialog= new Dialog(context, R.style.dialog_with_alpha);
        shareDialog.setContentView(R.layout.dialog_share_layout);
        Window window = shareDialog.getWindow();
        // 控制对话框在屏幕底部显示
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomAnimStyle);//动画
        WindowManager.LayoutParams params = window.getAttributes();//
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);

        TextView ll_share_wx = shareDialog.findViewById(R.id.ll_share_wx);
        TextView ll_share_wx_friend = shareDialog.findViewById(R.id.ll_share_wx_friend);
        ImageView tv_cancel = shareDialog.findViewById(R.id.tv_cancel);


        bindClickEvent(ll_share_wx, () -> {
            shareDialog.dismiss();
            sendToWeaChat(false);
        });
        bindClickEvent(ll_share_wx_friend, () -> {
            shareDialog.dismiss();
            sendToWeaChat(true);
        });
        bindClickEvent(tv_cancel, () -> {
            shareDialog.dismiss();
        });
        shareDialog.show();

    }
    public void dissMiss(){
        if(shareDialog!=null){
            shareDialog.dismiss();
        }
    }

    private void sendToWeaChat(final boolean isTimelineCb) {
        if (InstallWeChatOrAliPay.getInstance().isWeixinAvilible(context)) {
            Toast.makeText(context, "分享操作中..", Toast.LENGTH_SHORT).show();
            final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
            // 将该app注册到微信
            api.registerApp(Constants.WX_APP_ID);
            //初始化一个WXWebpageObject，填写url
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = pageUrl;

            //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = description;
//            Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
//            Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 120, 120, true);
            Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
            Bitmap thumbBmp = drawableToBitmap(drawable);
            msg.setThumbImage(thumbBmp);

            //构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            api.sendReq(req);
//            thumb.recycle();
            thumbBmp.recycle();
//            thumb = null;
            thumbBmp = null;
        } else {
            ToastUtil.showToast("请先安装微信");
        }
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    protected void bindClickEvent(View view, final Action action) {
        Observable<Object> observable = RxView.clicks(view);
        observable.throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(trigger -> action.run());
    }
}
