package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详细
 * Created by Dahai on 2018/12/18.
 */

public class CommodityDetailDto implements Serializable {
    private List<CommodityDetailBannerDto> bannerList;
    private List<CommodityDetailAdsDto> adsList;
    private CommodityDetailInfoDto goodsInfo;
    private ShopEvaluateRowsDto evaContent;
    private String evaTotal;
    private int isFavorite;

    public List<CommodityDetailBannerDto> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<CommodityDetailBannerDto> bannerList) {
        this.bannerList = bannerList;
    }

    public List<CommodityDetailAdsDto> getAdsList() {
        return adsList;
    }

    public void setAdsList(List<CommodityDetailAdsDto> adsList) {
        this.adsList = adsList;
    }

    public CommodityDetailInfoDto getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(CommodityDetailInfoDto goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public ShopEvaluateRowsDto getEvaContent() {
        return evaContent;
    }

    public void setEvaContent(ShopEvaluateRowsDto evaContent) {
        this.evaContent = evaContent;
    }

    public String getEvaTotal() {
        return evaTotal;
    }

    public void setEvaTotal(String evaTotal) {
        this.evaTotal = evaTotal;
    }
}
