package com.ggkjg.view.widgets;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 自定义Circle Transformation 实现圆形图片
 * Created by Administrator on 2017/12/21.
 */

public class CircleTransform extends BitmapTransformation {

    public CircleTransform() {
        super();
    }

    /**
     * 重写 生成圆形图片
     *
     * @param pool
     * @param toTransform
     * @param outWidth
     * @param outHeight
     * @return
     */
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        //画布中背景图片与绘制图片交集部分
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }
}