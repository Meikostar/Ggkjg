package com.ggkjg.http;

import com.ggkjg.db.bean.WEIXINREQ;
import com.ggkjg.dto.AccountBalanceDto;
import com.ggkjg.dto.AddreessDataDto;
import com.ggkjg.dto.AddressDto;
import com.ggkjg.dto.AdsDataDto;
import com.ggkjg.dto.AdsDto;
import com.ggkjg.dto.AgreeMentDto;
import com.ggkjg.dto.AreaDataDto;
import com.ggkjg.dto.ArticleDto;
import com.ggkjg.dto.BoundsDataDto;
import com.ggkjg.dto.BusinessDto;
import com.ggkjg.dto.BusinessListDto;
import com.ggkjg.dto.CategoryDto;
import com.ggkjg.dto.ChooseAddressDto;
import com.ggkjg.dto.CollectionDto;
import com.ggkjg.dto.CommodityDetailDto;
import com.ggkjg.dto.CommodityDetailListDto;
import com.ggkjg.dto.ConfirmOrderDto;
import com.ggkjg.dto.DataPageDto;
import com.ggkjg.dto.DistributeDto;
import com.ggkjg.dto.FavoriteStateDto;
import com.ggkjg.dto.FeedBackTypeDto;
import com.ggkjg.dto.GoodsColorAndSpecDto;
import com.ggkjg.dto.GoodsColorDto;
import com.ggkjg.dto.GoodsOrderDetailDto;
import com.ggkjg.dto.GoodsPushDto;
import com.ggkjg.dto.GoodsPushRowsDto;
import com.ggkjg.dto.GoodsSpecDto;
import com.ggkjg.dto.HelpDto;
import com.ggkjg.dto.HomeActiveIndexDto;
import com.ggkjg.dto.HomeAdsDto;
import com.ggkjg.dto.HomeCategoryIndexDto;
import com.ggkjg.dto.HomeDto;
import com.ggkjg.dto.HomeGoodsIndexDto;
import com.ggkjg.dto.HotSearchDto;
import com.ggkjg.dto.LoginDto;
import com.ggkjg.dto.LogisticsDataDto;
import com.ggkjg.dto.MemberLevelDto;
import com.ggkjg.dto.MyOrderDto;
import com.ggkjg.dto.MyccRecordDto;
import com.ggkjg.dto.PayOrderDto;
import com.ggkjg.dto.QRcodeShareDto;
import com.ggkjg.dto.RealNameDto;
import com.ggkjg.dto.RechargeDto;
import com.ggkjg.dto.RecommendDto;
import com.ggkjg.dto.MessageListDto;
import com.ggkjg.dto.ServiceTelDto;
import com.ggkjg.dto.ShareGoodsDto;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.dto.ShopEvaluateDto;
import com.ggkjg.dto.ShopEvaluateTotalDto;
import com.ggkjg.dto.SpikeDto;
import com.ggkjg.dto.SystemMessageDataDto;
import com.ggkjg.dto.UserInfoDto;
import com.ggkjg.dto.SquareDto;
import com.ggkjg.dto.StoreCategoryDto;
import com.ggkjg.dto.CartAtrrDto;
import com.ggkjg.dto.BrowerDto;
import com.ggkjg.dto.VoucherDto;
import com.ggkjg.http.response.HttpResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 请求方法接口
 * Created by win764-1 on 2016/12/12.
 */

public interface RetrofitService {

    /**
     * 获取短信验证码
     */
    @POST("silent/member/getMobileVerifyCode")
    Single<HttpResult<String>> getMobileVerifyCode(@Query("mobileNo") String mobileNo, @Query("pictureCode") String positionCode);

    /**
     * 会员注册
     */
    @POST("silent/member/register")
    Single<HttpResult<Object>> register(@Query("mobileNo") String mobileNo, @Query("pictureCode") String positionCode, @Query("smsCode") String smsCode,
                                        @Query("loginPwd") String loginPwd, @Query("pushCode") String pushCode);

