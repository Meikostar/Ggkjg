package com.ggkjg.view.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.GoodsPushRowsDto;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.MainActivity;
import com.ggkjg.view.adapter.HomeAdapter;
import com.ggkjg.view.adapter.ShopCartAdapter;
import com.ggkjg.view.mainfragment.login.LoginActivity;
import com.ggkjg.view.mainfragment.order.ConfirmOrderActivity;
import com.ggkjg.view.mainfragment.personalcenter.MessageCenterActivity;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.ggkjg.view.widgets.RecyclerItemDecoration;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

/**
 * 购物车
 * Created by dahai on 2019/01/18.
 */

public class ShopCartFragment extends BaseFragment {
    private static final String TAG = ShopCartFragment.class.getSimpleName();
    @BindView(R.id.recy_shop_cart)
    RecyclerView shopCartRecyclerView;
    private ShopCartAdapter shopCartAdapter;

    @BindView(R.id.recy_shop_cart_recommend)
    RecyclerView recommendRecyclerView;
    private HomeAdapter pushAdapter;

    @BindView(R.id.iv_top_message)
    ImageView iv_top_message;
    @BindView(R.id.iv_top_sweep)
    ImageView iv_top_qrCode;
    @BindView(R.id.tv_shop_cart_submit)
    TextView tv_shop_cart_submit;

    @BindView(R.id.cb_shop_cart_all_sel)
    CheckBox cb_all_select;
    @BindView(R.id.cb_shop_cart_price)
    TextView cb_shop_cart_price;

    @BindView(R.id.rl_shop_cart_bottom)
    RelativeLayout rl_shop_cart_bottom;


    private Set<ShopCartDto> selectList = new HashSet<>();//被选中的资源

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_cart_layout;
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        shopCartRecyclerView.setLayoutManager(linearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recommendRecyclerView.addItemDecoration(new RecyclerItemDecoration(3, 2));
        recommendRecyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void initData() {
        shopCartAdapter = new ShopCartAdapter(null);
        shopCartRecyclerView.setAdapter(shopCartAdapter);
        pushAdapter = new HomeAdapter(null, getActivity());
        recommendRecyclerView.setAdapter(pushAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

    @Override
    public void onResume() {
        selectList.clear();
        updateBottomView(selectList);
        findShoppingCartList();
        findQualityGoodsList();
        super.onResume();
    }

    @Override
    protected void initListener() {
        bindClickEvent(iv_top_qrCode, () -> {
            Intent intent = new Intent();
            intent.setAction(MainActivity.ACTION_SHOP_CART_CAPTURE_CODE);
            getActivity().sendBroadcast(intent);
        }, 2500);
        bindClickEvent(iv_top_message, () -> {
            gotoActivity(MessageCenterActivity.class);
        });
        shopCartAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ShopCartDto item = shopCartAdapter.getItem(position);
                CheckBox checkBox = view.findViewById(R.id.cb_item_shop_cart);
                switch (view.getId()) {
                    case R.id.tv_item_shop_cart_delete:
                        delShoppingCart(item.getId());
                        break;
                    case R.id.increase:
                        setCommodityCountIncrease(shopCartAdapter.getItem(position));
                        updateBottomView(selectList);
                        break;
                    case R.id.decrease:
                        setCommodityCountDecrease(shopCartAdapter.getItem(position));
                        updateBottomView(selectList);
                        break;
                    case R.id.cb_item_shop_cart:
                        if (!checkBox.isChecked()) {
                            selectList.remove(item);
                            item.setSelect(false);
                        } else {
                            selectList.add(item);
                            item.setSelect(true);
                        }
                        updateBottomView(selectList);
                        break;
                }
            }
        });
        bindClickEvent(tv_shop_cart_submit, () -> {
            if (!selectList.isEmpty()) {
                List<ShopCartDto> shopCartDtos = new ArrayList<>();
                for (ShopCartDto shopCartDto : selectList) {
                    shopCartDtos.add(shopCartDto);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConfirmOrderActivity.PRODUCT_LIST, (Serializable) shopCartDtos);
                gotoActivity(ConfirmOrderActivity.class, false, bundle);
            } else {
                ToastUtil.showToast("请选择商品");
            }
        });
        bindClickEvent(cb_all_select, () -> {
            setAllCheckBoxStatus();
        }, 500);

    }


