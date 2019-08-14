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
import com.ggkjg.dto.VoucherDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.ShopManVoucherAdapter;
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

public class VoucherFragments extends BaseFragment {
    private static final String TAG = VoucherFragments.class.getSimpleName();

    @BindView(R.id.recy_spike_cart)
    RecyclerView recySpikeCart;
    Unbinder unbinder;
    private ShopManVoucherAdapter shopSpikeAdapter;



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

    @Override
    protected void initData() {
        shopSpikeAdapter = new ShopManVoucherAdapter(null);
        recySpikeCart.setAdapter(shopSpikeAdapter);




    }
    public void getData(){
        DataManager.getInstance().findMemberConponIsPayout(new DefaultSingleObserver<List<VoucherDto>>() {
            @Override
            public void onSuccess(List<VoucherDto> helpDto) {
                if(helpDto!=null){
                    shopSpikeAdapter.setNewData(helpDto);
                }
                dissLoadDialog();

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

    @Override
    public void onResume() {

        getData();
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
