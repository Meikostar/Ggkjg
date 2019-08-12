package com.ggkjg.view.mainfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.type.MemberLevelType;
import com.ggkjg.common.utils.CallPhoneUtils;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.AdsDataDto;
import com.ggkjg.dto.AdsDto;
import com.ggkjg.dto.RealNameDto;
import com.ggkjg.dto.ServiceTelDto;
import com.ggkjg.dto.UserInfoDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.mainfragment.order.MyOrderActivity;
import com.ggkjg.view.mainfragment.personalcenter.GoodsAddressActivity;
import com.ggkjg.view.mainfragment.personalcenter.HKDTActivity;
import com.ggkjg.view.mainfragment.personalcenter.MYCCActivity;
import com.ggkjg.view.mainfragment.personalcenter.MemberUpgradeActivity;
import com.ggkjg.view.mainfragment.personalcenter.MessageCenterActivity;
import com.ggkjg.view.mainfragment.personalcenter.MyCollectionActivity;
import com.ggkjg.view.mainfragment.personalcenter.MyCommentActivity;
import com.ggkjg.view.mainfragment.personalcenter.RecommendActivity;
import com.ggkjg.view.mainfragment.personalcenter.RewardActivity;
import com.ggkjg.view.mainfragment.personalcenter.ShareQRcodeActivity;
import com.ggkjg.view.mainfragment.personalcenter.UserValidationActivity;
import com.ggkjg.view.mainfragment.personalcenter.wallet.WalletActivity;
import com.ggkjg.view.mainfragment.settings.UserInfoSetActivity;
import com.ggkjg.view.mainfragment.spike.GgBusinessActivity;
import com.ggkjg.view.mainfragment.spike.MineVoucherActivity;
import com.ggkjg.view.widgets.QRcodeDialog;
import com.ggkjg.view.widgets.autoview.ObservableScrollView;

import java.util.List;

import butterknife.BindView;

/**
 * 我的
 * Created by dahai on 2019/01/18.
 */

public class MeFragment extends BaseFragment {
    private static final String TAG = MeFragment.class.getSimpleName();
    @BindView(R.id.iv_scroll_view_top_layout)
    ImageView headImageView;
    @BindView(R.id.btn_message)
    ImageView btn_message;
    @BindView(R.id.rl_me_scroll_view)
    ObservableScrollView scrollView;
    private int layoutHeadHeight = 0;
    @BindView(R.id.iv_me_item_service)
    ImageView item_service;
    @BindView(R.id.rl_me_item_vip_level)
    RelativeLayout item_vip_level;//会员等级申请
    @BindView(R.id.rl_me_item_share_code)
    RelativeLayout item_share_code;//分享二维码
    @BindView(R.id.btn_setting)
    ImageView btn_setting;
    @BindView(R.id.btn_recommend_qr)
    ImageView btn_recommend_qr;
    @BindView(R.id.rl_me_item_wallet)
    RelativeLayout rl_me_item_wallet;
    @BindView(R.id.rl_me_item_mycc)
    RelativeLayout rl_me_item_mycc;
    @BindView(R.id.rl_voucher)
    RelativeLayout rl_voucher;
    @BindView(R.id.rl_business)
    RelativeLayout rl_business;

