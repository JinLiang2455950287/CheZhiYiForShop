package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.CommonAdapter;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：活动招募
 * <p/>
 * Created by ycw on 2016/9/10.
 */
public class ActivityToRecruitAdapter extends CommonAdapter<ActivityInfo> {

    public ActivityToRecruitAdapter(Context context, int layoutId, List<ActivityInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ActivityInfo activityInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageView imageView = holder.getView(R.id.iv_activities_to_recruit);
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(activityInfo.getActivityPath()), imageView, R.drawable.default_imge_small);
        LogX.d("ycw", "activityListInfo.getActivityPath()---->" + FileUtil.getImageUrl(activityInfo.getActivityPath()));
    }
}
