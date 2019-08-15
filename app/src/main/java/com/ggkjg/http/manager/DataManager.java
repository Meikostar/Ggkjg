package com.ggkjg.http.manager;

import android.text.TextUtils;

import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.dto.AccountBalanceDto;
import com.ggkjg.dto.AddreessDataDto;
import com.ggkjg.dto.AddressDto;
import com.ggkjg.dto.AdsDataDto;
import com.ggkjg.dto.AdsDto;
import com.ggkjg.dto.AgreeMentDto;
import com.ggkjg.dto.AreaDataDto;
import com.ggkjg.dto.ArticleDto;
import com.ggkjg.dto.BoundsDataDto;
import com.ggkjg.dto.BrowerDto;
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
import com.ggkjg.dto.VoucherDto;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.http.RetrofitHelper;
import com.ggkjg.http.RetrofitService;
import com.ggkjg.http.response.HttpResultMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * 接口数据管理类
 * Created by xld021 on 2018/4/11.
 */
public class DataManager {

    private static final String TAG = DataManager.class.getSimpleName();
    public RetrofitHelper retrofitHelper = RetrofitHelper.getInstance();
    private RetrofitService retrofitService = retrofitHelper.getServer();
    private static final DataManager INSTANCE = new DataManager();

    private <T> Disposable subscribe(Single<T> observable, DefaultSingleObserver<T> observer) {
        LogUtil.i("-- RXLOG-Thread: subscribe()", Long.toString(Thread.currentThread().getId()));
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return observer;
    }

    //获取单例
    public static DataManager getInstance() {
        return INSTANCE;
    }

    /**
     * 用户登录servlet会话token凭证
     *
     * @return
     */
    private String getServletToken() {
        return Constants.getInstance().getString(Constants.USER_SERVLET_TOKEN, "");
    }

    /**
     * 用户登录shiro会话token凭
     *
     * @return
     */
    private String getShiroToken() {
        return Constants.getInstance().getString(Constants.USER_SHIRO_TOKEN, "");
    }

