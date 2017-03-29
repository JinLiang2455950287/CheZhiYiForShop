package com.ruanyun.chezhiyi.view.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.HuiYuanKuaiChaInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.FlowLayout;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HuiYuanDetailActivity extends AutoLayoutActivity implements Topbar
        .onTopbarClickListener {


    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.img_photo)
    CircleImageView imgPhoto;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_persionname)
    TextView tvPersionname;
    @BindView(R.id.flow_view)
    FlowLayout flowView;
    @BindView(R.id.tv_remain)
    TextView tvremain;
    @BindView(R.id.tv_consume)
    TextView tvConsume;
    @BindView(R.id.ll_phone)
    TextView llPhone;
    private HuiYuanKuaiChaInfo huiYuanKuaiChaInfo;
    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hui_yuan_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar.setTttleText(com.ruanyun.chezhiyi.commonlib.R.string.account_mangement)
                .setBackBtnEnable(true)
                .setTttleText("个人资料")
                .onBackBtnClick()
                .setTopbarClickListener(this);
        huiYuanKuaiChaInfo = (HuiYuanKuaiChaInfo) getIntent().getSerializableExtra("huiYuanDetail");
        LogX.e("HuiYuanKuaiChaInfo", huiYuanKuaiChaInfo.toString());
        upDataUi(huiYuanKuaiChaInfo);
    }

    private void upDataUi(HuiYuanKuaiChaInfo huiYuanKuaiChaInfo) {
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(huiYuanKuaiChaInfo.getUserPhoto()), imgPhoto, R.drawable.default_img);
        LogX.e("HuiYuanKuaiChaInfo", FileUtil.getImageUrl(huiYuanKuaiChaInfo.getUserPhoto()) + ";;;");
        tvName.setText(huiYuanKuaiChaInfo.getNickName());
        tvNickname.setText("昵称：" + huiYuanKuaiChaInfo.getNickName());
        tvPersionname.setText(huiYuanKuaiChaInfo.getPersonalNote());
//        FlowLayout flowView;
        tvremain.setText(huiYuanKuaiChaInfo.getAccountBalance() + "");
        tvConsume.setText("消费记录");
        llPhone.setText(huiYuanKuaiChaInfo.getLinkTel());

        String[] arr = huiYuanKuaiChaInfo.getUserInterest().split(",");
        stringList = Arrays.asList(arr);
        flowView.removeAllViews();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8, 5, 0, 0);

        for (String info : stringList) {
            if (AppUtility.isNotEmpty(info)) {
                TextView view = new TextView(mContext);
                view.setText(info);
                view.setTextColor(Color.WHITE);
                view.setTextSize(12);
                view.setLayoutParams(lp);
                view.setBackgroundResource(com.ruanyun.chezhiyi.commonlib.R.drawable.shape_text_station_label);
                flowView.addView(view);
            }
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }
}
