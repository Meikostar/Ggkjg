package com.ggkjg.view.mainfragment.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggkjg.R;
import com.ggkjg.base.BaseActivity;
import com.ggkjg.common.Constants;
import com.ggkjg.common.utils.LogUtil;
import com.ggkjg.common.utils.StatusBarUtils;
import com.ggkjg.common.utils.ToastUtil;
import com.ggkjg.dto.AddressDto;
import com.ggkjg.dto.CartAtrrDto;
import com.ggkjg.dto.ConfirmOrderDto;
import com.ggkjg.dto.OrderPreviewSellersDto;
import com.ggkjg.dto.ShopCartDto;
import com.ggkjg.http.manager.DataManager;
import com.ggkjg.http.subscribers.DefaultSingleObserver;
import com.ggkjg.view.adapter.GoodsOrderAdapter;
import com.ggkjg.view.adapter.PopVoucherAdapter;
import com.ggkjg.view.mainfragment.personalcenter.GoodsAddressActivity;
import com.ggkjg.view.widgets.MCheckBox;
import com.ggkjg.view.widgets.OrderChooseAddressDialog;
import com.ggkjg.view.widgets.PhotoPopupWindow;
import com.ggkjg.view.widgets.autoview.ActionbarView;
import com.ggkjg.view.widgets.autoview.ObservableScrollView;
import com.ggkjg.view.widgets.autoview.SuperExpandableListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 确认订单
 * Created by dahai on 2018/4/14.
 */

public class ConfirmOrderActivity extends BaseActivity implements OrderChooseAddressDialog.ChooseAddressListener {
    private static final String TAG = ConfirmOrderActivity.class.getSimpleName();
    public static final String PRODUCT_LIST = "product_list";//调用者传递的名字
    @BindView(R.id.custom_action_bar)
    ActionbarView customActionBar;
    @BindView(R.id.iv_action_bar_line)
    ImageView ivActionBarLine;
    @BindView(R.id.img_item_left)
    ImageView imgItemLeft;
    @BindView(R.id.iv_address_right)
    ImageView ivAddressRight;
    @BindView(R.id.tv_weight_kg)
    TextView tvWeightKg;
    @BindView(R.id.tv_price_sum)
    TextView tvPriceSum;
    @BindView(R.id.rl_offer)
    RelativeLayout rlOffer;
    @BindView(R.id.iv_add)
    MCheckBox ivAdd;
    @BindView(R.id.iv_agree)
    MCheckBox ivAgree;
    @BindView(R.id.sv_confirm_order)
    ObservableScrollView svConfirmOrder;
    @BindView(R.id.tv_confirm_order_goods_bottom_view)
    LinearLayout tvConfirmOrderGoodsBottomView;
    private List<ShopCartDto> selectProduct;
    @BindView(R.id.iv_confirm_order_address_add)
    ImageView iv_confirm_order_address_add;
    @BindView(R.id.tv_confirm_order_submit)
    Button butSubmit;
    @BindView(R.id.rl_address_info)
    RelativeLayout rl_address_info;
    @BindView(R.id.tv_confirm_consignee)
    TextView tv_confirm_consignee;
    @BindView(R.id.tv_confirm_phone)
    TextView tv_confirm_phone;
    @BindView(R.id.tv_confirm_address)
    TextView tv_confirm_address;

    @BindView(R.id.et_confirm_order_msg)
    EditText et_confirm_order_msg;
    @BindView(R.id.tv_confirm_order_goods_num)
    TextView tv_confirm_order_goods_num;
    @BindView(R.id.tv_confirm_order_goods_total_money)
    TextView tv_confirm_order_goods_total_money;
    @BindView(R.id.tv_confirm_order_goods_total_weight)
    TextView tv_confirm_order_goods_total_weight;
    @BindView(R.id.tv_confirm_order_total_money)
    TextView tv_confirm_order_total_money;
    @BindView(R.id.tv_confirm_order_courier_num)
    TextView tv_confirm_order_courier_num;

