package com.ggkjg.view.mainfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ggkjg.R;
import com.ggkjg.base.BaseFragment;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.SwipeRefreshLayoutUtil;
import com.ggkjg.common.utils.TextUtil;
import com.ggkjg.dto.DataPageDto;
import com.ggkjg.dto.DistributeDto;
import com.ggkjg.dto.RecommendDto;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.DistributeAdapter;
import com.ggkjg.view.adapter.ShopVoucherAdapter;
import com.ggkjg.view.mainfragment.personalcenter.RecommendActivity;
import com.ggkjg.view.widgets.SuperSwipeRefreshLayout;
import com.ggkjg.view.widgets.autoview.EmptyView;

import java.util.ArrayList;
import java.util.HashMap;
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

public class DistributeFragment extends BaseFragment {
    private static final String TAG = DistributeFragment.class.getSimpleName();

    @BindView(R.id.common_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.common_refresh)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayoutUtil swipeRefreshLayoutUtil;
    Unbinder unbinder;
    private DistributeAdapter shopSpikeAdapter;


    private Set<ShopCartDto> selectList = new HashSet<>();//被选中的资源

    private String column="createTime";
    public void setColumn(String column){
        this.column=column;
    }
    private String mobileNo="";
    public void setSearch(String content){
        mobileNo=content;
        findMyTeam(true);

    }

    public void setChooseAll(int state){
        List<RecommendDto> data = shopSpikeAdapter.getData();
        if(state==1){
            for(RecommendDto dto:data){
                dto.isChoose=true;
            }
        }else {
            for(RecommendDto dto:data){
                dto.isChoose=false;
            }
        }
        shopSpikeAdapter.setNewData(data);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_distribute_layout;
    }
    private int currentPage = Constants.PAGE_NUM;
    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        shopSpikeAdapter = new DistributeAdapter(null);
        recyclerView.setAdapter(shopSpikeAdapter);
        setListener();
    }


    public DisFragmentListener mListener;

    //MainFragment开放的接口
    public static interface DisFragmentListener{

        //跳到h5页面
        void chooseAll(int state);

        //展示消息
        void showMsg(String str);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //对传递进来的Activity进行接口转换
        if(activity instanceof DisFragmentListener){

            mListener = ((DisFragmentListener)activity);

        }
    }
   private List<ShopCartDto> datas=new ArrayList<>();
    @Override
    protected void initData() {
        findMyTeam(true);


    }
    private void setListener() {
        swipeRefreshLayoutUtil = new SwipeRefreshLayoutUtil();
        swipeRefreshLayoutUtil.setSwipeRefreshView(swipeRefreshLayout, new SwipeRefreshLayoutUtil.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = Constants.PAGE_NUM;
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                findMyTeam(false);
            }

            @Override
            public void onLoadMore() {
                swipeRefreshLayoutUtil.setCanLoadMore(true);
                currentPage++;
                findMyTeam(false);
            }
        });

        shopSpikeAdapter.setChooseListener(new DistributeAdapter.ChooseListener() {
            @Override
            public void choose() {
                int i=0;
                List<RecommendDto> data = shopSpikeAdapter.getData();
                for(RecommendDto dto:data){
                    if(dto.isChoose){
                        ++i;
                    }
                }
                if(i==data.size()){
                    mListener.chooseAll(1);
                }else {
                    mListener.chooseAll(0);
                }
            }
        });
    }

    public boolean isChooseAll(){
        int i=0;
        List<RecommendDto> data = shopSpikeAdapter.getData();
        for(RecommendDto dto:data){
            if(dto.isChoose){
                ++i;
            }
        }
        if(i==data.size()){
            return  true;
        }else {
            return  false;
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

        }
    }
    private String ids="";
    public String getIds(){
        ids="";
        int i=0;
        List<RecommendDto> data = shopSpikeAdapter.getData();
        for(RecommendDto dto:data){
            if(dto.isChoose){
                if(i==0){
                    ids=dto.getId();
                }else {
                  ids=ids+","+dto.getId();
                }
                i++;
            }
        }
        return ids;
    }
    @Override
    public void onResume() {
        selectList.clear();


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




    /**
     * 获取团队信息
     */
    private void findMyTeam(boolean isLoad) {
        showLoadDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("page", currentPage + "");
        map.put("type", 2 + "");
        map.put("rows", Constants.PAGE_SIZE + "");
        map.put("column", column);
        if(TextUtil.isNotEmpty(mobileNo)){
            map.put("mobileNo", mobileNo);
        }

        DataManager.getInstance().findMyTeams(new DefaultSingleObserver<RecommendDto>() {
            @Override
            public void onSuccess(RecommendDto dataPageDto) {
                dissLoadDialog();
                mobileNo="";
                if (dataPageDto != null) {
                    if (currentPage == Constants.PAGE_NUM) {
                        shopSpikeAdapter.setNewData(dataPageDto.rows);
                        if(dataPageDto.rows ==null || dataPageDto.rows.size() == 0){
                            shopSpikeAdapter.setEmptyView(new EmptyView(getActivity()));
                        }
                    } else {
                        shopSpikeAdapter.addData(dataPageDto.rows);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                mobileNo="";
                dissLoadDialog();
            }
        }, map);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void dissLoadDialog() {
        super.dissLoadDialog();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setLoadMore(false);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
