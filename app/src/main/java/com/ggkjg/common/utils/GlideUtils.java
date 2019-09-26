package com.ggkjg.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ggkjg.R;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.view.widgets.CircleTransform;
import com.ggkjg.view.widgets.RoundCornerTransform;

/**
 * Created by Administrator on 2017/12/21.
 */

public class GlideUtils {

    private static GlideUtils glideUtils;

    public static GlideUtils getInstances() {
        if (glideUtils == null) {
            glideUtils = new GlideUtils();
        }
        return glideUtils;
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param cornerDp
     * @param imgUrl
     */
    public void loadRoundCornerImg(Context context, ImageView imageView, float cornerDp, Object imgUrl) {
        Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.img_default_1) // 预加载图片
                        .error(R.mipmap.img_default_1) // 加载失败显示图片
                        .priority(Priority.HIGH) // 优先级
                        // .diskCacheStrategy(DiskCacheStrategy.NONE) // 缓存策略
                        .transforms(new CenterCrop(), new RoundCornerTransform(ScreenSizeUtil.dp2px(cornerDp)))) // 转化为圆角
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadRoundImg(Context context, ImageView imageView, Object imgUrl) {
        Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.img_default_1)
                        .error(R.mipmap.img_default_1)
                        // .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transforms(new CenterCrop(), new CircleTransform()))
                .into(imageView);
    }
    public void loadRoundImg(Context context, ImageView imageView, Object imgUrl,int url) {
        Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .apply(new RequestOptions()
                        .placeholder(url)
                        .error(url)
                        // .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transforms(new CenterCrop(), new CircleTransform()))
                .into(imageView);
    }

    public void loadRoundImgs(Context context, ImageView imageView, Object imgUrl) {
        Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.user_default_icon)
                        .error(R.mipmap.user_default_icon)
                        // .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transforms(new CenterCrop(), new CircleTransform()))
                .into(imageView);
    }
    /**
     * 加载正常图片居中填充控件
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadNormalCenterCropImg(Context context, ImageView imageView, Object imgUrl) {
        Glide.with(context).asBitmap().load(imgUrl).apply(new RequestOptions()
                .placeholder(R.mipmap.img_default_1) // 预加载图片
                .error(R.mipmap.img_default_1) // 加载失败显示图片
                .priority(Priority.HIGH) // 优先级
                // .diskCacheStrategy(DiskCacheStrategy.NONE) // 缓存策略
                .transforms(new CenterCrop()))
                .into(imageView);
    }

    /**
     * 加载正常图片
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadNormalImg(Context context, ImageView imageView, Object imgUrl) {
        try {
            Glide.with(context).asBitmap().load(imgUrl instanceof String ?(((String)imgUrl).contains("http")?imgUrl: BuildConfig.BASE_IMAGE_URL +(String)imgUrl):imgUrl).apply(new RequestOptions()
                    .placeholder(R.mipmap.img_default_1) // 预加载图片
                    .error(R.mipmap.img_default_1) // 加载失败显示图片
                    .priority(Priority.LOW)) // 优先级
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("loadNormalImg", "loadNormalImg--isDestroyed");
        }

    }

    /**
     * 加载正常图片
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadNormalImg(Context context, ImageView imageView, Object imgUrl, int defaultImgId) {
        try {
            Glide.with(context).asBitmap().load(imgUrl).apply(new RequestOptions()
                    .placeholder(defaultImgId) // 预加载图片
                    .error(defaultImgId) // 加载失败显示图片
                    .priority(Priority.LOW)) // 优先级
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("loadNormalImg", "loadNormalImg--isDestroyed");
        }

    }

    /**
     * 加载正常图片
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadUserHead(Context context, ImageView imageView, Object imgUrl) {
        Glide.with(context).asBitmap().load(imgUrl).apply(new RequestOptions()
                .placeholder(R.mipmap.img_default_1) // 预加载图片
                .error(R.mipmap.img_default_1) // 加载失败显示图片
                .priority(Priority.HIGH)) // 优先级
                .into(imageView);
    }

    /**
     * 加载圆角图片无缓存
     *
     * @param context
     * @param imageView
     * @param cornerDp
     * @param imgUrl
     */
    public void loadRoundCornerImgUnCache(Context context, ImageView imageView, float cornerDp, Object imgUrl) {
        Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.img_default_1) // 预加载图片
                        .error(R.mipmap.img_default_1) // 加载失败显示图片
                        .priority(Priority.HIGH) // 优先级
                        .diskCacheStrategy(DiskCacheStrategy.NONE) // 缓存策略
                        .transforms(new CenterCrop(), new RoundCornerTransform(ScreenSizeUtil.dp2px(cornerDp)))) // 转化为圆角
                .into(imageView);
    }

    /**
     * 重新加载图片
     *
     * @param context
     */
    public void onResumeLoadImg(Context context) {
        if (context != null) {
            Glide.with(context).resumeRequests();
        }
    }

    /**
     * 暂停加载图片
     *
     * @param context
     */
    public void onPauseLoad(Context context) {
        if (context != null) {
            Glide.with(context).pauseRequests();
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
