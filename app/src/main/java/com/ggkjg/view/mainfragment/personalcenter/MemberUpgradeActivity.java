package com.ggkjg.view.mainfragment.personalcenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.type.MemberLevelType;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.UserInfoDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;

import java.util.HashMap;

import butterknife.BindView;

public class MemberUpgradeActivity extends BaseActivity {
    @BindView(R.id.tv_user_nickname)
    TextView tv_user_nickname;
    @BindView(R.id.tv_user_level)
    TextView tv_user_level;
    @BindView(R.id.img_user_header)
    ImageView img_user_header;
    @BindView(R.id.img_user_level)
    ImageView img_user_level;
    @BindView(R.id.img_level_1)
    ImageView img_level_1;
    @BindView(R.id.img_level_2)
    ImageView img_level_2;
    @BindView(R.id.img_level_3)
    ImageView img_level_3;
    @BindView(R.id.tv_level_1)
    TextView tv_level_1;
    @BindView(R.id.tv_level_2)
    TextView tv_level_2;
    @BindView(R.id.tv_level_3)
    TextView tv_level_3;
    @BindView(R.id.ed_name)
    EditText ed_name;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.ed_wx)
    EditText ed_wx;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.ed_remark)
    EditText ed_remark;
    @BindView(R.id.btn_confirm)
    TextView btn_confirm;

    private int selLevel = -1;
    ImageView imgLevel[];
    TextView tvLevel[] = new TextView[3];
    int currentLevel = 1;

    @Override
    public void initListener() {
        bindClickEvent(img_level_1, () -> {
            selLevel = 0;
            dealSelLevel();
        });
        bindClickEvent(img_level_2, () -> {
            selLevel = 1;
            dealSelLevel();
        });
        bindClickEvent(img_level_3, () -> {
            selLevel = 2;
            dealSelLevel();
        });
        bindClickEvent(btn_confirm, () -> {
            confirm();
        });
        bindClickEvent(tv_address, () -> {
            gotoActivity(AreaListActivity.class, false, null, Constants.INTENT_REQUESTCODE_AREA);

        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_member_upgrade_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        imgLevel = new ImageView[]{img_level_1, img_level_2, img_level_3};
        tvLevel = new TextView[]{tv_level_1, tv_level_2, tv_level_3};
        actionbar.changeBlueTop();
    }

    @Override
    public void initData() {

        loadData();
    }

    private void dealSelLevel() {
        for (int i = 0; i < imgLevel.length; i++) {
            if (selLevel == i) {
                imgLevel[i].setSelected(true);
                tvLevel[i].setTextColor(getResources().getColor(R.color.my_color_008CD6));
            } else {
                imgLevel[i].setSelected(false);
                tvLevel[i].setTextColor(getResources().getColor(R.color.my_color_333333));
            }
        }
    }

    private void loadData() {
        showLoadDialog();
        DataManager.getInstance().getMemberBaseInfo(new DefaultSingleObserver<UserInfoDto>() {
            @Override
            public void onSuccess(UserInfoDto userInfoDto) {
                dissLoadDialog();
                if (userInfoDto != null) {
                    currentLevel = userInfoDto.getMemberLevel();
                    tv_user_nickname.setText(userInfoDto.getNickName());
                    String userHeadUrl = BuildConfig.BASE_IMAGE_URL + userInfoDto.getHeadImg();
                    GlideUtils.getInstances().loadNormalImg(MemberUpgradeActivity.this, img_user_header, userHeadUrl);
                    String memberLevelName = "";
                    if (userInfoDto.getMemberLevel() == MemberLevelType.level_1.getType()) {
                        memberLevelName = MemberLevelType.level_1.getValue();
                        img_user_level.setImageResource(R.mipmap.icon_member_level_1);
                    } else if (userInfoDto.getMemberLevel() == MemberLevelType.level_2.getType()) {
                        memberLevelName = MemberLevelType.level_2.getValue();
                        img_user_level.setImageResource(R.mipmap.icon_member_level_2);
                    } else if (userInfoDto.getMemberLevel() == MemberLevelType.level_3.getType()) {
                        memberLevelName = MemberLevelType.level_3.getValue();
                        img_user_level.setImageResource(R.mipmap.icon_member_level_3);
                    } else if (userInfoDto.getMemberLevel() == MemberLevelType.level_4.getType()) {
                        memberLevelName = MemberLevelType.level_4.getValue();
                        img_user_level.setImageResource(R.mipmap.icon_member_level_4);
                    }
                    tv_user_level.setText(memberLevelName);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        });
    }

    private void confirm() {
        if (selLevel == -1) {
            ToastUtil.showToast("选择您想提升的会员等级");
            return;
        }
        if (TextUtils.isEmpty(ed_name.getText().toString())) {
            ToastUtil.showToast("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(ed_phone.getText().toString())) {
            ToastUtil.showToast("请输入联系电话");
            return;
        }
        if (TextUtils.isEmpty(ed_wx.getText().toString())) {
            ToastUtil.showToast("请输入微信号");
            return;
        }
        if (TextUtils.isEmpty(tv_address.getText().toString())) {
            ToastUtil.showToast("请所在区域");
            return;
        }
        if (TextUtils.isEmpty(ed_remark.getText().toString())) {
            ToastUtil.showToast("请输入备注");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("currentLevel", String.valueOf(currentLevel));
        map.put("upgradeLevel", String.valueOf(selLevel + 2));
        map.put("realName", ed_name.getText().toString());
        map.put("wxNO", ed_wx.getText().toString());
        map.put("remark", ed_remark.getText().toString());
        map.put("mobileNo", ed_phone.getText().toString());
        String address = tv_address.getText().toString();
        String areaArray[] = address.split(" ");
        if (areaArray != null && areaArray.length >= 3) {
            map.put("province", areaArray[0]);
            map.put("city", areaArray[1]);
            map.put("area", areaArray[2]);
        }
        showLoadDialog();
        DataManager.getInstance().memberLevelUpgrade(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                dissLoadDialog();
                if (httpResult != null) {
                    if (httpResult.getStatus() == 1) {
                        ToastUtil.showToast(httpResult.getMsg());
                        finish();
                    } else {
                        ToastUtil.showToast(httpResult.getMsg());
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.INTENT_REQUESTCODE_AREA && resultCode == Activity.RESULT_OK) {
            String allName = data.getStringExtra("areaName");
//            areaId = data.getStringExtra("areaId");
            tv_address.setText(allName);
        }
    }
}
