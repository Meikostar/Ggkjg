package com.ggkjg.view.mainfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.common.type.TransferType;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.view.mainfragment.square.GoldFragment;
import com.ggkjg.view.widgets.GoldReleaseDialog;
import com.ggkjg.view.widgets.TabEntity;
import com.ggkjg.view.widgets.autoview.AutoViewPager;
import java.util.ArrayList;
import butterknife.BindView;

/**
 * 广场
 * Created by dahai on 2019/01/18.
 */

public class SquareFragment extends BaseFragment {
    private static final String TAG = SquareFragment.class.getSimpleName();
    @BindView(R.id.home_square_common_tab_layout)
    CommonTabLayout commonTabLayout;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mSubTabEntities = new ArrayList<>();
    @BindView(R.id.home_square_vp)
    AutoViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;
    @BindView(R.id.iv_home_square_editor)
    ImageView iv_home_square_editor;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square_layout;
    }

    @Override
    protected void initView() {
        GoldFragment fragment1 = new GoldFragment();
        fragment1.setType(TransferType.push.getType());//港豆出让
        GoldFragment fragment2 = new GoldFragment();
        fragment2.setType(TransferType.pull.getType());//港豆收购
        fragments.add(fragment1);
        fragments.add(fragment2);
        initCommonTabLayout();
        initPagerAdapter();
    }

    private void initCommonTabLayout() {
        String[] titles = getResources().getStringArray(R.array.home_square_titles);
        for (int i = 0; i < titles.length; i++) {
            mSubTabEntities.add(new TabEntity(titles[i], R.mipmap.icon_arrow, R.mipmap.icon_arrow));
        }
        commonTabLayout.setTabData(mSubTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        commonTabLayout.setCurrentTab(0);
        viewPager.setCurrentItem(0);
    }

    private void initPagerAdapter() {
        pagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragments.get(arg0);
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.setScanScroll(false);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        bindClickEvent(iv_home_square_editor, () -> {
            new GoldReleaseDialog(getActivity()).show();
        });
    }

}
