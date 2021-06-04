package com.axehome.www.guojinapp.ui.activitys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadShop3Activity extends BaseActivity {


    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.iv_img_logo)
    ImageView ivImgLogo;
    @BindView(R.id.iv_img_logo_del)
    ImageView ivImgLogoDel;
    @BindView(R.id.iv_img_indoor)
    ImageView ivImgIndoor;
    @BindView(R.id.iv_img_indoor_del)
    ImageView ivImgIndoorDel;
    @BindView(R.id.iv_img_contract)
    ImageView ivImgContract;
    @BindView(R.id.iv_img_contract_del)
    ImageView ivImgContractDel;
    @BindView(R.id.iv_img_other)
    ImageView ivImgOther;
    @BindView(R.id.img_other_del)
    ImageView imgOtherDel;
    @BindView(R.id.iv_img_idcard_holding)
    ImageView ivImgIdcardHolding;
    @BindView(R.id.iv_img_idcard_holding_del)
    ImageView ivImgIdcardHoldingDel;
    @BindView(R.id.iv_img_open_permits)
    ImageView ivImgOpenPermits;
    @BindView(R.id.iv_img_open_permits_del)
    ImageView ivImgOpenPermitsDel;
    @BindView(R.id.iv_img_unincorporated)
    ImageView ivImgUnincorporated;
    @BindView(R.id.iv_img_unincorporated_del)
    ImageView ivImgUnincorporatedDel;
    @BindView(R.id.iv_img_standard_protocol)
    ImageView ivImgStandardProtocol;
    @BindView(R.id.iv_img_standard_protocol_del)
    ImageView ivImgStandardProtocolDel;
    @BindView(R.id.iv_img_val_add_protocol)
    ImageView ivImgValAddProtocol;
    @BindView(R.id.iv_img_val_add_protocol_del)
    ImageView ivImgValAddProtocolDel;
    @BindView(R.id.iv_img_sub_account_promiss)
    ImageView ivImgSubAccountPromiss;
    @BindView(R.id.iv_img_sub_account_promiss_del)
    ImageView ivImgSubAccountPromissDel;
    @BindView(R.id.iv_img_3rd_part)
    ImageView ivImg3rdPart;
    @BindView(R.id.iv_img_3rd_part_del)
    ImageView ivImg3rdPartDel;
    @BindView(R.id.iv_img_alicashier)
    ImageView ivImgAlicashier;
    @BindView(R.id.iv_img_alicashier_del)
    ImageView ivImgAlicashierDel;
    @BindView(R.id.iv_img_salesman_logo)
    ImageView ivImgSalesmanLogo;
    @BindView(R.id.iv_img_salesman_logo_del)
    ImageView ivImgSalesmanLogoDel;
    @BindView(R.id.iv_pic_14)
    ImageView ivPic14;
    @BindView(R.id.iv_pic_14_del)
    ImageView ivPic14Del;
    @BindView(R.id.iv_img_org_code)
    ImageView ivImgOrgCode;
    @BindView(R.id.iv_img_org_code_del)
    ImageView ivImgOrgCodeDel;
    @BindView(R.id.iv_img_tax_reg)
    ImageView ivImgTaxReg;
    @BindView(R.id.iv_img_tax_reg_del)
    ImageView ivImgTaxRegDel;
    @BindView(R.id.iv_pic_17)
    ImageView ivPic17;
    @BindView(R.id.iv_pic_17_del)
    ImageView ivPic17Del;
    @BindView(R.id.iv_pic_18)
    ImageView ivPic18;
    @BindView(R.id.iv_pic_18_del)
    ImageView ivPic18Del;
    @BindView(R.id.iv_pic_19)
    ImageView ivPic19;
    @BindView(R.id.iv_pic_19_del)
    ImageView ivPic19Del;
    @BindView(R.id.iv_pic_20)
    ImageView ivPic20;
    @BindView(R.id.iv_pic_20_del)
    ImageView ivPic20Del;
    @BindView(R.id.b_submit)
    Button bSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_shop3);
        ButterKnife.bind(this);
        title.setText("添加商户");
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {

    }
}
