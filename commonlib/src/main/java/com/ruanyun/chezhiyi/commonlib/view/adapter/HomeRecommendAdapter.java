package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.CommonAdapter;
import com.ruanyun.chezhiyi.commonlib.model.ParentCodeInfo;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;


/**
 * Description ： 首页推荐的
 * <p/>
 * Created by ycw on 2016/9/12.
 */
public class HomeRecommendAdapter extends CommonAdapter<RecommendInfo> {

    public HomeRecommendAdapter(Context context, int layoutId, List<RecommendInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, RecommendInfo recommendInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageView ivImage = holder.getView(R.id.iv_image);
        TextView tvSalesVolume = holder.getView(R.id.tv_sales_volume);
        FrameLayout flImage = holder.getView(R.id.fl_image);
        TextView tvTitle = holder.getView(R.id.tv_title);
        TextView tvType = holder.getView(R.id.tv_type);
        TextView tvTimeLimit = holder.getView(R.id.tv_time_limit);
        TextView tvPrice = holder.getView(R.id.tv_price);
        TextView tvOldPrice = holder.getView(R.id.tv_old_price);
        TextView tvComment = holder.getView(R.id.tv_comment);
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(recommendInfo.getMainPhoto()), ivImage, R.drawable.default_img);
//        Glide.with(mContext).load(FileUtil.getImageUrl(recommendInfo.getMainPhoto())).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(ivImage);
        LogX.d("HomeRecommendAdapter", "-------->" + FileUtil.getImageUrl(recommendInfo.getMainPhoto()));
        tvComment.setText(new StringBuilder().append(recommendInfo.getCommentCount()).append("评论").toString());
        tvOldPrice.setText(new StringBuilder().append("¥").append(recommendInfo.getMarketPrice()).toString());
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线（删除线）
        tvSalesVolume.setText(new StringBuilder().append("已售").append(recommendInfo.getSaleCount()).toString());
        tvPrice.setText(getPriceSpannableString(new StringBuilder().append("¥")
                .append(recommendInfo.getActivityPrice() == 0 ? recommendInfo.getSalePrice() : recommendInfo.getActivityPrice()).toString()));
        tvTitle.setText(recommendInfo.getTitle());
        tvType.setText(recommendInfo.getTypeName());
        setTypeAddTime(tvType, tvTimeLimit, recommendInfo);

    }

    private SpannableString getPriceSpannableString(String str) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new RelativeSizeSpan(1.2f), 1, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 类型背景色  秒杀时间
     *
     * @param tvType
     * @param tvTimeLimit
     * @param recommendInfo
     */
    private void setTypeAddTime(TextView tvType, TextView tvTimeLimit, RecommendInfo recommendInfo) {
        switch (recommendInfo.getGoodsType()) {
            case RecommendInfo.GOODS_TYPE_TG://团购
                tvTimeLimit.setVisibility(View.GONE);
                tvType.setBackgroundResource(R.drawable.shape_tg_bg);
                break;

            case RecommendInfo.GOODS_TYPE_CP://产品
                tvTimeLimit.setVisibility(View.GONE);
                tvType.setBackgroundResource(R.drawable.shape_cp_bg);
                break;

            case RecommendInfo.GOODS_TYPE_ZC://众筹
                tvTimeLimit.setVisibility(View.GONE);
                tvType.setBackgroundResource(R.drawable.shape_zc_bg);
                break;

            case RecommendInfo.GOODS_TYPE_MS://秒杀
                tvTimeLimit.setText("距离开始：" + recommendInfo.getBeginTime());
                tvType.setBackgroundResource(R.drawable.shape_ms_bg);
                break;

            case RecommendInfo.GOODS_TYPE_CX://促销
                tvTimeLimit.setText("限时购：" + StringUtil.getMonthDayTime(recommendInfo.getBeginDate()) + "-" + StringUtil.getMonthDayTime(recommendInfo.getEndDate()));
                tvType.setBackgroundResource(R.drawable.shape_cx_bg);
                break;
            default:
                tvTimeLimit.setVisibility(View.GONE);
                tvType.setVisibility(View.GONE);
                break;
        }
    }

    public void setDatas(List<RecommendInfo> result) {
        this.mDatas = result;
    }


    /**
     * 添加 typeName
     *
     * @param result
     * @return
     */
    public List<RecommendInfo> getTypeNameList(List<RecommendInfo> result) {
        List<ParentCodeInfo> codeInfoList = DbHelper.getInstance().getParentCodeList(C.ParentCode.RECOMMENT_PROJECT_GOODS_TYPE);
        for (RecommendInfo recommendInfo : result) {
            for (ParentCodeInfo parentCode : codeInfoList) {
                if (recommendInfo.getGoodsType().equals(parentCode.getItemCode())) {
                    recommendInfo.setTypeName(parentCode.getItemName());
                }
            }
        }
        return result;
    }

}
