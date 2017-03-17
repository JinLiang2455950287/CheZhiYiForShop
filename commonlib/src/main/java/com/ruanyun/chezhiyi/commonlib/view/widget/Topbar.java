package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.lang.reflect.Method;

/**
 * @author jery
 * @date 2016/4/20 14:15
 */
public class Topbar extends AutoRelativeLayout {

    public static final int WRAP_CONTENT = LayoutParams.WRAP_CONTENT;
    public static final int MACTCH_PARENT = LayoutParams.MATCH_PARENT;
    // 中间容器textview文字
    private String titleStr = "";
    // 中间容器textview文字大小
    private int titleTextSize = 18;
    // 标题默认字体颜色
    private static final int defaultTextColor = 0xffffffff;// 默认字体颜色
    // 默认背景颜色
    private static final int defaultBackGroudColor = 0xEDB526;

    private Context mContext;
    private ImageButton imgTitleLeft;
    private ImageButton imgTitleRight;
    private TextView tvTitle;
    private TextView tvTitleRight;
    private LayoutParams layoutParamsTitle, layoutParamsImgRight, layoutParamsImgLeft;

    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.Topbar, 0, 0);
        if (ta != null) {
            titleStr = ta.getString(R.styleable.Topbar_topbar_title_text);
        }
        ta.recycle();
        initView();
    }

    private void initView() {
        // setBackgroundDrawable(new ColorDrawable(getColor(R.color.theme_color_default)));
        setGravity(Gravity.CENTER_VERTICAL);
        initLayoutParams();

        imgTitleRight = new ImageButton(mContext);
        imgTitleLeft = new ImageButton(mContext);
        tvTitle = new TextView(mContext);
        tvTitleRight = new TextView(mContext);

        imgTitleRight.setId(R.id.img_btn_right);
        imgTitleLeft.setId(R.id.img_btn_left);
        tvTitle.setId(R.id.tv_topbar_title);
        tvTitleRight.setId(R.id.tv_title_right);


        layoutParamsTitle.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParamsImgLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParamsImgLeft.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParamsImgRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParamsImgRight.addRule(RelativeLayout.CENTER_VERTICAL);

        tvTitle.setText(checkNoneString(titleStr, getText(R.string.app_name)));
        tvTitle.setTextColor(defaultTextColor);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
//        tvTitle.setMaxWidth(500);
        tvTitle.setSingleLine();

        tvTitleRight.setTextColor(defaultTextColor);
        tvTitleRight.setPadding(20, 20, 30, 20);

        Drawable transparentDrawable = new BitmapDrawable();
        transparentDrawable.setAlpha(0);

        imgTitleRight.setBackgroundDrawable(transparentDrawable);
        imgTitleRight.setPadding(20, 20, 30, 20);
        imgTitleLeft.setBackgroundDrawable(transparentDrawable);
        imgTitleLeft.setPadding(45, 20, 20, 20);
        imgTitleLeft.setImageResource(R.drawable.icon_arrow_white);

//        layoutParamsTitle.setMargins(60, 0, 60, 0);
        AutoUtils.auto(tvTitle);
        addView(tvTitle, layoutParamsTitle);

    }


    private void initLayoutParams() {
        layoutParamsTitle = getWrapContentLayoutParams();
        layoutParamsImgLeft = getWrapContentLayoutParams();
        layoutParamsImgRight = getWrapContentLayoutParams();
    }

    public Topbar setBackBtnEnable(boolean enable) {
        if (enable) {
            AutoUtils.auto(imgTitleLeft);
            addView(imgTitleLeft, layoutParamsImgLeft);
        } else {
            removeView(imgTitleLeft);
        }
        return this;
    }

    public Topbar setLeftImgBtnBg(int resId) {
        this.imgTitleLeft.setImageResource(resId);
        return this;
    }

    public Topbar enableRightImageBtn() {
        addViewToTopbar(imgTitleRight, layoutParamsImgRight);
        return this;
    }

    public Topbar enableRightText() {
        addViewToTopbar(tvTitleRight, layoutParamsImgRight);
        return this;
    }

    public Topbar setRightImgBtnBg(int resId) {
        this.imgTitleRight.setImageResource(resId);
        return this;
    }

    public Topbar setRightImgBtnNull() {
        this.imgTitleRight.setOnClickListener(null);
        return this;
    }

    public Topbar setTttleText(String titleStr) {
        tvTitle.setText(titleStr);
        return this;
    }

    public Topbar setTttleText(int resId) {
        tvTitle.setText(getText(resId));
        return this;
    }

    public Topbar setRightText(String titleStr) {
        tvTitleRight.setText(titleStr);
        return this;
    }

    public Topbar setRightText(int resId) {
        tvTitleRight.setText(getText(resId));
        return this;
    }

    public Topbar onBackBtnClick(final Object base, final String method,
                                 final Object... parameters) {
        onTopbarViewClick(imgTitleLeft, base, method, parameters);
        return this;
    }

    public Topbar onBackBtnClick() {
        onTopbarViewClick(imgTitleLeft);
        return this;
    }

    public Topbar onRightImgBtnClick(final Object base, final String method,
                                     final Object... parameters) {
        onTopbarViewClick(imgTitleRight, base, method, parameters);
        return this;
    }

    public Topbar onRightImgBtnClick() {
        onTopbarViewClick(imgTitleRight);
        return this;
    }

    public Topbar onRightTextClick(final Object base, final String method,
                                   final Object... parameters) {
        onTopbarViewClick(tvTitleRight, base, method, parameters);
        return this;
    }

    public Topbar onRightTextClick() {
        onTopbarViewClick(tvTitleRight);
        return this;
    }

    public Topbar onTopbarViewClick(View v, final Object base, final String method,
                                    final Object... parameters) {
        v.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                onViewClick(base, method, parameters);
            }
        });
        return this;
    }

    public Topbar onTopbarViewClick(View v) {
        v.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                if (topbarClickListener != null)
                    topbarClickListener.onTobbarViewClick(v);
            }
        });
        return this;
    }

    public Topbar addViewToTopbar(View view, LayoutParams layoutParams) {
        AutoUtils.auto(view);
        addView(view, layoutParams);
        return this;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }


    public TextView getTvTitleRight() {
        return tvTitleRight;
    }

    public ImageButton getImgTitleRight() {
        return imgTitleRight;
    }


    public ImageButton getImgTitleLeft() {
        return imgTitleLeft;
    }


    public LayoutParams getWrapContentLayoutParams() {
        return new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
    }

    public LayoutParams getLayoutParams(int width, int height) {
        return new LayoutParams(width, height);
    }

    public LayoutParams getRightLayoutParams() {
        LayoutParams layoutParams = getWrapContentLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.setMargins(0, 25, 45, 0);
        return layoutParams;
    }

    public LayoutParams getLayoutParamsTitle() {
        return layoutParamsTitle;
    }

    public LayoutParams getLayoutParamsImgRight() {
        return layoutParamsImgRight;
    }

    public LayoutParams getLayoutParamsImgLeft() {
        return layoutParamsImgLeft;
    }

    private void onViewClick(Object base, String method,
                             Object... parameters) {
        int length = parameters.length;
        Class<?>[] paramsTypes = new Class<?>[length];
        for (int i = 0; i < length; i++) {
            paramsTypes[i] = parameters[i].getClass();
        }
        try {
            Method m = base.getClass().getMethod(method, paramsTypes);
            m.setAccessible(true);
            m.invoke(base, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String checkNoneString(String content, String defaultString) {
        if (TextUtils.isEmpty(content)) {
            return defaultString;
        } else {
            return content;
        }
    }

    private String getText(int stringId) {
        return getResources().getString(stringId);
    }

    private int getColor(int colorId) {
        return getResources().getColor(colorId);
    }


    public Topbar setTopbarClickListener(onTopbarClickListener topbarClickListener) {
        this.topbarClickListener = topbarClickListener;
        return this;
    }

    onTopbarClickListener topbarClickListener;

    public interface onTopbarClickListener {
        void onTobbarViewClick(View v);
    }
}