    @BindView(R.id.rl_me_item_reward)
    RelativeLayout rl_me_item_reward;
    @BindView(R.id.rl_me_item_group)
    RelativeLayout rl_me_item_group;
    @BindView(R.id.rl_me_item_address)
    RelativeLayout rl_me_item_address;
    @BindView(R.id.rl_me_item_comments)
    RelativeLayout rl_me_item_comments;
    @BindView(R.id.rl_me_item_collection)
    RelativeLayout rl_me_item_collection;
    @BindView(R.id.rl_me_item_certification)
    RelativeLayout rl_me_item_certification;
    @BindView(R.id.iv_me_all_order)
    TextView iv_me_all_order;
    @BindView(R.id.tv_me_order1)
    TextView tv_me_order1;
    @BindView(R.id.tv_me_order2)
    TextView tv_me_order2;
    @BindView(R.id.tv_me_order3)
    TextView tv_me_order3;
    @BindView(R.id.tv_me_order4)
    TextView tv_me_order4;
    //    @BindView(R.id.tv_me_order5)
//    TextView tv_me_order5; //退款/售后
    @BindView(R.id.iv_item_gold_time)
    TextView userNameTv;
    @BindView(R.id.iv_me_user_icon)
    ImageView userIcon;
    @BindView(R.id.tv_member_level)
    TextView tv_member_level;
    @BindView(R.id.img_member_level)
    ImageView img_member_level;
    @BindView(R.id.img_me_ads)
    ImageView img_me_ads;
    String referralQrcode;
    boolean isHidden;
    UserInfoDto mUserInfoDto;
    private String servicePhoneNum;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_layout;
    }

    @Override
    protected void initView() {
        headImageView.setBackgroundColor(Color.argb(0, 0x00, 0xAA, 0xED));
        headImageView.bringToFront();
    }

    @Override
    protected void initData() {
        findServiceTel();
    }

    @Override
    protected void initListener() {
        bindClickEvent(item_service, () -> {
            if (!TextUtils.isEmpty(servicePhoneNum)) {
                CallPhoneUtils.diallPhone(getActivity(), servicePhoneNum);
            } else {
                ToastUtil.showToast("未知客户电话");
            }
        });
        bindClickEvent(btn_setting, () -> {
            gotoActivity(UserInfoSetActivity.class);
        });
        bindClickEvent(rl_me_item_wallet, () -> {
            gotoActivity(WalletActivity.class);
        });
        bindClickEvent(rl_me_item_mycc, () -> {
            gotoActivity(MYCCActivity.class);
        });
        bindClickEvent(rl_voucher, () -> {
            gotoActivity(MineVoucherActivity.class);
        });
        bindClickEvent(rl_business, () -> {
            gotoActivity(GgBusinessActivity.class);
        });

//        bindClickEvent(rl_voucher, () -> {
//            gotoActivity(HKDTActivity.class);
//        });
        bindClickEvent(rl_me_item_reward, () -> {
            gotoActivity(RewardActivity.class);
        });
        bindClickEvent(rl_me_item_certification, () -> {
            findRealState();
        });
        bindClickEvent(rl_me_item_group, () -> {
            gotoActivity(RecommendActivity.class);
        });
        bindClickEvent(rl_me_item_address, () -> {
            gotoActivity(GoodsAddressActivity.class);
        });
        bindClickEvent(rl_me_item_comments, () -> {
            gotoActivity(MyCommentActivity.class);
        });
        bindClickEvent(item_vip_level, () -> {
            gotoActivity(MemberUpgradeActivity.class);
        });
        bindClickEvent(item_share_code, () -> {
            gotoActivity(ShareQRcodeActivity.class);
        });
        bindClickEvent(btn_recommend_qr, () -> {
//            findMemberQrCode();
            gotoActivity(ShareQRcodeActivity.class);

        });
        bindClickEvent(btn_message, () -> {
            gotoActivity(MessageCenterActivity.class);
        });
        bindClickEvent(rl_me_item_collection, () -> {
            gotoActivity(MyCollectionActivity.class);
        });
        //获取顶部图片高度后，设置滚动监听
        layoutHeadHeight = 150;// headImageView.getHeight();
        scrollView.setOnScrollChangedListener(new ObservableScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int top, int oldTop, int l, int oldl) {
                if (oldTop <= 0) {//没有往下滑
                    headImageView.setVisibility(View.GONE);
                    headImageView.setBackgroundColor(Color.argb(0, 0, 0, 0));
                } else if (oldTop > 0 && oldTop < layoutHeadHeight) {
                    headImageView.setVisibility(View.VISIBLE);
                    float scale = (float) oldTop / layoutHeadHeight;
                    float alpha = (255 * scale);
                    headImageView.setBackgroundColor(Color.argb((int) alpha, 0x00, 0xAA, 0xED));
                }
            }
        });
        bindClickEvent(iv_me_all_order, () -> {
            startMyOrderActivity(0);
        });
        bindClickEvent(tv_me_order1, () -> {
            startMyOrderActivity(1);
        });
        bindClickEvent(tv_me_order2, () -> {
            startMyOrderActivity(2);
        });
        bindClickEvent(tv_me_order3, () -> {
            startMyOrderActivity(3);
        });
        bindClickEvent(tv_me_order4, () -> {
            startMyOrderActivity(4);
        });
