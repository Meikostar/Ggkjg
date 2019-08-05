package com.ggkjg.view.widgets;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.ggkjg.R;
import com.ggkjg.view.mainfragment.square.SquareGoldInputActivity;
import com.ggkjg.view.mainfragment.square.SquareGoldOutActivity;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

/**
 * 港豆发布
 * Created by dahai on 2018/12/08.
 */
public class GoldReleaseDialog extends Dialog {
    @BindView(R.id.but_square_gold_out)
    Button but_square_gold_out;
    @BindView(R.id.but_square_gold_input)
    Button but_square_gold_input;
    private Context mContext;

    public GoldReleaseDialog(Context context) {
        super(context, R.style.dialog_with_alpha);
        this.mContext = context;
//      setCanceledOnTouchOutside(false);//设置点击外部不可以取消;
        setContentView(R.layout.dialog_gold_release_layout);
        ButterKnife.bind(this);
        initListener();
    }


    private void initListener() {
        bindClickEvent(but_square_gold_out, () -> {
            mContext.startActivity(new Intent(mContext, SquareGoldOutActivity.class));
            dismiss();
        });
        bindClickEvent(but_square_gold_input, () -> {
            mContext.startActivity(new Intent(mContext, SquareGoldInputActivity.class));
            dismiss();
        });
    }

    /**
     * 基本点击事件跳转默认节流1000毫秒
     *
     * @param view   绑定的view
     * @param action 跳转的Acticvity
     */
    protected void bindClickEvent(View view, final Action action) {
        bindClickEvent(view, action, 1000);
    }

    protected void bindClickEvent(View view, final Action action, long frequency) {
        Observable<Object> observable = RxView.clicks(view);
        if (frequency > 0) {
            observable.throttleFirst(frequency, TimeUnit.MILLISECONDS);
        }
        observable.subscribe(trigger -> action.run());
    }


}
