package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.CrowdFundingInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 司机我的众筹Adapter
 * Created by hdl on 2016/9/21
 */
public class MyCrowdFundingAdapter extends CommonAdapter<CrowdFundingInfo> {
    private String type;
    public MyCrowdFundingAdapter(Context context, int layoutId, List<CrowdFundingInfo> datas, String  type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    public void setData(List<CrowdFundingInfo> datas){
        mDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, final CrowdFundingInfo item, final int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageUtil.loadImage(mContext,FileUtil.getImageUrl(item.getMainPhoto()),
                (ImageView) holder.getView(R.id.iv_product_photo),R.drawable.default_img);//主图
        SpannableString spStr = new SpannableString(item.getSalePrice()==null?"¥0":"¥"+item.getSalePrice());
        spStr.setSpan(new RelativeSizeSpan(0.7f),0,1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        TextView tvPrice = holder.getView(R.id.tv_price);
        tvPrice.setText(spStr);//销售价
        holder.setText(R.id.tv_title,item.getTitle());//标题
        TextView tvSubtitle = holder.getView(R.id.tv_subtitle);//副标题
        TextView tvLimitNum = holder.getView(R.id.tv_limit_num);//满多少人售或购得或停售
        TextView tvTimeOrNum = holder.getView(R.id.tv_sold_number);////众筹剩余时间或人数
        if(type.equals(CrowdFundingInfo.UNDERWAY)) {//进行中
            tvSubtitle.setVisibility(View.GONE);//副标题不显示
            ProgressBar progressBar = holder.getView(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);//显示进度条
            int progress;
            if(item.getFullNum()<=0){
                progress = 0;
            }else {
                progress = item.getPartNum()*100/item.getFullNum();
            }
            progressBar.setProgress(progress);//显示进度
            holder.setText(R.id.tv_percentage, progress+"%");//显示进度百分比
            holder.setText(R.id.tv_limit_num,"满"+item.getFullNum()+"人即售");
            int residueDay;
            if(item.getEndTime()!=null&&item.getEndTime().length()>0){
                residueDay = residueDay(item.getEndTime());//众筹剩余时间
            }else {
                residueDay = 0;
            }
            String str="剩余<font color='#FBBB53'>"+residueDay+"</font>天";
            tvTimeOrNum.setText(Html.fromHtml(str));
        }else if(type.equals(CrowdFundingInfo.ENDED)){//已结束
            if(CrowdFundingInfo.SUCCEED.equals(item.getStatus())){//成功
                tvSubtitle.setText("众筹成功");
                tvLimitNum.setText("购得");
                tvTimeOrNum.setText(item.getPartNum()+"人参与");
            }else if(CrowdFundingInfo.FAIL.equals(item.getStatus())){//失败停售
                tvSubtitle.setText("众筹失败");
                tvLimitNum.setText("停售");
                tvTimeOrNum.setText("人数不足");
            }
        }
    }

    /**
     * 众筹剩余时间
     * @param endTime
     */
    private int residueDay(String endTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = new Date();
        try {
            endDate = timeFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long residueTime = (endDate.getTime()+(24*60*60*1000)-1)-System.currentTimeMillis();
        if(residueTime<=0){
            return 0;
        }
        double day = residueTime/1000.0/60/60/24;
        return (int)(day);
    }

}
