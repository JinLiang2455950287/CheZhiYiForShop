package com.ruanyun.czy.client.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruanyun.czy.client.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * author: zhangsan on 16/9/29 下午2:01.
 */
public class ViewPagerPointIndicator extends LinearLayout {
    private List<TextView> indicators = new ArrayList<>();

    public ViewPagerPointIndicator(Context context) {
        super(context);

    }

    public ViewPagerPointIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private TextView creatPointer(Context contetx) {
        TextView pointer = new TextView(contetx);
        LayoutParams layoutParam = new LayoutParams(28, 28);
        layoutParam.setMargins(15,0,0,0);
        pointer.setLayoutParams(layoutParam);
        pointer.setBackgroundResource(R.drawable.viewpager_indicator_selector);
        AutoUtils.auto(pointer);
        return pointer;
    }

    public void addPoint(Context contetx, int size) {
        for (int i = 0; i < size; i++) {
            TextView indicator=creatPointer(contetx);
            addView(indicator);
            indicators.add(indicator);
        }
    }

    public void addPoint(Context context){
        TextView indicator=creatPointer(context);
        addView(indicator);
        indicators.add(indicator);
    }

    public void removePoint(){
        removeView(indicators.remove(indicators.size()-1));
    }
    /**
      * 切换圆点指定位置选中
      *@author zhangsan
      *@date   16/10/12 上午11:37
      */
    public void changeIndicator(int index) {
        for (int i = 0,size=indicators.size(); i < size; i++) {
           if(i==index){
               indicators.get(i).setSelected(true);
           }else {
               indicators.get(i).setSelected(false);
           }
        }
    }

}
