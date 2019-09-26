package cn.udesk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import cn.udesk.R;
import cn.udesk.UdeskSDKManager;
import cn.udesk.UdeskUtil;
import cn.udesk.config.UdekConfigUtil;
import cn.udesk.config.UdeskConfig;
import cn.udesk.model.SDKIMSetting;
import cn.udesk.widget.UdeskTitleBar;
import udesk.core.UdeskConst;
import udesk.core.UdeskHttpFacade;
import udesk.core.utils.UdeskUtils;

public class PayWebActivity extends UdeskBaseWebViewActivity {
    private String h5Url = null;
    private String tranfer = null;
    private boolean isTranferByImGroup = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            UdeskUtil.setOrientation(this);
            initData();
            loadingView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                h5Url = intent.getStringExtra("pay_web");
                tranfer = intent.getStringExtra(UdeskConst.UDESKTRANSFER);
                isTranferByImGroup = intent.getBooleanExtra(UdeskConst.UDESKISTRANFERSESSION, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadingView() {
        try {
            settingTitlebar(tranfer);
            if (!TextUtils.isEmpty(h5Url)) {

                mwebView.loadUrl(h5Url);
            } else {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * titlebar 的设置
     */
    private void settingTitlebar(String tranfer) {
        try {
            mTitlebar = (UdeskTitleBar) findViewById(R.id.udesktitlebar);
            if (mTitlebar != null) {
                UdekConfigUtil.setUITextColor(UdeskSDKManager.getInstance().getUdeskConfig().udeskTitlebarTextLeftRightResId, mTitlebar.getLeftTextView(), mTitlebar.getRightTextView());
                if (mTitlebar.getRootView() != null) {
                    UdekConfigUtil.setUIbgDrawable(UdeskSDKManager.getInstance().getUdeskConfig().udeskTitlebarBgResId, mTitlebar.getRootView());
                }
                if (UdeskConfig.DEFAULT != UdeskSDKManager.getInstance().getUdeskConfig().udeskbackArrowIconResId) {
                    mTitlebar.getUdeskBackImg().setImageResource(UdeskSDKManager.getInstance().getUdeskConfig().udeskbackArrowIconResId);
                }
                mTitlebar
                        .setLeftTextSequence(UdeskSDKManager.getInstance().getImSetting() != null && UdeskSDKManager.getInstance().getImSetting().getRobot_name() != null
                                ? UdeskUtils.objectToString(UdeskSDKManager.getInstance().getImSetting().getRobot_name())
                                : getString(R.string.udesk_robot_title));
                mTitlebar.setLeftLinearVis(View.VISIBLE);
                mTitlebar.setLeftViewClick(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();

                    }
                });

                SDKIMSetting sdkimSetting = UdeskSDKManager.getInstance().getImSetting();
                if (sdkimSetting != null && UdeskUtils.objectToInt(sdkimSetting.getShow_robot_times()) > 0) {
                    return;
                }
                settingTitleBarRight(tranfer);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void showTransfer() {
//        super.showTransfer();
        if (tranfer.trim().equals("true")) {
            settingTitleBarRight("true");
        }
    }

    //根据传入的tranfer 控制右侧是否显示转人工
    private void settingTitleBarRight(String tranfer) {
        try {
            if (tranfer != null && tranfer.trim().equals("true")) {
                mTitlebar.setRightTextVis(View.VISIBLE);
                mTitlebar
                        .setRightTextSequence(getString(R.string.udesk_transfer_persion));
                mTitlebar.setudeskTransferImgVis(View.VISIBLE);
                mTitlebar.setRightViewClick(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        goChat();
                    }
                });
            } else {
                mTitlebar.setRightTextVis(View.GONE);
                mTitlebar.setudeskTransferImgVis(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void goChat() {
        try {
            if (isTranferByImGroup) {
                Intent intent = new Intent(getApplicationContext(), UdeskOptionsAgentGroupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Intent intent = new Intent(PayWebActivity.this, UdeskChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PayWebActivity.this.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void autoTransfer() {
        goChat();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}