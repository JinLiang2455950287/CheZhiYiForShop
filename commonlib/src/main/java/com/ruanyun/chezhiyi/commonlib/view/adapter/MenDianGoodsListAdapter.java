package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.MenDianServiceGoodsInfo;
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
public class MenDianGoodsListAdapter extends CommonAdapter<MenDianServiceGoodsInfo.ResultBean> {

    public MenDianGoodsListAdapter(Context context, int layoutId, List<MenDianServiceGoodsInfo.ResultBean> datas) {
        super(context, layoutId, datas);
    }


    public void setData(List<MenDianServiceGoodsInfo.ResultBean> datas) {
        mDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, final MenDianServiceGoodsInfo.ResultBean item, final int position) {
        AutoUtils.auto(holder.getConvertView());
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl("http://201703/4_58c1f4ec634b70.jpg"),
//                (ImageView) holder.getView(R.id.iv_product_photo), R.drawable.default_img);

//        List<ProjectType> projectTypes = new Gson().fromJson(item.getProjectNum(), new TypeToken<List<ProjectType>>() {
//        }.getType());
        holder.setText(R.id.tv_name, item.getGoodsName());
//        holder.setText(R.id.tv_salecount,"销量："+ item.getSaleCount());
        holder.setText(R.id.tv_price, "售价：¥"+ item.getSaleAmount());

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
        void onProductBuyItemClick(MenDianServiceGoodsInfo.ResultBean yuYueItemBean);
    }

    // click callback
    OnProductBuyClickListener onProductBuyClickListener;

    public void setOnPopupClickListener(OnProductBuyClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }
}
