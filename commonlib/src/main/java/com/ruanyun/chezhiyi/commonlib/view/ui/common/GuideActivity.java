package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

/*
* 引导页
* */

public class GuideActivity extends AutoLayoutActivity {

    private final int[] GUIDEVIEWS = {R.drawable.bg_guide1, R.drawable.bg_guide2, R.drawable.bg_guide3};
    private ViewPager viewpager;

    private List<ImageView> guideViews = new ArrayList<>();
    private GuideAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewpager = new ViewPager(mContext);
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        setContentView(viewpager, params);
        intView();
    }

    private void intView() {
        guideViews.add(getImageView(GUIDEVIEWS[0]));
        guideViews.add(getImageView(GUIDEVIEWS[1]));
        guideViews.add(getImageView(GUIDEVIEWS[2]));
        mAdapter = new GuideAdapter(guideViews);
        guideViews.get(guideViews.size() - 1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipActivity(SplashActivity.class);
//                showActivity(SplashActivity.class);
//                finish();
            }
        });
        viewpager.setAdapter(mAdapter);
    }


    private ImageView getImageView(int resId) {
        ImageView imageView = new ImageView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(resId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    class GuideAdapter extends PagerAdapter {
        List<ImageView> mImageViews;

        public GuideAdapter(List<ImageView> imageViews) {
            mImageViews = imageViews;
        }

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews.get(position));
            return mImageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews.get(position));
        }
    }
}
