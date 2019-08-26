package com.ggkjg.view.mainfragment.personalcenter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.GoodsPushDto;
import com.ggkjg.dto.GoodsPushRowsDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.dto.CollectionDto;
import com.ggkjg.dto.DataPageDto;
import com.ggkjg.view.adapter.CollectionAdapter;
import com.ggkjg.view.adapter.HomeAdapter;
import com.ggkjg.view.widgets.RecyclerItemDecoration;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.List;

import butterknife.BindView;

public class MyCollectionActivity extends BaseActivity {
    private static final String TAG = MyCollectionActivity.class.getSimpleName();
    @BindView(R.id.recy_collection)
    RecyclerView collectiondRecy;
    @BindView(R.id.recy_push)
    RecyclerView recommendRecy;
    HomeAdapter pushAdapter;
    CollectionAdapter collectionAdapter;
    private int currentPage = Constants.PAGE_NUM;


    @Override
    public int getLayoutId() {
        return R.layout.ui_collection_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initAdapter();
    }

    @Override
    public void initData() {
        findQualityGoodsList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCollect(true);
    }

    @Override
    public void initListener() {
    }

    private void initAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recommendRecy.addItemDecoration(new RecyclerItemDecoration(3, 2));
        recommendRecy.setLayoutManager(gridLayoutManager);

        pushAdapter = new HomeAdapter(null, this);
        recommendRecy.setAdapter(pushAdapter);

        LinearLayoutManager collectionManger = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        collectiondRecy.setLayoutManager(collectionManger);
        collectionAdapter = new CollectionAdapter(null);
        collectiondRecy.setAdapter(collectionAdapter);
        collectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CollectionDto collectionDto = collectionAdapter.getItem(position);
                addFavorite(collectionDto.getGoodsId());
            }
        });
    }


    /**
     * 获取精品推荐
     */
    private void findQualityGoodsList() {
        showLoadDialog();
        DataManager.getInstance().findQualityList(new DefaultSingleObserver<GoodsPushDto>() {
            @Override
            public void onSuccess(GoodsPushDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if (object != null && object.getRows()!=null) {
                    pushAdapter.setNewData(object.getRows());
                } else {
                    EmptyView emptyView = new EmptyView(MyCollectionActivity.this);
                    emptyView.setTvEmptyTip("暂无推荐商品");
                    pushAdapter.setEmptyView(emptyView);
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " );
                dissLoadDialog();
            }
        });
    }


    private void loadCollect(boolean isLoad) {
        if (isLoad) {
            showLoadDialog();
        }

        DataManager.getInstance().favoriteList(new DefaultSingleObserver<DataPageDto<CollectionDto>>() {
            @Override
            public void onSuccess(DataPageDto<CollectionDto> dataPageDto) {
                dissLoadDialog();
                if (dataPageDto != null && dataPageDto.getRows() != null && !dataPageDto.getRows().isEmpty()) {
                    collectionAdapter.setNewData(dataPageDto.getRows());
                } else {
                    collectionAdapter.setNewData(null);
                    EmptyView emptyView = new EmptyView(MyCollectionActivity.this);
                    emptyView.setTvEmptyTip("暂无收藏");
                    collectionAdapter.setEmptyView(emptyView);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, currentPage);
    }

    private void addFavorite(long goodsId) {
        showLoadDialog();
        DataManager.getInstance().addFavorite(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if (httpResult != null && httpResult.getStatus() == 1) {
                    loadCollect(false);
                } else {
                    dissLoadDialog();
                    if (httpResult != null) {
                        ToastUtil.showToast(httpResult.getMsg());
                    }

                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, goodsId, 2);
    }
}
