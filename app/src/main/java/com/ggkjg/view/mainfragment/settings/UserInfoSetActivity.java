package com.ggkjg.view.mainfragment.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BaseApplication;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.type.MemberLevelType;
import com.ggkjg.common.utils.Compressor;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.ImagePickerUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.UserInfoDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.mainfragment.login.LoginActivity;
import com.ggkjg.view.widgets.BaseDialog;
import com.ggkjg.view.widgets.ConfirmDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserInfoSetActivity extends BaseActivity {
    @BindView(R.id.ll_header)
    LinearLayout ll_header;
    @BindView(R.id.ll_nick_name)
    LinearLayout ll_nick_name;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;
    @BindView(R.id.ll_login_password)
    LinearLayout ll_login_password;
    @BindView(R.id.ll_pay_password)
    LinearLayout ll_pay_password;
    @BindView(R.id.ll_help)
    LinearLayout ll_help;
    @BindView(R.id.ll_feedback)
    LinearLayout ll_feedback;
    @BindView(R.id.btn_logout)
    Button btn_logout;
    @BindView(R.id.img_user_header)
    ImageView img_user_header;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    private UserInfoDto mUserInfoDto;

    @Override
    public void initListener() {
        bindClickEvent(ll_header, () -> {
            updateHeader();
        });
        bindClickEvent(ll_nick_name, () -> {
            if (mUserInfoDto != null && !"2".equals(mUserInfoDto.getNickModityState())) {
                gotoActivity(UserNameActivity.class);
            }else {
                ToastUtil.showToast("已修改过昵称");
            }
        });
        bindClickEvent(ll_phone, () -> {
            Bundle bundle = new Bundle();
            if(mUserInfoDto != null){
                bundle.putString(Constants.INTENT_PHONE,mUserInfoDto.getMobileNo());
            }
            gotoActivity(ValidateMobileNoActivity.class,false,bundle);
        });
        bindClickEvent(ll_login_password, () -> {
            gotoActivity(UpdateLoginPwsActivity.class);
        });
        bindClickEvent(ll_pay_password, () -> {
            if(mUserInfoDto != null && !TextUtils.isEmpty(mUserInfoDto.getTradePwd())){
                gotoActivity(ModifyPayPwdActivity.class);
            }else {
                gotoActivity(SetPayPwdActivity.class);
            }

        });
        bindClickEvent(ll_help, () -> {
            gotoActivity(HelpActivity.class);
        });
        bindClickEvent(ll_feedback, () -> {
            gotoActivity(FeedbackActivity.class);
        });
        bindClickEvent(btn_logout, () -> {
            ConfirmDialog confirmDialog = new ConfirmDialog(UserInfoSetActivity.this);
            confirmDialog.setYesOnclickListener("确定", new BaseDialog.OnYesClickListener() {
                @Override
                public void onYesClick() {
                    logout();
                    Constants.getInstance().cleanUserInfo();
                    gotoActivity(LoginActivity.class, true, null);
                }
            });
            confirmDialog.setCancelText("取消");
            confirmDialog.setTitle("温馨提示");
            confirmDialog.setMessage("确定要退出登录");
            confirmDialog.show();

        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_userinfo_set_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        ImagePickerUtil.initImagePicker(1, true, true);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        DataManager.getInstance().getMemberBaseInfo(new DefaultSingleObserver<UserInfoDto>() {
            @Override
            public void onSuccess(UserInfoDto userInfoDto) {
                if (userInfoDto != null) {
                    mUserInfoDto = userInfoDto;
                    tv_nickname.setText(userInfoDto.getNickName());
                    tv_phone.setText(userInfoDto.getMobileNo());
                    String userHeadUrl = BuildConfig.BASE_IMAGE_URL + userInfoDto.getHeadImg();
                    GlideUtils.getInstances().loadRoundImg(UserInfoSetActivity.this, img_user_header, userHeadUrl);

                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }

    private void updateHeader() {

        //打开选择器
        Intent intent = new Intent(UserInfoSetActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && data != null) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null && images.size() > 0) {
                uploadImg(images.get(0).path);
            }
        }

    }

    private void uploadImg(final String imgPath) {
        File file = new File(imgPath);
        File compressedImage = null;
        if (file.exists()) {
            //压缩文件
            try {
                compressedImage = new Compressor(BaseApplication.getInstance()).compressToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (compressedImage == null) {
                compressedImage = file;
            }
        } else {
            ToastUtil.showToast("上传头像失败");
            return;
        }
        showLoadDialog();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), compressedImage);
        MultipartBody.Part part = MultipartBody.Part.createFormData("imgFile", file.getName(), requestBody);
        DataManager.getInstance().uploadHeadImg(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                dissLoadDialog();
                if (httpResult != null && httpResult.getStatus() == 1) {
                    GlideUtils.getInstances().loadNormalImg(UserInfoSetActivity.this, img_user_header, imgPath);
                    ToastUtil.showToast("上传成功");
                } else {
                    if (httpResult != null) {
                        ToastUtil.showToast(httpResult.getMsg());
                    }

                }

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        }, part);
    }
    private void logout(){
        DataManager.getInstance().logout(new DefaultSingleObserver<HttpResult<Object>>(){
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {

            }
        });
    }
}
