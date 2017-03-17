package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import com.ruanyun.chezhiyi.commonlib.model.FunctionMessage;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * @author hdl
 * 我的界面图标recycler
 */
public class ClientMyRecyclerViewAdapter extends MultiItemTypeAdapter<HomeIconInfo> {

    public ClientMyRecyclerViewAdapter(Context context, List<HomeIconInfo> datas) {
        super(context, datas);

        addItemViewDelegate(new FavorableItemDelagate(context));
        addItemViewDelegate(new MyFunctionItemDelagate(context));
    }
}
