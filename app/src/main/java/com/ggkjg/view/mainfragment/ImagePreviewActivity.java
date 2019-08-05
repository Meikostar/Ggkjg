package com.ggkjg.view.mainfragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.dto.ShopEvaluateImgListDto;
import com.ggkjg.view.adapter.ImagePageAdapter;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class ImagePreviewActivity extends BaseActivity {
    @BindView(R.id.vp_image_pager)
    ViewPager mViewPager;
    @BindView(R.id.tv_page)
    TextView tvPage;
    ImagePageAdapter mAdapter;

    public static final String INTENT_KEY_DATA = "intent_key_data";//调用者传递的名字
    public static final String INTENT_KEY_START_POSITION = "intent_key_start_position";//调用者传递的名字
    private List<ShopEvaluateImgListDto> mDataList;
    private int mCurPosition;

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_image_preview_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mDataList = (List<ShopEvaluateImgListDto>) getIntent().getSerializableExtra(INTENT_KEY_DATA);//获取list
        mCurPosition = getIntent().getExtras().getInt(INTENT_KEY_START_POSITION, 0);
        ArrayList<String> images = new ArrayList<>();
        for (ShopEvaluateImgListDto shopEvaluateImgListDto:mDataList) {
            images.add(shopEvaluateImgListDto.getImgPath());
        }
        mAdapter = new ImagePageAdapter(this, images);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mViewPager.setCurrentItem(mCurPosition, false);
        mAdapter.setPhotoViewClickListener(new ImagePageAdapter.PhotoViewClickListener() {
            @Override
            public void OnPhotoTapListener(View view, float v, float v1) {
            }
        });
        mViewPager.setCurrentItem(mCurPosition);
        updateActionbarTitle();
    }

    private ViewPager.SimpleOnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mCurPosition = position;
            if (mDataList != null && position < mDataList.size()) {
                updateActionbarTitle();
            }
        }
    };

    //更新Title
    private void updateActionbarTitle() {
        if (tvPage != null) {
            tvPage.setText(String.format("%1$s/%2$s", String.valueOf(mCurPosition + 1), String.valueOf(mDataList.size())));
        }
    }
}
