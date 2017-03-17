package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Description ：选择优惠券Adapter
 * <p/>
 * Created by hdl on 2016/9/21.
 */
public class SelectDiscountCouponAdapter extends CommonAdapter<OrderGoodsInfo> {
    HashMap<String, String> stairprojectMap = new HashMap<>();//一级工单服务分类Map

    public SelectDiscountCouponAdapter(Context context, int layoutId, List<OrderGoodsInfo> datas,
                                       HashMap<String, String> stairprojectMap) {
        super(context, layoutId, datas);
        this.stairprojectMap = stairprojectMap;
    }

    public void setDatas(List<OrderGoodsInfo> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 设置选择的优惠劵 （单选）
     */
    public void setSelectCoupon(){

    }

    @Override
    protected void convert(ViewHolder holder, OrderGoodsInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());
        String price = "¥" + item.getSalePrice();
        SpannableString spStr = new SpannableString(price);
        spStr.setSpan(new RelativeSizeSpan(2), 1, price.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        TextView tvPrice = holder.getView(R.id.tv_discount_price);//优惠券金额
        tvPrice.setText(spStr);
        TextView tvDiscountName = holder.getView(R.id.tv_discount_name);//优惠券名称
        tvDiscountName.setText(getCouponName(item.getProjectParent()));
        TextView tvDiscontProject = holder.getView(R.id.tv_discont_project);//服务项目或满多少元可用
        tvDiscontProject.setText("满" + item.getMarketPrice() + "元可用");
        TextView tvTimePeriod = holder.getView(R.id.tv_time_period);//优惠券有效日期
        String createTime = cropDateString(item.getValiditBeginDate());//创建日期
        String validitTime = cropDateString(item.getValiditEndDate());//有效期限
        tvTimePeriod.setText("有效期：" + createTime + "-" + validitTime);
        ImageView ivSelectState = holder.getView(R.id.iv_is_select);
        if (item.isSelect()) {
            ivSelectState.setVisibility(View.VISIBLE);
        } else {
            ivSelectState.setVisibility(View.GONE);
        }
    }

    /**
     * 日期裁剪
     */
    private String cropDateString(String s) {
        if (s != null && s.length() > 11) {
            return s.substring(0, 4) + "." + s.substring(5, 7) + "." + s.substring(8, 10);
        }
        return "";
    }

    public String getCouponName(String projectParent) {
        if ("000000".equals(projectParent)) {
            return "全场通用";
        }
        return stairprojectMap.get(projectParent) + "券";
    }

}
