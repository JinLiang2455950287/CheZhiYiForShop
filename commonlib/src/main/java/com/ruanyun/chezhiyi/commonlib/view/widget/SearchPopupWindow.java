package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * 搜索泡泡窗口
 * Created by ycw on 2016/9/8.
 */
public class SearchPopupWindow extends PopupWindow implements TextView.OnEditorActionListener, Topbar.onTopbarClickListener, PopupWindow.OnDismissListener {

    private Context mContext;
    private CleanableEditText seachText;
    private LayoutInflater inflate;
    private Topbar topbar;
    private ListView searchLv;
    private BaseAdapter searchAdapter;
    private AdapterView.OnItemClickListener itemClickListener;
    private View bindView;
    private View popView;
    private String searchStr;
    private SearchPopupWindowAction action;

    public SearchPopupWindow(Context context, View bindView, SearchPopupWindowAction popupWindowAction, BaseAdapter searchAdapter, AdapterView.OnItemClickListener itemClickListener) {
        super(context);
        this.mContext = context;
        this.bindView = bindView;
        this.inflate = LayoutInflater.from(context);
        this.action= popupWindowAction;
        this.searchAdapter = searchAdapter;
        this.itemClickListener = itemClickListener;
        initView();
    }

    private void initView() {
        popView = inflate.inflate(R.layout.layout_search_popu_view, null);
        topbar = (Topbar) popView.findViewById(R.id.topbar);
        searchLv = (ListView) popView.findViewById(R.id.list_search);
        setContentView(popView);
        setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.color.half_transparent_black));
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setOnDismissListener(this);
        setFocusable(true);
        setOutsideTouchable(false);
        seachText = (CleanableEditText) inflate.inflate(R.layout.ease_layout_search_edttext, topbar, false);
        seachText.setOnEditorActionListener(this);
        topbar.addViewToTopbar(seachText, (AutoRelativeLayout.LayoutParams) seachText.getLayoutParams())
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .setBackBtnEnable(true)
                .setRightText("取消")
                .onRightTextClick()
                .onBackBtnClick()
                .setTopbarClickListener(this);
        topbar.getTvTitle().setVisibility(View.GONE);
        setBindViewClickListener();
        searchLv.setOnItemClickListener(this.itemClickListener);
        setSearchAdapter(searchAdapter);

        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public BaseAdapter getSearchAdapter() {
        return searchAdapter;
    }

    public void setSearchAdapter(BaseAdapter searchAdapter) {
        this.searchAdapter = searchAdapter;
        searchLv.setAdapter(searchAdapter);
    }

    public void bindView(View view) {
        bindView = view;
        setBindViewClickListener();
    }

    public void notifyDataSetChanged(){
        searchAdapter.notifyDataSetChanged();
    }

    public void setAction(SearchPopupWindowAction action) {
        this.action = action;
    }

    public void show() {
        if (bindView != null) {
            seachText.requestFocus();
            bindView.setVisibility(View.GONE);
            showAtLocation(bindView, Gravity.TOP, 0, 0);
            setBgAlpha(0.7f);
        }
    }

    public void setBindViewClickListener(){
        bindView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowing()) {
                    show();
                }
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {//按下输入法搜索键
            searchStr = seachText.getText().toString().trim();
            if (!TextUtils.isEmpty(searchStr)) {
                if (action != null)
                    action.startSearchString(searchStr);
            }
        }
        return false;
    }

    @Override
    public void onTobbarViewClick(View v) {
        this.dismiss();
        if(v.getId()==R.id.img_btn_right)
        action.startSearchString("");
    }

    @Override
    public void onDismiss() {
        if (bindView != null) {
            clearSearchData();
            bindView.setVisibility(View.VISIBLE);
            setBgAlpha(1f);
        }
    }

    private void clearSearchData() {
        if (action != null)
            action.clearData();
        seachText.setText("");
    }

    public void setBgAlpha(float alpha){
        WindowManager.LayoutParams layoutParams = ((Activity)mContext).getWindow().getAttributes();
        layoutParams.alpha = alpha;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN;
        ((Activity)mContext).getWindow().setAttributes(layoutParams);
        //   5.1  有效
        ((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public interface SearchPopupWindowAction {
        void startSearchString(String searchStr);

        /**
         * popWindow  消失的时候调用清除  searchAdapter 的数据
         */
        void clearData();
    }
}