//        bindClickEvent(tv_me_order5, () -> {
//            gotoActivity(AfterSaleActivity.class);
//        });
    }

    /**
     * 启动我的订单
     *
     * @param pageIndex 页面索引
     */
    private void startMyOrderActivity(int pageIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ORDER_TYPE, pageIndex);
        gotoActivity(MyOrderActivity.class, false, bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        isHidden = hidden;
        if (!hidden) {
            loadData();
            findCenterPosition();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden) {
            loadData();
            findCenterPosition();
        }
    }

    private void loadData() {
        DataManager.getInstance().getMemberBaseInfo(new DefaultSingleObserver<UserInfoDto>() {
            @Override
            public void onSuccess(UserInfoDto userInfoDto) {
                if (userInfoDto != null) {
                    mUserInfoDto = userInfoDto;
                    userNameTv.setText(userInfoDto.getNickName());
                    String userHeadUrl = BuildConfig.BASE_IMAGE_URL + userInfoDto.getHeadImg();
                    GlideUtils.getInstances().loadRoundImg(getContext(), userIcon, userHeadUrl);
                    String memberLevelName = "";
                    if (userInfoDto.getMemberLevel() == MemberLevelType.level_1.getType()) {
                        memberLevelName = MemberLevelType.level_1.getValue();
                        img_member_level.setImageResource(R.mipmap.icon_member_level_1);
                    } else if (userInfoDto.getMemberLevel() == MemberLevelType.level_2.getType()) {
                        memberLevelName = MemberLevelType.level_2.getValue();
                        img_member_level.setImageResource(R.mipmap.icon_member_level_2);
                    } else if (userInfoDto.getMemberLevel() == MemberLevelType.level_3.getType()) {
                        memberLevelName = MemberLevelType.level_3.getValue();
                        img_member_level.setImageResource(R.mipmap.icon_member_level_3);
                    } else if (userInfoDto.getMemberLevel() == MemberLevelType.level_4.getType()) {
                        memberLevelName = MemberLevelType.level_4.getValue();
                        img_member_level.setImageResource(R.mipmap.icon_member_level_4);
                    }
                    tv_member_level.setText(memberLevelName);
                    referralQrcode = mUserInfoDto.getReferralCode();
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }

    private void findCenterPosition() {
        DataManager.getInstance().findCenterPosition(new DefaultSingleObserver<List<AdsDataDto>>() {
            @Override
            public void onSuccess(List<AdsDataDto> adsDataDto) {

                if (adsDataDto != null && adsDataDto != null && adsDataDto.size() > 0) {
                    List<AdsDto> adsList = adsDataDto.get(0).getAdsList();
                    if (adsList != null && adsDataDto.size() > 0) {
                        AdsDto adsDto = adsList.get(0);
                        GlideUtils.getInstances().loadNormalImg(getContext(), img_me_ads, BuildConfig.BASE_IMAGE_URL + adsDto.getImgUrl(), R.mipmap.img_default_3);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.d("onError", throwable.toString());
            }
        });
    }

    private void findRealState() {
        showLoadDialog();
        DataManager.getInstance().findRealState(new DefaultSingleObserver<HttpResult<RealNameDto>>() {
            @Override
            public void onSuccess(HttpResult<RealNameDto> httpResult) {//auditState 审核状态 0-未上传 1未审核 2-已通过 3-未通过
                dissLoadDialog();
                if (httpResult != null && httpResult.getData() != null && ("1".equals(httpResult.getData().getAuditState()) || "2".equals(httpResult.getData().getAuditState()))) {
                    if ("1".equals(httpResult.getData().getAuditState())) {
                        ToastUtil.showToast("审核中");
                    } else {
                        ToastUtil.showToast("已通过");
                    }
                } else {
                    gotoActivity(UserValidationActivity.class);
                }

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        });
    }

    private void findMemberQrCode() {
        showLoadDialog();
        DataManager.getInstance().findMemberQrCode(new DefaultSingleObserver<String>() {
            @Override
            public void onSuccess(String imgUrl) {
                dissLoadDialog();
                if (!TextUtils.isEmpty(imgUrl)) {
                    QRcodeDialog dialog = new QRcodeDialog(getActivity(), imgUrl, mUserInfoDto);
                    dialog.show();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, referralQrcode);
    }

    /**
     * 获取客服电话
     */
    private void findServiceTel() {
        showLoadDialog();
        DataManager.getInstance().findServiceTel(new DefaultSingleObserver<ServiceTelDto>() {
            @Override
            public void onSuccess(ServiceTelDto object) {
                if (!TextUtils.isEmpty(object.getServiceTel())) {
                    servicePhoneNum = object.getServiceTel();
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        });
    }
}