    /**
     * 手机号登录
     */

    @POST("silent/member/login")
    Single<HttpResult<LoginDto>> phoneLogin(@Query("mobileNo") String mobileNo, @Query("loginPwd") String loginPwd);

    /**
     * 忘记密码
     */
    @POST("silent/member/forgetPwd")
    Single<HttpResult<Object>> forgetPwd(@Query("mobileNo") String mobileNo, @Query("pictureCode") String pictureCode, @Query("smsCode") String smsCode, @Query("newPwd") String newPwd);

    /**
     * 帮助中心
     */
    @POST("silent/member/findHelpCenter")
    Single<HttpResult<HelpDto>> helpCenter(@Query("page") int page, @Query("rows") int rows);


    /**
     * 商学院首页
     */
    @POST("silent/store/findCommercialCollegeIndex")
    Single<HttpResult<BusinessDto>> findCommercialCollegeIndex();


    /**
     * 商学院首页
     */
    @POST("noSilent/member/findMemberConponIsPayout")
    Single<HttpResult<List<VoucherDto>>> findMemberConponIsPayout();

    /**
     * 商学院首页
     */
    @POST("noSilent/member/payoutConpon")
    Single<HttpResult<Object>> payoutConpon(@Query("memberIds") String memberIds, @Query("conponId") String conponId);


    /**
     * 获取可用优惠券列表
     */
    @POST("noSilent/member/findMemberConpon")
    Single<HttpResult<List<VoucherDto>>> findMemberConpon(@Query("conponStatus") long conponStatus);

    /**
     * 商学院列表
     */
    @POST("silent/store/findCommercialCollegeList")
    Single<HttpResult<BusinessListDto>> findCommercialCollegeList(@QueryMap HashMap<String, String> map);
    /**
     * 获取反馈类型
     */
    @POST("silent/member/findFeedBackType")
    Single<HttpResult<List<FeedBackTypeDto>>> getFeedBackType();

    /**
     * 获取商城分类(全部)
     */
    @POST("silent/store/findStoreCategory")
    Single<HttpResult<List<StoreCategoryDto>>> findStoreCategory();

    /**
     * 获取商城分类(点击)
     */
    @POST("silent/store/findStoreCategoryById")
    Single<HttpResult<StoreCategoryDto>> findStoreCategoryById(@Query("categoryId") String categoryId);

    /**
     * 广场列表
     */
    @POST("noSilent/member/findTransferCenter")
    Single<HttpResult<SquareDto>> getSquareList(@Query("page") int page,
                                                @Query("rows") int rows, @Query("transferType") int transferType);

    /**
     * 会员发布交易
     */
    @POST("noSilent/member/publishTransfer")
    Single<HttpResult<Object>> publishTransfer(@QueryMap HashMap<String, String> map);

    /**
     * 意见与反馈
     */
    @POST("noSilent/member/submitFeedBack")
    Single<HttpResult<String>> submitFeedBack(@Query("type") String type, @Query("name") String name, @Query("content") String content);

    /**
     * 获取会员基本信息
     */
    @POST("noSilent/member/findMemberBaseInfo")
    Single<HttpResult<UserInfoDto>> getMemberBaseInfo();

    /**
     * 获取地区信息(省市区)
     */
    @POST("silent/store/findSysArea")
    Single<HttpResult<AreaDataDto>> getSysArea(@Query("parentId") String parentId);

    /**
     * 获取账户余额
     */
    @POST("noSilent/member/findAccountBalance")
    Single<HttpResult<List<AccountBalanceDto>>> findAccountBalance(@Query("fundType") String fundType);

    /**
     * 修改昵称
     */
    @POST("noSilent/member/modifyNickName")
    Single<HttpResult<Object>> modifyNickName(@Query("nickName") String nickName);

    /**
     * 修改登录密码
     */
    @POST("noSilent/member/modifyLoginPwd")
    Single<HttpResult<Object>> modifyLoginPwd(@QueryMap HashMap<String, String> map);

