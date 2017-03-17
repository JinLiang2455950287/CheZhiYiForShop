package com.ruanyun.chezhiyi.commonlib.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyFragmentPagerAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyPagerAdapter;

import java.util.List;

/**
 * Description:
 * author: zhangsan on 16/9/19 下午4:17.
 */
public class LazyFragmentCommonAdapter extends LazyFragmentPagerAdapter {
   List<Fragment> tabs;

    public LazyFragmentCommonAdapter(FragmentManager fm, List<Fragment> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    protected Fragment getItem(ViewGroup container, int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
