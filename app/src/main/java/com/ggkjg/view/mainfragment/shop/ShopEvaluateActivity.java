package com.ggkjg.view.mainfragment.shop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.dto.ShopEvaluateDto;
import com.ggkjg.dto.ShopEvaluateImgListDto;
import com.ggkjg.dto.ShopEvaluateRowsDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.ShopEvaluateAdapter;
import com.ggkjg.view.mainfragment.ImagePreviewActivity;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 商品评价列表
 */
public class ShopEvaluateActivity extends BaseActivity {
    private static final String TAG = ShopEvaluateActivity.class.getSimpleName();
    public static final String PRODUCT_ID = "product_id";//调用者传递的名字
    private long product_id;

    @BindView(R.id.tv_evaluate_1)
    TextView tv_evaluate_1;
    @BindView(R.id.tv_evaluate_2)
    TextView tv_evaluate_2;
    @BindView(R.id.tv_evaluate_3)
    TextView tv_evaluate_3;
    @BindView(R.id.tv_evaluate_4)
    TextView tv_evaluate_4;

    @BindView(R.id.rl_evaluate_list)
    RecyclerView recyclerView;
    private ShopEvaluateAdapter adapter;

    @BindView(R.id.refresh_evaluate_list)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayoutUtil swipeRefreshLayoutUtil;
    private int currentPage = Constants.PAGE_NUM;
    private int pageRows = 10; //每页加载行数
    private int dataType = 1;//加载数据类型  1:全部 2:好评 3:有图 4:差评

    @Override
    public int getLayoutId() {
        return R.layout.ui_shop_evaluate_layout;
    }

    @Override
    public void initView() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//        recyclerView.setLayoutManager(linearLayoutManager);
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void initData() {
        product_id = getIntent().getLongExtra(PRODUCT_ID, 0);
        adapter = new ShopEvaluateAdapter(null);
        recyclerView.setAdapter(adapter);
        setTopTitlesView(tv_evaluate_1);
    }


    @Override
    public void initListener() {
        bindClickEvent(tv_evaluate_1, () -> {
            setTopTitlesView(tv_evaluate_1);
        });
        bindClickEvent(tv_evaluate_2, () -> {
            setTopTitlesView(tv_evaluate_2);
        });
        bindClickEvent(tv_evaluate_3, () -> {
            setTopTitlesView(tv_evaluate_3);
        });
        bindClickEvent(tv_evaluate_4, () -> {
            setTopTitlesView(tv_evaluate_4);
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ShopEvaluateRowsDto item = (ShopEvaluateRowsDto) adapter.getItem(position);
                if (item != null) {
                    switch (view.getId()) {
                        case R.id.iv_user_img1:
                            dealImagePreview(0, item.getImgList());
                            break;
                        case R.id.iv_user_img2:
                            dealImagePreview(1, item.getImgList());
                            break;
                        case R.id.iv_user_img3:
                            dealImagePreview(2, item.getImgList());
                            break;
                    }
                }
            }
        });
        setScrollListener();
    }

    private void setTopTitlesView(View view) {
        switch (view.getId()) {
            case R.id.tv_evaluate_1:
                dataType = 0; //全部
                tv_evaluate_1.setSelected(true);
                tv_evaluate_2.setSelected(false);
                tv_evaluate_3.setSelected(false);
                tv_evaluate_4.setSelected(false);
                break;
            case R.id.tv_evaluate_2:
                dataType = 2;//好评
                tv_evaluate_1.setSelected(false);
                tv_evaluate_2.setSelected(true);
                tv_evaluate_3.setSelected(false);
                tv_evaluate_4.setSelected(false);
                break;
            case R.id.tv_evaluate_3:
                dataType = 1;//有图
                tv_evaluate_1.setSelected(false);
                tv_evaluate_2.setSelected(false);
                tv_evaluate_3.setSelected(true);
                tv_evaluate_4.setSelected(false);
                break;
            case R.id.tv_evaluate_4:
                dataType = 3;//差评
                tv_evaluate_1.setSelected(false);
                tv_evaluate_2.setSelected(false);
                tv_evaluate_3.setSelected(false);
                tv_evaluate_4.setSelected(true);
                break;
        }
        findAllEvaList(false, currentPage, pageRows, product_id, dataType);
    }

    private void dealImagePreview(int position, List<ShopEvaluateImgListDto> imageList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePreviewActivity.INTENT_KEY_DATA,  (Serializable) imageList);
        bundle.putInt(ImagePreviewActivity.INTENT_KEY_START_POSITION, position);
        gotoActivity(ImagePreviewActivity.class, false, bundle);
    }

    /**
     * 获取商品评价列表
     *
     * @param currentPage 页数
     * @param rows    行数
     * @param goodsId 商品ID
     * @param type    类型: type=1 有图 type=2好评 type=3 差评
     */
    private void findAllEvaList(boolean isScrollLoad, int currentPage, int rows, long goodsId, int type) {
        if (!isScrollLoad) {
            showLoadDialog();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", currentPage);
        map.put("rows", rows);
        map.put("goodsId", goodsId);
        map.put("type", type + "");
        DataManager.getInstance().findAllEvaList(new DefaultSingleObserver<ShopEvaluateDto>() {
            @Override
            public void onSuccess(ShopEvaluateDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if(object != null) {
                    setTopTitleNum(object);
                    if (object.getEvaList().getRows() != null && object.getEvaList().getRows() != null) {
                        if (currentPage <= Constants.PAGE_NUM) {
                            adapter.setNewData(object.getEvaList().getRows());
                        } else {
                            adapter.addData(object.getEvaList().getRows());
                        }
                        swipeRefreshLayoutUtil.isMoreDate(currentPage, rows, object.getEvaList().getTotal());
                    } else {
                        swipeRefreshLayoutUtil.isMoreDate(currentPage, rows, 0);
                    }
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                dissLoadDialog();
            }
        }, map);
    }

    private void setTopTitleNum(ShopEvaluateDto object) {
        tv_evaluate_2.setText(String.format("好评(%s)", object.getGoodCount() + ""));
        tv_evaluate_3.setText(String.format("有图(%s)", object.getImgCount() + ""));
        tv_evaluate_4.setText(String.format("差评(%s)", object.getBadCount() + ""));
    }

    /**
     * 滚动监听
     */
    private void setScrollListener() {
        swipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
//        swipeRefreshLayout.setEnablePull(false);
        swipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = Constants.PAGE_NUM;
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                findAllEvaList(true, currentPage, pageRows, product_id, dataType);
            }

            @Override
            public void onLoadMore() {
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                currentPage++;
                findAllEvaList(true, currentPage, pageRows, product_id, dataType);
            }
        });
    }

    @Override
    protected void dissLoadDialog() {
        super.dissLoadDialog();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setLoadMore(false);
        }
    }
}
