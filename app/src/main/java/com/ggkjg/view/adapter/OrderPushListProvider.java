package com.ggkjg.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.annotation.ItemProviderTag;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ggkjg.R;
import com.ggkjg.common.utils.UIUtil;
import com.ggkjg.dto.FloorBean;
import com.ggkjg.dto.GoodsPushRowsDto;
import com.lzy.imagepicker.view.GridSpacingItemDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

/**
 * Created by lihaoqi on 2019/1/30.
 */

@ItemProviderTag(
        viewType = 1,
        layout = R.layout.list_my_good_push
)
public class OrderPushListProvider extends BaseItemProvider<FloorBean,BaseViewHolder> {
    @Override
    public void convert(BaseViewHolder helper, FloorBean data, int position) {
        if (data.isHasTitle()){
            helper.setVisible(R.id.title,true);
        }else{
            helper.setVisible(R.id.title,false);
        }
        RecyclerView rvList = helper.getView(R.id.rv_list);
        rvList.setLayoutManager(new GridLayoutManager(mContext,2));
        rvList.addItemDecoration(new GridSpacingItemDecoration(2,10,true));
        List<GoodsPushRowsDto> goodsPushRowsDtos = data.getGoodsPushdetails();
        HomeAdapter homeAdapter = new HomeAdapter(goodsPushRowsDtos,mContext);
        rvList.setAdapter(homeAdapter);
    }

    @Override
    public void onClick(BaseViewHolder helper, FloorBean data, int position) {

    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, FloorBean data, int position) {
        return false;
    }
}
