package com.ggkjg.common.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ggkjg.R;
import com.ggkjg.common.Constants;
import com.ggkjg.view.mainfragment.shop.CommodityDetailActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.udesk.UdeskSDKManager;
import cn.udesk.callback.IFunctionItemClickCallBack;
import cn.udesk.callback.ILocationMessageClickCallBack;
import cn.udesk.callback.INavigationItemClickCallBack;
import cn.udesk.callback.ITxtMessageWebonCliclk;
import cn.udesk.callback.IUdeskFormCallBack;
import cn.udesk.callback.IUdeskStructMessageCallBack;
import cn.udesk.config.UdeskConfig;
import cn.udesk.model.NavigationMode;
import cn.udesk.model.UdeskCommodityItem;
import cn.udesk.presenter.ChatActivityPresenter;
import udesk.core.UdeskConst;

public class CreatUdeskStyle {

    public static UdeskConfig.Builder makeBuilder(Context context,UdeskCommodityItem item)  {
        String shiroToken = Constants.getInstance().getString(Constants.USER_SHIRO_TOKEN, "");
        String mobileNO = Constants.getInstance().getString(Constants.USER_PHONE, "");
        String nickName = Constants.getInstance().getString(Constants.USER_NICK_NAME, "");
        Map<String, String> info = new HashMap<String, String>();
        if(TextUtil.isNotEmpty(shiroToken)){
            String sdktoken = shiroToken;

            info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, sdktoken);
            //以下信息是可选
            info.put(UdeskConst.UdeskUserInfo.NICK_NAME,nickName);
//            info.put(UdeskConst.UdeskUserInfo.EMAIL,"0631@163.com");
            info.put(UdeskConst.UdeskUserInfo.CELLPHONE,mobileNO);
//            info.put(UdeskConst.UdeskUserInfo.DESCRIPTION,"描述信息")


        }
        UdeskConfig.Builder builder = new UdeskConfig.Builder();
        builder.setUdeskTitlebarBgResId(R.color.udesk_titlebar_bg1) //设置标题栏TitleBar的背景色
                .setUdeskTitlebarTextLeftRightResId(R.color.udesk_color_navi_text1) //设置标题栏TitleBar，左右两侧文字的颜色
                .setUdeskIMLeftTextColorResId(R.color.udesk_color_im_text_left1) //设置IM界面，左侧文字的字体颜色
                .setUdeskIMRightTextColorResId(R.color.udesk_color_im_text_right1) // 设置IM界面，右侧文字的字体颜色
                .setUdeskIMAgentNickNameColorResId(R.color.udesk_color_im_left_nickname1) //设置IM界面，左侧客服昵称文字的字体颜色
                .setUdeskIMTimeTextColorResId(R.color.udesk_color_im_time_text1) // 设置IM界面，时间文字的字体颜色
                .setUdeskIMTipTextColorResId(R.color.udesk_color_im_tip_text1) //设置IM界面，提示语文字的字体颜色，比如客服转移
                .setUdeskbackArrowIconResId(R.drawable.udesk_titlebar_back) // 设置返回箭头图标资源id
                .setUdeskCommityBgResId(R.color.udesk_color_im_commondity_bg1) //咨询商品item的背景颜色
                .setUdeskCommityTitleColorResId(R.color.udesk_color_im_commondity_title1) // 商品介绍Title的字样颜色
                .setUdeskCommitysubtitleColorResId(R.color.udesk_color_im_commondity_subtitle1)// 商品咨询页面中，商品介绍子Title的字样颜色
                .setUdeskCommityLinkColorResId(R.color.udesk_color_im_commondity_link1) //商品咨询页面中，发送链接的字样颜色
                .setUserSDkPush(false) // 配置 是否使用推送服务  true 表示使用  false表示不使用
                .setOnlyUseRobot(false)//配置是否只使用机器人功能 只使用机器人功能,只使用机器人功能;  其它功能不使用。
                .setUdeskQuenuMode(false? UdeskConfig.UdeskQuenuFlag.FORCE_QUIT : UdeskConfig.UdeskQuenuFlag.Mark)  //  配置放弃排队的策略
                .setUseVoice(true) // 是否使用录音功能  true表示使用 false表示不使用
                .setUsephoto(true) //是否使用发送图片的功能  true表示使用 false表示不使用
                .setUsecamera(true) //是否使用拍照的功能  true表示使用 false表示不使用
                .setUsefile(false) //是否使用上传文件功能  true表示使用 false表示不使用
                .setUseMap(false) //是否使用发送位置功能  true表示使用 false表示不使用
                .setUseEmotion(true) //是否使用表情 true表示使用 false表示不使用
                .setUseMore(true) // 是否使用更多控件 展示出更多功能选项 true表示使用 false表示不使用
                .setUseNavigationSurvy(true) //设置是否使用导航UI中的满意度评价UI rue表示使用 false表示不使用
                .setUseSmallVideo(false)  //设置是否需要小视频的功能 rue表示使用 false表示不使用
                .setScaleImg(true) //上传图片是否使用原图 还是缩率图
                .setScaleMax(1024) // 缩放图 设置最大值，如果超出则压缩，否则不压缩
                .setOrientation(false ? UdeskConfig.OrientationValue.landscape :
                        (true ? UdeskConfig.OrientationValue.user : UdeskConfig.OrientationValue.portrait)) //设置默认屏幕显示习惯
                .setUserForm(true) //在没有请求到管理员在后端对sdk使用配置下，在默认的情况下，是否需要表单留言，true需要， false 不需要
                .setDefualtUserInfo(info) // 创建用户基本信息
//                .setDefinedUserTextField(getDefinedUserTextField()) //创建用户自定义的文本信息
//                .setDefinedUserRoplist(getDefinedUserRoplist()) //创建用户自定义的列表信息
//                .setUpdateDefualtUserInfo(getUpdateDefualtUserInfo()) // 设置更新用户的基本信息
//                .setUpdatedefinedUserTextField(getUpdateDefinedTextField()) //设置用户更新自定义字段文本信息
//                .setUpdatedefinedUserRoplist(getUpdateDefinedRoplist()) //设置用户更新自定义列表字段信息
                .setFirstMessage("您好 需要你的帮助") //设置带入一条消息  会话分配就发送给客服
                .setCustomerUrl(Constants.getInstance().getString(Constants.USER_HEAD_IMG, "")) //设置客户的头像地址
                .setRobot_modelKey("6395") // udesk 机器人配置插件 对应的Id值
                .setConcatRobotUrlWithCustomerInfo("")
                .setCommodity(item)//配置发送商品链接的mode
//                .setExtreFunctions(getExtraFunctions(), new IFunctionItemClickCallBack() {
//                    @Override
//                    public void callBack(Context context, ChatActivityPresenter mPresenter, int id, String name) {
//
//                        if (id == 21) {
//                            UdeskSDKManager.getInstance().toLanuchHelperAcitivty(context, UdeskSDKManager.getInstance().getUdeskConfig());
//                            mPresenter.sendTxtMessage("打开帮助中心");
//                        } else if (id == 22) {
//                            mPresenter.sendTxtMessage("打开表单留言");
//                            UdeskSDKManager.getInstance().goToForm(context, UdeskSDKManager.getInstance().getUdeskConfig());
//                        }
//                    }
//                })//在more 展开面板中设置额外的功能按钮
                .setNavigations(false, null, new INavigationItemClickCallBack() {
                    @Override
                    public void callBack(Context context, ChatActivityPresenter mPresenter, NavigationMode navigationMode) {
                        if (navigationMode.getId() == 1) {
                            UdeskSDKManager.getInstance().toLanuchHelperAcitivty(context, UdeskSDKManager.getInstance().getUdeskConfig());
                        } else if (navigationMode.getId() == 2) {
                            mPresenter.sendTxtMessage(UUID.randomUUID().toString());
                            mPresenter.sendTxtMessage("www.baidu.com");
                        }
                    }
                })//设置是否使用导航UI rue表示使用 false表示不使用
                .setTxtMessageClick(new ITxtMessageWebonCliclk() {
                    @Override
                    public void txtMsgOnclick(String url) {

                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(context,CommodityDetailActivity.class);
                        bundle.putLong(CommodityDetailActivity.PRODUCT_ID, CommodityDetailActivity.product_id);
                        if (bundle != null) {
                            intent.putExtras(bundle);
                        }
                        context.startActivity(intent);
//                        Toast.makeText(context, "对文本消息中的链接消息处理设置回调", Toast.LENGTH_SHORT).show();
                    }
                })   //如果需要对文本消息中的链接消息处理可以设置该回调，点击事件的拦截回调。 包含表情的不会拦截回调。
                .setFormCallBack(new IUdeskFormCallBack() {
                    @Override
                    public void toLuachForm(Context context) {
                        Toast.makeText(context, "不用udesk系统提供的留言功能", Toast.LENGTH_SHORT).show();
                    }
                })//离线留言表单的回调接口：  如果不用udesk系统提供的留言功能，可以设置该接口  回调使用自己的处理流程
                .setStructMessageCallBack(new IUdeskStructMessageCallBack() {

                    @Override
                    public void structMsgCallBack(Context context, String josnValue) {
                        Toast.makeText(context, "结构化消息控件点击事件回调", Toast.LENGTH_SHORT).show();
                    }
                })//设置结构化消息控件点击事件回调接口.
        ;

        return builder;
    }

}
