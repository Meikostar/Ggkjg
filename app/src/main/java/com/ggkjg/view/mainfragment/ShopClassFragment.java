package com.ggkjg.view.mainfragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.common.utils.UIUtil;
import com.ggkjg.dto.SlidersDto;
import com.ggkjg.dto.StoreCategoryDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.MainActivity;
import com.ggkjg.view.adapter.ClassifyOneAdapter;
import com.ggkjg.view.adapter.ClassifyTwoAdapter;
import com.ggkjg.view.mainfragment.login.LoginActivity;
import com.ggkjg.view.mainfragment.personalcenter.MessageCenterActivity;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.mainfragment.shop.SearchShopProduct;
import com.ggkjg.view.mainfragment.shop.ShopProductListActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Action;

/**
 * 商品分类
 * Created by dahai on 2019/01/18.
 */

public class ShopClassFragment extends BaseFragment {
    private static final String TAG = ShopClassFragment.class.getSimpleName();

    @BindView(R.id.rv_one)
    RecyclerView rvOne;
    @BindView(R.id.rv_two)
    RecyclerView rvTwo;
    Unbinder unbinder;
    @BindView(R.id.banner)
    Banner       banner;
    @BindView(R.id.iv_top_message)
    ImageView    iv_top_message;
    @BindView(R.id.iv_calss_top_sweep)
    ImageView    iv_top_qrCode;
    @BindView(R.id.top_search_view)
    LinearLayout top_search_view;

