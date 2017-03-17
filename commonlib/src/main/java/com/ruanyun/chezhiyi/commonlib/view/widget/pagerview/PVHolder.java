package com.ruanyun.chezhiyi.commonlib.view.widget.pagerview;

import android.content.Context;
import android.view.View;

public interface PVHolder<T>{
    View createView(Context context);
    void updateUI(Context context,int position,T data);

}