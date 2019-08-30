package com.ggkjg.view.mainfragment.shop;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.CallPhoneUtils;
import com.ggkjg.common.utils.CreatUdeskStyle;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.ScreenSizeUtil;
import com.ggkjg.common.utils.ShareSdkUtils;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.TimeUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.common.utils.WebViewUtil;
import com.ggkjg.dto.CommodityDetailAdsDto;
import com.ggkjg.dto.CommodityDetailBannerDto;
import com.ggkjg.dto.CommodityDetailDto;
import com.ggkjg.dto.CommodityDetailInfoDto;
import com.ggkjg.dto.FavoriteStateDto;
import com.ggkjg.dto.GoodsAttrDto;
import com.ggkjg.dto.GoodsColorAndSpecDto;
import com.ggkjg.dto.ServiceTelDto;
import com.ggkjg.dto.ShareGoodsDto;
import com.ggkjg.dto.ShopEvaluateDto;
import com.ggkjg.dto.ShopEvaluateImgListDto;
import com.ggkjg.dto.ShopEvaluateRowsDto;
import com.ggkjg.dto.SlidersDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.MainActivity;
import com.ggkjg.view.adapter.LoopViewPagerAdapter;
import com.ggkjg.view.mainfragment.ImagePreviewActivity;
import com.ggkjg.view.mainfragment.login.LoginActivity;
import com.ggkjg.view.widgets.DeliverDetailDialog;
import com.ggkjg.view.widgets.ShareProductDialog;
import com.ggkjg.view.widgets.ShopProductTypeDialog;
import com.ggkjg.view.widgets.ShopSecurityDetailDialog;
import com.ggkjg.view.widgets.autoview.ActionbarView;
import com.shehuan.niv.NiceImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;
import cn.udesk.model.UdeskCommodityItem;
import udesk.core.UdeskConst;

/**
 * 商品详细
 * Created by dahai on 2019/01/20.
 */

