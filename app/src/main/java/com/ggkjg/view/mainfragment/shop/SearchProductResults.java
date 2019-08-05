package com.ggkjg.view.mainfragment.shop;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.utils.InputUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.view.adapter.HomeAdapter;
import com.ggkjg.view.widgets.RecyclerItemDecoration;
import com.ggkjg.view.widgets.autoview.EmptyView;
import butterknife.BindView;

/**
 * 搜索结果列表
 * Created by dahai on 2018/12/17.
 */
public class SearchProductResults extends BaseActivity implements TextView.OnEditorActionListener {
    private static final String TAG = SearchProductResults.class.getSimpleName();
    @BindView(R.id.actionbar_back)
    ImageView iv_back;
    @BindView(R.id.et_search_str)
    EditText et_search;
    private HomeAdapter adapter;
    public static final String ACTION_SEARCH_KEY = "activity_action_search_key"; //调用者传参名字
    private String searchKey;
    @BindView(R.id.recy_search_results)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.ui_search_results_layout;
    }

    @Override
    public void initView() {
        StatusBarUtils.StatusBarLightMode(this);
        recyclerView.addItemDecoration(new RecyclerItemDecoration(3, 2));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//      recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//      recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    public void initData() {
        searchKey = getIntent().getStringExtra(ACTION_SEARCH_KEY);
        et_search.setHint(searchKey);
        adapter = new HomeAdapter(null, this);
        recyclerView.setAdapter(adapter);
        getSearchResults(searchKey);
    }

    @Override
    public void initListener() {
        bindClickEvent(iv_back, this::finish);
        et_search.setOnEditorActionListener(this);
    }

    /**
     * 主页搜索结果列表
     */
    private void getSearchResults(String seachStr) {
        EmptyView emptyView = new EmptyView(this);
        emptyView.setTvEmptyTip(String.format("没搜索到%s相关数据", seachStr));
        adapter.setEmptyView(emptyView);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //防止两次发送请求
        if (actionId == EditorInfo.IME_ACTION_SEND ||
                (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_UP:
                    searchKey = et_search.getText().toString().trim();
                    InputUtil.HideKeyboard(et_search);
                    getSearchResults(searchKey);
                    return true;
                default:
                    return true;
            }
        }
        return false;
    }


}