    /**
     * 获取精品推荐
     */
    private void findQualityGoodsList() {
        showLoadDialog();
        DataManager.getInstance().findQualityList(new DefaultSingleObserver<List<GoodsPushRowsDto>>() {
            @Override
            public void onSuccess(List<GoodsPushRowsDto> object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if (object != null && !object.isEmpty()) {
                    pushAdapter.setNewData(object);
                } else {
                    EmptyView emptyView = new EmptyView(getActivity());
                    emptyView.setTvEmptyTip("暂无推荐商品");
                    pushAdapter.setEmptyView(emptyView);
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

    /**
     * 获取购物车列表
     */
    private void findShoppingCartList() {
        showLoadDialog();
        DataManager.getInstance().findShoppingCartList(new DefaultSingleObserver<List<ShopCartDto>>() {
            @Override
            public void onSuccess(List<ShopCartDto> shopCartDtoList) {
                dissLoadDialog();
                if (shopCartDtoList != null && shopCartDtoList.size() > 0) {
                    shopCartAdapter.setNewData(shopCartDtoList);
                    rl_shop_cart_bottom.setVisibility(View.VISIBLE);
                } else {
                    shopCartAdapter.setNewData(shopCartDtoList);
                    EmptyView emptyView = new EmptyView(getActivity());
                    emptyView.setTvEmptyTip("购物车暂无数据");
                    shopCartAdapter.setEmptyView(emptyView);
                    rl_shop_cart_bottom.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        });
    }

    /**
     * 删除购物车商品
     *
     * @param cartId
     */
    private void delShoppingCart(long cartId) {
        showLoadDialog();
        DataManager.getInstance().delShoppingCart(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                dissLoadDialog();
                if (httpResult != null) {
                    ToastUtil.showToast(httpResult.getMsg());
                    if (httpResult.getStatus() == 1) {
                        findShoppingCartList();
                    }

                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, cartId);
    }

    /**
     * 修改购物车商品个数
     *
     * @param cartId
     * @param cartNum
     */
    private void modifyShoppingCart(long cartId, int cartNum) {
        DataManager.getInstance().modifyShoppingCart(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, cartId, cartNum);
    }

    private void setCommodityCountDecrease(ShopCartDto item) {
        int count = item.getCartNum();
        if (count <= 1) {
            count = 1;
        } else {
            --count;
        }
        updateSelectDataCount(item, count);
    }

    private void setCommodityCountIncrease(ShopCartDto item) {
        int count = item.getCartNum();
        if (count >= item.getStockName()) {//getStock()库存数量
            count = item.getStockName();
            ToastUtil.showToast("库存不足");
        } else {
            ++count;
        }
        updateSelectDataCount(item, count);
    }

    /**
     * @param item
     */
    private void updateSelectDataCount(ShopCartDto item, int count) {
        item.setCartNum(count);
        shopCartAdapter.notifyDataSetChanged();
        modifyShoppingCart(item.getId(), count);
    }

    /**
     * 更新底部View状态
     *
     * @param object 选中的集合
     */
    private void updateBottomView(Set<ShopCartDto> object) {
        double price = 0;
        int selectNum = 0;
        if (object != null && !object.isEmpty()) {
            Iterator<ShopCartDto> items = object.iterator();
            while (items.hasNext()) {
                selectNum++;
                ShopCartDto item = items.next();
                price += item.getCartNum() * item.getGdPrice();
            }
        }
        cb_shop_cart_price.setText(price + "");
        //全选状态
        if (shopCartAdapter.getData() != null && !shopCartAdapter.getData().isEmpty()) {
            if (shopCartAdapter.getData().size() == selectNum) {
                cb_all_select.setChecked(true);
            } else {
                cb_all_select.setChecked(false);
            }
        }
    }

    private void setAllCheckBoxStatus() {
        if (!cb_all_select.isChecked()) {
            cb_all_select.setChecked(false);
            selectList.clear();
        } else {
            cb_all_select.setChecked(true);
        }
        if (shopCartAdapter.getData() != null && !shopCartAdapter.getData().isEmpty()) {
            for (int i = 0; i < shopCartAdapter.getData().size(); i++) {
                shopCartAdapter.getData().get(i).setSelect(cb_all_select.isChecked());
                if (cb_all_select.isChecked()) {//全选按钮选中时添加到selectList
                    ShopCartDto item = shopCartAdapter.getData().get(i);
                    item.setSelect(true);
                    selectList.add(item);
                }
            }
        }
        updateBottomView(selectList);
        shopCartAdapter.notifyDataSetChanged();
    }
}
