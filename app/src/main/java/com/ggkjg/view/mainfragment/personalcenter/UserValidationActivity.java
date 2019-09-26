package com.ggkjg.view.mainfragment.personalcenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BaseApplication;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.Compressor;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.ImagePickerUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.StringUtil;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.mainfragment.settings.UserInfoSetActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 个人中心 --> 设置 -->  实名验证
 * Created by dahai on 2018/12/08.
 */
public class UserValidationActivity extends BaseActivity {

    @BindView(R.id.set_user_validation_view_1)
    LinearLayout set_user_validation_view_1;
    @BindView(R.id.et_set_user_validation_name)
    EditText et_set_user_validation_name;
    @BindView(R.id.et_set_user_validation_code)
    EditText et_set_user_validation_code;
    @BindView(R.id.but_set_user_validation_next)
    Button but_set_user_validation_next;
    @BindView(R.id.set_user_validation_view_2)
    LinearLayout set_user_validation_view_2;
    @BindView(R.id.img_card_1)
    ImageView img_card_1;
    @BindView(R.id.img_card_2)
    ImageView img_card_2;
    @BindView(R.id.img_card_3)
    ImageView img_card_3;
    @BindView(R.id.but_set_user_validation_submit)
    Button but_set_user_validation_submit;
    private boolean isSubmitView = false;

    private String imgUrl1, imgUrl2, imgUrl3;
    private List<String> uploadImgList = new ArrayList<>();
    private List<String> areadUploadImg = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.ui_setting_user_validation;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        set_user_validation_view_1.setVisibility(View.VISIBLE);
        ImagePickerUtil.initImagePicker(1, true, false);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {

        bindClickEvent(but_set_user_validation_next, () -> {
            if (TextUtils.isEmpty(et_set_user_validation_name.getText().toString().trim())) {
                ToastUtil.showToast("姓名不能为空");
                return;
            }
            if (TextUtils.isEmpty(et_set_user_validation_code.getText().toString().trim())) {
                ToastUtil.showToast("身份证号不能为空！");
                return;
            }
            if(!StringUtil.IDCardValidate(et_set_user_validation_code.getText().toString().trim())){
                ToastUtil.showToast("身份证号码无效！");
                return;
            }
            addRealNameApply(et_set_user_validation_name.getText().toString().trim(), et_set_user_validation_code.getText().toString().trim());

        });
        bindClickEvent(but_set_user_validation_submit, () -> {
            if (TextUtils.isEmpty(imgUrl1) || TextUtils.isEmpty(imgUrl2) || TextUtils.isEmpty(imgUrl3)) {
                ToastUtil.showToast("请上传身份证照片");
                return;
            }

            initUploadData();
        });
        bindClickEvent(actionbar.getBackView(), () -> {
            if (isSubmitView) {
                set_user_validation_view_1.setVisibility(View.VISIBLE);
                set_user_validation_view_2.setVisibility(View.GONE);
                isSubmitView = false;
            } else {
                onBackPressed();
            }
        });
        bindClickEvent(img_card_1, () -> {
            selImge(Constants.INTENT_REQUESTCODE_VERIFIED_IMG1);
        });
        bindClickEvent(img_card_2, () -> {
            selImge(Constants.INTENT_REQUESTCODE_VERIFIED_IMG2);
        });
        bindClickEvent(img_card_3, () -> {
            selImge(Constants.INTENT_REQUESTCODE_VERIFIED_IMG3);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == Constants.INTENT_REQUESTCODE_VERIFIED_IMG1 || requestCode == Constants.INTENT_REQUESTCODE_VERIFIED_IMG2 || requestCode == Constants.INTENT_REQUESTCODE_VERIFIED_IMG3) {
            ArrayList<ImageItem> resultList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (resultList != null && resultList.size() > 0) {
                String imageUrl = resultList.get(0).path;
                switch (requestCode) {
                    case Constants.INTENT_REQUESTCODE_VERIFIED_IMG1:
                        imgUrl1 = imageUrl;
                        loadImage(imageUrl, img_card_1);
                        break;
                    case Constants.INTENT_REQUESTCODE_VERIFIED_IMG2:
                        imgUrl2 = imageUrl;
                        loadImage(imageUrl, img_card_2);
                        break;
                    case Constants.INTENT_REQUESTCODE_VERIFIED_IMG3:
                        imgUrl3 = imageUrl;
                        loadImage(imageUrl, img_card_3);
                        break;
                }
            }

        }

    }

    private void selImge(int imgCode) {
        //打开选择器
        Intent intent = new Intent(UserValidationActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, imgCode);
    }

    private void loadImage(String imageUrl, ImageView imageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap fileBitMap = getFileBitMap(imageUrl);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GlideUtils.getInstances().loadNormalImg(UserValidationActivity.this, imageView, fileBitMap);
                    }
                });
            }
        }).start();

//        imageView.setImageBitmap(fileBitMap);

    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getFileBitMap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void initUploadData() {
        showLoadDialog();
        uploadImg();
    }


    private void uploadImg() {
        showLoadDialog();

        Observable observable = Observable.create(new ObservableOnSubscribe<List<MultipartBody.Part>>() {//图片压缩
            @Override
            public void subscribe(ObservableEmitter<List<MultipartBody.Part>> emitter) throws Exception {
                List<String> pathList = new ArrayList<>();
                List<MultipartBody.Part> partList = new ArrayList<>();
                pathList.add(imgUrl1);
                pathList.add(imgUrl2);
                pathList.add(imgUrl3);
                for (int i = 0; i < pathList.size(); i++) {
                    File file = new File(pathList.get(i));
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
                        emitter.onError(null);
                        return;
                    }

                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), compressedImage);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("imgFile", file.getName(), requestBody);
                    partList.add(part);
                }
                emitter.onNext(partList);
            }


        });
        DisposableObserver<List<MultipartBody.Part>> disposableObserver = new DisposableObserver<List<MultipartBody.Part>>() {

            @Override
            public void onNext(List<MultipartBody.Part> partList) {
                uploadRealImg(partList);
            }

            @Override
            public void onError(Throwable e) {
                dissLoadDialog();
                ToastUtil.showToast("上传图片失败");
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
    }

    private void addRealNameApply(String realName, String identifyNo) {
        showLoadDialog();
        DataManager.getInstance().addRealNameApply(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                dissLoadDialog();
                if (httpResult != null && httpResult.getStatus() == 1) {
                    set_user_validation_view_1.setVisibility(View.GONE);
                    set_user_validation_view_2.setVisibility(View.VISIBLE);
                    isSubmitView = true;
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
        }, realName, identifyNo);
    }

    private void uploadRealImg(List<MultipartBody.Part> partList) {
        DataManager.getInstance().uploadRealImg(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                dissLoadDialog();
                if (httpResult != null) {
                    ToastUtil.showToast(httpResult.getMsg());
                    if (httpResult.getStatus() == 1) {
                        finish();
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, partList);
    }
}