    @BindView(R.id.layout_level_1)
    LinearLayout layout_level_1;
    @BindView(R.id.layout_level_2)
    LinearLayout layout_level_2;
    @BindView(R.id.img_level_1)
    ImageView img_level_1;
    @BindView(R.id.img_level_2)
    ImageView img_level_2;
    @BindView(R.id.tv_level_1)
    TextView tv_level_1;
    @BindView(R.id.tv_level_2)
    TextView tv_level_2;
    @BindView(R.id.iv_choose_address_line)
    ImageView iv_choose_address_line;
    @BindView(R.id.layout_choose_address)
    RelativeLayout layout_choose_address;
    @BindView(R.id.tv_zt_address)
    TextView tv_zt_address;

    @BindView(R.id.elv_confirm_order)
    SuperExpandableListView expandableListView;
    GoodsOrderAdapter adapter;
    private String cardIds = ""; //购物车ID集合
    private int selLevel = 1;//默认选中快递模式
    ImageView imgLevel[];
    TextView tvLevel[] = new TextView[2];

    private RecyclerView recy_choose_address;
    private View contentView;
    private PopupWindow window;
    private int deliverType = 2;//默认快递
    private String contactNameStr;
    private String addressStr;

    @Override
    public int getLayoutId() {
        return R.layout.ui_confirm_order_layout;
    }

    @Override
    public void initView() {
        actionbar.setImgStatusBar(R.color.my_color_white);
        StatusBarUtils.StatusBarLightMode(this);
        imgLevel = new ImageView[]{img_level_1, img_level_2};
        tvLevel = new TextView[]{tv_level_1, tv_level_2};

        dealSelLevel();
    }
    private View mView;
    private Context mContext;
    private RecyclerView recyclerView;
    private Button btSure;
    private ImageView iv_close;


    private List<ShopCartDto> datas=new ArrayList<>();
    private PopVoucherAdapter popadapter;

