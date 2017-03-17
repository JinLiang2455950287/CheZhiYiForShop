package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by msq on 2016/9/10.
 */
public class ActivityListAdapter extends CommonAdapter<ActivityInfo>{

    private String activityType = null;
    public ActivityListAdapter(Context context, int layoutId, List<ActivityInfo> datas, String type) {
        super(context, layoutId, datas);
        this.activityType = type;
    }

    @Override
    protected void convert(ViewHolder holder, ActivityInfo item, int position) {

        AutoUtils.auto(holder.getConvertView());

        holder.setText(R.id.tv_title,item.getActivityName());
        holder.setText(R.id.tv_time,sub(item.getActivityBeginTime()) + "-" + sub(item.getActivityEndTime()));

        if("1".equals(activityType)){
            holder.setTextColorRes(R.id.tv_time,R.color.theme_color_default);
            holder.setImageResource(R.id.iv,R.drawable.icon_myactivity_time);
        }
        if("2".equals(activityType)){
            holder.setTextColorRes(R.id.tv_time,R.color.theme_color_default);
            holder.setImageResource(R.id.iv,R.drawable.icon_myactivity_time);
        }
        if("3".equals(activityType)){
            holder.setTextColorRes(R.id.tv_time,R.color.text_gray);
            holder.setImageResource(R.id.iv,R.drawable.icon_myactivity_time_over);
        }
        if("-1".equals(activityType)){
            holder.setTextColorRes(R.id.tv_time,R.color.text_gray);
            holder.setImageResource(R.id.iv,R.drawable.icon_myactivity_time_over);
        }

        ImageView image = holder.getView(R.id.imageView);
        Glide.with(mContext)
                .load(FileUtil.getFileUrl(item.getActivityPath()))
                .error(R.drawable.default_img)
                .placeholder(R.drawable.default_img)
                .into(image);
    }

    public void setData(List<ActivityInfo> activityList){
        this.mDatas = activityList;
        notifyDataSetChanged();
    }

    public void addData(List<ActivityInfo> activityList){
        this.mDatas.addAll(activityList);
        notifyDataSetChanged();
    }

    //截取字符串
    private String sub(String data){
        String str = "";
        str = data.substring(0,4) + "/" + data.substring(5,7) + "/" + data.substring(8,10);
        return str;
    }

}