    private ClassifyOneAdapter mClassifyOneAdapter;
    private ClassifyTwoAdapter mClassifyTwoAdapter;
    private List<StoreCategoryDto> mListCategorys;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_class_layout;
    }

    @Override
    protected void initView() {
        mClassifyOneAdapter = new ClassifyOneAdapter();
        rvOne.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOne.setAdapter(mClassifyOneAdapter);
        rvOne.setSelected(true);
        rvOne.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.parseColor("#f1f1f1"))
                .size(UIUtil.dp2px(1))
                .build());
        mClassifyTwoAdapter = new ClassifyTwoAdapter(getActivity());
        rvTwo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTwo.setAdapter(mClassifyTwoAdapter);
    }

    @Override
    protected void initData() {
        findStoreCategory();
    }
    /**
     * 启动产品列表
     *
     * @param product_type 商品类型
     */
    private void startActivityProductList(int product_type) {
        Bundle bundle = new Bundle();
        bundle.putInt(ShopProductListActivity.PRODUCT_TYPE, product_type);

        gotoActivity(ShopProductListActivity.class, false, bundle);
    }
    /**
     * 启动商品详细
     *
     * @param product_id 商品ID
     */
    private void startActivityCommodityDetail(long product_id) {
        Bundle bundle = new Bundle();
        bundle.putLong(CommodityDetailActivity.PRODUCT_ID, product_id);
        gotoActivity(CommodityDetailActivity.class, false, bundle);
    }

    private List<StoreCategoryDto> datas;
    @Override
    protected void initListener() {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if(datas!=null&&datas.size()>0){
                    StoreCategoryDto slidersDto=datas.get(position);
                    if (!TextUtils.isEmpty(slidersDto.clickType)) {
                        switch(slidersDto.clickType){
                            case "1":
                                if(!TextUtils.isEmpty(slidersDto.clickUrl)) {
                                    String str=slidersDto.clickUrl;
                                    Pattern p = Pattern.compile("[0-9]*");
                                    Matcher m = p.matcher(str);
                                    if(!m.matches() ){
                                        String str2="";
                                        if(str != null && !"".equals(str)) {
                                            for (int i = 0; i < str.length(); i++) {
                                                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                                                    str2 += str.charAt(i);
                                                }
                                            }
                                        }
                                        str=str2;
                                    }
                                    startActivityProductList(Integer.valueOf(str));
//                                    Log.i("hahahah","返回来的数据是" + Integer.valueOf(slidersDto.clickUrl));
                                }
                                break;
                            case "2":
                                if(!TextUtils.isEmpty(slidersDto.clickUrl)) {
                                    startActivityCommodityDetail(Long.valueOf(slidersDto.clickUrl));
                                }
                                break;
                            default:
                                break;
                        }

                    }
                }

            }
        });
        rvOne.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                int prePos = mClassifyOneAdapter.selctedPos;  //之前的位置
                mClassifyOneAdapter.selctedPos = position; //之后选择的位置
                if (position != prePos) {//更新item的状态
                    mClassifyOneAdapter.notifyItemChanged(prePos);
                    mClassifyOneAdapter.notifyItemChanged(position);
                    mClassifyTwoAdapter.setNewData(mListCategorys.get(position).getCateList());
                    setAdData(mListCategorys.get(position).getImgUrl(), mListCategorys.get(position).getAdClickUrl());
                    if(mListCategorys.get(position).bannerList!=null){
                        datas=mListCategorys.get(position).bannerList;
                        startBanner(mListCategorys.get(position).bannerList);
                    }else {
                        startBanner(null);
                    }

                }
            }
        });

        bindClickEvent(top_search_view, () -> {
            gotoActivity(SearchShopProduct.class);
        });
        bindClickEvent(iv_top_qrCode, () -> {
            Intent intent = new Intent();
            intent.setAction(MainActivity.ACTION_SHOP_CLASS_CAPTURE_CODE);
            getActivity().sendBroadcast(intent);
        }, 2500);
        bindClickEvent(iv_top_message, () -> {
            if (!Constants.getInstance().isLogin()) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            } else {
                gotoActivity(MessageCenterActivity.class);
            }
//            gotoActivity(MessageCenterActivity.class);
        });
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            StoreCategoryDto slidersDto= (StoreCategoryDto) path;
            String  imgStr = slidersDto.getImgUrl();
            if(imgStr != null){
                if(imgStr.contains("http://")){
                    GlideUtils.getInstances().loadNormalImg(getActivity(), imageView, imgStr,R.mipmap.img_default_3);
                } else{
                    GlideUtils.getInstances().loadNormalImg(getActivity(), imageView, BuildConfig.BASE_IMAGE_URL + imgStr,R.mipmap.img_default_3);
                }
            }




        }
    }
    List<StoreCategoryDto>   dats=new ArrayList<>();
    private void startBanner(List<StoreCategoryDto> data) {
        if(data==null ||data.size()==0){
            dats.clear();
            StoreCategoryDto storeCategoryDto = new StoreCategoryDto();
            storeCategoryDto.setImgUrl("mmk");
            dats.add(storeCategoryDto);
            data=dats;
        }
        //设置banner样式(显示圆形指示器)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（指示器居右）
        banner.setIndicatorGravity(BannerConfig.CENTER);

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(data);
        //设置banner动画效果
        //        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
    /**
     * 请求获取商城分类(全部)
     */
    private void findStoreCategory() {
        showLoadDialog();
        DataManager.getInstance().findStoreCategory(new DefaultSingleObserver<List<StoreCategoryDto>>() {
            @Override
            public void onSuccess(List<StoreCategoryDto> data) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() = " + data);
                dissLoadDialog();
                mListCategorys = data;
                mClassifyOneAdapter.setNewData(data);
                if (mListCategorys.size() > 0) {
                    StoreCategoryDto firstData = mListCategorys.get(0);
                    setAdData(firstData.getImgUrl(), firstData.getAdClickUrl());

                    if(firstData.bannerList!=null){
                        datas=firstData.bannerList;
                        startBanner(firstData.bannerList);
                    }else {
                        startBanner(null);
                    }
                    mClassifyTwoAdapter.setNewData(firstData.getCateList());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " );
                dissLoadDialog();
            }
        });
    }


    private void setAdData(String imgUrl, String adClickUrl) {
//        GlideUtils.getInstances().loadNormalImg(getActivity(), mIvAd, BuildConfig.BASE_IMAGE_URL + imgUrl, R.mipmap.img_default_3);
//        bindClickEvent(mIvAd, new Action() {
//            @Override
//            public void run() throws Exception {
//                //TODO 实现 跳转 adClickUrl
//            }
//        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
