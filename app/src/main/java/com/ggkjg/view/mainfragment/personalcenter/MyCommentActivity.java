package com.ggkjg.view.mainfragment.personalcenter;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.view.widgets.TabEntity;
import java.util.ArrayList;
import butterknife.BindView;


/**
 * 我的评论
 */
public class MyCommentActivity extends BaseActivity {
    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.comment_contentLayout)
    FrameLayout mContentLayout;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] mSubTitles = {"全部评价", "有图评价"};
    private ArrayList<CustomTabEntity> mSubTabEntities = new ArrayList<>();

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.ui_my_comment_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        MyCommentFragment allCommentFragment = new MyCommentFragment();
        allCommentFragment.setType(Constants.COMMENT_ALL_DATA_TYPE);
        MyCommentFragment imageCommentFragment = new MyCommentFragment();
        imageCommentFragment.setType(Constants.COMMENT_IMAGE_DATA_TYPE);
        fragments.add(allCommentFragment);
        fragments.add(imageCommentFragment);
        initTab();
    }


    @Override
    public void initData() {

    }

    private void initTab() {
        for (int i = 0; i < mSubTitles.length; i++) {
            mSubTabEntities.add(new TabEntity(mSubTitles[i], R.mipmap.icon_arrow, R.mipmap.icon_arrow));
        }
        commonTabLayout.setTabData(mSubTabEntities, MyCommentActivity.this, R.id.comment_contentLayout, fragments);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

}
