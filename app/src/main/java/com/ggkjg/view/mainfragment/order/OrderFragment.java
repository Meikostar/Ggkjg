package com.ggkjg.view.mainfragment.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import butterknife.BindView;

/**
 * 我的订单(0:全部, 1:待付款, 2:待发货, 3:待收货, 4:待评论)
 */

public class OrderFragment extends BaseFragment {
    private static final String TAG = OrderFragment.class.getSimpleName();
    private int type = 1;//0:全部, 1:待付款, 2:待发货, 3:待收货, 4:待评论
    @BindView(R.id.recy_order_layout)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_oder_layout;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        switch (getType()) {
            case 0://0:全部
                break;
            case 1://1:待付款
                break;
            case 2://2:待发货
                break;
            case 3://3:待收货
                break;
            case 4://4:待评论
                break;
        }

    }

    @Override
    protected void initListener() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