    public void showPopWindows() {
        datas.clear();
         if(mView==null){
             mView = LayoutInflater.from(this).inflate(R.layout.chat_voucher_popwindow_view, null);
             recyclerView = mView.findViewById(R.id.recy_voucher);
             btSure = mView.findViewById(R.id.bt_sure);
             iv_close = mView.findViewById(R.id.iv_close);
             LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true) {
                 @Override
                 public boolean canScrollVertically() {
                     return true;
                 }
             };
             recyclerView.setLayoutManager(linearLayoutManager);

             popadapter=new PopVoucherAdapter(null);
             recyclerView.setAdapter(popadapter);
             btSure.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     mWindowAddPhoto.dismiss();
                 }
             });
             iv_close.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     mWindowAddPhoto.dismiss();
                 }
             });
             recyclerView.setAdapter(popadapter);
             datas.add(new ShopCartDto());
             datas.add(new ShopCartDto());
             datas.add(new ShopCartDto());
             datas.add(new ShopCartDto());
             datas.add(new ShopCartDto());
             popadapter.setNewData(datas);
             mWindowAddPhoto = new PhotoPopupWindow(this).bindView(mView);
             mWindowAddPhoto.showAtLocation(tv_zt_address, Gravity.BOTTOM, 0, 0);
         }else {
             mWindowAddPhoto.showAtLocation(tv_zt_address, Gravity.BOTTOM, 0, 0);
         }


    }
    private View view;
    private PhotoPopupWindow mWindowpayPhoto;
    private LinearLayout llBalance;
    private LinearLayout llZfb;
    private LinearLayout llWx;
    private MCheckBox mcbBalance;
    private MCheckBox mcbZfb;
    private MCheckBox mcbWx;
    public void showPopPayWindows() {
        datas.clear();
         if(view==null){
             view = LayoutInflater.from(this).inflate(R.layout.pay_popwindow_view, null);

             btSure = view.findViewById(R.id.bt_sure);
             iv_close = view.findViewById(R.id.iv_close);
             llBalance = view.findViewById(R.id.ll_balance);
             llZfb = view.findViewById(R.id.ll_zfb);
             llWx = view.findViewById(R.id.ll_wx);
             mcbBalance = view.findViewById(R.id.mcb_balance);
             mcbZfb = view.findViewById(R.id.mcb_zfb);
             mcbWx= view.findViewById(R.id.mcb_wx);


             btSure.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     mWindowpayPhoto.dismiss();
                 }
             });
             iv_close.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                mWindowpayPhoto.dismiss();
                 }
             });

             mWindowpayPhoto = new PhotoPopupWindow(this).bindView(view);
             mWindowpayPhoto.showAtLocation(tv_zt_address, Gravity.BOTTOM, 0, 0);
         }else {
             mWindowpayPhoto.showAtLocation(tv_zt_address, Gravity.BOTTOM, 0, 0);
         }


    }
    private PhotoPopupWindow mWindowAddPhoto;
    @Override
    public void initData() {
        findDefaultReceiptAddr();
        selectProduct = (List<ShopCartDto>) getIntent().getSerializableExtra(PRODUCT_LIST);//获取list
        adapter = new GoodsOrderAdapter(this);
        expandableListView.setAdapter(adapter);
        List<OrderPreviewSellersDto> dataList = new ArrayList<>();
        OrderPreviewSellersDto orderPreviewSellersDto = new OrderPreviewSellersDto();
        orderPreviewSellersDto.setTitle("港港商城自营店");
        orderPreviewSellersDto.setProduct(selectProduct);
        dataList.add(orderPreviewSellersDto);
        adapter.setNewData(dataList);
        expandableListView.setGroupIndicator(null);//把指示器设为null
        //默认展开子项
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            if (expandableListView != null) {
                expandableListView.expandGroup(i);
            }
        }
        //获取商品质量和总价等
        for (int i = 0; i < selectProduct.size(); i++) {
            if (i == 0) {
                cardIds = String.valueOf(selectProduct.get(i).getId());
            } else {
                cardIds += ("," + String.valueOf(selectProduct.get(i).getId()));
            }
        }
        calculateBill(cardIds);
        tv_confirm_order_goods_num.setText(String.format("共%s件商品  合计:", selectProduct.size() + ""));
    }

    @Override
    public void initListener() {
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return true;
            }
        });

        bindClickEvent(iv_confirm_order_address_add, () -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.INTENT_SEL_ADDRESS, true);
            gotoActivity(GoodsAddressActivity.class, false, bundle, Constants.INTENT_REQUESTCODE_SEL_ADDRESS);
        });

        bindClickEvent(rl_address_info, () -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.INTENT_SEL_ADDRESS, true);
            gotoActivity(GoodsAddressActivity.class, false, bundle, Constants.INTENT_REQUESTCODE_SEL_ADDRESS);
        });
        bindClickEvent(butSubmit, () -> {
            showPopPayWindows();
            if (iv_confirm_order_address_add.getVisibility() == View.VISIBLE) {
                ToastUtil.showToast("请添加收货地址");
            } else {
//              InputPwdDialog dialog = new InputPwdDialog(this, new InputPwdDialog.InputPasswordListener() {
//              @Override
//              public void callbackPassword(String password) {
//              LogUtil.i(TAG, "--password=" + password);
                if ((cardIds.substring(cardIds.length() - 1)).equals(",")) {
                    cardIds = cardIds.substring(0, cardIds.length() - 1);
                }
//              String pwdMd5 = MD5Utils.getMD5Str(password + "hkonline");
                if (deliverType == 1) {
                    String addressStr = tv_zt_address.getText().toString();
                    if (addressStr.length() != 0) {
                        addOrder(cardIds, "");
                    } else {
                        ToastUtil.showToast("请选择自提地址");
                    }
                } else {
                    addOrder(cardIds, "");
                }
//               }
//              });
//             dialog.show();
            }
        });
