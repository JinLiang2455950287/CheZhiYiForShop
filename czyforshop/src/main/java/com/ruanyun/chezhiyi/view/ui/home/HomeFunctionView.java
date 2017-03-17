package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：技师端  首页  分页
 * <p/>
 * Created by ycw on 2016/10/8.
 */
public class HomeFunctionView extends GridLayout {

    private Context mContext;
    private ItemViewClickListener listener;
    private List<HomeIconInfo> datas;

    public HomeFunctionView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        setRowCount(1);
        setColumnCount(4);
    }

    /**
     * 设置点击事件的监听
     * @param listener
     */
    public void setListener(ItemViewClickListener listener) {
        this.listener = listener;
    }

    public void setDatas(List<HomeIconInfo> datas) {
        this.datas = datas;
        addItemView();
    }

    private void addItemView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AppUtility.getScreenPix
                (mContext).widthPixels / getColumnCount(),
                ViewGroup.LayoutParams.MATCH_PARENT);
        for (final HomeIconInfo info : this.datas) {
            FrameLayout itemRoot = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout
                    .item_shop_my_rv_service_state, null, false);
            ImageView ivHomeIcon = (ImageView) itemRoot.findViewById(R.id.iv_my_state_photo);
            TextView tvHomeName = (TextView) itemRoot.findViewById(R.id.tv_my_state_name);
            ImageUtil.loadImage(mContext, FileUtil.getImageUrl(info.getAndroidPic()), ivHomeIcon);
            tvHomeName.setText(info.getHomeIconName());
            itemRoot.setTag(info.getModuleNum());
            itemRoot.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.itemClick(info);
                }
            });
            AutoUtils.auto(itemRoot);
            addView(itemRoot, params);
        }
    }

    public interface ItemViewClickListener {
        /**
         * 技师端的分页按钮  点击事件
         *
         * @param info
         */
        void itemClick(HomeIconInfo info);
    }
}
