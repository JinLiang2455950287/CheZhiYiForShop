package com.ruanyun.czy.client.view.widget;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.util.LogX;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

	private static final float MIN_SCALE = 0.9f;
	private static final float MIN_ALPHA = 0.5f;

	private static float defaultScale = 0.9f;

	public void transformPage(View view, float position) {
		int pageWidth = view.getWidth();
		int pageHeight = view.getHeight();
		LogX.d("ZoomOutPageTransformer","position"+position);
		if (position < -1||position>1) { // [-Infinity,-1)
			// This page is way off-screen to the left.
//			view.setAlpha(0);
			view.setScaleX(defaultScale);
			view.setScaleY(defaultScale);
		} else if (position <= 1) { // [-1,1]
			// Modify the default slide transition to shrink the page as well
			float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
			float vertMargin = pageHeight * (1 - scaleFactor) / 2;
			float horzMargin = pageWidth * (1 - scaleFactor) / 2;
			if (position < 0) {
				view.setTranslationX(horzMargin - vertMargin / 2);
			} else {
				view.setTranslationX(-horzMargin + vertMargin / 2);
			}

			// Scale the page down (between MIN_SCALE and 1)
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);
		} /*else if(position>=1){ // (1,+Infinity]
			// This page is way off-screen to the right.
//			view.setAlpha(0);
			view.setScaleX(defaultScale);
			view.setScaleY(defaultScale);
		}*/
	}
}
