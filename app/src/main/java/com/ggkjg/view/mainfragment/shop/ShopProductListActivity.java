package com.ggkjg.view.mainfragment.shop;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.ggkjg.view.widgets.RecyclerItemDecoration;
import com.ggkjg.view.widgets.SelectProductDialog;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 产品列表
 * Created by dahai on 2019/01/18.
 */
public class ShopProductListActivity extends BaseActivity {
    private static final String TAG = ShopProductListActivity.class.getSimpleName();
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

    public static final String ACTION_SEARCH_KEY = "activity_action_search_key"; //调用者传参名字
    private String searchKey;
    public static final String PRODUCT_TYPE = "product_type";//调用者传递的名字
    private int mCategoryId = 1;//产品类型:
    private ProductListAdapter mAdapter;
    private HashMap<String, Object> mParamsMaps;
    private int loadDataType = ProductListType.synthesize.getType(); //加载数据类型
    private int mCurrentPage = 1;
    private int state;
    private SwipeRefreshLayoutUtil mSwipeRefreshLayoutUtil;


    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        recyclerView.addItemDecoration(new RecyclerItemDecoration(3, 2));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new ProductListAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        searchKey = getIntent().getStringExtra(ACTION_SEARCH_KEY);
        mCategoryId = getIntent().getExtras().getInt(PRODUCT_TYPE);
        state= getIntent().getExtras().getInt("state");
        mParamsMaps = new HashMap<>();
        mParamsMaps.put("rows", 16);
        if(searchKey != null){
            mParamsMaps.put("goodsName", searchKey);
        }else {
            mParamsMaps.put("categoryId", mCategoryId);
            mParamsMaps.put("goodsName", "");
        }
        if(state==1){
            mParamsMaps.put("all", "1");
        }else {

//            mParamsMaps.put("all", "");
        }
        setTopTitlesView(tv_shop_product_1);
    }
    private int type;
    private void getProductListData() {
        mParamsMaps.clear();
        mParamsMaps = new HashMap<>();
        mParamsMaps.put("rows", 16);
        mParamsMaps.put("page", mCurrentPage);
        if(searchKey != null){
            mParamsMaps.put("goodsName", searchKey);
        }else {
            mParamsMaps.put("categoryId", mCategoryId);
        }
        if(state==1){
            mParamsMaps.put("all", "1");
        }
        if(type==0){
            mParamsMaps.put("prop", "");

        }else if(type==1){
            mParamsMaps.put("prop", "sales_total");
        }else if(type==2){
            mParamsMaps.put("prop", "gd_price");
            if(order.equals("desc")){
                mParamsMaps.put("order", "desc");
            }else {
                mParamsMaps.put("order", "asc");
            }

        }else if(type==3){
            if(TextUtil.isNotEmpty(min)){
                mParamsMaps.put("minPrice", min);
            } if(TextUtil.isNotEmpty(max)){
                mParamsMaps.put("maxPrice", max);
            } if(TextUtil.isNotEmpty(colorId)){
                mParamsMaps.put("colorId", colorId);
            }

        }

        DataManager.getInstance().findGoodsList(mParamsMaps, new DefaultSingleObserver<CommodityDetailListDto>() {
            @Override
            public void onSuccess(CommodityDetailListDto data) {
                EmptyView emptyView = new EmptyView(ShopProductListActivity.this);
                if (null != data.getRows() && !data.getRows().isEmpty()) {
                    if (mCurrentPage == 1) {
                        if(data.getRows().size()>0){
                            mAdapter.setNewData(data.getRows());
                        }else {
                            mAdapter.setNewData(null);
                            emptyView.setTvEmptyTip("暂无商品数据");
                        }

                        refreshLayout.setRefreshing(false);
                    } else {
                        mAdapter.addData(data.getRows());
                        refreshLayout.setLoadMore(false);
                    }
                } else {

                    if(!TextUtils.isEmpty(searchKey)){
                        emptyView.setTvEmptyTip(String.format("没搜索到%s相关数据", searchKey));
                    }else{
                        mAdapter.setNewData(null);
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
     private String min;
     private String max;
     private String colorId;
    @Override
    public void initListener() {
        bindClickEvent(tv_shop_product_1, () -> {
            setTopTitlesView(tv_shop_product_1);
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
            SelectProductDialog dialog = new SelectProductDialog(this, mCategoryId,new SelectProductDialog.SelectProductListener(){

                @Override
                public void callbackSelectProduct(String mins,String maxs, String type, String color) {
                    min=mins;
                    max=maxs;
                    colorId=color;
                    getProductListData();
                }
            });
            dialog.show();
        });
        actionbar.setImageAction(R.mipmap.shop_product_search_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(SearchShopProduct.class);
            }
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
        return R.layout.ui_shop_product_list_layout;
    }


    private void setTopTitlesView(View view) {
        mCurrentPage = 1;
        mParamsMaps.put("goodsName", "");
        switch (view.getId()) {
            case R.id.tv_shop_product_1:
                type=0;
                loadDataType = ProductListType.synthesize.getType();
                tv_shop_product_1.setSelected(true);
                tv_shop_product_2.setSelected(false);
                tv_shop_product_3.setSelected(false);
                tv_shop_product_4.setSelected(false);
                iv_shop_product_3.setTag("priceSelect");
                iv_shop_product_3.setImageResource(R.mipmap.shop_product_price_litre);

                getProductListData();
                break;
            case R.id.tv_shop_product_2:
                type=1;
                loadDataType = ProductListType.sales.getType();
                tv_shop_product_1.setSelected(false);
                tv_shop_product_2.setSelected(true);
                tv_shop_product_3.setSelected(false);
                tv_shop_product_4.setSelected(false);
                iv_shop_product_3.setTag("priceSelect");
                iv_shop_product_3.setImageResource(R.mipmap.shop_product_price_litre);

                getProductListData();
                break;
            case R.id.ll_shop_product_3:
                type=2;
                tv_shop_product_1.setSelected(false);
                tv_shop_product_2.setSelected(false);
                tv_shop_product_3.setSelected(true);
                tv_shop_product_4.setSelected(false);
                break;
            case R.id.ll_shop_product_4:
                type=3;
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
        if (iv_shop_product_3.getTag().equals("priceSelect")) {
            loadDataType = ProductListType.priceLitre.getType();
            iv_shop_product_3.setTag("unPriceSelect");
            iv_shop_product_3.setImageResource(R.mipmap.shop_product_price_drop);

            order= "desc";
        } else {
            loadDataType = ProductListType.priceDrop.getType();
            iv_shop_product_3.setTag("priceSelect");
            iv_shop_product_3.setImageResource(R.mipmap.shop_product_price_litre);
            order= "asc";
        }
        getProductListData();
    }
     private String order= "desc";
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
