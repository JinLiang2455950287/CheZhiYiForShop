package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.LazyFragmentCommonAdapter;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.SeckillHeadInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.SeckillHeadDatePresentr;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.SeckillHeadDateMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CountDownViewIndicator;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.commonlib.view.widget.coutdownview.CountdownView;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyViewPager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 秒杀列表界面
 * Created by hdl on 2016/9/12
 */
public class SeckillActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, SeckillHeadDateMvpView, CountDownViewIndicator.onCountDownFinshListener {
    CountDownViewIndicator countDownViewIndicator;
    Topbar topbar;
    LazyViewPager viewPager;
    List<Fragment> tabs = new ArrayList<>();
    LazyFragmentCommonAdapter lazyFragmentCommonAdapter;
    TabLayout tableLayout;
    /**
     * 头部信息
     */
    private List<SeckillHeadInfo> seckillHeadInfos;
    private SeckillHeadDatePresentr presenter = new SeckillHeadDatePresentr();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_seckill_list);
        presenter.attachView(this);
        initView();
        getSeckillHeadDate();
    }

    private void initView() {
        tableLayout=getView(R.id.tablayout);
        topbar = getView(R.id.topbar);
        viewPager = getView(R.id.content_panle);
        countDownViewIndicator = getView(R.id.indicator);
        topbar.setTttleText("秒杀")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        countDownViewIndicator.setOnCountDownFinshListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // LogX.d(TAG,"positionOffset"+positionOffset);
                //LogX.d(TAG,"positionOffsetPixels"+positionOffsetPixels);
             /* if(isScrolling) {
                  countDownViewIndicator.onPageScrolled(positionOffsetPixels / 2);
              }*/
            }

            @Override
            public void onPageSelected(int position) {
                countDownViewIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
           /*     if (state == 1) {
                    isScrolling = true;
                } else {
                    isScrolling = false;
                }*/
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownViewIndicator.stopCountDown();
        presenter.detachView();

    }
    /**
     * TopBar监听
     *
     * @param v
     */
    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 获取秒杀头部日期信息
     */
    private void getSeckillHeadDate() {
        Call<ResultBase<List<SeckillHeadInfo>>> call = app.getApiService().getSeckillHeadInfo(app
                .getCurrentUserNum());
        presenter.getSeckillHeadDate(call);
    }

    @Override
    public void showLoadingView(String msg) {
        showLoading(msg);
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showToast(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void onHeadDateResult(List<SeckillHeadInfo> result) {
        this.seckillHeadInfos = result;
        if (!seckillHeadInfos.isEmpty()) {
            for (int i = 0; i < (result.size() > 2 ? 2 : result.size()); i++) {
                SeckillHeadInfo headInfo = result.get(i);
                SecKillListFragment secKillListFragment = new SecKillListFragment();
                Bundle bundle = new Bundle();
                bundle.putString(C.IntentKey.SECKILLMAININFONUM, headInfo.getSeckillMainInfoNum());
                bundle.putParcelable(C.IntentKey.SECKILLINFO, headInfo);
                bundle.putInt(C.IntentKey.DAY_TIME, (i == 0 ? SecKillListFragment.LEFT : SecKillListFragment.RIGHT));
                secKillListFragment.setArguments(bundle);
                tabs.add(secKillListFragment);
                tableLayout.addTab(tableLayout.newTab().setText(""));
            }
            lazyFragmentCommonAdapter = new LazyFragmentCommonAdapter(getSupportFragmentManager(), tabs);
            viewPager.setAdapter(lazyFragmentCommonAdapter);
            tableLayout.setupWithViewPager(viewPager);
            countDownViewIndicator.startCountDown(result.get(0));
            countDownViewIndicator.startNextCountDown(result.get(1));
        }
    }

    @Override
    public void onCounDownFinish(CountdownView countdownView, int status) {
       switch (status){
           case CountDownViewIndicator.STATUS_END:
               ((SecKillListFragment)tabs.get(0)).refreshListStatus(false);
               break;
           case CountDownViewIndicator.STATUS_START:
               ((SecKillListFragment)tabs.get(0)).refreshListStatus(true);
               break;
       }
    }
}
