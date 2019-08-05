package com.ggkjg.view.mainfragment.personalcenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.dto.AreaDataDto;
import com.ggkjg.dto.AreaDto;
import com.ggkjg.dto.DataDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.AreaAdapter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;


public class AreaListActivity extends BaseActivity {
    public static final String TAG = AreaListActivity.class.getName();
    @BindView(R.id.recy_area)
    RecyclerView recyclerView;
    @BindView(R.id.tv_area_parent_name)
    TextView parentName;
    AreaAdapter mAdapter;
    int parendId = -1;
    int currentHierarchy = 0;
    String name;
    private String allName = "";

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_area_list_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            allName = bundle.getString(Constants.INTENT_AREA_NAME);
            currentHierarchy = bundle.getInt(Constants.INTENT_POSITION);
            Serializable serializable = bundle.getSerializable(Constants.INTENT_DATA);
            if (serializable != null) {
                AreaDto areaDto = (AreaDto) serializable;
                name = areaDto.getAreaName();
                parendId = areaDto.getId();
            }
        }
        if (TextUtils.isEmpty(name)) {
            name = "中国";
        }
        parentName.setText(name);
        currentHierarchy = currentHierarchy + 1;
        initAdapter();
    }

    @Override
    public void initData() {

        if (parendId == -1) {
            getArea("0");
        } else {
            getArea(parendId + "");
        }

    }

    private void initAdapter() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new AreaAdapter(this, name);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Object object = adapter.getItem(position);
                AreaDto areaDto = null;
                if (object != null) {
                    areaDto = (AreaDto) object;
                    if (currentHierarchy == 3) {
                        allName = allName + " " + areaDto.getAreaName();
                        Intent intent = new Intent();
                        intent.putExtra("areaName", allName);
                        intent.putExtra("areaId", areaDto.getId() + "");
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        if(TextUtils.isEmpty(allName)){
                            allName = areaDto.getAreaName();
                        }else {
                            allName = allName + " " + areaDto.getAreaName();
                        }

                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.INTENT_DATA, areaDto);
                        bundle.putString(Constants.INTENT_AREA_NAME, allName);
                        bundle.putInt(Constants.INTENT_POSITION, currentHierarchy);
                        Intent intent = new Intent(AreaListActivity.this, AreaListActivity.class);
                        intent.putExtras(bundle);

                        startActivityForResult(intent, Constants.INTENT_REQUESTCODE_AREA);
                    }

                }

            }
        });
    }

    private void getArea(String parendId) {
        showLoadDialog();
        DataManager.getInstance().getSysArea(new DefaultSingleObserver<AreaDataDto>() {
            @Override
            public void onSuccess(AreaDataDto dataDto) {
                dissLoadDialog();
                mAdapter.setNewData(dataDto.getAreaList());
            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, parendId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.INTENT_REQUESTCODE_AREA) {
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }


}
