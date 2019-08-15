package com.ggkjg.view.mainfragment.spike;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.type.ProductListType;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.CommodityDetailInfoDto;
import com.ggkjg.dto.CommodityDetailListDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.ProductListAdapter;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.mainfragment.shop.SearchShopProduct;
import com.ggkjg.view.widgets.ClearEditText;
import com.ggkjg.view.widgets.RecyclerItemDecoration;
import com.ggkjg.view.widgets.SelectProductDialog;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 产品列表
 * Created by dahai on 2019/01/18.
 */
public class VoucherProductListActivity extends BaseActivity {
    private static final String TAG = VoucherProductListActivity.class.getSimpleName();
    @BindView(R.id.refreshLayout)
    SuperSwipeRefreshLayout refreshLayout;

    @BindView(R.id.tv_shop_product_1)
    TextView tv_shop_product_1;

    @BindView(R.id.tv_shop_product_2)
    TextView tv_shop_product_2;

    @BindView(R.id.ll_shop_product_3)
    LinearLayout ll_shop_product_3;

    @BindView(R.id.tv_shop_product_3)
    TextView tv_shop_product_3;

    @BindView(R.id.iv_shop_product_3)
    ImageView iv_shop_product_3;

    @BindView(R.id.ll_shop_product_4)
    LinearLayout ll_shop_product_4;

    @BindView(R.id.tv_shop_product_4)
    TextView tv_shop_product_4;

    @BindView(R.id.iv_shop_product_4)
    ImageView iv_shop_product_4;

    @BindView(R.id.recy_shop_product_list)
    RecyclerView recyclerView;

    @BindView(R.id.ll_bg)
    LinearLayout llbg;
    public static final String ACTION_SEARCH_KEY = "activity_action_search_key"; //调用者传参名字
    @BindView(R.id.acb_status_bar)
    ImageView acbStatusBar;
    @BindView(R.id.actionbar_back)
    ImageView actionbarBack;
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    private String searchKey;
    public static final String PRODUCT_TYPE = "product_type";//调用者传递的名字
    private int mCategoryId = 1;//产品类型:
    private ProductListAdapter mAdapter;
    private HashMap<String, Object> mParamsMaps;
    private int loadDataType = ProductListType.synthesize.getType(); //加载数据类型
    private int mCurrentPage = 1;
    private SwipeRefreshLayoutUtil mSwipeRefreshLayoutUtil;


    @Override
    public void initView() {

        StatusBarUtils.StatusBarLightMode(this);
        recyclerView.addItemDecoration(new RecyclerItemDecoration(3, 2));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new ProductListAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        searchKey = getIntent().getStringExtra(ACTION_SEARCH_KEY);
        mCategoryId = getIntent().getIntExtra(PRODUCT_TYPE,0);
        mParamsMaps = new HashMap<>();
        mParamsMaps.put("rows", Constants.PAGE_SIZE);

        setTopTitlesView(tv_shop_product_1);
    }

    private void getProductListData() {
        mParamsMaps.put("page", mCurrentPage);
        mParamsMaps.put("isConpon", 1);
        if (TextUtil.isNotEmpty(searchKey)) {
            mParamsMaps.put("goodsName", searchKey);
        }

        DataManager.getInstance().findGoodsList(mParamsMaps, new DefaultSingleObserver<CommodityDetailListDto>() {
            @Override
            public void onSuccess(CommodityDetailListDto data) {
                if (null != data.getRows() && !data.getRows().isEmpty()) {
                    if (mCurrentPage == 1) {
                        mAdapter.setNewData(data.getRows());
                        refreshLayout.setRefreshing(false);
                    } else {
                        mAdapter.addData(data.getRows());
                        refreshLayout.setLoadMore(false);
                    }
                } else {
                    EmptyView emptyView = new EmptyView(VoucherProductListActivity.this);
                    if (!TextUtils.isEmpty(searchKey)) {
                        emptyView.setTvEmptyTip(String.format("没搜索到%s相关数据", searchKey));
                    } else {
                        emptyView.setTvEmptyTip("暂无商品数据");
                    }
                    mAdapter.setEmptyView(emptyView);
                }
                mSwipeRefreshLayoutUtil.isMoreDate(mCurrentPage, Constants.PAGE_SIZE, data.getTotal());
            }

            @Override
            public void onError(Throwable throwable) {
                if (mCurrentPage == 1) {
                    refreshLayout.setRefreshing(false);
                } else {
                    refreshLayout.setLoadMore(false);
                }
            }
        });
    }

