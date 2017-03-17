package com.ruanyun.czy.client;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.MainBaseActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.ClientContactListFragment;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.ConversationListFragment;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.PickContactActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.widget.TopMenuPopupWindow;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.BottomLayoutTextView;
import com.ruanyun.chezhiyi.commonlib.view.widget.TopTabButton;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.view.ui.main.FindFragment;
import com.ruanyun.czy.client.view.ui.main.HomeFragment;
import com.ruanyun.czy.client.view.ui.main.MyFragment;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 司机端主页
 */
public class MainActivity
        extends MainBaseActivity
        implements Topbar.onTopbarClickListener, TopMenuPopupWindow.onMenuClickListener {

    @BindString(R.string.home)
    String home;
    @BindString(R.string.message)
    String message;
    @BindString(R.string.find)
    String find;
    @BindString(R.string.mine)
    String mine;
    @BindView(R.id.tv_news_count)
    TextView tvNewsCount;
    @BindViews({R.id.tv_home, R.id.tv_news, R.id.tv_find, R.id.tv_person})
    List<BottomLayoutTextView> layoutTextViews;

    Topbar topbar;
    TopTabButton topTabButton;
    TopMenuPopupWindow topMenuPopupWindow;
//    ConversationListFragment conversationListFragment;
//    ClientContactListFragment hxContactListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LeakCanary.install(app);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        String[] strings = {home, message, find, mine};
        Drawable[] drawables = {ContextCompat.getDrawable(mContext, R.drawable.ic_bottom_home),
                ContextCompat.getDrawable(mContext, R.drawable.ic_bottom_news),
                ContextCompat.getDrawable(mContext, R.drawable.ic_bottom_find),
                ContextCompat.getDrawable(mContext, R.drawable.ic_bottom_personal)};
        super.initView(layoutTextViews, strings, drawables);
        init();
        onTabClick(1);
    }

    private void init() {
        topbar = getView(R.id.topbar);
        topTabButton = getViewFromLayout(R.layout.layout_toptabbar, topbar, false);
        topTabButton.setRightText("通讯录");
        topTabButton.setLeftText("消息");
//        topTabButton.setLeftTabStatus(true);      //
        topTabButton.setRightTabStatus(true);       //
        topTabButton.onLeftTabClick(this, "onLeftTabClick");
        topTabButton.onRightTabClick(this, "onRightTabClick");
        topbar.getTvTitle().setVisibility(View.GONE);
        topbar.addViewToTopbar(topTabButton, (AutoRelativeLayout.LayoutParams) topTabButton.getLayoutParams())
                .enableRightImageBtn()
                .setRightImgBtnBg(R.drawable.nav_add)
                .setTopbarClickListener(this)
                .onRightImgBtnClick();
//        conversationListFragment = new ConversationListFragment();
//        hxContactListFragment = new ClientContactListFragment();

        topMenuPopupWindow = new TopMenuPopupWindow(this, TopMenuPopupWindow.MODE_SINGLE);
        topMenuPopupWindow.setMenuClickListener(this);
        // statusbarTint(topbar);

        initFragment();
//        fragments.add(new HomeFragment());
//        fragments.add(conversationListFragment);
//        fragments.add(new FindFragment());
//        fragments.add(new MyFragment());
//        fragments.add(hxContactListFragment);
    }

    private void initFragment() {
        fragments.add(new HomeFragment());
        fragments.add(/*hxContactListFragment*/new ClientContactListFragment());
        fragments.add(new FindFragment());
        fragments.add(new MyFragment());
        fragments.add(/*conversationListFragment*/new ConversationListFragment());
    }

    public void onLeftTabClick() {
        isMessage = true;
        onTabClick(2);
    }

    public void onRightTabClick() {
        isMessage = false;
        onTabClick(2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        topTabButton.setLeftTabStatus(isMessage);
        if (fragments != null && fragments.size() > 0)
            onTabClick(currentPageIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtility.fixInputMethodManagerLeak(this);
    }

    @Override
    public void onTobbarViewClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_right:
                if (topMenuPopupWindow != null) {
                    topMenuPopupWindow.showAsDropDownRightPadding(topbar, 40);
                }
                break;
        }
    }

    @Override
    public void menuMsgClick() {
        Intent intent = new Intent(mContext, PickContactActivity.class);
        intent.putExtra(C.IntentKey.PICK_CONTACT_PAGE_TYPE, PickContactActivity.TYPE_NEW_GROUP);
        showActivity(intent);
        //app.beanCacheHelper.getLocalParentCodes(C.ParentCode.USER_GROUP);
    }

    @Override
    public void menuAddFriendClick() {

    }

    @OnClick({R.id.tv_home, R.id.tv_news, R.id.tv_find, R.id.tv_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home:
                onTabClick(1);
                break;
            case R.id.tv_news:
                msgCount = 0;
                tvNewsCount.setVisibility(View.GONE);
                onTabClick(2);
                break;
            case R.id.tv_find:
                onTabClick(3);
                break;
            case R.id.tv_person:
                onTabClick(4);
                break;
        }
    }


    @Override
    protected void onReciverMsgCount(final int msgCount) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvNewsCount.setVisibility(View.VISIBLE);
                if (msgCount > 99) {
                    tvNewsCount.setText("99+");
                } else {
                    tvNewsCount.setText(Integer.toString(msgCount));
                }

            }
        });
    }

    @Override
    protected void onTabClick(int index) {
        super.onTabClick(index);
        topbar.setVisibility(index == 2 ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }
}
