package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.hyphenate.util.ImageUtils;
import com.ruanyun.chezhiyi.commonlib.data.api.ApiManager;
import com.ruanyun.chezhiyi.commonlib.model.AttachInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * @author hdl
 * 记账本明细 图片adapter
 */
public class ParticularsPhotoAdapter extends CommonAdapter<AttachInfo> {

    public ParticularsPhotoAdapter(Context context, int layoutId, List<AttachInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, AttachInfo attachInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageUtil.loadImage(mContext,FileUtil.getImageUrl(attachInfo.getFilePath()),(ImageView) holder.getView(R.id.iv_particulars_photo),R.drawable.default_img);
    }
}
