package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.widget.EditText;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.DistrCommissionModel;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.view.widget.AfterMoneyChanged;
import com.ruanyun.chezhiyi.view.widget.ItemTextWatcher;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msq on 2016/10/9.
 * 分配提成adapter
 */
public class DistributionCommissionAdapter extends CommonAdapter<DistrCommissionModel> {

    private AfterMoneyChanged afterTextChanged;
    private boolean enable = true;//是否可以编辑
    private List<ItemTextWatcher> itemTextWatcherList= new ArrayList<>();

    public ItemTextWatcher getItemTextWatcher(int position) {
        return itemTextWatcherList.get(position);
    }

    public void addItemTextWatcherList(ItemTextWatcher itemTextWatcherList) {
        this.itemTextWatcherList.add( itemTextWatcherList );
    }

    public void setAfterTextChanged(AfterMoneyChanged afterTextChanged) {
        this.afterTextChanged = afterTextChanged;
    }

    public void setDatas(List<DistrCommissionModel> list){
        this.mDatas = list;
    }

    public List<DistrCommissionModel>  getDatas(){
        return mDatas;
    }

    public DistributionCommissionAdapter(Context context, int layoutId, List<DistrCommissionModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, final DistrCommissionModel item, int position) {
        AutoUtils.auto(viewHolder.getConvertView());
        viewHolder.setText(R.id.tv_name, item.getUserName());
        EditText editText = viewHolder.getView(R.id.editText);
        if (enable) {
            for (int i = 0; i < itemTextWatcherList.size(); i++) {
                editText.removeTextChangedListener(getItemTextWatcher(i));
            }
            ItemTextWatcher itemTextWatcher = getItemTextWatcher(position);
            editText.addTextChangedListener( itemTextWatcher );
        } else {
            editText.setEnabled(false);
        }
        editText.setText(String.valueOf(item.getCommissionAmount()));
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public double getAdapterMoney(){
        double sum=0;
        for (int i = 0; i < getDatas().size(); i++) {
            sum += getItem(i).getCommissionAmount();
            LogX.d("retrofit", getItem(i).toString());
        }
        return sum;
    }
}
