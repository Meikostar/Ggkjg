package com.ggkjg.dto;

import java.io.Serializable;
import java.util.List;

/**
 *  确认订单页面获取商品重量，总价，快递方式，数目
 *  Created by rzb on 2019/3/15
 */

public class CartAtrrDto implements Serializable {



  public String sumWeight;
  public String sumFreight;
  public boolean isChoose;
  public String sumgdPrice;
  public String sumCartNum;
  public String isConpon;
  public String conponCount;
  public List<CartAtrrDto> cartInfoRspList;
  public List<CartAtrrDto> conponList;
  public CartAtrrDto conponBase;
  public CartAtrrDto conpon;
  public String id;
  public String conponName;
  public String numberLimit;
  public String imageUrl;
  public String fullPrice;
  public String addPrice;
  public String subPrice;
  public String discountRate;
  public String createTime;
  public String longTime;
  public String goodsId;
  public String categoryId;
  public String sort;
  public String applyType;
  public String conponType;
  public String conponStatus;

  public String userId;
  public String conponBaseId;
  public String startTime;
  public String endTime;
  public String grantTime;
  public String useTime;
  public String isPayout;

  public String memberId;
  public String tempId;
  public String goodsImg;
  public String weight;
  public String freight;
  public String goodsName;
  public String gdPrice;
  public String cartNum;
  public String stockTotal;
  public String specId;
  public String specName;
  public String oneCatsId;
  public String twoCatsId;
  public String threeCatsId;
  public String activePrice;
  public String isNowSedKill;

}
