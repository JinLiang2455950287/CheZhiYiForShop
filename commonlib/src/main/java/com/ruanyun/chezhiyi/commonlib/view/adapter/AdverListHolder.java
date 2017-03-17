package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.ruanyun.chezhiyi.commonlib.model.AdverListInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;

/**
 * 滚动广告
 */
public class AdverListHolder
        implements Holder<AdverListInfo> {
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
    public void UpdateUI(Context context, int position, AdverListInfo data) {
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(data.getMainPhoto()), imageView);
        LogX.d("ycw", "data.getFilePath() --------> " + FileUtil.getImageUrl(data.getMainPhoto()));

    }
}