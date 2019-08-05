package com.ggkjg.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ggkjg.R;
import com.ggkjg.common.Constants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.i(TAG, "onPayFinish, errCode = " + req.checkArgs());
    }

    @Override
    public void onResp(BaseResp resp) {
//        ShareUtil.getInstance().saveInt(Constants.WX_ERROR_CODE,resp.errCode);
        Log.i(TAG, "onPayFinish, errCode = " + resp.errCode);
        loginRequest(resp.errCode);
    }
    private void loginRequest(int code) {
        Intent intent = new Intent(Constants.WX_PAY_BROADCAST);
        intent.putExtra(Constants.INTENT_CODE, code);
        //发送一个广播
        sendBroadcast(intent);
        finish();
    }
}