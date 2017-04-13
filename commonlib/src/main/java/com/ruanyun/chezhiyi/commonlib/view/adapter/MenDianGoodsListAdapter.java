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
public class MenDianGoodsListAdapter extends CommonAdapter<String> {

    public MenDianGoodsListAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }


    public void setData(List<String> datas) {
        mDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, final String item, final int position) {
        AutoUtils.auto(holder.getConvertView());
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl("http://201703/4_58c1f4ec634b70.jpg"),
//                (ImageView) holder.getView(R.id.iv_product_photo), R.drawable.default_img);

//        holder.setText(R.id.tv_number, item.getMakeNum());
//        StringBuffer prjectBuffer = new StringBuffer();
//        List<ProjectType> projectTypes = new Gson().fromJson(item.getProjectNum(), new TypeToken<List<ProjectType>>() {
//        }.getType());
//        for (int i = 0, size = projectTypes.size(); i < size; i++) {
//            prjectBuffer.append(projectTypes.get(i).getProjectName());
//            if (i != projectTypes.size()-1) {
//                prjectBuffer.append(",");
//            }
//        }
//        holder.setText(R.id.tv_project, prjectBuffer.toString());
//        holder.setText(R.id.tv_time, item.getPredictShopTime());
//        holder.setText(R.id.tv_detail, item.getRemark());
//        TextView dealwith_btn = holder.getView(R.id.dealwith_btn);


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
        void onProductBuyItemClick(YuYueItemBean yuYueItemBean);
    }

    // click callback
    OnProductBuyClickListener onProductBuyClickListener;

    public void setOnPopupClickListener(OnProductBuyClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }
}
