package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.CommonAdapter;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * @author hdl
 * @ClassName: ${file_name}
 * @Description:
 * @date ${date}${time}
 */
public class RememberingRvAdapter extends CommonAdapter<ProjectType> {
    /**
     * 选中的位置
     */
    int selectPosition;

    public RememberingRvAdapter(Context context, int layoutId, List datas,int type) {
        super(context, layoutId, datas);
        this.selectPosition = type;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    @Override
    protected void convert(ViewHolder holder, ProjectType projectType, int position) {
        AutoUtils.auto(holder.getConvertView());
        TextView mtextView = holder.getView(R.id.tv_expenditure_classify);
        mtextView.setText(projectType.getProjectName());
        if(selectPosition == position){
            mtextView.setBackgroundResource(R.color.theme_color_default);
            mtextView.setTextColor(Color.rgb(255,255,255));
        }else {
            mtextView.setBackgroundResource(R.color.white);
            mtextView.setTextColor(Color.rgb(51,51,51));
        }
    }
}
