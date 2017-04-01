package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.HuiYuanKuaiChaInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 会员快查 Adapter
 * Created by hdl on 2017/3/10
 * jin
 */
public class HuiYuanListAdapter extends CommonAdapter<HuiYuanKuaiChaInfo> {

    public HuiYuanListAdapter(Context context, int layoutId, List<HuiYuanKuaiChaInfo> datas) {
        super(context, layoutId, datas);
    }

    public void setData(List<HuiYuanKuaiChaInfo> datas) {
        mDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, final HuiYuanKuaiChaInfo item, final int position) {
        AutoUtils.auto(holder.getConvertView());
        CircleImageView userPhoto = holder.getView(R.id.iv_product_photo);
        String imageUrl = FileUtil.getImageUrl(FileUtil.getImageUrl(item.getUserPhoto()));
        LogX.e("会员快查", item.toString() + imageUrl);
        ImageUtil.loadImage(mContext, imageUrl.trim(), userPhoto, R.drawable.default_img);

        holder.setText(R.id.tv_number, item.getLinkTel());
        holder.setText(R.id.tv_name, item.getNickName());
        holder.setText(R.id.tv_detail, item.getCarAllName());
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
        void onProductBuyItemClick(HuiYuanKuaiChaInfo huiYuanKuaiChaInfo);
    }

    // click callback
    OnProductBuyClickListener onProductBuyClickListener;

    public void setOnPopupClickListener(OnProductBuyClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }
}
