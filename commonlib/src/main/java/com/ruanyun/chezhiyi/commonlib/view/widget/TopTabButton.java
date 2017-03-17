package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.zhy.autolayout.AutoLinearLayout;

import java.lang.reflect.Method;

/**
 * 
 * @ClassName: TopTabButton
 * @Description: 仿扣扣切换标签
 * @author sjj
 * @date 2015-12-4 下午3:29:57
 * 
 */
public class TopTabButton extends AutoLinearLayout {
	private TextView tabLeft;
	private TextView tabRight;
	private String letfTabStr, rightTabStr;

	public TopTabButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public TopTabButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.TopTabButton, 0, 0);
		if(ta!=null){
		letfTabStr = ta.getString(R.styleable.TopTabButton_lefttab_str);
		rightTabStr = ta.getString(R.styleable.TopTabButton_righttab_str);
		ta.recycle();
		}
		initView(context);
	}

	public TopTabButton(Context context) {
		super(context);

	}

	@SuppressLint("NewApi")
	public TopTabButton(Context context, AttributeSet attrs, int defStyleAttr,
						int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

	}

	@SuppressWarnings("ResourceType")
	private void initView(Context context) {
		setOrientation(HORIZONTAL);
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tabLeft = new TextView(context);
		tabRight = new TextView(context);
		tabLeft.setId(R.id.toptab_left);
		tabRight.setId(R.id.toptab_right);

		tabLeft.setLayoutParams(getTextLayoutParams());
		tabRight.setLayoutParams(getTextLayoutParams());

		tabLeft.setBackgroundResource(R.drawable.top_tabbtn_left_selector);
		tabRight.setBackgroundResource(R.drawable.top_tabbtn_right_selector);
		tabLeft.setTextColor(getResources().getColorStateList(R.drawable.toptab_textcolor_selector));
		tabRight.setTextColor(getResources().getColorStateList(R.drawable.toptab_textcolor_selector));
		tabLeft.setGravity(Gravity.CENTER);
		tabRight.setGravity(Gravity.CENTER);
		if (!TextUtils.isEmpty(letfTabStr)) {
			setLeftText(letfTabStr);
		}
		if (!TextUtils.isEmpty(rightTabStr)) {
			setRightText(rightTabStr);
		}
		addView(tabLeft);
		addView(tabRight);
	}

	private LayoutParams getTextLayoutParams() {
		LayoutParams params = new LayoutParams(194,
				LayoutParams.WRAP_CONTENT);
		params.weight = 1.0f;
		//params.width=;
		params.gravity = Gravity.CENTER;
		return params;
	}

	public void onLeftTabClick(final Object base, final String methodName,
			final Object... parameters) {
		tabLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setLeftTabStatus(true);
				onViewClick(base,methodName,parameters);

			}
		});
	}

	public void onRightTabClick(final Object base, final String methodName,
			final Object... parameters) {
		tabRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setRightTabStatus(true);
             onViewClick(base,methodName,parameters);

			}
		});
	}
	private void onViewClick(final Object base, final String methodName,
							 final Object... parameters){
		int length = parameters.length;
		Class<?>[] paramsTypes = new Class<?>[length];
		for (int i = 0; i < length; i++) {
			paramsTypes[i] = parameters[i].getClass();
		}
		try {
			Method m = base.getClass().getDeclaredMethod(methodName,
					paramsTypes);
			m.setAccessible(true);
			m.invoke(base, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setLeftText(String text) {
		if (tabLeft != null) {
			tabLeft.setText(text);
		}
	}

	public void setLeftTabStatus(boolean flag) {
		tabLeft.setSelected(flag);
		tabRight.setSelected(!flag);
	}

	public void setRightTabStatus(boolean flag) {
		tabRight.setSelected(flag);
		tabLeft.setSelected(!flag);

	}

	public void setLeftText(int resId) {
		setLeftText(getResources().getString(resId));
	}

	public void setRightText(String text) {
		if (tabRight != null) {
			tabRight.setText(text);
		}
	}

	public void setRightText(int resId) {
		setRightText(getResources().getString(resId));
	}

	public boolean isLeftTabChecked() {
		return tabLeft.isSelected();
	}

	public boolean isRightTabChecked() {
		return tabRight.isSelected();
	}

}
