package com.ggkjg.view.mainfragment.square;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.type.TransferType;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.widgets.GoldReleaseDialog;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 广场 - 港豆收购
 * Created by dahai on 2018/4/21.
 */

public class SquareGoldInputActivity extends BaseActivity {
    private static final String TAG = SquareGoldInputActivity.class.getSimpleName();
    @BindView(R.id.btn_gold_input_title)
    EditText btn_gold_input_title;
    @BindView(R.id.btn_gold_input_realName)
    EditText btn_gold_input_realName;
    @BindView(R.id.btn_gold_input_QQ)
    EditText btn_gold_input_QQ;
    @BindView(R.id.btn_gold_input_transferNum)
    EditText btn_gold_input_transferNum;
    @BindView(R.id.btn_gold_input_transferPrice)
    EditText btn_gold_input_transferPrice;
    @BindView(R.id.btn_gold_input_submit)
    Button btn_gold_input_submit;
    @Override
    public int getLayoutId() {
        return R.layout.ui_square_gold_input_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        bindClickEvent(btn_gold_input_submit, () -> {
            String title = btn_gold_input_title.getText().toString().trim();
            String realName = btn_gold_input_realName.getText().toString().trim();
            String QQ = btn_gold_input_QQ.getText().toString().trim();
            String transferNum = btn_gold_input_transferNum.getText().toString().trim();
            String transferPrice = btn_gold_input_transferPrice.getText().toString().trim();
            onSubmit(title,realName,QQ,transferNum,transferPrice);
        });
    }

    private void onSubmit(String title, String realName, String QQ,
                          String transferNum, String transferPrice) {
        showLoadDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("title",title);
        map.put("realName",realName);
        map.put("txNO",QQ);
        map.put("transferNum",transferNum);
        map.put("transferPrice",transferPrice);
        map.put("transferType",TransferType.pull.getType()+"");
        DataManager.getInstance().publishTransfer(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() = ");
                dissLoadDialog();
                ToastUtil.showToast(object.getMsg());
                if(object.getStatus() == 1){
                    onBackPressed();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                dissLoadDialog();
            }
        },map);
    }

}
