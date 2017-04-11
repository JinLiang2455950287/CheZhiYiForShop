package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.FlowLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msq on 2016/9/26.
 * 当天预约adapter
 */
public class DayAppointmentCommentAdapter extends CommonAdapter<AppointmentInfo> {
    public static final String STATUS_RECEPTED = "recepted";
    public static final String STATUS_UNFINISH = "unfinish";
    String status;
    List<AppointmentInfo> data;
    List<ProjectType> serverList = new ArrayList<>();

    public DayAppointmentCommentAdapter(Context context, int layoutId, List<AppointmentInfo> datas, String statu) {
        super(context, layoutId, datas);
        this.data = datas;
        this.status = statu;
        initLayoutParams();
    }

    @Override
    protected void convert(ViewHolder viewHolder, AppointmentInfo item, int position) {
        AutoUtils.auto(viewHolder.getConvertView());
        //未到店
        if (STATUS_UNFINISH.equals(status)) {
            //预计到店字体为灰色
            viewHolder.setText(R.id.tv_time, mContext.getString(R.string.arrive_time, StringUtil.getTimeFromDate(item.getPredictShopTime())));
        } else if (STATUS_RECEPTED.equals(status)) { //已接待
            //到店字体为灰色
            viewHolder.setText(R.id.tv_time, StringUtil.getTimeFromDate(item.getPredictShopTime() + "到店"));
        }

        ImageView im_photo = viewHolder.getView(R.id.head_picCir);
        ImageUtil.loadImage(mContext, FileUtil.getFileUrl(item.getUser().getUserPhoto()), im_photo, R.drawable.default_imge_middle);
        LogX.e("已到店", FileUtil.getFileUrl(item.getUser().getUserPhoto()));

        //用户UserNum
        viewHolder.setText(R.id.tv_user_name, item.getUser() == null ? "" : item.getUser().getNickName());

        //服务标签流
        FlowLayout labelFlowLayout = viewHolder.getView(R.id.labelflow_appointment_item);
        getLableFromeProjectJson(item.getProjectNum(), labelFlowLayout);

    }

    ViewGroup.MarginLayoutParams lp;

    @SuppressWarnings("ResourceType")
    private void initLayoutParams() {
        lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 1;
        lp.bottomMargin = 1;
    }

    //解析服务项目json
    public void getLableFromeProjectJson(String jsonString, FlowLayout lableFlowLayout) {
        serverList.clear();
        List<ProjectType> projectTypes = new Gson().fromJson(jsonString, new TypeToken<List<ProjectType>>() {
        }.getType());
        lableFlowLayout.removeAllViews();
        for (int i = 0, size = projectTypes.size(); i < size; i++) {
            TextView view = new TextView(mContext);
            view.setText(projectTypes.get(i).getProjectName());
            view.setTextColor(Color.rgb(255, 151, 114));
            view.setTextSize(12);
            view.setBackgroundResource(R.drawable.shape_text_station_label_new);
            lableFlowLayout.addView(view, lp);
        }
    }


    public void setData(List<AppointmentInfo> appointmentInfo) {
        this.mDatas = appointmentInfo;
        notifyDataSetChanged();
    }

    public void addData(List<AppointmentInfo> appointmentInfo) {
        this.mDatas.addAll(appointmentInfo);
        notifyDataSetChanged();
    }

}
