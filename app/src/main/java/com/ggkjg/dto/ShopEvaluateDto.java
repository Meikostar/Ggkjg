package com.ggkjg.dto;

import java.io.Serializable;
public class ShopEvaluateDto implements Serializable {

//   "badCount": 0,
//           "evaList": {
//            "total": 21,
//            "rows": [{
//            "id": 35,
//                    "orderId": 124,
//                    "memberId": 5,
//                    "nickName": "Ficks",
//                    "headImg": "/static/uploads/image/head/abdd1eb5-605c-4935-bb2d-59e9b2419633.png",
//                    "goodsId": 1,
//                    "specId": 1,
//                    "goodsScore": 5.00,
//                    "serviceScore": 5.00,
//                    "timeScore": 5.00,
//                    "content": "逛公园",
//                    "replay": null,
//                    "replayTime": null,
//                    "isImg": "2",
//                    "createTime": "2019-02-15 17:49:33",
//                    "modifyTime": "2019-02-15 17:49:33",
//                    "enableFlag": "1",
//                    "imgList": []
//        }]
//    },
//            "goodCount": 6,
//            "imgCount": 0

    private long badCount;
    private ShopEvaluateTotalDto evaList;
    private long goodCount;
    private long imgCount;

    public long getBadCount() {
        return badCount;
    }

    public void setBadCount(long badCount) {
        this.badCount = badCount;
    }

    public ShopEvaluateTotalDto getEvaList() {
        return evaList;
    }

    public void setEvaList(ShopEvaluateTotalDto evaList) {
        this.evaList = evaList;
    }

    public long getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(long goodCount) {
        this.goodCount = goodCount;
    }

    public long getImgCount() {
        return imgCount;
    }

    public void setImgCount(long imgCount) {
        this.imgCount = imgCount;
    }
}
