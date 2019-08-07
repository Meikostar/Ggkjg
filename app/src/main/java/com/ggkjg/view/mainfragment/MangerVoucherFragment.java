package com.ggkjg.view.mainfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.FragmentViewPagerAdapter;
import com.ggkjg.view.adapter.ShopVoucherAdapter;
import com.ggkjg.view.widgets.NoScrollViewPager;

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

public class MangerVoucherFragment extends BaseFragment {
    private static final String TAG = MangerVoucherFragment.class.getSimpleName();


    Unbinder unbinder;
    @BindView(R.id.tv_un_user)
    TextView tvUnUser;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_no_user)
    TextView tvNoUser;
    @BindView(R.id.viewpager_main)
    NoScrollViewPager viewpagerMain;
    private ShopVoucherAdapter shopSpikeAdapter;


    private Set<ShopCartDto> selectList = new HashSet<>();//被选中的资源

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manger_voucher_layout;
    }

    @Override
    protected void initView() {


    }

    private List<Fragment> list_productfragment;   //定义要装fragment的列表
    private FragmentViewPagerAdapter mainViewPagerAdapter;
    @Override
    protected void initData() {
        initFragMents(0);

    }
    private void initFragMents(int poistion) {
        list_productfragment = new ArrayList<>();
        VoucherFragment spikeFragment = new VoucherFragment();
        list_productfragment.add(spikeFragment);
        VoucherFragment spikeFragment1 = new VoucherFragment();
        list_productfragment.add(spikeFragment1);
        VoucherFragment spikeFragment2 = new VoucherFragment();
        list_productfragment.add(spikeFragment2);

        mainViewPagerAdapter = new FragmentViewPagerAdapter(getChildFragmentManager(), list_productfragment);
        viewpagerMain.setAdapter(mainViewPagerAdapter);
        viewpagerMain.setOffscreenPageLimit(2);//设置缓存view 的个数
        viewpagerMain.setCurrentItem(poistion);

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
        viewpagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selection(position,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        bindClickEvent(tvUnUser, () -> {
            selection(0, true);
        });
        bindClickEvent(tvUser, () -> {
            selection(1, true);
        });
        bindClickEvent(tvNoUser, () -> {
            selection(2, true);
        });
        shopSpikeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


            }
        });


    }

    public void selection(int poition, boolean isShow) {
        switch (poition) {
            case 0:
                tvUnUser.setTextColor(getResources().getColor(R.color.my_color_blue));
                tvUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                tvNoUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                if (isShow) {
                    viewpagerMain.setCurrentItem(0, false);
                }

                break;
            case 1:
                tvUnUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                tvUser.setTextColor(getResources().getColor(R.color.my_color_blue));
                tvNoUser.setTextColor(getResources().getColor(R.color.my_color_333333));

                if (isShow) {
                    viewpagerMain.setCurrentItem(1, false);
                }
                break;
            case 2:
                tvUnUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                tvUser.setTextColor(getResources().getColor(R.color.my_color_333333));
                tvNoUser.setTextColor(getResources().getColor(R.color.my_color_blue));
                if (isShow) {
                    viewpagerMain.setCurrentItem(2, false);
                }

                break;
        }
    }

    private void findShoppingCartList() {
        showLoadDialog();
        DataManager.getInstance().findShoppingCartList(new DefaultSingleObserver<List<ShopCartDto>>() {
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
    public void onDestroyView() {
        super.onDestroyView();

    }
}
