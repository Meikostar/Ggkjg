package com.ggkjg.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.dto.BusinessDto;
import com.ggkjg.dto.SlidersDto;
import com.ggkjg.view.adapter.baseadapter.BaseLoopPagerAdapter;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.mainfragment.shop.ShopProductListActivity;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Action;

/**
 * Created by Aspsine on 2017/9/7.
 */
public class LoopViewAdapter extends BaseLoopPagerAdapter {

    private final List<BusinessDto> bannerDtoList;

    private final ViewGroup mIndicators;
    private final Activity activity;

    private int mLastPosition;

    public LoopViewAdapter(Activity activity, ViewPager viewPager, ViewGroup indicators) {
        super(viewPager);
        mIndicators = indicators;
        bannerDtoList = new ArrayList<>();
        this.activity = activity;
    }

    public void setList(List<BusinessDto> heroes) {
        bannerDtoList.clear();
        bannerDtoList.addAll(heroes);
        notifyDataSetChanged();
    }

    /**
     * oh shit! An indicator view is badly needed!
     * this shit have no animation at all.
     */
    private void initIndicators() {
        if (mIndicators.getChildCount() != bannerDtoList.size() && bannerDtoList.size() > 1) {
            mIndicators.removeAllViews();
            Resources res = mIndicators.getResources();
            int size = res.getDimensionPixelOffset(R.dimen.indicator_size);
            int margin = res.getDimensionPixelOffset(R.dimen.indicator_margin);
            for (int i = 0; i < getPagerCount(); i++) {
                ImageView indicator = new ImageView(mIndicators.getContext());
                indicator.setAlpha(180);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
                lp.setMargins(margin, 0, 0, 0);
                lp.gravity = Gravity.CENTER;
                indicator.setLayoutParams(lp);
                Drawable drawable = res.getDrawable(R.drawable.selector_indicator);
                indicator.setImageDrawable(drawable);
                mIndicators.addView(indicator);
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        initIndicators();
        super.notifyDataSetChanged();
    }

    @Override
    public int getPagerCount() {
        if (bannerDtoList != null) {
            return bannerDtoList.size();
        }
        return 0;
    }

    @Override
    public String getItem(int position) {
        if (bannerDtoList != null && bannerDtoList.size() > 0) {
            return bannerDtoList.get(position).imgUrl;
        }
        return "";
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager, parent, false);
            holder = new ViewHolder();
            holder.ivBanner = convertView.findViewById(R.id.ivBanner);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BusinessDto slidersDto = bannerDtoList.get(position);
        String  imgStr = slidersDto.imgUrls;
        if(imgStr != null){
            if(imgStr.contains("http://")){
                GlideUtils.getInstances().loadNormalImg(activity, holder.ivBanner, imgStr,R.mipmap.img_default_2);
            } else{
                GlideUtils.getInstances().loadNormalImg(activity, holder.ivBanner, BuildConfig.BASE_IMAGE_URL + imgStr,R.mipmap.img_default_2);
            }
        }
        bindClickEvent(holder.ivBanner, () -> {

        });
        return convertView;
    }

    /**
     * 启动商品详细
     *
     * @param product_id 商品ID
     */
    private void startActivityCommodityDetail(long product_id) {
        Bundle bundle = new Bundle();
        bundle.putLong(CommodityDetailActivity.PRODUCT_ID, product_id);
        Intent intent = new Intent(activity, CommodityDetailActivity.class);
        intent.putExtras(bundle);
        activity.getApplication().startActivity(intent);
    }

    /**
     * 启动产品列表
     *
     * @param product_type 商品类型
     */
    private void startActivityProductList(int product_type) {
        Bundle bundle = new Bundle();
        bundle.putInt(ShopProductListActivity.PRODUCT_TYPE, product_type);
        Intent intent = new Intent(activity, ShopProductListActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        Log.i("hahahah","返回来的数据是" + activity);
    }


    @SuppressLint("CheckResult")
    private void bindClickEvent(View view, final Action action) {
        Observable<Object> observable = RxView.clicks(view);
        observable.throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(trigger -> action.run());
    }

    @Override
    public void onPageItemSelected(int position) {
        if (mIndicators.getChildAt(mLastPosition) != null) {
            mIndicators.getChildAt(mLastPosition).setActivated(false);
            mIndicators.getChildAt(position).setActivated(true);
        }
        mLastPosition = position;
    }

    static class ViewHolder {
        ImageView ivBanner;
    }
}
