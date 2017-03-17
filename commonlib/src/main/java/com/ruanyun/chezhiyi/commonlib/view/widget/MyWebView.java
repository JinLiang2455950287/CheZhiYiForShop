package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebView;


/**
 * Description ：滚动监听的 webView
 * <p/>
 * Created by ycw on 2016/12/6.
 */

public class MyWebView
        extends WebView {

    private onScrollChangedListent listent;

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context) {
        super(context);
    }

    public void setListent(onScrollChangedListent listent) {
        this.listent = listent;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listent != null) listent.onScrollChang(l, t, oldl, oldt);
    }

    public interface onScrollChangedListent {
        void onScrollChang(int l, int t, int oldl, int oldt);
    }
}
