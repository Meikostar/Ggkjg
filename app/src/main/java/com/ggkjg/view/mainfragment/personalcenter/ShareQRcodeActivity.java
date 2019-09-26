package com.ggkjg.view.mainfragment.personalcenter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.type.MemberLevelType;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.ScreenSizeUtil;
import com.ggkjg.common.utils.ShareSdkUtils;
import com.ggkjg.common.utils.ZXingUtils;
import com.ggkjg.dto.QRcodeShareDto;
import com.ggkjg.dto.UserInfoDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

import butterknife.BindView;

public class ShareQRcodeActivity extends BaseActivity {
    @BindView(R.id.img_qrcode_back)
    ImageView mBackView;
    @BindView(R.id.img_qrcode)
    ImageView img_qrcode;
    @BindView(R.id.img_qrcode_share)
    ImageView img_qrcode_share;
    @BindView(R.id.img_user_header)
    ImageView img_user_header;
    @BindView(R.id.tv_user_nickname)
    TextView tv_user_nickname;
    @BindView(R.id.tv_user_code)
    TextView tv_user_code;
    String referralQrcode = "";
    private String shareUrl;

    @Override
    public void initListener() {
        bindClickEvent(mBackView, () -> {
            finish();
        });
        bindClickEvent(img_qrcode_share, () -> {
            ShareSdkUtils.getInstances().showShareDialog(this, "港港跨境二维码分享", "港港跨境", "", shareUrl,null);

        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_share_qr_code_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        loadUserInfo();
    }

    private void findMemberQrCode() {

        DataManager.getInstance().findMemberQrCode(new DefaultSingleObserver<String>() {
            @Override
            public void onSuccess(String imgUrl) {
                dissLoadDialog();
                if (!TextUtils.isEmpty(imgUrl)) {
//                    int width = ScreenSizeUtil.dp2px(250);
                    String shareUrlCode = BuildConfig.BASE_IMAGE_URL + imgUrl;
//                    Bitmap bitmap = ZXingUtils.createQRImage(BuildConfig.BASE_IMAGE_URL + imgUrl, width, width);
//                    img_qrcode.setImageBitmap(bitmap);
                    GlideUtils.getInstances().loadRoundCornerImgUnCache(ShareQRcodeActivity.this, img_qrcode, 0, shareUrlCode);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, referralQrcode);
    }

    private void loadUserInfo() {
        showLoadDialog();
        DataManager.getInstance().getMemberBaseInfo(new DefaultSingleObserver<UserInfoDto>() {
            @Override
            public void onSuccess(UserInfoDto userInfoDto) {
                if (userInfoDto != null) {
                    referralQrcode = userInfoDto.getReferralCode();
                    tv_user_nickname.setText(userInfoDto.getNickName());
                    tv_user_code.setText("推荐码: "+referralQrcode);
                    findMemberQrCode();
                    GlideUtils.getInstances().loadNormalImg(ShareQRcodeActivity.this, img_user_header, BuildConfig.BASE_IMAGE_URL + userInfoDto.getHeadImg(),R.mipmap.user_default_icon);
                    findPersonalShareParam();
                } else {
                    dissLoadDialog();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        });
    }

    private void findPersonalShareParam() {
        DataManager.getInstance().findPersonalShareParam(new DefaultSingleObserver<QRcodeShareDto>() {
            @Override
            public void onSuccess(QRcodeShareDto shareDto) {
                if (shareDto != null ) {
                    shareUrl = shareDto.getShareUrl();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, referralQrcode);
    }
}
