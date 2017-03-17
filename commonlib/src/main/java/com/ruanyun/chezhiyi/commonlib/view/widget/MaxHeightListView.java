package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/***
 * 
 * @ClassName: MaxHeightListView.java
 * @Description:  最大化高度的listview，防止嵌套异常
 * @author Daniel
 * @date 2015年12月2日上午10:22:14
 *
 */
public class MaxHeightListView extends ListView {
	public MaxHeightListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MaxHeightListView(Context context) {
		super(context);
	}

	public MaxHeightListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
