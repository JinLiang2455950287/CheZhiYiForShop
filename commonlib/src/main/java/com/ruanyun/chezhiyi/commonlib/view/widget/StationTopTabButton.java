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
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Description ：仿扣扣三个切换标签
 * <p/>
 * Created by hdl on 2016/9/26.
 */
public class StationTopTabButton extends AutoLinearLayout {
	private TextView tabLeft;
	private TextView tabMiddle;
	private TextView tabMiddle1;
	private TextView tabRight;
	private String letfTabStr, middleTabStr, rightTabStr, middle1TabStr;



	public StationTopTabButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public StationTopTabButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.StationTopTabButton, 0, 0);
		if(ta!=null){
			letfTabStr = ta.getString(R.styleable.StationTopTabButton_station_lefttab_str);
			middleTabStr = ta.getString(R.styleable.StationTopTabButton_station_middletab_str);
			middle1TabStr = ta.getString(R.styleable.StationTopTabButton_station_middle1tab_str);
			rightTabStr = ta.getString(R.styleable.StationTopTabButton_station_righttab_str);
			ta.recycle();
		}
		initView(context);
	}

	public StationTopTabButton(Context context) {
		super(context);

	}

	@SuppressLint("NewApi")
	public StationTopTabButton(Context context, AttributeSet attrs, int defStyleAttr,
							   int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

	}

	@SuppressWarnings("ResourceType")
	private void initView(Context context) {
		setOrientation(HORIZONTAL);
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		tabLeft = new TextView(context);
		tabMiddle = new TextView(context);
		tabRight = new TextView(context);
		tabMiddle1=new TextView(context);

		tabLeft.setId(R.id.toptab_left);
		tabMiddle.setId(R.id.toptab_middle);
		tabMiddle1.setId(R.id.toptab_middle1);
		tabRight.setId(R.id.toptab_right);


		tabLeft.setLayoutParams(getTextLayoutParams());
		tabMiddle.setLayoutParams(getTextLayoutParams());
		tabMiddle1.setLayoutParams(getTextLayoutParams());
		tabRight.setLayoutParams(getTextLayoutParams());

		tabLeft.setBackgroundResource(R.drawable.top_tabbtn_left_selector);
		tabMiddle.setBackgroundResource(R.drawable.top_tabbtn_middle_selector);
		tabMiddle1.setBackgroundResource(R.drawable.top_tabbtn_middle_selector);
		tabRight.setBackgroundResource(R.drawable.top_tabbtn_right_selector);

		tabLeft.setTextColor(getResources().getColorStateList(R.drawable.toptab_textcolor_selector));
		tabMiddle.setTextColor(getResources().getColorStateList(R.drawable.toptab_textcolor_selector));
		tabMiddle1.setTextColor(getResources().getColorStateList(R.drawable.toptab_textcolor_selector));
		tabRight.setTextColor(getResources().getColorStateList(R.drawable.toptab_textcolor_selector));

		tabLeft.setGravity(Gravity.CENTER);
		tabMiddle.setGravity(Gravity.CENTER);
		tabRight.setGravity(Gravity.CENTER);
		tabMiddle1.setGravity(Gravity.CENTER);

		if (!TextUtils.isEmpty(letfTabStr)) {
			setLeftText(letfTabStr);
		}
		if (!TextUtils.isEmpty(middleTabStr)) {
			setLeftText(middleTabStr);
		}
		if (!TextUtils.isEmpty(rightTabStr)) {
			setRightText(rightTabStr);
		}
		if (!TextUtils.isEmpty(middle1TabStr)) {
			setRightText(middle1TabStr);
		}
		AutoUtils.auto(tabLeft);
		AutoUtils.auto(tabMiddle);
		AutoUtils.auto(tabMiddle1);
		AutoUtils.auto(tabRight);

		addView(tabLeft);
		addView(tabMiddle);
		addView(tabMiddle1);
		addView(tabRight);
	}

	private LayoutParams getTextLayoutParams() {
		LayoutParams params = new LayoutParams(/*194*/LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.weight = 1.0f;
		//params.width=;
		params.gravity = Gravity.CENTER;
		return params;
	}

	public void setUpTabClickListener(onTabClickListener listener){
		onTabClickListener=listener;
		setViewClickListener(tabLeft);
		setViewClickListener(tabMiddle);
		setViewClickListener(tabMiddle1);
		setViewClickListener(tabRight);
	}

	private void setViewClickListener(View view){

		view.setOnClickListener(new NoDoubleClicksListener() {
			@Override
			public void noDoubleClick(View v) {
				if(onTabClickListener!=null)
					onTabClickListener.onTabClick(v);
			}
		});
	}

	public void setLeftText(String text) {
		if (tabLeft != null) {
			tabLeft.setText(text);
		}
	}

	public void setMiddleText(String text) {
		if (tabMiddle != null) {
			tabMiddle.setText(text);
		}
	}

	public void setMiddle1Text(String text){
		if(tabMiddle1!=null){
			tabMiddle1.setText(text);
		}
	}

	public void setLeftTabStatus(boolean flag) {
		tabLeft.setSelected(flag);
		tabMiddle.setSelected(!flag);
		tabMiddle1.setSelected(!flag);
		tabRight.setSelected(!flag);
	}

	public void setMiddleTabStatus(boolean flag) {
		tabLeft.setSelected(!flag);
		tabMiddle.setSelected(flag);
		tabMiddle1.setSelected(!flag);
		tabRight.setSelected(!flag);
	}

	public void setTabMiddle1TabStatus(boolean flag){
		tabLeft.setSelected(!flag);
		tabMiddle.setSelected(!flag);
		tabMiddle1.setSelected(flag);
		tabRight.setSelected(!flag);
	}


	public void setRightTabStatus(boolean flag) {
		tabRight.setSelected(flag);
		tabMiddle.setSelected(!flag);
		tabLeft.setSelected(!flag);
		tabMiddle1.setSelected(!flag);
	}

	public void setLeftText(int resId) {
		setLeftText(getResources().getString(resId));
	}

	public void setMiddleText(int resId) {
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

	public boolean isMiddleTabChecked() {
		return tabLeft.isSelected();
	}

	public boolean isRightTabChecked() {
		return tabRight.isSelected();
	}

	public void setOnTabClickListener(StationTopTabButton.onTabClickListener onTabClickListener) {
		this.onTabClickListener = onTabClickListener;
	}
	onTabClickListener onTabClickListener;
	public  interface  onTabClickListener{
		void onTabClick(View v);
	}
}

