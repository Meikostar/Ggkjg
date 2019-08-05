package com.ggkjg.view.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.type.MemberLevelType;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.ScreenSizeUtil;
import com.ggkjg.common.utils.ZXingUtils;
import com.ggkjg.dto.UserInfoDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.mainfragment.personalcenter.ShareQRcodeActivity;

/*我的二维码*/
public class QRcodeDialog extends Dialog {
    String imgUrl;
    UserInfoDto userInfoDto;
    Context context;
    public QRcodeDialog(@NonNull Context context, String imgUrl,UserInfoDto userInfoDto) {
        super(context, R.style.transparent_style_dialog);
        this.imgUrl = imgUrl;
        this.userInfoDto = userInfoDto;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qr_code_layout);
        ImageView img_qrcode = findViewById(R.id.img_qrcode);
        ImageView userIcon = findViewById(R.id.img_user_header);

        TextView userNameTv = findViewById(R.id.tv_user_nickname);
//        int width = ScreenSizeUtil.dp2px(250);
        String shareUrl = BuildConfig.BASE_IMAGE_URL + imgUrl;
//        Bitmap bitmap = ZXingUtils.createQRImage(shareUrl, width, width);
//        img_qrcode.setImageBitmap(bitmap);
        GlideUtils.getInstances().loadRoundCornerImgUnCache(context,img_qrcode,0,shareUrl);

        if (userInfoDto != null) {
            userNameTv.setText(userInfoDto.getNickName());
            String userHeadUrl = BuildConfig.BASE_IMAGE_URL + userInfoDto.getHeadImg();
            GlideUtils.getInstances().loadNormalImg(getContext(), userIcon, userHeadUrl);
//            String memberLevelName = "";
//            if (userInfoDto.getMemberLevel() == MemberLevelType.level_1.getType()) {
//                memberLevelName = MemberLevelType.level_1.getValue();
//            } else if (userInfoDto.getMemberLevel() == MemberLevelType.level_2.getType()) {
//                memberLevelName = MemberLevelType.level_2.getValue();
//                img_member_level.setImageResource(R.mipmap.icon_member_level_2);
//            } else if (userInfoDto.getMemberLevel() == MemberLevelType.level_3.getType()) {
//                memberLevelName = MemberLevelType.level_3.getValue();
//                img_member_level.setImageResource(R.mipmap.icon_member_level_3);
//            } else if (userInfoDto.getMemberLevel() == MemberLevelType.level_4.getType()) {
//                memberLevelName = MemberLevelType.level_4.getValue();
//                img_member_level.setImageResource(R.mipmap.icon_member_level_4);
//            }
//            tv_member_level.setText(memberLevelName);
        }
    }


}
