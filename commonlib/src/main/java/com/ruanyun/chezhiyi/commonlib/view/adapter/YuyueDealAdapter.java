package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.YuYueItemBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 预约处理 Adapter
 * Created by hdl on 2017/3/9
 * jin
 */
public class YuyueDealAdapter extends CommonAdapter<ProjectType> {

    public YuyueDealAdapter(Context context, int layoutId, List<ProjectType> datas) {
        super(context, layoutId, datas);
    }


    public void setData(List<ProjectType> datas) {
        mDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, final ProjectType item, final int position) {
        AutoUtils.auto(holder.getConvertView());
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl("http://201703/4_58c1f4ec634b70.jpg"),
//                (ImageView) holder.getView(R.id.iv_product_photo), R.drawable.default_img);

        holder.setText(R.id.tv_project, item.getProjectName() + "(" + item.getProjectAllSelectName() + ")");
        holder.setText(R.id.tv_projectNumber, "预约项目" + (position + 1) + "：");

        //处理按钮的回掉
//        dealwith_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onProductBuyClickListener.onProductBuyItemClick(item);
//            }
//        });
    }

    public interface OnProductBuyClickListener {
        void onProductBuyItemClick(ProjectType projectType);
    }

    // click callback
    OnProductBuyClickListener onProductBuyClickListener;

    public void setOnPopupClickListener(OnProductBuyClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }
}
