package com.ggkjg.view.mainfragment.shop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.donkingliang.labels.LabelsView;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.utils.InputUtil;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.db.SeachHistotyDBUtil;
import com.ggkjg.db.bean.SearchHistory;
import com.ggkjg.dto.HotSearchDto;
import com.ggkjg.dto.MessageListDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.SearchProductAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索商品
 * Created by dahai on 2018/12/17.
 */
public class SearchShopProduct extends BaseActivity implements TextView.OnEditorActionListener {
    private static final String TAG = SearchShopProduct.class.getSimpleName();
    @BindView(R.id.tv_search_back)
    TextView iv_back;
    @BindView(R.id.et_search_str)
    EditText et_search;
    @BindView(R.id.tv_search_clean)
    TextView tv_search_clean;
    @BindView(R.id.rl_search_select_datas)
    LabelsView labelsView;
    @BindView(R.id.recy_search_message)
    RecyclerView recyclerView;
    private SearchProductAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.ui_search_product_layout;
    }

    @Override
    public void initView() {
        StatusBarUtils.StatusBarLightMode(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
//      recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    public void initData() {
        findHotSearchList();
        List<SearchHistory> datas = SeachHistotyDBUtil.getInstance().loadAll();
        Collections.reverse(datas);
        adapter = new SearchProductAdapter(datas, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SearchHistory object = (SearchHistory) adapter.getItem(position);
                if (object != null) {
                    switch (view.getId()) {
                        case R.id.tv_search_item_title:
                            startSearchProductResults(object.getName());
                            break;
                        case R.id.tv_search_item_clean:
                            SeachHistotyDBUtil.getInstance().delete(object);
                            List<SearchHistory> datas = SeachHistotyDBUtil.getInstance().loadAll();
                            Collections.reverse(datas);
                            adapter.setNewData(datas);
                            break;
                    }

                }
            }
        });
        //标签的点击监听
        labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                //label是被点击的标签，data是标签所对应的数据，position是标签的位置。
                addSeachHistoty((String) data);
                startSearchProductResults((String) data);
            }
        });
        bindClickEvent(iv_back, this::finish);
        bindClickEvent(tv_search_clean, () -> {
            SeachHistotyDBUtil.getInstance().deleteAll();
            adapter.setNewData(null);
        });
        et_search.setOnEditorActionListener(this);
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //防止两次发送请求
        if (actionId == EditorInfo.IME_ACTION_SEND ||
                (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_UP:
                    String seachStr = et_search.getText().toString().trim();
                    addSeachHistoty(seachStr);
                    InputUtil.HideKeyboard(et_search);
                    startSearchProductResults(seachStr);
                    return true;
                default:
                    return true;
            }
        }
        return false;
    }

    public void addSeachHistoty(String key) {
        SearchHistory history = SeachHistotyDBUtil.getInstance().query(key);
        if (history != null) {
            SeachHistotyDBUtil.getInstance().delete(history);
            history = new SearchHistory();
            history.setName(key);
            SeachHistotyDBUtil.getInstance().save(history);
        } else {
            history = new SearchHistory();
            history.setName(key);
            SeachHistotyDBUtil.getInstance().save(history);
        }
    }

    /**
     * 启动搜索结果列表
     *
     * @param key 搜索值
     */
    private void startSearchProductResults(String key) {
        // Bundle bundle = new Bundle();
        // bundle.putString(SearchProductResults.ACTION_SEARCH_KEY, key);
        // gotoActivity(SearchProductResults.class, true, bundle);

        //bundle.putString(ShopProductListActivity.ACTION_SEARCH_KEY, key);
        //gotoActivity(ShopProductListActivity.class, true, bundle);

        Bundle bundle = new Bundle();
        bundle.putString(ShopProductListActivity.ACTION_SEARCH_KEY, key);
        gotoActivity(ShopProductListActivity.class, false, bundle);
    }


    /**
     * 获取热门搜索
     */
    private void findHotSearchList() {
        showLoadDialog();
        DataManager.getInstance().findHotSearchList(new DefaultSingleObserver<List<HotSearchDto>>() {
            @Override
            public void onSuccess(List<HotSearchDto> object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                labelsView.setSelectType(LabelsView.SelectType.SINGLE);
                List<String> search_hot = new ArrayList<>();
                if (object != null && !object.isEmpty()) {
                    for (HotSearchDto hotSearchDto : object) {
                        search_hot.add(hotSearchDto.getSearchName());
                    }
                    labelsView.setLabels(search_hot, new LabelsView.LabelTextProvider<String>() {
                        @Override
                        public CharSequence getLabelText(TextView label, int position, String data) {
                            return data;
                        }
                    });
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                dissLoadDialog();
            }
        });
    }

}
