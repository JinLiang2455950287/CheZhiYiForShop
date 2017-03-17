package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.view.ui.mine.DiscountCouponActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Description ：技师优惠券管理Adapter
 * <p/>
 * Created by hdl on 2016/9/22.
 */
public class DiscountCouponAdapter extends CommonAdapter<ProductInfo> {
    private String couponType;
    HashMap<String,String> stairprojectMap = new HashMap<>();//一级工单服务分类Map
    public DiscountCouponAdapter(Context context, int layoutId, List<ProductInfo> datas,
                                 String couponType, HashMap<String,String> stairprojectMap) {
        super(context, layoutId, datas);
        this.couponType = couponType;
        this.stairprojectMap = stairprojectMap;
    }
    public void setDatas(List<ProductInfo> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, ProductInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());
        String price = "¥" + item.getSalePrice();
        SpannableString spStr = new SpannableString(price);
        spStr.setSpan(new RelativeSizeSpan(2),1,price.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        TextView tvPrice = holder.getView(R.id.tv_discount_price);//优惠券金额
        tvPrice.setText(spStr);
        TextView tvDiscountName = holder.getView(R.id.tv_discount_name);//优惠券名称
        TextView tvDiscontProject = holder.getView(R.id.tv_discont_project);//抵扣服务项目或满多少元可用
        if(ProductInfo.REBATE_COUPON.equals(item.getGoodsType())){//抵扣券
            tvDiscountName.setText(item.getGoodsName());
//            tvDiscontProject.setText("抵扣"+stairprojectMap.get(item.getProjectParent())+"服务一次");
            tvDiscontProject.setText(item.getGoodsDescrip());
        }else {
            tvDiscountName.setText(getCouponName(item.getProjectParent()));
            tvDiscontProject.setText("满" + item.getMarketPrice() + "元可用");
        }
        TextView tvTimePeriod = holder.getView(R.id.tv_time_period);//优惠券有效日期
        String createTime = cropDateString(item.getValiditBeginDate());//优惠卷有效期开始时间
        String validitTime = cropDateString(item.getValiditEndDate());//优惠卷有效期限结束时间
        tvTimePeriod.setText("有效期："+createTime+"-"+validitTime);
        TextView tvUseOrDue = holder.getView(R.id.tv_use_or_due);
        tvUseOrDue.setVisibility(View.VISIBLE);
        if(DiscountCouponActivity.UNUSED.equals(couponType)){//未送出
            tvUseOrDue.setText(item.getCouponCount()+"张");
            (holder.getView(R.id.ll_coupon_bg_photo)).setBackgroundResource(R.drawable.coupon_bg_normal);
        }else {
            (holder.getView(R.id.ll_coupon_bg_photo)).setBackgroundResource(R.drawable.coupon_bg_overdue);
            ColorStateList csl = mContext.getResources().getColorStateList(R.color.border_bg);
            tvPrice.setTextColor(csl);
            tvDiscountName.setTextColor(csl);
            tvDiscontProject.setTextColor(csl);
            tvTimePeriod.setTextColor(csl);
            tvUseOrDue.setTextColor(csl);
            if(DiscountCouponActivity.SEND.equals(couponType)){//已送出
                tvUseOrDue.setText(String.format("已送出%d张", item.getCouponCountAll() - item.getCouponCount()));
            }else if (DiscountCouponActivity.EXCEED_TIME_LIMIT.equals(couponType)){//已过期
                tvUseOrDue.setText("已过期");
            }
        }
    }

    /**
     * 日期裁剪
     */
    private String cropDateString(String s) {
        if(s!=null&&s.length()>11) {
            return s.substring(0, 4) + "." + s.substring(5, 7) + "." + s.substring(8, 10);
        }
        return "";
    }

    public String getCouponName(String projectParent){
        if("000000".equals(projectParent)){
            return "全场通用";
        }
        return stairprojectMap.get(projectParent)+"券";
    }

}
