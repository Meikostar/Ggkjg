package com.ggkjg.view.mainfragment.personalcenter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.AccountBalanceDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.widgets.TabEntity;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HKDTActivity extends BaseActivity {
    private static final String TAG = HKDTActivity.class.getSimpleName();
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.ll_transfer_money)
    View ll_transfer_money;
    @BindView(R.id.ll_transfer_coin)
    View ll_transfer_coin;
    @BindView(R.id.tv_gold_coin)
    TextView tv_gold_coin;

    private String[] mSubTitles = {"全部", "收入", "支出"};
    private ArrayList<CustomTabEntity> mSubTabEntities = new ArrayList<>();



    HKDTFragment fragments[] = new HKDTFragment[3];
    private String fragments_name[] = {"all", "inCome", "outCome"};
    int currentTabIndex = -1;
    @Override
    public void initListener() {
        bindClickEvent(ll_transfer_coin, () -> {
            gotoActivity(HKDTTransferActivity.class);
        });
        bindClickEvent(ll_transfer_money, () -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.INTENT_FLAG, true);
            gotoActivity(HKDTTransferActivity.class, false, bundle);

        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_hkdt_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initTab();
    }

    @Override
    public void initData() {
        switchFragment(0);
    }

    private void initTab() {
        for (int i = 0; i < mSubTitles.length; i++) {
            mSubTabEntities.add(new TabEntity(mSubTitles[i], R.mipmap.icon_arrow, R.mipmap.icon_arrow));
        }
        commonTabLayout.setTabData(mSubTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
    /**
     * 切换页面
     *
     * @param index
     */
    private void switchFragment(int index) {
        if (currentTabIndex == index) {
            return;
        }
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        if (fragments[index] == null) {
            fragments[index] = new HKDTFragment();
            trx.add(R.id.content_layout, fragments[index], fragments_name[index]);
        }
        if (index == 0) {
            fragments[index].setType("");
        } else if (index == 1) {
            fragments[index].setType("1");
        } else if (index == 2) {
            fragments[index].setType("2");
        }
        if (currentTabIndex != -1) {
            trx.hide(fragments[currentTabIndex]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }
    private void getHKDTBalance() {
        showLoadDialog();
        DataManager.getInstance().findAccountBalance(new DefaultSingleObserver<List<AccountBalanceDto>>() {
            @Override
            public void onSuccess(List<AccountBalanceDto> object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                if(object != null&&object.size()>0){
                    tv_gold_coin.setText(object.get(0).getAvailAmount());
                }
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = ");
                dissLoadDialog();
            }
        },3+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHKDTBalance();
    }
}
