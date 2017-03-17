package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description ：已发送优惠劵
 * <p/>
 * Created by ycw on 2016/11/28.
 */
public class CouponUserListAdapter extends CommonAdapter<OrderGoodsInfo> {

    private CouponUserClickListener listener;

    public void setListener(CouponUserClickListener listener) {
        this.listener = listener;
    }

    public CouponUserListAdapter(Context context, int layoutId, List<OrderGoodsInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, final OrderGoodsInfo item, int position) {
        viewHolder.setText(R.id.tv_name, item.getOrderUserName());
        viewHolder.setTextColorRes(R.id.tv_name, R.color.theme_color_default);
        viewHolder.setOnClickListener(R.id.tv_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.couponUserClick(item.getOrderUserNum());
            }
        });
        viewHolder.setText(R.id.tv_num, "优惠劵编号:"+item.getOrderNum());
        viewHolder.setText(R.id.tv_time, TextUtils.isEmpty(item.getOrderCreateTime())?"":StringUtil.getTimeStrFromFormatStr("yyyy/MM/dd",item.getOrderCreateTime()));
        if (item.getOrderStatus() > 2) {
            viewHolder.setText(R.id.tv_state, "已使用");
            viewHolder.setTextColorRes(R.id.tv_state, R.color.text_black);
        } else {
            viewHolder.setText(R.id.tv_state, "未使用");
            viewHolder.setTextColorRes(R.id.tv_state, R.color.orange);
        }
    }

    public void addAllDatas(List<OrderGoodsInfo> datas) {
        this.mDatas.addAll(datas);
    }

    public void setDatas(List<OrderGoodsInfo> datas) {
        this.mDatas = datas;
    }

    public interface CouponUserClickListener{
        void couponUserClick(String userNum);
    }

}