public class CommodityDetailActivity extends BaseActivity implements BaseActivity.PermissionsListener {
    private static final String TAG = CommodityDetailActivity.class.getSimpleName();
    public static final String PRODUCT_ID = "product_id";//调用者传递的名字
    @BindView(R.id.custom_action_bar)
    ActionbarView customActionBar;
    @BindView(R.id.rl_banner_vp_container)
    RelativeLayout rlBannerVpContainer;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.tv_tobuy)
    TextView tvTobuy;
    @BindView(R.id.tv_first_price)
    TextView tvFirstPrice;
    @BindView(R.id.tv_integer)
    TextView tvInteger;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minter)
    TextView tvMinter;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.tv_voucher_cout)
    TextView tv_voucher_cout;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
    @BindView(R.id.tv_commodity_info_type_v)
    TextView tvCommodityInfoTypeV;
    @BindView(R.id.tv_commodity_info_security_v)
    TextView tvCommodityInfoSecurityV;
    @BindView(R.id.tv_commodity_info_delivery_v)
    TextView tvCommodityInfoDeliveryV;
    @BindView(R.id.rl_commodity_comment)
    RelativeLayout rl_commodity_comments;
    @BindView(R.id.rl_ads_vp_container)
    RelativeLayout rlAdsVpContainer;
    @BindView(R.id.header_commodity_detail_bot_layout)
    LinearLayout headerCommodityDetailBotLayout;
    private long product_id;//产品ID

    @BindView(R.id.tv_commodity_info_title)
    TextView tv_commodity_info_title;
    @BindView(R.id.tv_commodity_info_price)
    TextView tv_commodity_info_price;

    @BindView(R.id.tv_commodity_info_courier)
    TextView tv_commodity_info_courier;
    @BindView(R.id.tv_commodity_info_sales)
    TextView tv_commodity_info_sales;
    @BindView(R.id.tv_commodity_info_inventory)
    TextView tv_commodity_info_inventory;

    @BindView(R.id.tv_commodity_info_type)
    TextView tv_commodity_info_type;

    @BindView(R.id.rl_commodity_info_select_v)
    RelativeLayout rl_commodity_info_select_v;
    @BindView(R.id.rl_commodity_info_security_v)
    RelativeLayout rl_commodity_info_security_v;
    @BindView(R.id.rl_commodity_info_deliver_v)
    RelativeLayout rl_commodity_info_deliver_v;

    @BindView(R.id.webView_commodity)
    WebView webView;

    @BindView(R.id.commodity_detail_banner_vp_container)
    ViewPager banner_vp_container;
    @BindView(R.id.commodity_detail_banner_ll_indicators)
    LinearLayout banner_ll_indicators;

    @BindView(R.id.commodity_detail_ads_vp_container)
    ViewPager ads_vp_container;
    @BindView(R.id.commodity_detail_ads_ll_indicators)
    LinearLayout ads_ll_indicators;

    @BindView(R.id.tv_commodity_service)
    TextView tv_commodity_service;
    @BindView(R.id.tv_commodity_collection)
    TextView tv_commodity_collection;
    @BindView(R.id.tv_commodity_cart)
    TextView tv_commodity_cart;
    @BindView(R.id.tv_shop_cart_submit)
    TextView tv_shop_cart_submit;


    //    评论view

    @BindView(R.id.ll_commodity_comments)
    LinearLayout ll_commodity_comments;
    @BindView(R.id.tv_evaluate_null)
    TextView tv_evaluate_null;
    @BindView(R.id.ll_evaluate_view)
    LinearLayout ll_evaluate_view;
    @BindView(R.id.tv_commodity_comments_num)
    TextView tv_commodity_comments_num;
    @BindView(R.id.iv_me_user_icon)
    NiceImageView iv_me_user_icon;
    @BindView(R.id.tv_commodity_comments_name)
    TextView tv_commodity_comments_name;
    @BindView(R.id.tv_commodity_comments_msg)
    TextView tv_commodity_comments_msg;
    @BindView(R.id.ll_commodity_comments_imgs)
    LinearLayout ll_commodity_comments_imgs;
    @BindView(R.id.iv_commodity_comments_img1)
    NiceImageView iv_commodity_comments_img1;
    @BindView(R.id.iv_commodity_comments_img2)
    NiceImageView iv_commodity_comments_img2;
    @BindView(R.id.iv_commodity_comments_img3)
    NiceImageView iv_commodity_comments_img3;

    private String servicePhoneNum;
    private CommodityDetailInfoDto commodityDetailInfoDto;
    private boolean isFavorite = false;//是否收藏
    CommodityDetailDto mCommodityDetailDto;
    private String shareImgUrl;
    private String productImg;

    @Override
    public int getLayoutId() {
        return R.layout.ui_commodity_detail_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
    }

    @Override
    public void initData() {
        product_id = getIntent().getLongExtra(PRODUCT_ID, 0);
        findGoodsDetail(product_id);
        findGoodsAttr(product_id);
        findGoodsQrCode(product_id);
        findEvaState(product_id);
        findServiceTel();
        //findAllEvaList(1, 10, product_id, 1);
    }
   private ShareProductDialog dialog;
    @Override
    public void initListener() {
        actionbar.setImageAction(R.mipmap.share_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.getInstance().isLogin()) {
                    dialog = new ShareProductDialog(CommodityDetailActivity.this, productImg, shareImgUrl);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();

                    findShareParam();
                } else {
                    gotoActivity(LoginActivity.class);
                }
            }
        });
        bindClickEvent(tv_commodity_service, () -> {
            String shiroToken = Constants.getInstance().getString(Constants.USER_SHIRO_TOKEN, "");

            if(TextUtil.isNotEmpty(shiroToken)){
                CommodityDetailInfoDto goodsInfo = mCommodityDetailDto.getGoodsInfo();
                //创建咨询对象的实例
                UdeskCommodityItem item = new UdeskCommodityItem();
                // 咨询对象主标题
                item.setTitle(goodsInfo.getGoodsName());
                //咨询对象描述
                item.setSubTitle(goodsInfo.getGdPrice());
                //左侧图片
                item.setThumbHttpUrl(url);
                // 咨询对象网络链接
                item.setCommodityUrl("https://detail.tmall.com/item.htm?spm=a1z10.3746-b.w4946-14396547293.1.4PUcgZ&id=529634221064&sku_properties=-1:-1");



                UdeskSDKManager.getInstance().entryChat(CommodityDetailActivity.this, CreatUdeskStyle.makeBuilder(CommodityDetailActivity.this,item).build(), shiroToken);
            }
//            if (!isPermissioin(CALL_PHONE)) {
//                checkPermissioin(CALL_PHONE);
//                setPermissionsListener(this::callbackPermissions);
//            } else {
//                if (!TextUtils.isEmpty(servicePhoneNum)) {
//                    CallPhoneUtils.diallPhone(this, servicePhoneNum);
//                } else {
//                    ToastUtil.showToast("未知客户电话");
//                }
//            }
        });
        bindClickEvent(tv_commodity_collection, () -> {
            if (Constants.getInstance().isLogin()) {
                addFavorite(getCommodityDetailInfoDto().getId(), isFavorite ? 2 : 1);
            } else {
                gotoActivity(LoginActivity.class);
            }
        });
        bindClickEvent(tv_commodity_cart, () -> {


            if (Constants.getInstance().isLogin()) {
                finishAll();
                Bundle bundle = new Bundle();
                bundle.putInt(MainActivity.PAGE_INDEX, 2);
                gotoActivity(MainActivity.class, true, bundle);
            } else {
                gotoActivity(LoginActivity.class);
            }
        });
        bindClickEvent(rl_commodity_comments, () -> {
            Bundle bundle = new Bundle();
            bundle.putLong(ShopEvaluateActivity.PRODUCT_ID, product_id);
            gotoActivity(ShopEvaluateActivity.class, false, bundle);
        });
        bindClickEvent(ll_commodity_comments, () -> {
            Bundle bundle = new Bundle();
            bundle.putLong(ShopEvaluateActivity.PRODUCT_ID, product_id);
            gotoActivity(ShopEvaluateActivity.class, false, bundle);
        });
        bindClickEvent(tv_shop_cart_submit, () -> {
            if (Constants.getInstance().isLogin()) {
                ShopProductTypeDialog dialog = new ShopProductTypeDialog(this, getCommodityDetailInfoDto(), new ShopProductTypeDialog.ShopProductTypeListener() {
                    @Override
                    public void callbackSelect(long specId, int cartNum) {
                        findGoodsDetail(getCommodityDetailInfoDto().getId(), specId, cartNum);
                    }
                }, true);
                dialog.show();
            } else {
                gotoActivity(LoginActivity.class);
            }
        });
        bindClickEvent(rl_commodity_info_select_v, () -> {
            ShopProductTypeDialog dialog = new ShopProductTypeDialog(this, getCommodityDetailInfoDto(), new ShopProductTypeDialog.ShopProductTypeListener() {

                @Override
                public void callbackSelect(long specId, int cartNum) {
                    //String str = cartNum + "件";
                    //tv_commodity_info_type.setText(str);
                    findGoodsDetail(getCommodityDetailInfoDto().getId(), specId, cartNum);
                }
            }, true);
            dialog.show();
        });
        bindClickEvent(rl_commodity_info_deliver_v, () -> {
            DeliverDetailDialog dialog = new DeliverDetailDialog(this);
            dialog.show();
        });
        bindClickEvent(rl_commodity_info_security_v, () -> {
            ShopSecurityDetailDialog dialog = new ShopSecurityDetailDialog(this);
            dialog.show();
        });

    }

    public CommodityDetailInfoDto getCommodityDetailInfoDto() {
        return commodityDetailInfoDto;
    }

    public void setCommodityDetailInfoDto(CommodityDetailInfoDto commodityDetailInfoDto) {
        this.commodityDetailInfoDto = commodityDetailInfoDto;
    }

    private void dealImagePreview(int position, List<ShopEvaluateImgListDto> imageList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePreviewActivity.INTENT_KEY_DATA, (Serializable) imageList);
        bundle.putInt(ImagePreviewActivity.INTENT_KEY_START_POSITION, position);
        gotoActivity(ImagePreviewActivity.class, false, bundle);
    }

    /**
     * 获取商品详情
     *
     * @param goodsId 商品id
     */
    private void findGoodsDetail(long goodsId) {
        showLoadDialog();
        DataManager.getInstance().findGoodsDetail(new DefaultSingleObserver<CommodityDetailDto>() {
            @Override
            public void onSuccess(CommodityDetailDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if (object == null) {
                    ToastUtil.toast("未知错误");
                    dissLoadDialog();
                } else {
                    mCommodityDetailDto = object;
                    notifyData(object);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError()");
                dissLoadDialog();
            }
        }, goodsId);
    }

    /**
     * 获取商品二维码路径
     *
     * @param goodsId 商品id
     */
    private void findGoodsQrCode(long goodsId) {
        showLoadDialog();
        DataManager.getInstance().findGoodsQrCode(new DefaultSingleObserver<String>() {
            @Override
            public void onSuccess(String object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() object=" + object);
                shareImgUrl = BuildConfig.BASE_URL + object;
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError()");
                dissLoadDialog();
            }
        }, goodsId);
    }


    /**
     * 获取客服电话
     */
    private void findServiceTel() {
        showLoadDialog();
        DataManager.getInstance().findServiceTel(new DefaultSingleObserver<ServiceTelDto>() {
            @Override
            public void onSuccess(ServiceTelDto object) {
                dissLoadDialog();
                if (!TextUtils.isEmpty(object.getServiceTel())) {
                    servicePhoneNum = object.getServiceTel();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        });
    }

    /**
     * 添加/取消收藏
     *
     * @param goodsId 商品ID
     * @param opType  1-收藏 2取消收藏
     */

    private void addFavorite(long goodsId, int opType) {
        showLoadDialog();
        DataManager.getInstance().addFavorite(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                // opType:	 1-收藏 2取消收藏
                if (!isFavorite) {
                    Drawable drawable = getResources().getDrawable(R.mipmap.icon_commodity_collection_);
                    drawable.setBounds(0, 0, 42, 37);
                    tv_commodity_collection.setCompoundDrawables(null, drawable, null, null);
                    tv_commodity_collection.setTextColor(getResources().getColor(R.color.my_color_00AAED));
                    tv_commodity_collection.setText("取消收藏");
                } else {
                    Drawable drawable = getResources().getDrawable(R.mipmap.icon_commodity_collection);
                    drawable.setBounds(0, 0, 42, 37);
                    tv_commodity_collection.setCompoundDrawables(null, drawable, null, null);
                    tv_commodity_collection.setTextColor(getResources().getColor(R.color.my_color_696969));
                    tv_commodity_collection.setText("收藏");
                }
                isFavorite = !isFavorite;
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, goodsId, opType);
    }

    /**
     * 获取商品收藏状态
     *
     * @param goodsId 商品ID
     */

    private void findEvaState(long goodsId) {
        DataManager.getInstance().findEvaState(new DefaultSingleObserver<FavoriteStateDto>() {
            @Override
            public void onSuccess(FavoriteStateDto httpResult) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()" + httpResult);
                if (null != httpResult) {
                    // 	 1-收藏 2取消收藏
                    if (null != httpResult.getIsFavorite() && httpResult.getIsFavorite().equals("1")) {
                        isFavorite = true;
                        Drawable drawable = getResources().getDrawable(R.mipmap.icon_commodity_collection_);
                        drawable.setBounds(0, 0, 42, 37);
                        tv_commodity_collection.setCompoundDrawables(null, drawable, null, null);
                        tv_commodity_collection.setTextColor(getResources().getColor(R.color.my_color_00AAED));
                        tv_commodity_collection.setText("取消收藏");
                    }
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, goodsId);
    }

    /**
     * 添加购物车
     *
     * @param goodsId 商品ID
     * @param specId  规格ID
     * @param cartNum 数量
     */
    private void findGoodsDetail(long goodsId, long specId, int cartNum) {
        showLoadDialog();
        DataManager.getInstance().addShoppingCart(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                ToastUtil.toast(object.getMsg());
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, goodsId, specId, cartNum);
    }

    /**
     * 获取商品评价列表
     *
     * @param currentPage 页数
     * @param rows        行数
     * @param goodsId     商品ID
     * @param type        类型: type=1 有图 type=2好评 type=3 差评
     */
    private void findAllEvaList(int currentPage, int rows, long goodsId, int type) {
        dissLoadDialog();
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", currentPage);
        map.put("rows", rows);
        map.put("goodsId", goodsId);
        map.put("type", type + "");
        DataManager.getInstance().findAllEvaList(new DefaultSingleObserver<ShopEvaluateDto>() {
            @Override
            public void onSuccess(ShopEvaluateDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if (object.getEvaList().getRows() != null && object.getEvaList().getRows() != null && object.getEvaList().getTotal() > 0) {
                    tv_evaluate_null.setVisibility(View.GONE);
                    ll_evaluate_view.setVisibility(View.VISIBLE);
                    tv_commodity_comments_num.setText(String.format("评论(%s)", object.getEvaList().getTotal() + ""));
                    ShopEvaluateRowsDto item = object.getEvaList().getRows().get(0);
                    GlideUtils.getInstances().loadRoundImg(CommodityDetailActivity.this, iv_me_user_icon, BuildConfig.BASE_URL + item.getHeadImg());
                    tv_commodity_comments_name.setText(item.getNickName());
                    tv_commodity_comments_msg.setText(item.getContent());
                    if (null != item.getImgList() && !item.getImgList().isEmpty()) {
                        ll_commodity_comments_imgs.setVisibility(View.VISIBLE);
                        switch (item.getImgList().size() - 1) {
                            case 0:
                                iv_commodity_comments_img1.setVisibility(View.VISIBLE);
                                iv_commodity_comments_img2.setVisibility(View.INVISIBLE);
                                iv_commodity_comments_img3.setVisibility(View.INVISIBLE);
                                GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img1,
                                        BuildConfig.BASE_IMAGE_URL + item.getImgList().get(0).getImgPath(), R.mipmap.img_default_1);
                                break;
                            case 1:
                                iv_commodity_comments_img1.setVisibility(View.VISIBLE);
                                iv_commodity_comments_img2.setVisibility(View.VISIBLE);
                                iv_commodity_comments_img3.setVisibility(View.INVISIBLE);
                                GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img1,
                                        BuildConfig.BASE_IMAGE_URL + item.getImgList().get(0).getImgPath(), R.mipmap.img_default_1);
                                GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img2,
                                        BuildConfig.BASE_IMAGE_URL + item.getImgList().get(1).getImgPath(), R.mipmap.img_default_1);
                                break;
                            case 2:
                                iv_commodity_comments_img1.setVisibility(View.VISIBLE);
                                iv_commodity_comments_img2.setVisibility(View.VISIBLE);
                                iv_commodity_comments_img3.setVisibility(View.VISIBLE);
                                GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img1,
                                        BuildConfig.BASE_IMAGE_URL + item.getImgList().get(0).getImgPath(), R.mipmap.img_default_1);
                                GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img2,
                                        BuildConfig.BASE_IMAGE_URL + item.getImgList().get(1).getImgPath(), R.mipmap.img_default_1);
                                GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img3,
                                        BuildConfig.BASE_IMAGE_URL + item.getImgList().get(2).getImgPath(), R.mipmap.img_default_1);
                                break;
                        }
                        bindClickEvent(iv_commodity_comments_img1, () -> {
                            dealImagePreview(0, item.getImgList());
                        });
                        bindClickEvent(iv_commodity_comments_img2, () -> {
                            dealImagePreview(1, item.getImgList());
                        });
                        bindClickEvent(iv_commodity_comments_img3, () -> {
                            dealImagePreview(2, item.getImgList());
                        });
                    } else {
                        ll_commodity_comments_imgs.setVisibility(View.GONE);
                        iv_commodity_comments_img1.setVisibility(View.GONE);
                        iv_commodity_comments_img2.setVisibility(View.GONE);
                        iv_commodity_comments_img3.setVisibility(View.GONE);
                    }
                } else {
                    tv_evaluate_null.setVisibility(View.VISIBLE);
                    ll_evaluate_view.setVisibility(View.GONE);
                    ll_commodity_comments_imgs.setVisibility(View.GONE);
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, map);
    }


    @Override
    public void callbackPermissions(String permissions, boolean isSuccess) {
        if (permissions.equals(CALL_PHONE) && isSuccess) {
            if (!TextUtils.isEmpty(servicePhoneNum)) {
                CallPhoneUtils.diallPhone(this, servicePhoneNum);
            } else {
                ToastUtil.showToast("未知客户电话");
            }
        } else {
            ToastUtil.showToast("请在设置中打开拨打电话权限.");
        }
    }

    /**
     * 刷新商品详细数据
     *
     * @param commodityDetailDto
     */
    private CountDownTimer countDownTimer;
    private void notifyData(CommodityDetailDto commodityDetailDto) {
        long times=0;
        if(commodityDetailDto.isGoodsSedKill){
            llMoney.setVisibility(View.GONE);
            llMore.setVisibility(View.VISIBLE);
            if(commodityDetailDto.getGoodsInfo()!=null){
                long curTime=System.currentTimeMillis();
                long starTime=0;
                long endTime=0;
                if(TextUtil.isNotEmpty(commodityDetailDto.getGoodsInfo().startTime)){
                     starTime=TimeUtil.getStringToDate(commodityDetailDto.getGoodsInfo().startTime);
                }
                if(TextUtil.isNotEmpty(commodityDetailDto.getGoodsInfo().startTime)){
                     endTime=TimeUtil.getStringToDate(commodityDetailDto.getGoodsInfo().endTime);
                }
                if(commodityDetailDto.sedKillTime!=null&&commodityDetailDto.sedKillTime.startTime!=null){
                    starTime=TimeUtil.getStringToDate(commodityDetailDto.sedKillTime.startTime);

                }
                if(commodityDetailDto.sedKillTime!=null&&commodityDetailDto.sedKillTime.endTime!=null){
                    endTime=TimeUtil.getStringToDate(commodityDetailDto.sedKillTime.endTime);
                }

                  if(starTime==0){
                      if(TextUtil.isNotEmpty(commodityDetailDto.getGoodsInfo().activePrice)){
                          tvPrice.setText("￥"+commodityDetailDto.getGoodsInfo().activePrice);
                      }else {
                          tvPrice.setText("￥"+commodityDetailDto.getGoodsInfo().getGdPrice());
                      }
                      if(TextUtil.isNotEmpty(commodityDetailDto.getGoodsInfo().getMarketPrice())){
                          tvFirstPrice.setText(commodityDetailDto.getGoodsInfo().getMarketPrice());
                      }


                  }
                if(starTime>curTime){
                    times=starTime-curTime;
                    tvTobuy.setText("秒杀预告");
                    tvInteger.setText("距开始时间");
                    line.setVisibility(View.GONE);

                    tvTobuy.setTextColor(getResources().getColor(R.color.my_color_green52e));
                    tvHour.setTextColor(getResources().getColor(R.color.my_color_green52e));
                    tvMinter.setTextColor(getResources().getColor(R.color.my_color_green52e));
                    tvSecond.setTextColor(getResources().getColor(R.color.my_color_green52e));
                    tvPrice.setText(commodityDetailDto.getGoodsInfo().getMarketPrice());
                    if(TextUtil.isNotEmpty(commodityDetailDto.getGoodsInfo().activePrice)){
                        tvFirstPrice.setText("秒杀价: ￥"+commodityDetailDto.getGoodsInfo().activePrice);
                    }else {
                        tvFirstPrice.setText("秒杀价: ￥"+commodityDetailDto.getGoodsInfo().getGdPrice());
                    }
                    llMore.setBackgroundColor(getResources().getColor(R.color.my_color_green));

                }else {


                    times=endTime-curTime;
                    tvTobuy.setText("港港秒杀");
                    tvInteger.setText("距结束时间");
                    line.setVisibility(View.VISIBLE);
                    tvFirstPrice.setText(commodityDetailDto.getGoodsInfo().getMarketPrice());
                    tvTobuy.setTextColor(getResources().getColor(R.color.my_color_f23));
                    tvHour.setTextColor(getResources().getColor(R.color.my_color_f23));
                    tvMinter.setTextColor(getResources().getColor(R.color.my_color_f23));
                    tvSecond.setTextColor(getResources().getColor(R.color.my_color_f23));
                    if(TextUtil.isNotEmpty(commodityDetailDto.getGoodsInfo().activePrice)){
                        tvPrice.setText("￥"+commodityDetailDto.getGoodsInfo().activePrice);
                    }else {
                        tvPrice.setText("￥"+commodityDetailDto.getGoodsInfo().getGdPrice());
                    }
                    llMore.setBackgroundColor(getResources().getColor(R.color.my_color_f23));

                }
            }
        }else {
            llMoney.setVisibility(View.VISIBLE);
            llMore.setVisibility(View.GONE);
            if(TextUtil.isNotEmpty(commodityDetailDto.getGoodsInfo().isConpon)&&commodityDetailDto.getGoodsInfo().isConpon.equals("1")){
                tv_voucher_cout.setVisibility(View.VISIBLE);
                tv_voucher_cout.setText("用券立减" +commodityDetailDto.getGoodsInfo().conponPrice);
            }else {
                tv_voucher_cout.setVisibility(View.VISIBLE);
            }

        }
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(times, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeFormat = TimeUtil.getTimeFormat(millisUntilFinished/1000);
                String[] split = timeFormat.split(",");
                if(tvHour!=null){
                    tvHour.setText(split[1]);
                    tvMinter.setText(split[2]);
                    tvSecond.setText(split[3]);
                }

            }
            @Override
            public void onFinish() {

                if(tvHour!=null){
                    tvHour.setText("00");
                    tvMinter.setText("00");
                    tvSecond.setText("00");
                }

            }
        }.start();
        if(TextUtil.isNotEmpty(commodityDetailDto.getGoodsInfo().getMarketPrice())){
            String name = commodityDetailDto.getGoodsInfo().getMarketPrice();
            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(12);
            int with = (int) textPaint.measureText(name);
            FrameLayout.LayoutParams linearParams =(FrameLayout.LayoutParams) line.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.width = ScreenSizeUtil.dp2px(with-4);// 控件的宽强制设成30

            line.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }


        if (commodityDetailDto.getBannerList() != null && !commodityDetailDto.getBannerList().isEmpty()) {
            onBannerSuccess(commodityDetailDto.getBannerList());
        }
        if (commodityDetailDto.getAdsList() != null && !commodityDetailDto.getAdsList().isEmpty()) {
            onAdsSuccess(commodityDetailDto.getAdsList());
        }
        CommodityDetailInfoDto goodsInfo = commodityDetailDto.getGoodsInfo();
        productImg = BuildConfig.BASE_IMAGE_URL + goodsInfo.getGoodsImg();
        setCommodityDetailInfoDto(goodsInfo);
        tv_commodity_info_title.setText(goodsInfo.getGoodsName());
        tv_commodity_info_price.setText(goodsInfo.getGdPrice());
        if (goodsInfo.getFreight() != null) {
            if (goodsInfo.getFreight().equals("0.00")) {
                tv_commodity_info_courier.setText("快递费:计重");
            } else {
                tv_commodity_info_courier.setText("快递费:" + goodsInfo.getFreight());
            }
        } else {
            tv_commodity_info_courier.setText("快递费:计重");
        }
        tv_commodity_info_sales.setText("销量:" + goodsInfo.getSalesToatl());
        tv_commodity_info_inventory.setText("库存:" + goodsInfo.getStockTotal());
        onEvaluationSuccess(commodityDetailDto);
        WebViewUtil.setWebView(webView, Objects.requireNonNull(this));
        WebViewUtil.loadHtml(webView, goodsInfo.getGoodsDetail());
        dissLoadDialog();
    }
    /**
     * 获取商品颜色，规格
     *
     * @param goodsId 商品id
     */
    private String content;
    private void findGoodsAttr(long goodsId) {
        DataManager.getInstance().findGoodsAttr(new DefaultSingleObserver<GoodsColorAndSpecDto>() {
            @Override
            public void onSuccess(GoodsColorAndSpecDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                content="";
                loadingDialog.cancelDialog();
                int i=0;
                if (null != object.getAttrMap() && !object.getAttrMap().isEmpty()) {
                  for(GoodsAttrDto dto:object.getAttrMap()){
                      if(i==0){
                          content=dto.getAttrList().get(0).getColorCode();
                      }else {
                          content=content+","+dto.getAttrList().get(0).getColorCode();
                      }
                      i++;
                  }
                }
                tv_commodity_info_type.setText(content);
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " + throwable.toString());
                loadingDialog.cancelDialog();
            }
        }, goodsId);
    }
    /**
     * 评论
     *
     * @param commodityDetailDto
     */
    public void onEvaluationSuccess(CommodityDetailDto commodityDetailDto) {
        if (commodityDetailDto != null) {
            ShopEvaluateRowsDto evaCon = commodityDetailDto.getEvaContent();
            if (evaCon != null) {
                tv_evaluate_null.setVisibility(View.GONE);
                ll_evaluate_view.setVisibility(View.VISIBLE);
                if (commodityDetailDto.getEvaTotal() != null) {
                    tv_commodity_comments_num.setText(String.format("评论(%s)", commodityDetailDto.getEvaTotal()));
                } else {
                    tv_commodity_comments_num.setText("评论(0)");
                }
                GlideUtils.getInstances().loadRoundImg(CommodityDetailActivity.this, iv_me_user_icon, BuildConfig.BASE_URL + evaCon.getHeadImg());
                tv_commodity_comments_name.setText(evaCon.getNickName());
                tv_commodity_comments_msg.setText(evaCon.getContent());
                if (null != evaCon.getImgList() && !evaCon.getImgList().isEmpty()) {
                    ll_commodity_comments_imgs.setVisibility(View.VISIBLE);
                    switch (evaCon.getImgList().size() - 1) {
                        case 0:
                            iv_commodity_comments_img1.setVisibility(View.VISIBLE);
                            iv_commodity_comments_img2.setVisibility(View.INVISIBLE);
                            iv_commodity_comments_img3.setVisibility(View.INVISIBLE);
                            GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img1,
                                    BuildConfig.BASE_URL + evaCon.getImgList().get(0).getImgPath(), R.mipmap.img_default_1);
                            break;
                        case 1:
                            iv_commodity_comments_img1.setVisibility(View.VISIBLE);
                            iv_commodity_comments_img2.setVisibility(View.VISIBLE);
                            iv_commodity_comments_img3.setVisibility(View.INVISIBLE);
                            GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img1,
                                    BuildConfig.BASE_URL + evaCon.getImgList().get(0).getImgPath(), R.mipmap.img_default_1);
                            GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img2,
                                    BuildConfig.BASE_URL + evaCon.getImgList().get(1).getImgPath(), R.mipmap.img_default_1);
                            break;
                        case 2:
                            iv_commodity_comments_img1.setVisibility(View.VISIBLE);
                            iv_commodity_comments_img2.setVisibility(View.VISIBLE);
                            iv_commodity_comments_img3.setVisibility(View.VISIBLE);
                            GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img1,
                                    BuildConfig.BASE_URL + evaCon.getImgList().get(0).getImgPath(), R.mipmap.img_default_1);
                            GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img2,
                                    BuildConfig.BASE_URL + evaCon.getImgList().get(1).getImgPath(), R.mipmap.img_default_1);
                            GlideUtils.getInstances().loadNormalImg(CommodityDetailActivity.this, iv_commodity_comments_img3,
                                    BuildConfig.BASE_URL + evaCon.getImgList().get(2).getImgPath(), R.mipmap.img_default_1);
                            break;
                    }
                    bindClickEvent(iv_commodity_comments_img1, () -> {
                        dealImagePreview(0, evaCon.getImgList());
                    });
                    bindClickEvent(iv_commodity_comments_img2, () -> {
                        dealImagePreview(1, evaCon.getImgList());
                    });
                    bindClickEvent(iv_commodity_comments_img3, () -> {
                        dealImagePreview(2, evaCon.getImgList());
                    });
                } else {
                    ll_commodity_comments_imgs.setVisibility(View.GONE);
                    iv_commodity_comments_img1.setVisibility(View.GONE);
                    iv_commodity_comments_img2.setVisibility(View.GONE);
                    iv_commodity_comments_img3.setVisibility(View.GONE);
                }
            } else {
                tv_evaluate_null.setVisibility(View.VISIBLE);
                ll_evaluate_view.setVisibility(View.GONE);
                ll_commodity_comments_imgs.setVisibility(View.GONE);
            }
        }
    }
     private String url;
    /**
     * 顶部海报
     *
     * @param bannerList
     */
    public void onBannerSuccess(List<CommodityDetailBannerDto> bannerList) {
        List<SlidersDto> slidersDtos = new ArrayList<>();
        int i=0;
        for (CommodityDetailBannerDto item : bannerList) {
            SlidersDto slidersDto = new SlidersDto();
            slidersDto.setId(item.getId());
            slidersDto.setPositionId(item.getResId());
            slidersDto.setType(item.getType());
            slidersDto.setImgUrl(item.getImgPath());
            slidersDto.setSort(item.getSort());
            slidersDto.setCreateTime(item.getCreateTime());
            slidersDto.setModifyTime(item.getModifyTime());
            slidersDto.setEnableFlag(item.getEnableFlag());
            slidersDtos.add(slidersDto);
            if(i==0){
                url=BuildConfig.BASE_IMAGE_URL + item.getImgPath();
            }
            i++;
        }
        LoopViewPagerAdapter loopViewPagerAdapter = new LoopViewPagerAdapter(this, banner_vp_container, banner_ll_indicators);
        banner_vp_container.setAdapter(loopViewPagerAdapter);
        loopViewPagerAdapter.setList(slidersDtos);
        banner_vp_container.addOnPageChangeListener(loopViewPagerAdapter);
    }

    /**
     * 中间广告位图片
     *
     * @param adsList
     */
    public void onAdsSuccess(List<CommodityDetailAdsDto> adsList) {
        List<SlidersDto> slidersDtos = new ArrayList<>();
        for (CommodityDetailAdsDto item : adsList) {
            SlidersDto slidersDto = new SlidersDto();
            slidersDto.setId(item.getId());
            slidersDto.setPositionId(item.getPositionId());
            slidersDto.setImgUrl(item.getImgUrl());
            slidersDto.setSort(item.getSort());
            slidersDto.setCreateTime(item.getCreateTime());
            slidersDto.setModifyTime(item.getModifyTime());
            slidersDto.setEnableFlag(item.getEnableFlag());
            slidersDto.setClickUrl(item.getClickUrl());
            slidersDto.setClickType(item.getClickType());
            slidersDtos.add(slidersDto);
        }
        LoopViewPagerAdapter loopViewPagerAdapter = new LoopViewPagerAdapter(this, ads_vp_container, ads_ll_indicators);
        ads_vp_container.setAdapter(loopViewPagerAdapter);
        loopViewPagerAdapter.setList(slidersDtos);
        ads_vp_container.addOnPageChangeListener(loopViewPagerAdapter);
    }

    private void findShareParam() {
        DataManager.getInstance().findShareParam(new DefaultSingleObserver<ShareGoodsDto>() {
            @Override
            public void onSuccess(ShareGoodsDto shareGoodsDto) {
                if (shareGoodsDto != null && mCommodityDetailDto != null)

                    ShareSdkUtils.getInstances().showShareDialog(CommodityDetailActivity.this, mCommodityDetailDto.getGoodsInfo().getGoodsName(), "", "", shareGoodsDto.getShareUrl(), new ShareSdkUtils.onCancelDissListener() {
                        @Override
                        public void cancels() {
                            if(dialog!=null){
                                dialog.dismiss();
                            }
                        }
                    });



            }
        }, product_id + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dissLoadDialog();
        WebViewUtil.destroyWebView(webView);
    }


}