    /**
     * 设置支付密码
     */
    @POST("noSilent/member/setTradePwd")
    Single<HttpResult<Object>> setTradePwd(@Query("newPwd") String newPwd);

    /**
     * 修改交易密码
     */
    @POST("noSilent/member/modifyTradePwd")
    Single<HttpResult<Object>> modifyTradePwd(@QueryMap HashMap<String, String> map);

    /**
     * 获取收货地址列表
     */
    @POST("noSilent/member/findReceiptAddrList")
    Single<HttpResult<AddreessDataDto>> getReceiptAddrList(@Query("page") int page, @Query("rows") int rows);

    /**
     * 添加收货地址
     */
    @POST("noSilent/member/addReceiptAddr")
    Single<HttpResult<Object>> addReceiptAddr(@QueryMap HashMap<String, String> map);

    /**
     * 修改收货地址
     */
    @POST("noSilent/member/modifyReceiptAddr")
    Single<HttpResult<Object>> modifyReceiptAddr(@QueryMap HashMap<String, String> map);

    /**
     * 删除收货地址
     */
    @POST("noSilent/member/delReceiptAddr")
    Single<HttpResult<Object>> delReceiptAddr(@Query("addressId") String addressId);

    /**
     * 获取默认收货地址
     */
    @POST("noSilent/member/findDefaultReceiptAddr")
    Single<HttpResult<AddressDto>> findDefaultReceiptAddr();

    /**
     * 获取自提地址
     */
    @POST("noSilent/store/takeList")
    Single<HttpResult<List<ChooseAddressDto>>> takeList();

    /**
     * 获取会员等级
     */
    @POST("silent/member/findMemberLevel")
    Single<HttpResult<List<MemberLevelDto>>> findMemberLevel();

    /**
     * 获取团队信息
     */
    @POST("noSilent/member/findMyTeam")
    Single<HttpResult<DataPageDto<RecommendDto>>> findMyTeam(@QueryMap HashMap<String, String> map);

    /**
     * 会员等级申请
     */
    @POST("noSilent/member/memberLevelUpgrade")
    Single<HttpResult<Object>> memberLevelUpgrade(@QueryMap HashMap<String, String> map);


    /**
     * 获取资讯列表
     */
    @POST("cmsinfo/cmsinfo/CmsInfo/pageBy")
    Single<HttpResult<MessageListDto>> getHomeMessages(@QueryMap HashMap<String, String> map);

    /**
     * 获取精选好货
     */
    @POST("silent/store/findGoodsIndex")
    Single<HttpResult<HomeDto>> findGoodsIndex();

    /**
     * 获取精选好货
     */
    @POST("silent/store/findGoodsSedKill")
    Single<HttpResult<SpikeDto>> findGoodsSedKill();

    /**
     * 获取精选好货
     */
    @POST("silent/store/findGoodsSedKill")
    Single<HttpResult<SpikeDto>> findGoodsSedKill(@QueryMap HashMap<String, String> map);

    /**
     * 获取首页活动商品
     */
    @POST("silent/store/findQualityGoodsList")
    Single<HttpResult<GoodsPushDto>> findActiveIndex();

    /**
     * 获取精品推荐
     */
    @POST("silent/store/findQualityGoodsList")
    Single<HttpResult<GoodsPushDto>> findQualityList();

    /**
     * 获取精品推荐(分页)
     */
    @POST("silent/store/findQualityGoodsList")
    Single<HttpResult<GoodsPushDto>> findQualityGoodsList(@Query("page") int page, @Query("rows") int rows);

    /**
     * 获取商品详情
     */
    @POST("silent/store/findGoodsDetail")
    Single<HttpResult<CommodityDetailDto>> findGoodsDetail(@Query("goodsId") long goodsId);

    /**
     * 获取商品二维码路径
     */
    @POST("silent/store/findGoodsQrCode")
    Single<HttpResult<String>> findGoodsQrCode(@Query("goodsId") long goodsId);

