package com.ggkjg.view.widgets.autoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.ggkjg.R;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.ToastUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * get请求 显示图片
 * Created by xxy on 2017/12/08.
 */
public class RemoteImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = RemoteImageView.class.getSimpleName();
    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;
    //子线程不能操作UI，通过Handler设置图片
    protected Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case GET_DATA_SUCCESS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    setImageBitmap(bitmap);
                    handler.removeMessages(GET_DATA_SUCCESS);
                    break;
                case NETWORK_ERROR:
                    ToastUtil.showToast("网络连接失败");
                    RemoteImageView.this.setBackgroundResource(R.mipmap.get_img_update_default_1);
                    handler.removeMessages(NETWORK_ERROR);
                    break;
                case SERVER_ERROR:
                    ToastUtil.showToast("服务器发生错误");
                    RemoteImageView.this.setBackgroundResource(R.mipmap.get_img_update_default_1);
                    handler.removeMessages(SERVER_ERROR);
                    break;
            }
            return true;
        }
    });

    public RemoteImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RemoteImageView(Context context) {
        super(context);
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //设置网络图片
    public void setImageURL(final String path) {
        //开启一个线程用于联网
        new Thread() {
            @Override
            public void run() {
                try {
                    //把传过来的路径转成URL
                    URL url = new URL(path);
                    //获取连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //使用GET方法访问网络
                    connection.setRequestMethod("GET");
                    //超时时间为10秒
                    connection.setConnectTimeout(10000);
//                    Map<String, List<String>> header = connection.getHeaderFields();
//                    for (Map.Entry<String, List<String>> entry : header.entrySet()) {
//                        String key = entry.getKey();
//                        for (String value : entry.getValue()) {
//                            if(key != null && key.equals("Set-Cookie")){
//
//                                break;
//                            }
//                        }
//                    }
                    //获取返回码
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        //使用工厂把网络的输入流生产Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        //利用Message把图片发给Handler
                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        msg.what = GET_DATA_SUCCESS;
                        handler.sendMessage(msg);
                        inputStream.close();
                    } else {
                        //服务启发生错误
                        handler.sendEmptyMessage(SERVER_ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //网络连接错误
                    handler.sendEmptyMessage(NETWORK_ERROR);
                }
            }
        }.start();
    }

}