//        bindClickEvent(butSubmit, () -> {
//            showPopPayWindows();
//
//        });
        bindClickEvent(layout_level_1, () -> {
            selLevel = 0;
            deliverType = 1;
            dealSelLevel();
        });

        bindClickEvent(layout_level_2, () -> {
            selLevel = 1;
            deliverType = 2;
            dealSelLevel();
        });

        bindClickEvent(rlOffer, () -> {
            showPopWindows();
        });

        bindClickEvent(layout_choose_address, () -> {
            OrderChooseAddressDialog dialog = new OrderChooseAddressDialog(this, this);
            dialog.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.INTENT_REQUESTCODE_SEL_ADDRESS && data != null) {
            Serializable serializable = data.getSerializableExtra("result");
            if (serializable != null) {
                AddressDto addressDto = (AddressDto) serializable;
                initAddress(addressDto);
            }
        }
    }


    private void initAddress(AddressDto addressDto) {
        if (addressDto != null) {
            iv_confirm_order_address_add.setVisibility(View.GONE);
            rl_address_info.setVisibility(View.VISIBLE);
            contactNameStr = addressDto.getContactName();
            tv_confirm_consignee.setText("收货人:" + contactNameStr);
            tv_confirm_phone.setText(addressDto.getMobileNo());
            addressStr = addressDto.getProvince() + addressDto.getCity()
                    + addressDto.getArea() + addressDto.getAddrDetail();
            tv_confirm_address.setText("收货地址:" + addressStr);
        } else {
            iv_confirm_order_address_add.setVisibility(View.VISIBLE);
            rl_address_info.setVisibility(View.GONE);
        }
    }

    /**
     * 获取商品配送方式，重量，快递费，商品总价
     */
    private void calculateBill(String cardIds) {
        showLoadDialog();
        DataManager.getInstance().calculateBill(new DefaultSingleObserver<CartAtrrDto>() {
            @Override
            public void onSuccess(CartAtrrDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()" + object.getWeight() + object.getGdPrice());
                tv_confirm_order_courier_num.setText(object.getFreight());
                tv_confirm_order_goods_total_money.setText(object.getGdPrice());
                tv_confirm_order_total_money.setText(object.getGdPrice());
                tv_confirm_order_goods_total_weight.setText(object.getWeight() + "");
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread:onError() = ");
                dissLoadDialog();
            }
        }, cardIds);
    }

    /**
     * 获取默认收货地址
     */
    private void findDefaultReceiptAddr() {
        showLoadDialog();
        DataManager.getInstance().findDefaultReceiptAddr(new DefaultSingleObserver<AddressDto>() {
            @Override
            public void onSuccess(AddressDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: onSuccess()");
                initAddress(object);
                dissLoadDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread:onError() = ");
                dissLoadDialog();
            }
        });
    }

    /**
     * 生成订单
     *
     * @param cartIds  购物车ID
     * @param tradePwd 交易密码
     */
    private void addOrder(String cartIds, String tradePwd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("cartId", cartIds);//购物车ID
//      map.put("tradePwd", tradePwd);//交易密码
        map.put("contactName", contactNameStr);//收货人
        map.put("mobileNo", tv_confirm_phone.getText().toString());//手机号
        if (deliverType == 1) {
            map.put("address", tv_zt_address.getText().toString());//自提地址
        } else {
            map.put("address", addressStr);//收货地址
        }
        map.put("deliverType", deliverType + "");//收货方式 1-自提 2-配送
        map.put("deliverMoney", 0 + "");//运费
        map.put("weight", tv_confirm_order_goods_total_weight.getText().toString());
        map.put("goodsTotalMoney", tv_confirm_order_goods_total_money.getText().toString());//商品总价
        map.put("realOrderMoney", tv_confirm_order_total_money.getText().toString());//实际订单总金额
        map.put("remark", et_confirm_order_msg.getText().toString());//订单备注
        showLoadDialog();
        DataManager.getInstance().addOrder(new DefaultSingleObserver<ConfirmOrderDto>() {
            @Override
            public void onSuccess(ConfirmOrderDto object) {
                LogUtil.i(TAG, "--RxLog-Thread: addOrder onSuccess()");
                dissLoadDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ORDER_ID, object.getOrderId() + "");
                bundle.putString(Constants.ORDER_NO, object.getOrderNo());
                bundle.putString(Constants.ORDER_MONEY, object.getRealOrderMoney());
                gotoActivity(PayOrderActivity.class, true, bundle);//支付方式
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "--RxLog-Thread:addOrder onError() = ");
                dissLoadDialog();
            }
        }, map);
    }

    private void dealSelLevel() {
        for (int i = 0; i < imgLevel.length; i++) {
            if (selLevel == i) {
                imgLevel[i].setSelected(true);
                tvLevel[i].setTextColor(getResources().getColor(R.color.my_color_008CD6));
            } else {
                imgLevel[i].setSelected(false);
                tvLevel[i].setTextColor(getResources().getColor(R.color.my_color_333333));
            }
        }

        if (selLevel == 0) {
            iv_choose_address_line.setVisibility(View.VISIBLE);
            layout_choose_address.setVisibility(View.VISIBLE);
        } else {
            iv_choose_address_line.setVisibility(View.GONE);
            layout_choose_address.setVisibility(View.GONE);
        }
    }

    @Override
    public void callbackChooseAddress(String chooseAddressStr) {
        tv_zt_address.setText(chooseAddressStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