    /**
     * 获取短信验证码
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void getMobileVerifyCode(DefaultSingleObserver<String> observer, String mobileNo, String positionCode) {
        Single<String> observable = retrofitService.getMobileVerifyCode(mobileNo, positionCode)
                .map(new HttpResultMapper.HttpResultString(null));
        subscribe(observable, observer);
    }


    /**
     * 会员注册
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void register(DefaultSingleObserver<HttpResult<Object>> observer, String mobileNo, String positionCode, String smsCode, String loginPwd, String pushCode) {
        Single<HttpResult<Object>> observable = retrofitService.register(mobileNo, positionCode, smsCode, loginPwd, pushCode)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 手机号登录
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void phoneLogin(DefaultSingleObserver<LoginDto> observer, String mobileNo, String loginPwd) {
        Single<LoginDto> observable = retrofitService.phoneLogin(mobileNo, loginPwd)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 忘记密码
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void forgetPwd(DefaultSingleObserver<HttpResult<Object>> observer, String mobileNo, String pictureCode, String smsCode, String newPwd) {
        Single<HttpResult<Object>> observable = retrofitService.forgetPwd(mobileNo, pictureCode, smsCode, newPwd)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 帮助中心
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void helpCenter(DefaultSingleObserver<HelpDto> observer, int page) {
        Single<HelpDto> observable = retrofitService.helpCenter(page, Constants.PAGE_SIZE)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取反馈类型
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void getFeedBackType(DefaultSingleObserver<List<FeedBackTypeDto>> observer) {
        Single<List<FeedBackTypeDto>> observable = retrofitService.getFeedBackType()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 商学院首页
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void findCommercialCollegeIndex(DefaultSingleObserver<BusinessDto> observer) {
        Single<BusinessDto> observable = retrofitService.findCommercialCollegeIndex()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }
    /**
     * 商学院首页
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void findMemberConponIsPayout(DefaultSingleObserver<List<VoucherDto>> observer) {
        Single<List<VoucherDto>> observable = retrofitService.findMemberConponIsPayout()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 商学院列表
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void findCommercialCollegeList(DefaultSingleObserver<BusinessListDto> observer, HashMap<String, String> map) {
        Single<BusinessListDto> observable = retrofitService.findCommercialCollegeList(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 意见与反馈
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void submitFeedBack(DefaultSingleObserver<HttpResult<String>> observer, String type, String name, String content) {
        Single<HttpResult<String>> observable = retrofitService.submitFeedBack(type, name, content)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取会员基本信息
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void getMemberBaseInfo(DefaultSingleObserver<UserInfoDto> observer) {
        Single<UserInfoDto> observable = retrofitService.getMemberBaseInfo()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取商城分类(全部)
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void findStoreCategory(DefaultSingleObserver<List<StoreCategoryDto>> observer) {
        Single<List<StoreCategoryDto>> observable = retrofitService.findStoreCategory()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 广场列表
     *
     * @param observer     由调用者传过来的观察者对象
     * @param page         交易数量
     * @param rows         交易金额
     * @param transferType 交易类型 1-转让 2-收购(TransferType.java)
     */
    public void getSquareList(DefaultSingleObserver<SquareDto> observer, int page, int rows, int transferType) {
        Single<SquareDto> observable = retrofitService.getSquareList(page, rows, transferType)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 会员发布交易
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void publishTransfer(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map) {
        Single<HttpResult<Object>> observable = retrofitService.publishTransfer(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取地区信息(省市区)
     */
    public void getSysArea(DefaultSingleObserver<AreaDataDto> observer, String parentId) {
        Single<AreaDataDto> observable = retrofitService.getSysArea(parentId)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取账户余额
     *
     * @param observer
     * @param fundType 资金类型 1-港豆 2-MYCC 3-HKDT
     */
    public void findAccountBalance(DefaultSingleObserver<AccountBalanceDto> observer, int fundType) {
        Single<AccountBalanceDto> observable = retrofitService.findAccountBalance(fundType)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 修改昵称
     *
     * @param observer
     */
    public void modifyNickName(DefaultSingleObserver<HttpResult<Object>> observer, String nickName) {
        Single<HttpResult<Object>> observable = retrofitService.modifyNickName(nickName)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 修改登录密码
     *
     * @param observer
     */
    public void modifyLoginPwd(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map) {
        Single<HttpResult<Object>> observable = retrofitService.modifyLoginPwd(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 设置支付密码
     *
     * @param observer
     */
    public void setTradePwd(DefaultSingleObserver<HttpResult<Object>> observer, String newPwd) {
        Single<HttpResult<Object>> observable = retrofitService.setTradePwd(newPwd)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 修改交易密码
     *
     * @param observer
     */
    public void modifyTradePwd(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map) {
        Single<HttpResult<Object>> observable = retrofitService.modifyTradePwd(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 获取收货地址列表
     */
    public void getReceiptAddrList(DefaultSingleObserver<AddreessDataDto> observer, int page) {
        Single<AddreessDataDto> observable = retrofitService.getReceiptAddrList(page, Constants.PAGE_SIZE)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 添加收货地址
     */
    public void addReceiptAddr(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map) {
        Single<HttpResult<Object>> observable = retrofitService.addReceiptAddr(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 修改收货地址
     */
    public void modifyReceiptAddr(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map) {
        Single<HttpResult<Object>> observable = retrofitService.modifyReceiptAddr(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 删除收货地址
     */
    public void delReceiptAddr(DefaultSingleObserver<HttpResult<Object>> observer, String id) {
        Single<HttpResult<Object>> observable = retrofitService.delReceiptAddr(id)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取默认收货地址
     */
    public void findDefaultReceiptAddr(DefaultSingleObserver<AddressDto> observer) {
        Single<AddressDto> observable = retrofitService.findDefaultReceiptAddr()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取自提地址
     */
    public void takeList(DefaultSingleObserver<List<ChooseAddressDto>> observer) {
        Single<List<ChooseAddressDto>> observable = retrofitService.takeList()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取会员等级
     */
    public void findMemberLevel(DefaultSingleObserver<List<MemberLevelDto>> observer) {
        Single<List<MemberLevelDto>> observable = retrofitService.findMemberLevel()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取团队信息
     */
    public void findMyTeam(DefaultSingleObserver<DataPageDto<RecommendDto>> observer, int page, String memberLevel) {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", Constants.PAGE_SIZE + "");
        if (!TextUtils.isEmpty(memberLevel)) {
            map.put("memberLevel", memberLevel);
        }
        Single<DataPageDto<RecommendDto>> observable = retrofitService.findMyTeam(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }
    /**
     * 获取团队信息
     */
    public void findMyTeams(DefaultSingleObserver<DataPageDto<RecommendDto>> observer,HashMap<String, String> map) {


        Single<DataPageDto<RecommendDto>> observable = retrofitService.findMyTeam(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }
    /**
     * 会员等级申请
     */
    public void memberLevelUpgrade(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map) {

        Single<HttpResult<Object>> observable = retrofitService.memberLevelUpgrade(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取资讯列表
     */
    public void getHomeMessages(DefaultSingleObserver<MessageListDto> observer, int page, int rows) {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        Single<MessageListDto> observable = retrofitService.getHomeMessages(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取精选好货
     */
    public void findGoodsIndex(DefaultSingleObserver<HomeDto> observer) {
        Single<HomeDto> observable = retrofitService.findGoodsIndex()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 秒杀
     */
    public void findGoodsSedKill(DefaultSingleObserver<SpikeDto> observer) {
        Single<SpikeDto> observable = retrofitService.findGoodsSedKill()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }
    /**
     * 秒杀
     */
    public void findGoodsSedKill(DefaultSingleObserver<SpikeDto> observer  , int page, int rows) {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", rows + "");
        Single<SpikeDto> observable = retrofitService.findGoodsSedKill(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 获取首页活动商品
     */
    public void findActiveIndex(DefaultSingleObserver<List<HomeActiveIndexDto>> observer) {
        Single<List<HomeActiveIndexDto>> observable = retrofitService.findActiveIndex()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取精品推荐
     */
    public void findQualityList(DefaultSingleObserver<List<GoodsPushRowsDto>> observer) {
        Single<List<GoodsPushRowsDto>> observable = retrofitService.findQualityList()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取精品推荐(分页)
     */
    public void findQualityList(DefaultSingleObserver<GoodsPushDto> observer, int page, int rows) {
        Single<GoodsPushDto> observable = retrofitService.findQualityGoodsList(page, rows)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取商品详情
     */
    public void findGoodsDetail(DefaultSingleObserver<CommodityDetailDto> observer, long goodsId) {
        Single<CommodityDetailDto> observable = retrofitService.findGoodsDetail(goodsId)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取商品二维码路径
     */
    public void findGoodsQrCode(DefaultSingleObserver<String> observer, long goodsId) {
        Single<String> observable = retrofitService.findGoodsQrCode(goodsId)
                .map(new HttpResultMapper.HttpResultString(null));
        subscribe(observable, observer);
    }

    /**
     * 获取商品颜色
     */
    public void findGoodsColor(DefaultSingleObserver<List<GoodsColorDto>> observer, long goodsId) {
        Single<List<GoodsColorDto>> observable = retrofitService.findGoodsColor(goodsId)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取商品规格库存
     */
    public void findGoodsSpec(DefaultSingleObserver<List<GoodsSpecDto>> observer, long goodsId, long colorId) {
        Single<List<GoodsSpecDto>> observable = retrofitService.findGoodsSpec(goodsId, colorId)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取商品重量，总价等
     */
    public void calculateBill(DefaultSingleObserver<CartAtrrDto> observer, String cardIds) {
        Single<CartAtrrDto> observable = retrofitService.calculateBill(cardIds)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取商品规格库存（修改）
     */
    public void findGoodsSpec(DefaultSingleObserver<GoodsSpecDto> observer, long goodsId, String colorIds) {
        Single<GoodsSpecDto> observable = retrofitService.findGoodsSpec(goodsId,colorIds)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取收藏列表
     */
    public void favoriteList(DefaultSingleObserver<DataPageDto<CollectionDto>> observer, int page) {
        Single<DataPageDto<CollectionDto>> observable = retrofitService.favoriteList(page, 100)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 添加/取消收藏
     *
     * @param observer
     * @param goodsId  商品ID
     * @param opType   1-收藏 2取消收藏
     */
    public void addFavorite(DefaultSingleObserver<HttpResult<Object>> observer, long goodsId, int opType) {
        Single<HttpResult<Object>> observable = retrofitService.addFavorite(goodsId, opType)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取商品收藏状态
     *
     * @param observer
     * @param goodsId  商品ID
     */
    public void findEvaState(DefaultSingleObserver<FavoriteStateDto> observer, long goodsId) {
        Single<FavoriteStateDto> observable = retrofitService.findEvaState(goodsId)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 获取个人中心广告位
     *
     * @param observer
     */
    public void findCenterPosition(DefaultSingleObserver<List<AdsDataDto>> observer) {
        Single<List<AdsDataDto>> observable = retrofitService.findCenterPosition()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 购物车列表
     *
     * @param observer
     */
    public void findShoppingCartList(DefaultSingleObserver<List<ShopCartDto>> observer) {
        Single<List<ShopCartDto>> observable = retrofitService.findShoppingCartList()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }



    /**
     *可用优惠券
     *
     * @param observer
     */
    public void findMemberConpon(DefaultSingleObserver<List<VoucherDto>> observer,long conponStatus) {
        Single<List<VoucherDto>> observable = retrofitService.findMemberConpon(conponStatus)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     *可用优惠券
     *
     * @param observer
     */
    public void payoutConpon(DefaultSingleObserver<Object> observer,String memberIds,String conponId) {
        Single<Object> observable = retrofitService.payoutConpon(memberIds,conponId)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 删除购物车
     *
     * @param observer
     */
    public void delShoppingCart(DefaultSingleObserver<HttpResult<Object>> observer, long cartId) {
        Single<HttpResult<Object>> observable = retrofitService.delShoppingCart(cartId)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 修改购物车
     *
     * @param observer
     */
    public void modifyShoppingCart(DefaultSingleObserver<HttpResult<Object>> observer, long cartId, int cartNum) {
        Single<HttpResult<Object>> observable = retrofitService.modifyShoppingCart(cartId, cartNum)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 添加购物车
     *
     * @param observer
     * @param goodsId  商品ID
     * @param specId   规格ID
     * @param cartNum  数量
     */

    public void addShoppingCart(DefaultSingleObserver<HttpResult<Object>> observer, long goodsId, long specId, int cartNum) {
        Single<HttpResult<Object>> observable = retrofitService.addShoppingCart(goodsId, specId, cartNum)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取会员二维码路径
     *
     * @param observer
     * @param referralCode 推荐码
     */

    public void findMemberQrCode(DefaultSingleObserver<String> observer, String referralCode) {
        Single<String> observable = retrofitService.findMemberQrCode(referralCode)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取支付方式
     *
     * @param observer
     */

    public void findPaymentList(DefaultSingleObserver<List<PayOrderDto>> observer) {
        Single<List<PayOrderDto>> observable = retrofitService.findPaymentList()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取某个分类的商品列表
     *
     * @param observer
     * @param map
     */
    public void findGoodsList(Map<String, Object> map, DefaultSingleObserver<CommodityDetailListDto> observer) {
        Single<CommodityDetailListDto> observable = retrofitService.findGoodsList(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取二级分类/颜色 (商品筛选)
     *
     * @param categoryId 父级分类ID
     * @param observer
     */
    public void findCategoryAndColor(int categoryId, DefaultSingleObserver<CategoryDto> observer) {
        Single<CategoryDto> observable = retrofitService.findCategoryAndColor(categoryId)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 获取首页广告位列表
     *
     * @param observer
     */
    public void findHomeAdsList(DefaultSingleObserver<List<HomeAdsDto>> observer) {
        Single<List<HomeAdsDto>> observable = retrofitService.findHomeAdsList()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取首页分类
     *
     * @param observer
     */
    public void findCategoryIndex(DefaultSingleObserver<List<HomeCategoryIndexDto>> observer) {
        Single<List<HomeCategoryIndexDto>> observable = retrofitService.findCategoryIndex()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 获取订单列表
     *
     * @param orderStatus
     * @param observer
     */
    public void findOrderList(String orderStatus, DefaultSingleObserver<List<GoodsOrderDetailDto>> observer) {
        Single<List<GoodsOrderDetailDto>> observable = retrofitService.findOrderList(orderStatus)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @param observer
     */
    public void findOrderDetail(DefaultSingleObserver<List<MyOrderDto>> observer, String orderId) {
        Single<List<MyOrderDto>> observable = retrofitService.findOrderDetail(orderId)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @param observer
     */
    public void cancelOrder(DefaultSingleObserver<HttpResult<Object>> observer, String orderId) {
        Single<HttpResult<Object>> observable = retrofitService.cancelOrder(orderId)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 确认收货
     *
     * @param orderId
     * @param observer
     */
    public void confirmOrder(DefaultSingleObserver<HttpResult<Object>> observer, String orderId) {
        Single<HttpResult<Object>> observable = retrofitService.confirmOrder(orderId)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 订单支付
     *
     * @param observer
     */
    public void submitOrder(DefaultSingleObserver<HttpResult<RechargeDto>> observer, HashMap<String, String> map) {
        Single<HttpResult<RechargeDto>> observable = retrofitService.submitOrder(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 上传头像
     *
     * @param observer
     * @param file
     */
    public void uploadHeadImg(DefaultSingleObserver<HttpResult<Object>> observer, MultipartBody.Part file) {
        Single<HttpResult<Object>> observable = retrofitService.uploadHeadImg(file)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 实名认证
     *
     * @param observer
     */
    public void addRealNameApply(DefaultSingleObserver<HttpResult<Object>> observer, String realName, String identifyNo) {
        Single<HttpResult<Object>> observable = retrofitService.addRealNameApply(realName, identifyNo)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取实名认证状态
     *
     * @param observer
     */
    public void findRealState(DefaultSingleObserver<HttpResult<RealNameDto>> observer) {
        Single<HttpResult<RealNameDto>> observable = retrofitService.findRealState()
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 上传实名图片
     *
     * @param observer
     */
    public void uploadRealImg(DefaultSingleObserver<HttpResult<Object>> observer, List<MultipartBody.Part> fileList) {
        Single<HttpResult<Object>> observable = retrofitService.uploadRealImg(fileList)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 我的MYCC
     *
     * @param observer
     */
    public void findMYCC(DefaultSingleObserver<HttpResult<MyccRecordDto>> observer, HashMap<String, String> map) {
        Single<HttpResult<MyccRecordDto>> observable = retrofitService.findMYCC(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 我的HKDT
     *
     * @param observer
     */
    public void findHKDT(DefaultSingleObserver<HttpResult<MyccRecordDto>> observer, HashMap<String, String> map) {
        Single<HttpResult<MyccRecordDto>> observable = retrofitService.findHKDT(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 我的奖金
     *
     * @param observer
     */
    public void findMyBonus(DefaultSingleObserver<BoundsDataDto> observer, HashMap<String, String> map) {
        Single<BoundsDataDto> observable = retrofitService.findMyBonus(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 我的钱包(港豆)
     *
     * @param observer
     */
    public void findGD(DefaultSingleObserver<HttpResult<MyccRecordDto>> observer, HashMap<String, String> map) {
        Single<HttpResult<MyccRecordDto>> observable = retrofitService.findGD(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 会员转账
     *
     * @param observer
     */
    public void memberTransfer(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map) {
        Single<HttpResult<Object>> observable = retrofitService.memberTransfer(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 会员置换
     *
     * @param observer
     */
    public void memberSwap(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map) {
        Single<HttpResult<Object>> observable = retrofitService.memberSwap(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 获取置换手续费
     *
     * @param observer
     */
    public void findSwapCharge(DefaultSingleObserver<HttpResult<String>> observer, String swapNum) {
        Single<HttpResult<String>> observable = retrofitService.findSwapCharge(swapNum)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 系统消息
     *
     * @param observer
     */
    public void systemMessage(DefaultSingleObserver<SystemMessageDataDto> observer, HashMap<String, String> map) {
        Single<SystemMessageDataDto> observable = retrofitService.systemMessage(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 生成订单
     *
     * @param observer
     */
    public void addOrder(DefaultSingleObserver<ConfirmOrderDto> observer, HashMap<String, String> map) {
        Single<ConfirmOrderDto> observable = retrofitService.addOrder(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 商品评价有图
     *
     * @param observer
     */
    public void goodsEvaluate(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map, List<MultipartBody.Part> files) {
        Single<HttpResult<Object>> observable = null;
        if (files == null || files.size() == 0) {
            observable = retrofitService.goodsEvaluate(map);
        } else {
            observable = retrofitService.goodsEvaluate(map, files);
        }
        observable.map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 验证手机号码
     *
     * @param observer
     */
    public void validateMobileNo(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map) {
        Single<HttpResult<Object>> observable = retrofitService.validateMobileNo(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 设置手机号码
     *
     * @param observer
     */
    public void settingMobileNo(DefaultSingleObserver<HttpResult<Object>> observer, HashMap<String, String> map) {
        Single<HttpResult<Object>> observable = retrofitService.settingMobileNo(map)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 查询物流信息
     *
     * @param observer
     */
    public void findExpressInfo(DefaultSingleObserver<HttpResult<LogisticsDataDto>> observer, String expressId, String expressNo) {
        Single<HttpResult<LogisticsDataDto>> observable = retrofitService.findExpressInfo(expressId, expressNo)
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取热门搜索
     *
     * @param observer
     */
    public void findHotSearchList(DefaultSingleObserver<List<HotSearchDto>> observer) {
        Single<List<HotSearchDto>> observable = retrofitService.findHotSearchList()
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取商品评价列表
     *
     * @param observer
     */
    public void findAllEvaList(DefaultSingleObserver<ShopEvaluateDto> observer, HashMap<String, Object> map) {
        Single<ShopEvaluateDto> observable = retrofitService.findAllEvaList(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取我的评价
     *
     * @param observer
     */
    public void findMyEvaList(DefaultSingleObserver<ShopEvaluateTotalDto> observer, HashMap<String, String> map) {
        Single<ShopEvaluateTotalDto> observable = retrofitService.findMyEvaList(map)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取客服电话
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void findServiceTel(DefaultSingleObserver<ServiceTelDto> observer) {
        Single<ServiceTelDto> observable = retrofitService.findServiceTel()
                .map(new HttpResultMapper.HttpResultData(null));
        subscribe(observable, observer);
    }

    /**
     * 获取注册协议
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void findAgreeMent(DefaultSingleObserver<AgreeMentDto> observer) {
        Single<AgreeMentDto> observable = retrofitService.findAgreeMent()
                .map(new HttpResultMapper.HttpResultData(null));
        subscribe(observable, observer);
    }
    /**
     * 文章详情
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void findCommercialCollegeDetail(DefaultSingleObserver<ArticleDto> observer,String id) {
        Single<ArticleDto> observable = retrofitService.findCommercialCollegeDetail(id)
                .map(new HttpResultMapper.HttpResultData(null));
        subscribe(observable, observer);
    }


    /**
     * 获取分享参数
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void findShareParam(DefaultSingleObserver<ShareGoodsDto> observer, String goodId) {
        Single<ShareGoodsDto> observable = retrofitService.findShareParam(goodId)
                .map(new HttpResultMapper.HttpResultData(null));
        subscribe(observable, observer);
    }

    /**
     * 会员退出
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void logout(DefaultSingleObserver<HttpResult<Object>> observer) {
        Single<HttpResult<Object>> observable = retrofitService.logout()
                .map(new HttpResultMapper.HttpResultOtheData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 获取个人分享参数
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void findPersonalShareParam(DefaultSingleObserver<QRcodeShareDto> observer, String referralCode) {
        Single<QRcodeShareDto> observable = retrofitService.findPersonalShareParam(referralCode)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }


    /**
     * 会员充值
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void memberRecharge(DefaultSingleObserver<RechargeDto> observer, String paymentId, String pamentAmount) {
        Single<RechargeDto> observable = retrofitService.memberRecharge(paymentId, pamentAmount)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取商品颜色,规格等属性
     */
    public void findGoodsAttr(DefaultSingleObserver<GoodsColorAndSpecDto> observer, long goodsId) {
        Single<GoodsColorAndSpecDto> observable = retrofitService.findGoodsColor1(goodsId)
                .map(new HttpResultMapper.HttpResultData<>(null));
        subscribe(observable, observer);
    }

    /**
     * 获取图片请求接口
     */
    public void fileBrower(DefaultSingleObserver<BrowerDto> observer) {
        Single<BrowerDto> observable = retrofitService.fileBrower()
                .map(new HttpResultMapper.HttpResultData<BrowerDto>(null));
        subscribe(observable, observer);
    }

}