    /**
     * 获取商品颜色
     */
    @POST("silent/store/findGoodsColor")
    Single<HttpResult<List<GoodsColorDto>>> findGoodsColor(@Query("goodsId") long goodsId);

    /**
     * 获取商品颜色 （接口修改后）
     */
    @POST("silent/store/findGoodsColor")
    Single<HttpResult<GoodsColorAndSpecDto>> findGoodsColor1(@Query("goodsId") long goodsId);

    /**
     * 获取商品规格库存
     */
    @POST("silent/store/findGoodsSpec")
    Single<HttpResult<List<GoodsSpecDto>>> findGoodsSpec(@Query("goodsId") long goodsId, @Query("colorId") long colorId);

    /**
     * 获取商品规格库存数和规格id(接口修改后)
     */
    @POST("silent/store/findGoodsSpec")
    Single<HttpResult<GoodsSpecDto>> findGoodsSpec(@Query("goodsId") long goodsId,@Query("colorIds") String colorIds);

    /**
     * 获取商品重量，总价等
     */
    @POST("noSilent/store/calculateBill")
    Single<HttpResult<CartAtrrDto>> calculateBill(@Query("cardIds") String cardIds);


    /**
     * 获取收藏列表
     */
    @POST("silent/store/favoriteList")
    Single<HttpResult<DataPageDto<CollectionDto>>> favoriteList(@Query("page") int page, @Query("rows") int rows);

    /**
     * 添加/取消收藏
     */
    @POST("silent/store/addFavorite")
    Single<HttpResult<Object>> addFavorite(@Query("goodsId") long goodsId, @Query("opType") int opType);

    /**
     * 获取商品收藏状态
     */
    @POST("noSilent/store/findEvaState")
    Single<HttpResult<FavoriteStateDto>> findEvaState(@Query("goodsId") long goodsId);


    /**
     * 获取个人中心广告位
     */
    @POST("silent/store/findCenterPosition")
    Single<HttpResult<List<AdsDataDto>>> findCenterPosition();

    /**
     * 购物车列表
     */
    @POST("noSilent/store/findShoppingCartList")
    Single<HttpResult<List<ShopCartDto>>> findShoppingCartList();




    /**
     * 删除购物车
     */
    @POST("noSilent/store/delShoppingCart")
    Single<HttpResult<Object>> delShoppingCart(@Query("cartId") long cartId);

    /**
     * 修改购物车
     */
    @POST("noSilent/store/modifyShoppingCart")
    Single<HttpResult<Object>> modifyShoppingCart(@Query("cartId") long cartId, @Query("cartNum") int cartNum);

    /**
     * 添加购物车
     */
    @POST("noSilent/store/addShoppingCart")
    Single<HttpResult<Object>> addShoppingCart(@Query("goodsId") long cartId, @Query("specId") long specId, @Query("cartNum") int cartNum);


    /**
     * 获取会员二维码路径
     */
    @POST("noSilent/member/findMemberQrCode")
    Single<HttpResult<String>> findMemberQrCode(@Query("referralCode") String referralCode);

    /**
     * 获取支付方式
     */
    @POST("noSilent/store/findPaymentList")
    Single<HttpResult<List<PayOrderDto>>> findPaymentList();


    /**
     * 获取某个分类的商品列表
     */
    @POST("silent/store/findGoodsList")
    @FormUrlEncoded
    Single<HttpResult<CommodityDetailListDto>> findGoodsList(@FieldMap Map<String, Object> map);

    /**
     * 获取二级分类/颜色 (商品筛选)
     */
    @POST("silent/store/findCategoryAndColor")
    Single<HttpResult<CategoryDto>> findCategoryAndColor(@Query("categoryId") int categoryId);

    /**
     * 获取首页广告列表
     *
     * @return
     */
    @POST("silent/store/findIndexPosition")
    Single<HttpResult<List<HomeAdsDto>>> findHomeAdsList();

