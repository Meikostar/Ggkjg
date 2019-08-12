package com.ggkjg.view.mainfragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.ShopSpikeAdapter;
import com.ggkjg.view.adapter.ShopVoucherAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 购物车
 * Created by dahai on 2019/01/18.
 */

public class VoucherFragment extends BaseFragment {
    private static final String TAG = VoucherFragment.class.getSimpleName();

    @BindView(R.id.recy_spike_cart)
    RecyclerView recySpikeCart;
    Unbinder unbinder;
    private ShopVoucherAdapter shopSpikeAdapter;


    private Set<ShopCartDto> selectList = new HashSet<>();//被选中的资源

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_voucher_layout;
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recySpikeCart.setLayoutManager(linearLayoutManager);


    }
   private List<ShopCartDto> datas=new ArrayList<>();
    @Override
    protected void initData() {
        shopSpikeAdapter = new ShopVoucherAdapter(null);
        recySpikeCart.setAdapter(shopSpikeAdapter);
        datas.add(new ShopCartDto());
        datas.add(new ShopCartDto());
        datas.add(new ShopCartDto());
        datas.add(new ShopCartDto());
        datas.add(new ShopCartDto());
        shopSpikeAdapter.setNewData(datas);

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
        findShoppingCartList();

        super.onResume();
    }

    @Override
    protected void initListener() {

        shopSpikeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


            }
        });


    }


    /**
     * 获取购物车列表
     */
    private void findShoppingCartList() {
        showLoadDialog();
        DataManager.getInstance().findMemberConpon(new DefaultSingleObserver<List<ShopCartDto>>() {
            @Override
            public void onSuccess(List<ShopCartDto> shopCartDtoList) {
                dissLoadDialog();


            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
