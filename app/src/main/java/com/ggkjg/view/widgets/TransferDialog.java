package com.ggkjg.view.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.view.adapter.InputPwdAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Action;

/**
 * 港豆/HKDT 置换
 */
public class TransferDialog extends Dialog {

    private Context mContext;
    TextView tv_title, tv_transfer_1, tv_transfer_2, tv_transfer_money;
    Button btn_confirm;
    ImageView btn_close;
    boolean isCoinTransferHKDT;//true 港豆置换，false HKDT置换
    String money, transfer_money;
    TransferListener transferListener;

    public TransferDialog(Context context, boolean isCoinTransferHKDT, String money, String transfer_money, TransferListener transferListener) {
        super(context, R.style.dialog_with_alpha);
        this.mContext = context;
        this.isCoinTransferHKDT = isCoinTransferHKDT;
        this.money = money;
        this.transfer_money = transfer_money;
        this.transferListener = transferListener;
    }

    private void initView() {
        WindowManager manager = getWindow().getWindowManager();
        Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (display.getWidth());
        Window window = getWindow();
        window.setAttributes(p);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomAnimStyle);
        tv_title = findViewById(R.id.tv_title);
        tv_transfer_1 = findViewById(R.id.tv_transfer_1);
        tv_transfer_2 = findViewById(R.id.tv_transfer_2);
        tv_transfer_money = findViewById(R.id.tv_transfer_money);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_close = findViewById(R.id.btn_close);
        if (isCoinTransferHKDT) {
            tv_transfer_1.setText(money + "港豆");
            tv_transfer_2.setText(money + "HKDT");
        } else {
            tv_transfer_1.setText(money + "HKDT");
            tv_transfer_2.setText(money + "港豆");
        }
        tv_transfer_money.setText(transfer_money);

    }

    private void initListener() {
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (transferListener != null) {
                    transferListener.sure();
                }
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_transfer_layout);
        initView();
        initListener();
    }

    public interface TransferListener {
        void sure();
    }
}
