package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.FunctionMessage;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author hdl
 * 我的界面 图标 adapter
 */
public class ShopMyRecyclerViewAdapter extends MultiItemTypeAdapter<HomeIconInfo> {

    public ShopMyRecyclerViewAdapter(Context context, List<HomeIconInfo> datas) {
        super(context, datas);

        addItemViewDelegate(new ServiceWorkorderItemDelagate(context));
        addItemViewDelegate(new ServiceStatusItemDelagate(context));
        addItemViewDelegate(new MyFunctionItemDelagate(context));
    }

    public void setDatas(List<HomeIconInfo> datas){
        this.mDatas = datas;
    }
}
