package com.ruanyun.chezhiyi.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:客户接待三个切换标签
 * author: zhangsan on 16/10/8 下午5:22.
 */
public class ChooseServiceTab extends LinearLayout {
    public static final int TAB_WORK_STATION = 65;//选工位
    public static final int TAB_TECHNICIAN = 67;//选技师
    public static final int TAB_GOODS = 60;//选商品

    private TextView tvChooseWorkStation, tvChooseTechnican, tvAddGoods;
    private List<TextView> tablist = new ArrayList<>();
    private LayoutParams tabLayoutParams;
    private int currentTab=-1;
    public int relatedPosition=-1;//与列表关联的postion
    public String relatedProjectNum;
    public ChooseServiceTab(Context context) {
        super(context);
        init(context);
    }

    public ChooseServiceTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void initTabLayoutParams() {
        tabLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tabLayoutParams.weight = 1;
        tabLayoutParams.gravity=Gravity.CENTER_VERTICAL;
    }


    private void init(Context context) {
        initTabLayoutParams();
        setOrientation(HORIZONTAL);
        tvChooseTechnican = new TextView(context);
        tvAddGoods = new TextView(context);
        tvChooseWorkStation = new TextView(context);

        tvChooseWorkStation.setLayoutParams(tabLayoutParams);
        tvAddGoods.setLayoutParams(tabLayoutParams);
        tvChooseTechnican.setLayoutParams(tabLayoutParams);

        tvChooseTechnican.setBackgroundResource(com.ruanyun.chezhiyi.commonlib.R.drawable.box_choose_tab_selector);
        tvAddGoods.setBackgroundResource(com.ruanyun.chezhiyi.commonlib.R.drawable.box_choose_tab_selector);
        tvChooseWorkStation.setBackgroundResource(com.ruanyun.chezhiyi.commonlib.R.drawable.box_choose_tab_selector);

        //tvChooseTechnican.setGravity(Gravity.CENTER_VERTICAL);
       // tvAddGoods.setGravity(Gravity.CENTER_VERTICAL);
       // tvChooseWorkStation.setGravity(Gravity.CENTER_VERTICAL);
        tvChooseWorkStation.setText("选择工位");
        tvAddGoods.setText("添加服务商品");
        tvChooseTechnican.setText("选择技师");

        tvChooseWorkStation.setGravity(Gravity.CENTER);
        tvAddGoods.setGravity(Gravity.CENTER);
        tvChooseTechnican.setGravity(Gravity.CENTER);

        addView(tvChooseWorkStation);
        addView(tvChooseTechnican);
        addView(tvAddGoods);

         tvChooseWorkStation.setSelected(true);
        tablist.add(tvChooseWorkStation);
        tablist.add(tvChooseTechnican);
        tablist.add(tvAddGoods);
        tvChooseWorkStation.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                onTabClick(TAB_WORK_STATION);
            }
        });
        tvChooseTechnican.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                onTabClick(TAB_TECHNICIAN);
            }
        });
        tvAddGoods.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                onTabClick(TAB_GOODS);
            }
        });

    }

    private void onTabClick(int tabType) {
        if (onTabClickListener != null) {
            onTabClickListener.onTabClick(tabType,relatedPosition, relatedProjectNum);
        }
        setTabSelcted(tabType);
    }

    private void deselecAllTab(){
        for (int i = 0, size = tablist.size(); i < size; i++) {
            tablist.get(i).setSelected(false);
        }
    }

    public void setTabSelcted(int tabToSelcted){
        deselecAllTab();
        switch (tabToSelcted){
            case TAB_GOODS:
                tablist.get(2).setSelected(true);
                break;
            case TAB_TECHNICIAN :
                tablist.get(1).setSelected(true);
                break;
            case TAB_WORK_STATION :
                tablist.get(0).setSelected(true);
                break;
        }
        currentTab = tabToSelcted;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public void setOnTabClickListener(ChooseServiceTab.onTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    onTabClickListener onTabClickListener;
    public interface onTabClickListener {
        void onTabClick(int tabType,int relataedPostion,String relatedProjectNum);
    }
}
