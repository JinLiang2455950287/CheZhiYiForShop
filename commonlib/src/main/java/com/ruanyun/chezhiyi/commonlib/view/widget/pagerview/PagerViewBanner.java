package com.ruanyun.chezhiyi.commonlib.view.widget.pagerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ViewPagerScroller;
import com.bigkoo.convenientbanner.listener.CBPageChangeListener;
import com.ruanyun.chezhiyi.commonlib.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2017/1/11.
 */

public class PagerViewBanner<T>
        extends LinearLayout {
    private List<T> mDatas;
    private int[] page_indicatorId;
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    private ArrayList<View> tabs = new ArrayList<>();
    private CBPageChangeListener pageChangeListener;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private ViewPager viewPager;
    private ViewPagerScroller scroller;
    private ViewGroup loPageTurningPoint;

    //    private int mPerRowCount = 1; //每一行的条数
    //    private int mPerPagerRow = 1; //每一页的行数
    private int mPerPagerCount; //每一页的总数


    private PVHolderCreator holderCreator;
    private int mPageCount;
    private Context mContext;

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    public PagerViewBanner(Context context) {
        super(context);
        init(context);
    }

    public PagerViewBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public PagerViewBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PagerViewBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        this.mContext = context;
        View hView = LayoutInflater.from(context).inflate(R.layout.include_pagerview_viewpager, this, true);
        viewPager = (ViewPager) hView.findViewById(R.id.cbLoopViewPager);
        loPageTurningPoint = (ViewGroup) hView.findViewById(R.id.loPageTurningPoint);
        initViewPagerScroll();
    }

    public PagerViewBanner setPages(PVHolderCreator holderCreator, List<T> datas) {
        this.mDatas = datas;
        this.holderCreator = holderCreator;
        mPerPagerCount = getPerPagerCount();
        //需要的页数
        mPageCount = (datas.size() / mPerPagerCount) + (datas.size() % mPerPagerCount == 0 ? 0 : 1);
        for (int i = 0; i < mPageCount; i++) {
            List<T> iconInfos = null;
            if (i < mPageCount - 1) {
                iconInfos = datas.subList(mPerPagerCount * i, mPerPagerCount * i + mPerPagerCount);
            } else {
                iconInfos = datas.subList(mPerPagerCount * i, datas.size());
            }
            tabs.add(holderCreator.addItemView(iconInfos));
        }

        viewPager.setAdapter(new PagerAdapter() {
            protected SparseArray<View> mLazyItems = new SparseArray<View>();

            @Override
            public int getCount() {
                return mPageCount;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View itemView = getItem(container, position);
                mLazyItems.put(position, itemView);
                return itemView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // we do not want to remove view at #position from container
            }

            private String makeTag(int position) {
                return String.format("Attach #%d to ViewPager", position);
            }
        });

        if (mPageCount > 1) {
            loPageTurningPoint.setVisibility(View.VISIBLE);
            if (page_indicatorId != null) setPageIndicator(page_indicatorId);
        }
        return this;
    }


    private View getItem(ViewGroup container, int position) {
        return tabs.get(position);
    }

    public int getPerPagerCount() {
        if (holderCreator == null) return 0;
        return holderCreator.perRowCount() * holderCreator.perPagerRow();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 通知数据变化
     * 如果只是增加数据建议使用 notifyDataSetAdd()
     */
    public void notifyDataSetChanged() {
        viewPager.getAdapter().notifyDataSetChanged();
        if (page_indicatorId != null) setPageIndicator(page_indicatorId);
    }

    /**
     * 设置底部指示器是否可见
     *
     * @param visible
     */
    public PagerViewBanner setPointViewVisible(boolean visible) {
        loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 底部指示器资源图片
     *
     * @param page_indicatorId
     */
    public PagerViewBanner setPageIndicator(int[] page_indicatorId) {
        loPageTurningPoint.removeAllViews();
        mPointViews.clear();
        for (int count = 0; count < mPageCount; count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(mContext);
            pointView.setPadding(5, 0, 5, 0);
            if (mPointViews.isEmpty()) {
                pointView.setImageResource(page_indicatorId[1]);
            } else {
                pointView.setImageResource(page_indicatorId[0]);
            }
            mPointViews.add(pointView);
            loPageTurningPoint.addView(pointView);
        }
        return this;
    }


    /**
     * 指示器的方向
     *
     * @param align 三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中 （RelativeLayout.CENTER_HORIZONTAL），居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */
    public PagerViewBanner setPageIndicatorAlign(PageIndicatorAlign align) {
        LinearLayout.LayoutParams layoutParams = (LayoutParams) loPageTurningPoint.getLayoutParams();
        int gravity = Gravity.CENTER_HORIZONTAL;
        if (align == PageIndicatorAlign.ALIGN_PARENT_LEFT) {
            gravity = Gravity.LEFT;
        } else if (align == PageIndicatorAlign.CENTER_HORIZONTAL) {
            gravity = Gravity.CENTER_HORIZONTAL;
        } else if (align == PageIndicatorAlign.ALIGN_PARENT_RIGHT) {
            gravity = Gravity.RIGHT;
        }
        layoutParams.gravity = gravity;
        loPageTurningPoint.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 自定义翻页动画效果
     *
     * @param transformer
     * @return
     */
    public PagerViewBanner setPageTransformer(ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
        return this;
    }


    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new ViewPagerScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    /**
     * 设置翻页监听器
     *
     * @param onPageChangeListener
     * @return
     */
    public PagerViewBanner setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
        if (pageChangeListener != null) pageChangeListener.setOnPageChangeListener(onPageChangeListener);
        else viewPager.setOnPageChangeListener(onPageChangeListener);
        return this;
    }

    /**
     * 设置ViewPager的滚动速度
     *
     * @param scrollDuration
     */
    public void setScrollDuration(int scrollDuration) {
        scroller.setScrollDuration(scrollDuration);
    }

    public int getScrollDuration() {
        return scroller.getScrollDuration();
    }

}
