package com.ggkjg.view.mainfragment.order;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.base.BaseApplication;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.Compressor;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.ImagePickerUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.MyOrderItemDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.ReasonUploadPicAdapter;
import com.ggkjg.view.widgets.ratingbar.BaseRatingBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MyOrderEvaluateActivity extends BaseActivity {
    @BindView(R.id.img_me_order_logo)
    ImageView img_me_order_logo;
    @BindView(R.id.tv_me_order_des)
    TextView tv_me_order_des;
    @BindView(R.id.tv_me_order_form)
    TextView tv_me_order_form;
    @BindView(R.id.ed_content)
    EditText ed_content;
    @BindView(R.id.recy_evaluate)
    RecyclerView recyPic;
    @BindView(R.id.rb_shop_evaluate)
    BaseRatingBar rb_shop_evaluate;
    @BindView(R.id.tv_rb_shop_tip)
    TextView tv_rb_shop_tip;
    @BindView(R.id.rb_logistics_evaluate)
    BaseRatingBar rb_logistics_evaluate;
    @BindView(R.id.tv_rb_logistics_tip)
    TextView tv_rb_logistics_tip;
    @BindView(R.id.rb_service_evaluate)
    BaseRatingBar rb_service_evaluate;
    @BindView(R.id.tv_rb_service_tip)
    TextView tv_rb_service_tip;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    ReasonUploadPicAdapter mAdapter;
    private int goodsScore = 5, serviceScore = 5, timeScore = 5;
    private String orderId, goodsId, specId;
    private String specStr;

    @Override
    public void initListener() {
        bindClickEvent(btn_confirm, () -> {
            goodsEvaluate();
        });

        rb_shop_evaluate.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating) {
                goodsScore = (int) rating;
                dealRatingBar(goodsScore, tv_rb_shop_tip);
            }
        });
        rb_logistics_evaluate.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating) {
                serviceScore = (int) rating;
                dealRatingBar(serviceScore, tv_rb_logistics_tip);
            }
        });
        rb_service_evaluate.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating) {
                timeScore = (int) rating;
                dealRatingBar(timeScore, tv_rb_service_tip);
            }
        });

        ed_content.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ed_content.getText().length() == 0){
                    tv_me_order_form.setText(specStr);
                }else{
                    tv_me_order_form.setText(ed_content.getText().length() + "/200");
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_order_evaluate_layout;
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable serializable = bundle.getSerializable(Constants.INTENT_DATA);
            if (serializable != null) {
                MyOrderItemDto item = (MyOrderItemDto) serializable;
                orderId = item.getOrderId();
                goodsId = item.getGoodsId();
                specId = item.getGoodsSpecId();

                String imgUrl = com.ggkjg.base.BuildConfig.BASE_IMAGE_URL + item.getGoodsImg();
                GlideUtils.getInstances().loadNormalImg(this, img_me_order_logo, imgUrl, R.mipmap.img_default_1);
                tv_me_order_des.setText(item.getGoodsName());
                specStr = item.getGoodsSpecName();
                tv_me_order_form.setText(specStr);
            }
        }
        initPicAdapter();
    }

    @Override
    public void initData() {

    }

    // 1个星，差评 2个星一般 3个星 好 4个星很好，5个星非常好
    private void dealRatingBar(int ratingNumber, TextView tv_tip) {
        String rbTip = "";
        switch (ratingNumber) {
            //case 0:
            case 1:
                rbTip = "差评";
                break;
            case 2:
                rbTip = "一般";
                break;
            case 3:
                rbTip = "好";
                break;
            case 4:
                rbTip = "很好";
                break;
            case 5:
                rbTip = "非常好";
                break;
        }
        tv_tip.setText(rbTip);
    }

    private void initPicAdapter() {
        mAdapter = new ReasonUploadPicAdapter(null, 3, "evaluate_pic");
        mAdapter.refresh();
        recyPic.setLayoutManager(new GridLayoutManager(this, 4));
        recyPic.setHasFixedSize(true);
        recyPic.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (Constants.IMAGEITEM_DEFAULT_ADD.equals(mAdapter.getItem(position).path)) {
                    //打开选择
                    new RxPermissions(MyOrderEvaluateActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    if (!aBoolean) {
                                        return;
                                    }
                                    ImagePickerUtil.initImagePicker(4 - mAdapter.getItemCount(), true, false);
                                    //打开选择器
                                    Intent intent = new Intent(MyOrderEvaluateActivity.this, ImageGridActivity.class);
                                    startActivityForResult(intent, Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                                }
                            });
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_del:
                        mAdapter.remove(position);
                        mAdapter.refresh();
                        break;
                }
            }
        });
    }

    private void goodsEvaluate() {
        showLoadDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("goodsId", goodsId);
        map.put("specId", specId);
        map.put("goodsScore", goodsScore + "");
        map.put("serviceScore", serviceScore + "");
        map.put("timeScore", timeScore + "");
        if (!TextUtils.isEmpty(ed_content.getText().toString().trim())) {
            map.put("content", ed_content.getText().toString().trim());
        }

        Observable observable = Observable.create(new ObservableOnSubscribe<List<MultipartBody.Part>>() {//图片压缩
            @Override
            public void subscribe(ObservableEmitter<List<MultipartBody.Part>> emitter) throws Exception {

                List<MultipartBody.Part> files = new ArrayList<>();
                List<ImageItem> pathList = mAdapter.getData();
                if (pathList != null && pathList.size() > 0) {
                    for (int i = 0; i < pathList.size(); i++) {
                        if (Constants.IMAGEITEM_DEFAULT_ADD.equals(pathList.get(i).path)) {
                            continue;
                        }
                        File file = new File(pathList.get(i).path);
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
                        files.add(part);
                    }
                }
                emitter.onNext(files);

            }


        });
        DisposableObserver<List<MultipartBody.Part>> disposableObserver = new DisposableObserver<List<MultipartBody.Part>>() {

            @Override
            public void onNext(List<MultipartBody.Part> partList) {
                goodsEvaluate(map, partList);
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

    private void goodsEvaluate(HashMap<String, String> map, List<MultipartBody.Part> partList) {
        DataManager.getInstance().goodsEvaluate(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                dissLoadDialog();
                if (httpResult != null && httpResult.getStatus() == 1) {
                    ToastUtil.showToast(httpResult.getMsg());
                    if (httpResult.getStatus() == 1) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, map, partList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && data != null) {
            ArrayList<ImageItem> resultList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (resultList != null && resultList.size() > 0) {
                mAdapter.addData(0, resultList);
                mAdapter.refresh();
            }
        }

    }
}
