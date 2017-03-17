package com.ruanyun.chezhiyi.commonlib.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description:
 * author: zhangsan on 16/9/13 上午11:10.
 */
public class CommonFragmentAdapter extends PagerAdapter {
    private List<Fragment> fragments;

    private FragmentManager fragmentManager;
    private FragmentTransaction mCurTransaction = null;

    public CommonFragmentAdapter(List<Fragment> fragments, FragmentManager fragmentManager) {
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void startUpdate(ViewGroup container) {

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
