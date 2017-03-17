package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

/**
 * Description ：通知公告详情
 * <p/>
 * Created by ycw on 2016/12/14.
 */
public class ShowContentActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    public static final String STRING_YHXY = "用户协议";
    private Topbar topbar;
    private TextView tvNotice;
    private String title;
    private String context;
    private LinearLayout llRoot;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_noticeinfo);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        tvNotice = getView(R.id.tv_notice);
        llRoot = getView(R.id.ll_root);
        scrollView = getView(R.id.nested_view);

        context = getIntent().getStringExtra(C.IntentKey.EDIT_CONTEXT);
        title = getIntent().getStringExtra(C.IntentKey.TOPBAR_TITLE);
        if (title.equals(STRING_YHXY)) {
            int color = ContextCompat.getColor(mContext, R.color.bg_login);
            llRoot.setBackgroundColor(color);
            topbar.setBackgroundColor(color);
            tvNotice.setBackgroundColor(color);
            tvNotice.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }
        topbar.setTttleText(title)
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        tvNotice.setText(context);
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left){
            finish();
        }
    }
}