    @Override
    public void initListener() {

        bindClickEvent(tv_shop_product_1, () -> {
            setTopTitlesView(tv_shop_product_1);
        });
        bindClickEvent(actionbarBack, () -> {
            finish();
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtil.isNotEmpty(charSequence.toString())){
                    llbg.setVisibility(View.GONE);
                    searchKey=charSequence.toString();
                }else {
                    llbg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        bindClickEvent(tv_shop_product_2, () -> {
            setTopTitlesView(tv_shop_product_2);
        });
        bindClickEvent(ll_shop_product_3, () -> {
            setTopTitlesView(ll_shop_product_3);
            setTopTitles3View();
        });
        bindClickEvent(ll_shop_product_4, () -> {
            setTopTitlesView(ll_shop_product_4);
            SelectProductDialog dialog = new SelectProductDialog(this, mCategoryId, new SelectProductDialog.SelectProductListener() {

                @Override
                public void callbackSelectProduct(String price, String type, String color) {
                    LogUtil.i(TAG, "-- callbackSelectProduct price=" + price + " |type=" + type + " |color=" + color);
                }
            });
            dialog.show();
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommodityDetailInfoDto item = (CommodityDetailInfoDto) adapter.getItem(position);
                startActivityCommodityDetail(item.getId());
            }
        });
        mSwipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
        mSwipeRefreshLayoutUtil.setSwipeRefreshView(refreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                getProductListData();
            }

            @Override
            public void onLoadMore() {
                // mSwipeRefreshLayoutUtil.setCanLoadMore(true);
                mCurrentPage++;
                getProductListData();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_voucher_product_list_layout;
    }


    private void setTopTitlesView(View view) {
        mCurrentPage = 1;
        mParamsMaps.put("order", "");
        switch (view.getId()) {
            case R.id.tv_shop_product_1:
                loadDataType = ProductListType.synthesize.getType();
                tv_shop_product_1.setSelected(true);
                tv_shop_product_2.setSelected(false);
                tv_shop_product_3.setSelected(false);
                tv_shop_product_4.setSelected(false);
                iv_shop_product_3.setTag("priceSelect");
                iv_shop_product_3.setImageResource(R.mipmap.shop_product_price_litre);
                mParamsMaps.put("prop", "");
                getProductListData();
                break;
            case R.id.tv_shop_product_2:
                loadDataType = ProductListType.sales.getType();
                tv_shop_product_1.setSelected(false);
                tv_shop_product_2.setSelected(true);
                tv_shop_product_3.setSelected(false);
                tv_shop_product_4.setSelected(false);
                iv_shop_product_3.setTag("priceSelect");
                iv_shop_product_3.setImageResource(R.mipmap.shop_product_price_litre);
                mParamsMaps.put("prop", "sales_total");
                getProductListData();
                break;
            case R.id.ll_shop_product_3:
                tv_shop_product_1.setSelected(false);
                tv_shop_product_2.setSelected(false);
                tv_shop_product_3.setSelected(true);
                tv_shop_product_4.setSelected(false);
                break;
            case R.id.ll_shop_product_4:
                loadDataType = ProductListType.screening.getType();
                tv_shop_product_1.setSelected(false);
                tv_shop_product_2.setSelected(false);
                tv_shop_product_3.setSelected(false);
                tv_shop_product_4.setSelected(true);
                iv_shop_product_3.setTag("priceSelect");
                iv_shop_product_3.setImageResource(R.mipmap.shop_product_price_litre);
                break;
        }
    }

    private void setTopTitles3View() {
        mCurrentPage = 1;
        mParamsMaps.put("prop", "gd_price");
        if (iv_shop_product_3.getTag().equals("priceSelect")) {
            loadDataType = ProductListType.priceLitre.getType();
            iv_shop_product_3.setTag("unPriceSelect");
            iv_shop_product_3.setImageResource(R.mipmap.shop_product_price_drop);
            mParamsMaps.put("order", "desc");
        } else {
            loadDataType = ProductListType.priceDrop.getType();
            iv_shop_product_3.setTag("priceSelect");
            iv_shop_product_3.setImageResource(R.mipmap.shop_product_price_litre);
            mParamsMaps.put("order", "asc");
        }
        getProductListData();
    }

    /**
     * 启动商品详细
     *
     * @param product_id 商品ID
     */
    private void startActivityCommodityDetail(long product_id) {
        Bundle bundle = new Bundle();
        bundle.putLong(CommodityDetailActivity.PRODUCT_ID, product_id);
        gotoActivity(CommodityDetailActivity.class, false, bundle);
    }



}
