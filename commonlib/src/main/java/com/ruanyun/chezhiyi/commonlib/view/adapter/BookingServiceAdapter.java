package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：预约详情界面
 * <p/>
 * Created by hdl on 2016/9/19.
 */
public class BookingServiceAdapter extends MultiItemTypeAdapter<WorkOrderInfo> {

    private boolean hasChild = true;

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public BookingServiceAdapter(Context context, List<WorkOrderInfo> datas) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<WorkOrderInfo>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_server_parent_type_workorder;
            }

            @Override
            public boolean isForViewType(WorkOrderInfo item, int position) {
                return item.isParent();
            }

            @Override
            public void convert(ViewHolder holder, final WorkOrderInfo item, int position) {
                AutoUtils.auto(holder.getConvertView());
                TextView tvTypeName = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.tv_type_name);
                TextView tvPrice = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.tv_price);
                tvPrice.setVisibility(View.VISIBLE);
                tvPrice.setTextSize(12);
                tvPrice.setTextColor(Color.rgb(170,170,170));
                String str="(订金：<font color='#FBBB53'>¥"+item.getDownPayment()+"</font>)";
                tvPrice.setText(Html.fromHtml(str));
                tvTypeName.setText(item.getProjectName());
                if (hasChild) {
                    holder.setVisible(R.id.iv_type_name, true);
                    if (item.isSelected()) {
                        holder.getView(R.id.iv_type_name).setSelected(true);
                    } else {
                        holder.getView(R.id.iv_type_name).setSelected(false);
                    }
                } else {
                    String text = String.format("（合计：¥%s）", item.getTotalAmount());
                    SpannableString ss = new SpannableString(text);
                    ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.text_orange)), 3, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvPrice.setText(ss);
                    holder.setVisible(R.id.iv_type_name, false);
                }
                if(onBookingServiceClickListener!=null) {
                    TextView tvLookWorkorder = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.tv_look);
                    tvLookWorkorder.setVisibility(View.VISIBLE);
                    tvLookWorkorder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBookingServiceClickListener.onBookingServiceClick(item);
                        }
                    });
                }
            }
        });

        addItemViewDelegate(new ItemViewDelegate<WorkOrderInfo>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_server_chid_type;
            }

            @Override
            public boolean isForViewType(WorkOrderInfo item, int position) {
                return !item.isParent();
            }

            @Override
            public void convert(ViewHolder holder, WorkOrderInfo projectType, int position) {
                AutoUtils.auto(holder.getConvertView());
                TextView tvServerSubType = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.tv_server_sub_type);
                tvServerSubType.setText(projectType.getProjectName());
                tvServerSubType.setSelected(true);
            }
        });
    }

    public void setDatas(List<WorkOrderInfo> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }
    public interface OnBookingServiceClickListener {
        void onBookingServiceClick(WorkOrderInfo workOrderInfo);
    }

    // click callback
    OnBookingServiceClickListener onBookingServiceClickListener;

    public void setOnBookingServiceClickListener(OnBookingServiceClickListener onBookingServiceClickListener) {
        this.onBookingServiceClickListener = onBookingServiceClickListener;
    }
}
