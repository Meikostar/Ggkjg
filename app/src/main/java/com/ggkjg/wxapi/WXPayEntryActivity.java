package com.ggkjg.wxapi;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ggkjg.R;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.db.bean.WEIXINREQ;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, AppRegister.APP_ID);
        api.handleIntent(getIntent(), this);
        sb=new StringBuffer();
        sendPayReq();
    }

    private void sendPayReq() {
        WEIXINREQ weixinreq = (WEIXINREQ) getIntent().getSerializableExtra("weixinreq");


        PayReq req = new PayReq();
        req.appId = AppRegister.APP_ID;
        req.partnerId = weixinreq.partnerid;
        req.prepayId = weixinreq.prepayid;

        req.nonceStr = weixinreq.noncestr;
        req.timeStamp = weixinreq.timestamp;
        req.packageValue ="Sign=WXPay";
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        //        req.sign = genAppSign(signParams);
        sb.append("sign"+req.sign+"\n\n");
        req.sign = weixinreq.sign;
        api.registerApp(AppRegister.APP_ID);
        LogUtil.e("wchat_pay","appid: "+ req.appId+"noncestr: "+ req.nonceStr+"package: "+req.packageValue+"partnerid: "+ req.partnerId+"prepayid: "+req.prepayId+"timestamp: "+ req.timeStamp+"sign: "+req.sign);
        boolean b = api.sendReq(req);
        if (b == false) {
            Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
            finish();
        }
        System.out.println("weixin" + b);
    }
    private StringBuffer sb;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    private void startWX() {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        startActivity(intent);
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {//支付成功
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            } else if (resp.errCode == -1) {
                Toast.makeText(this, "微信加载失败，请重试", Toast.LENGTH_SHORT).show();
                startWX(); //启动微信
            } else if (resp.errCode == -2) {
                Toast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }


}