    /**
     * 获取首页分类
     *
     * @return
     */
    @POST(" silent/store/findCategoryIndex")
    Single<HttpResult<List<HomeCategoryIndexDto>>> findCategoryIndex();


    /**
     * 获取订单列表
     */
    @POST("noSilent/store/findOrderList")
    Single<HttpResult<List<GoodsOrderDetailDto>>> findOrderList(@Query("orderStatus") String orderStatus);

    /**
     * 订单详情
     */
    @POST("noSilent/store/findOrderDetail")
    Single<HttpResult<List<MyOrderDto>>> findOrderDetail(@Query("orderId") String orderId);

    /**
     * 取消订单
     */
    @POST("noSilent/store/cancelOrder")
    Single<HttpResult<Object>> cancelOrder(@Query("orderId") String orderId);


    /**
     * 确认收货
     */
    @POST("noSilent/store/confirmOrder")
    Single<HttpResult<Object>> confirmOrder(@Query("orderId") String orderId);

    /**
     * 订单支付
     */
    @POST("noSilent/store/submitOrder")
    Single<HttpResult<RechargeDto>> submitOrder(@QueryMap HashMap<String, String> map);
    /**
     * 订单支付
     */
    @POST("noSilent/store/submitOrder")
    Single<HttpResult<WEIXINREQ>> submitWxOrder(@QueryMap HashMap<String, String> map);

    /**
     * 上传头像
     *
     * @return
     */
    @Multipart
    @POST("noSilent/member/uploadHeadImg")
    Single<HttpResult<Object>> uploadHeadImg(@Part() MultipartBody.Part file);

    /**
     * 实名认证
     */
    @POST("noSilent/member/addRealNameApply")
    Single<HttpResult<Object>> addRealNameApply(@Query("realName") String realName, @Query("identifyNo") String identifyNo);

    /**
     * 获取实名认证状态
     */
    @POST("noSilent/member/findRealState")
    Single<HttpResult<RealNameDto>> findRealState();

    /**
     * 上传实名图片
     *
     * @return
     */
    @Multipart
    @POST("noSilent/member/uploadRealImg")
    Single<HttpResult<Object>> uploadRealImg(@Part() List<MultipartBody.Part> files);

    /**
     * 我的MYCC
     */
    @POST("noSilent/fund/findMYCC")
    Single<HttpResult<MyccRecordDto>> findMYCC(@QueryMap HashMap<String, String> map);

    /**
     * 我的HKDT
     */
    @POST("noSilent/fund/findHKDT")
    Single<HttpResult<MyccRecordDto>> findHKDT(@QueryMap HashMap<String, String> map);

    /**
     * 我的奖金
     */
    @POST("noSilent/fund/findMyBonus")
    Single<HttpResult<BoundsDataDto>> findMyBonus(@QueryMap HashMap<String, String> map);

    /**
     * 我的钱包(港豆)
     */
    @POST("noSilent/fund/findGD")
    Single<HttpResult<MyccRecordDto>> findGD(@QueryMap HashMap<String, String> map);

    /**
     * 会员转账
     */
    @POST("noSilent/fund/memberTransfer")
    Single<HttpResult<Object>> memberTransfer(@QueryMap HashMap<String, String> map);

    /**
     * 会员置换
     */
    @POST("noSilent/fund/memberSwap")
    Single<HttpResult<Object>> memberSwap(@QueryMap HashMap<String, String> map);

    /**
     * 获取置换手续费
     */
    @POST("noSilent/fund/findSwapCharge")
    Single<HttpResult<String>> findSwapCharge(@Query("swapNum") String swapNum);

    /**
     * 系统消息
     */
    @POST("platforminfo/platforminfo/pageBy")
    Single<HttpResult<SystemMessageDataDto>> systemMessage(@QueryMap HashMap<String, String> map);

