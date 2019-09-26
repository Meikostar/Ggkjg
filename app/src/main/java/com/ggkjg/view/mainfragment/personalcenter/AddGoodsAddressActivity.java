package com.ggkjg.view.mainfragment.personalcenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.StringUtil;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.AddressDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.response.HttpResult;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import java.io.Serializable;
import java.util.HashMap;

import butterknife.BindView;


/**
 * 个人中心 --> 收货地址 -->  添加收货地址
 */
public class AddGoodsAddressActivity extends BaseActivity {
    private String areaId;
    public String id;
    public static boolean isChangeArea = false;
    boolean isEdit;
    @BindView(R.id.ed_setting_name)
    EditText edName;
    @BindView(R.id.ed_add_address_mobile)
    EditText edMobile;
    @BindView(R.id.tv_setting_address_area)
    TextView tvAddressArea;
    @BindView(R.id.ed_add_address_detail)
    EditText edAddressDetail;
    @BindView(R.id.img_add_address_sel)
    ImageView imgCheck;
    @BindView(R.id.ll_sel)
    LinearLayout ll_sel;
    @BindView(R.id.btn_add_address_sure)
    Button btnSure;

    @Override
    public int getLayoutId() {
        return R.layout.ui_setting_add_goods_address_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        initEdit();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        ll_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSel = imgCheck.isSelected();
                imgCheck.setSelected(!isSel);
            }
        });

        bindClickEvent(tvAddressArea, () -> {
            gotoActivity(AreaListActivity.class, false, null, Constants.INTENT_REQUESTCODE_AREA);

        });
        bindClickEvent(btnSure, () -> {
            String name = edName.getText().toString();
            String mobile = edMobile.getText().toString();
            String detail = edAddressDetail.getText().toString();
            String area = tvAddressArea.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "请输入收货人的姓名", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(mobile)) {
                Toast.makeText(this, "请输入手机号码", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(area)) {
                Toast.makeText(this, "请选择省市区", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(detail)) {
                Toast.makeText(this, "请输入地区", Toast.LENGTH_LONG).show();
                return;
            }
            if (!StringUtil.isMobileNO(mobile)) {
                Toast.makeText(this, "请输入正确手机号码", Toast.LENGTH_LONG).show();
                return;
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("contactName", name);
            map.put("mobileNo", mobile);
            area = area.trim();
            String areaArray[] = area.split(" ");
            if (areaArray != null && areaArray.length >= 3) {
                map.put("province", areaArray[0]);
                map.put("city", areaArray[1]);
                map.put("area", areaArray[2]);
            }

            map.put("addrDetail", detail);

            boolean isSel = imgCheck.isSelected();
            if (isSel) {
                map.put("isDef", "1");
            } else {
                map.put("isDef", "2");
            }
            if (isEdit) {
                map.put("addressId",String.valueOf(id));
                updateAddress(map);
            } else {
                addAddress(map);
            }

        });
    }


    private void initEdit() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isEdit = bundle.getBoolean(Constants.INTENT_ADDRESS_ISEDIT);
            Serializable serializable = bundle.getSerializable(Constants.INTENT_DATA);
            if (serializable != null) {
                AddressDto addressDto = (AddressDto) serializable;
                String name = addressDto.getContactName();
                String mobile = addressDto.getMobileNo();
                String detail = addressDto.getAddrDetail();
                String area = addressDto.getProvince() + " "+ addressDto.getCity() +" "+ addressDto.getArea();
                String isDefault = addressDto.getIsDef();
                id = addressDto.getId();
                edName.setText(name);
                edMobile.setText(mobile);
                edAddressDetail.setText(detail);
                tvAddressArea.setText(area);
                if ("1".equals(isDefault)) {
                    imgCheck.setSelected(true);
                }

            }
        }
    }

    private void addAddress(HashMap<String, String> map) {
        showLoadDialog();
        DataManager.getInstance().addReceiptAddr(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                dissLoadDialog();
                if (httpResult != null) {
                    if (httpResult.getStatus() == 1) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        ToastUtil.showToast(httpResult.getMsg());
                    }
                }

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();
            }
        }, map);

    }

    private void updateAddress(HashMap<String, String> map) {
        showLoadDialog();
        DataManager.getInstance().modifyReceiptAddr(new DefaultSingleObserver<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if (httpResult != null) {
                    if (httpResult.getStatus() == 1) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        ToastUtil.showToast(httpResult.getMsg());
                    }
                }

            }

            @Override
            public void onError(Throwable throwable) {
                dissLoadDialog();

            }
        }, map);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.INTENT_REQUESTCODE_AREA && resultCode == Activity.RESULT_OK) {
            String allName = data.getStringExtra("areaName");
            areaId = data.getStringExtra("areaId");
            tvAddressArea.setText(allName);
        }
    }
}
