package com.ggkjg.view.mainfragment.settings;

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
import com.ggkjg.dto.HelpDto;
import com.ggkjg.dto.HelpItemDto;
import com.ggkjg.dto.LoginDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.MainActivity;
import com.ggkjg.view.adapter.CollectionAdapter;
import com.ggkjg.view.adapter.HelpAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HelpActivity extends BaseActivity {
    @BindView(R.id.recy_view)
    RecyclerView recyclerView;
    HelpAdapter mAdapter;
    private int currentPage = Constants.PAGE_NUM;

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_help_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initAdapter();
    }

    @Override
    public void initData() {
        loadData();
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        List<HelpDto> helpList = new ArrayList<>();

        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new HelpAdapter(null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HelpItemDto helpDto = mAdapter.getItem(position);
                helpDto.setShow(!helpDto.isShow());
                mAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void loadData() {
        showLoadDialog();
        DataManager.getInstance().helpCenter(new DefaultSingleObserver<HelpDto>() {
            @Override
            public void onSuccess(HelpDto helpDto) {
                dissLoadDialog();
                if (helpDto != null) {
                    if (currentPage == Constants.PAGE_NUM) {
                        mAdapter.setNewData(helpDto.getRows());
                    } else {
                        mAdapter.addData(helpDto.getRows());
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        }, currentPage);
    }
}
