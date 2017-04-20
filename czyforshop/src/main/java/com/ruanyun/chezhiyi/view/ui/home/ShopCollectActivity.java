package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WebViewActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.view.ui.mendian.BusinessTotalActivity;
import com.ruanyun.chezhiyi.view.ui.mendian.GongDanActivity;
import com.ruanyun.chezhiyi.view.ui.mendian.MemberCountActivity;
import com.ruanyun.chezhiyi.view.ui.mendian.ServiceGoodsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description ：门店统计
 * <p/>
 * Created by ycw on 2017/3/9.
 */
public class ShopCollectActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.business_summary)
    RelativeLayout businessSummary;
    @BindView(R.id.number_Stastic)
    RelativeLayout numberStastic;
    @BindView(R.id.new_User)
    RelativeLayout newUser;
    @BindView(R.id.sale_good)
    RelativeLayout saleGood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_collect);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar.setTttleText("门店统计")
                .onBackBtnClick()
                .setBackBtnEnable(true)
                .setTopbarClickListener(this);
    }

    @OnClick({R.id.business_summary, R.id.number_Stastic, R.id.new_User, R.id.sale_good})
    public void setMenuClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.business_summary://营业汇总
//                toWebView(String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_JS_REPORT_YYE), app.getCurrentUserNum(), "1"), WebViewActivity.YYHZ);
                Intent intent2 = new Intent(mContext, BusinessTotalActivity.class);
                showActivity(intent2);
                break;
            case R.id.number_Stastic://会员统计
//                toWebView(String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_JS_REPORT_HY), app.getCurrentUserNum()), WebViewActivity.HYTJ);
                Intent intent3 = new Intent(mContext, MemberCountActivity.class);
                showActivity(intent3);
                break;
            case R.id.new_User://工单统计
//                toWebView(String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_JS_REPORT_XZKH), app.getCurrentUserNum()), WebViewActivity.XZYH);
                Intent intent5 = new Intent(mContext, GongDanActivity.class);
                showActivity(intent5);
                break;
            case R.id.sale_good://服务商品
//                toWebView(String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_JS_REPORT_XSSP), app.getCurrentUserNum()), WebViewActivity.XSSP);
                Intent intent4 = new Intent(mContext, ServiceGoodsActivity.class);
                showActivity(intent4);
                break;
        }

    }

    /**
     * web界面
     *
     * @param url
     * @param title
     */
    private void toWebView(String url, String title) {
        Intent intent = AppUtility.getWebIntent(mContext, url, title);
        intent.putExtra(C.IntentKey.NEED_SHARE, false);
        showActivity(intent);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }
}
