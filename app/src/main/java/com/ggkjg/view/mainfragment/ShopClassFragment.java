package com.ggkjg.view.mainfragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.base.BuildConfig;
import com.ggkjg.common.utils.GlideUtils;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.common.utils.UIUtil;
import com.ggkjg.dto.StoreCategoryDto;
import com.ggkjg.http.error.ApiException;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.MainActivity;
import com.ggkjg.view.adapter.ClassifyOneAdapter;
import com.ggkjg.view.adapter.ClassifyTwoAdapter;
import com.ggkjg.view.mainfragment.personalcenter.MessageCenterActivity;
import com.ggkjg.view.mainfragment.shop.SearchShopProduct;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Action;

/**
 * 商品分类
 * Created by dahai on 2019/01/18.
 */

public class ShopClassFragment extends BaseFragment {
    private static final String TAG = ShopClassFragment.class.getSimpleName();

    @BindView(R.id.rv_one)
    RecyclerView rvOne;
    @BindView(R.id.rv_two)
    RecyclerView rvTwo;
    Unbinder unbinder;
    @BindView(R.id.iv_ad)
    ImageView mIvAd;

    @BindView(R.id.iv_top_message)
    ImageView iv_top_message;
    @BindView(R.id.iv_calss_top_sweep)
    ImageView iv_top_qrCode;
    @BindView(R.id.top_search_view)
    LinearLayout top_search_view;

    private ClassifyOneAdapter mClassifyOneAdapter;
    private ClassifyTwoAdapter mClassifyTwoAdapter;
    private List<StoreCategoryDto> mListCategorys;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_class_layout;
    }

    @Override
    protected void initView() {
        mClassifyOneAdapter = new ClassifyOneAdapter();
        rvOne.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOne.setAdapter(mClassifyOneAdapter);
        rvOne.setSelected(true);
        rvOne.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(Color.parseColor("#f1f1f1"))
                .size(UIUtil.dp2px(1))
                .build());
        mClassifyTwoAdapter = new ClassifyTwoAdapter(getActivity());
        rvTwo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTwo.setAdapter(mClassifyTwoAdapter);
    }

    @Override
    protected void initData() {
        findStoreCategory();
    }

    @Override
    protected void initListener() {
        rvOne.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                int prePos = mClassifyOneAdapter.selctedPos;  //之前的位置
                mClassifyOneAdapter.selctedPos = position; //之后选择的位置
                if (position != prePos) {//更新item的状态
                    mClassifyOneAdapter.notifyItemChanged(prePos);
                    mClassifyOneAdapter.notifyItemChanged(position);
                    mClassifyTwoAdapter.setNewData(mListCategorys.get(position).getCateList());
                    setAdData(mListCategorys.get(position).getImgUrl(), mListCategorys.get(position).getAdClickUrl());
                }
            }
        });

        bindClickEvent(top_search_view, () -> {
            gotoActivity(SearchShopProduct.class);
        });
        bindClickEvent(iv_top_qrCode, () -> {
            Intent intent = new Intent();
            intent.setAction(MainActivity.ACTION_SHOP_CLASS_CAPTURE_CODE);
            getActivity().sendBroadcast(intent);
        }, 2500);
        bindClickEvent(iv_top_message, () -> {
            gotoActivity(MessageCenterActivity.class);
        });
    }


    /**
     * 请求获取商城分类(全部)
     */
    private void findStoreCategory() {
        showLoadDialog();
        DataManager.getInstance().findStoreCategory(new DefaultSingleObserver<List<StoreCategoryDto>>() {
            @Override
            public void onSuccess(List<StoreCategoryDto> data) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess() = " + data);
                dissLoadDialog();
                mListCategorys = data;
                mClassifyOneAdapter.setNewData(data);
                if (mListCategorys.size() > 0) {
                    StoreCategoryDto firstData = mListCategorys.get(0);
                    setAdData(firstData.getImgUrl(), firstData.getAdClickUrl());
                    mClassifyTwoAdapter.setNewData(firstData.getCateList());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread: onError() = " );
                dissLoadDialog();
            }
        });
    }


    private void setAdData(String imgUrl, String adClickUrl) {
        GlideUtils.getInstances().loadNormalImg(getActivity(), mIvAd, BuildConfig.BASE_IMAGE_URL + imgUrl, R.mipmap.img_default_3);
        bindClickEvent(mIvAd, new Action() {
            @Override
            public void run() throws Exception {
                //TODO 实现 跳转 adClickUrl
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
