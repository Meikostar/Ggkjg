package com.ggkjg.view.mainfragment.settings;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.FeedBackTypeDto;
import com.ggkjg.dto.HelpDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.FeedbackAdapter;
import com.ggkjg.view.widgets.RecyclerItemDecoration;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


/**
 * 个人中心 --> 设置  --> 帮助与反馈
 * Created by dahai on 2018/12/08.
 */
public class FeedbackActivity extends BaseActivity implements TextWatcher {
    private static final String TAG = FeedbackActivity.class.getSimpleName();

    @BindView(R.id.et_set_feedback_message)
    EditText editText;
    @BindView(R.id.tv_set_feedback_message_num)
    TextView textView;
    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.recy_type)
    RecyclerView recyclerView;
    FeedbackAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.ui_setting_feedback;
    }

    @Override
    public void initView() {
        initAdapter();
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);

    }

    @Override
    public void initData() {
        loadData();
    }

    @Override
    public void initListener() {
        editText.addTextChangedListener(this);
        bindClickEvent(btn_sure, () -> {
            userFeedback();
        });


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        textView.setText(editText.getText().length() + "/200");

    }

    private void initAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.addItemDecoration(new RecyclerItemDecoration(3, 3));
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new FeedbackAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.setSelPosition(position);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void userFeedback() {
        String contentStr = editText.getText().toString();
        if (TextUtils.isEmpty(contentStr)&&contentStr.length()>0) {
            ToastUtil.showToast("请输入反馈");
            return;
        }

        FeedBackTypeDto feedBackTypeDto = mAdapter.getItem(mAdapter.getSelPosition());

        showLoadDialog();
        DataManager.getInstance().submitFeedBack(new DefaultSingleObserver<HttpResult<String>>() {
            @Override
            public void onSuccess(HttpResult<String> httpResult) {
                dissLoadDialog();
                if (httpResult != null && "1".equals(httpResult.getStatus())) {
                    ToastUtil.showToast("成功");
                    finish();
                } else {
                    ToastUtil.showToast(httpResult.getMsg());
                }

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();


            }
        }, feedBackTypeDto.getType(), feedBackTypeDto.getName(), contentStr);
    }

    private void loadData() {
        showLoadDialog();
        DataManager.getInstance().getFeedBackType(new DefaultSingleObserver<List<FeedBackTypeDto>>() {
            @Override
            public void onSuccess(List<FeedBackTypeDto> feedBackTypeDtoList) {
                dissLoadDialog();
                mAdapter.setNewData(feedBackTypeDtoList);
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        });
    }
}
