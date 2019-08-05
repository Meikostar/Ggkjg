package com.ggkjg.common.utils.pay;

public interface PayResultListener {
    void zfbPayOk(boolean payOk);
    void wxPayOk(boolean payOk);
}
