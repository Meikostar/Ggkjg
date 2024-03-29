package com.ggkjg.view.widgets;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;
import com.ggkjg.R;


/**
 * Created by Administrator on 2017/2/15.
 */

public class ConfirmHintDialog extends BaseDialog {


    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本


    public ConfirmHintDialog(Activity context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_confirm_hint_layout;
    }

    /**
     * 初始化界面控件
     */
    @Override
    protected void initView() {
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        btn_sure = (Button) findViewById(R.id.yes);
        titleTv = (TextView) findViewById(R.id.title);
        messageTv = (TextView) findViewById(R.id.message);
    }

    public void setMessageLocation(int location) {
        if (messageTv != null)
            messageTv.setGravity(location);
    }

    /**
     * 初始化界面控件的显示数据
     */
    @Override
    protected void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        //如果设置按钮的文字
        if (yesStr != null) {
            btn_sure.setText(yesStr);
        }
        if (noStr != null) {
            btn_cancel.setText(noStr);
        }
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    @Override
    protected void initEvent() {
        bindClickEvent(btn_sure, () -> {
            if (onYesClickListener != null) {
                onYesClickListener.onYesClick();
            }
            hide();
        });
    }

}
