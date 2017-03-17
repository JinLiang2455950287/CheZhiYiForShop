package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;

/**
 * HomeFragment滚动广告
 * 加载网络图片
 */
public class HomeAdverListHolder
        implements Holder<String> {
    private ImageView imageView;
    private Context mContext;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.mContext = context;
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        ImageUtil.loadImage(mContext, data, imageView);
    }
}