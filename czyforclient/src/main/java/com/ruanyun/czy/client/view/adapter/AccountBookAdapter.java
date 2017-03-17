package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.ruanyun.chezhiyi.commonlib.model.AccountBookInfo;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * @author hdl
 * 记账本Adapter
 */
public class AccountBookAdapter extends CommonAdapter<AccountBookInfo> {

    public AccountBookAdapter(Context context, int layoutId, List<AccountBookInfo> datas) {
        super(context, layoutId, datas);
    }

    public List<AccountBookInfo> getDatas() {
        return mDatas;
    }

    public void setDatas(List<AccountBookInfo> datas) {
        mDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, AccountBookInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());
        AccountBookInfo info = mDatas.get(position);
        String time = info.getBookDate();
        if(info.getBookType()==1) {
            time = time.substring(5,7)+"/"+time.substring(8,10)+time.substring(10,16);
        }else {
            time = time.substring(5,7)+"/"+time.substring(8,10);
        }
        holder.setText(R.id.tv_account_disbursement_time, time);
        if(info.getProjectName()!=null) {
            holder.setText(R.id.tv_account_book_type, String.format("【%s】", info.getProjectName()));
        }else holder.setText(R.id.tv_account_book_type, "【其他】");
        holder.setText(R.id.tv_account_remark, info.getRemark());
        String bookPrice = info.getBookPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        holder.setText(R.id.tv_account_bookPrice, String.format("%s", bookPrice));

        ImageView img = holder.getView(R.id.iv_account_book_list_img);
        if(item.getAttachInfoList().size()>0){
            img.setVisibility(View.VISIBLE);
            img.setImageResource(R.drawable.icon_account_pic);
        }else img.setVisibility(View.GONE);


    }

}