    /**
     * 生成订单
     */
    @POST("noSilent/store/addOrder")
    Single<HttpResult<ConfirmOrderDto>> addOrder(@QueryMap HashMap<String, String> map);

    /**
     * 商品评价
     */
    @POST("noSilent/store/goodsEvaluate")
    Single<HttpResult<Object>> goodsEvaluate(@QueryMap HashMap<String, String> map);

    /**
     * 商品评价有图
     *
     * @return
     */
    @Multipart
    @POST("noSilent/store/goodsEvaluate")
    Single<HttpResult<Object>> goodsEvaluate(@QueryMap HashMap<String, String> map, @Part() List<MultipartBody.Part> files);

    /**
     * 验证手机号码
     *
     * @return
     */
    @POST("noSilent/member/validateMobileNo")
    Single<HttpResult<Object>> validateMobileNo(@QueryMap HashMap<String, String> map);

    /**
     * 设置手机号码
     *
     * @return
     */
    @POST("noSilent/member/settingMobileNo")
    Single<HttpResult<Object>> settingMobileNo(@QueryMap HashMap<String, String> map);

    /**
     * 查询物流信息
     *
     * @return
     */
    @POST("noSilent/store/findExpressInfo")
    Single<HttpResult<LogisticsDataDto>> findExpressInfo(@Query("expressId") String expressId, @Query("expressNo") String expressNo);

    /**
     * 获取热门搜索
     *
     * @return
     */
    @POST("silent/store/findHotSearchList")
    Single<HttpResult<List<HotSearchDto>>> findHotSearchList();

    /**
     * 获取商品评价列表
     *
     * @return
     */
    @POST("silent/store/findAllEvaList")
    Single<HttpResult<ShopEvaluateDto>> findAllEvaList(@QueryMap HashMap<String, Object> map);


    /**
     * 获取我的评价
     *
     * @return
     */
    @POST("noSilent/store/findMyEvaList")
    Single<HttpResult<ShopEvaluateTotalDto>> findMyEvaList(@QueryMap HashMap<String, String> map);


    /**
     * 获取客服电话
     *
     * @return
     */
    @POST("silent/member/findServiceTel")
    Single<HttpResult<ServiceTelDto>> findServiceTel();

    /**
     * 获取注册协议
     *
     * @return
     */
    @POST("silent/member/findAgreeMent")
    Single<HttpResult<AgreeMentDto>> findAgreeMent();
    /**
     * 获取注册协议
     *
     * @return
     */
    @POST("silent/store/findCommercialCollegeDetail")
    Single<HttpResult<ArticleDto>> findCommercialCollegeDetail(@Query("collegeInfoId") String collegeInfoId);


    /**
     * 获取分享参数
     *
     * @return
     */
    @POST("silent/member/findShareParam")
    Single<HttpResult<ShareGoodsDto>> findShareParam(@Query("goodsId") String goodsId);

    /**
     * 会员退出
     *
     * @return
     */
    @POST("noSilent/member/logout")
    Single<HttpResult<Object>> logout();


    /**
     * 获取个人分享参数
     *
     * @return
     */
    @POST("silent/member/findPersonalShareParam")
    Single<HttpResult<QRcodeShareDto>> findPersonalShareParam(@Query("referralCode") String referralCode);


    /**
     * 会员充值
     *
     * @return
     * @paymentId 微信传2 支付宝传3
     * @pamentAmount 金額
     */
    @POST("noSilent/fund/memberRecharge")
    Single<HttpResult<RechargeDto>> memberRecharge(@Query("paymentId") String paymentId, @Query("pamentAmount") String pamentAmount);
    @POST("noSilent/fund/memberRecharge")
    Single<HttpResult<HttpResult<WEIXINREQ>>> memberWxRecharge(@Query("paymentId") String paymentId, @Query("pamentAmount") String pamentAmount);

    /**
     * 获取图片请求接口地址
     * @return
     */

    @POST("fileBrower")
    Single<HttpResult<BrowerDto>> fileBrower();

}
