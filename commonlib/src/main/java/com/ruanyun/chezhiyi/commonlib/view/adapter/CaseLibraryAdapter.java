package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.AttachInfo;
import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 案例库
 * Created by ycw on 2016/9/9.
 */
public class CaseLibraryAdapter extends MultiItemTypeAdapter<CaseInfo> {


    private boolean showCheck = false;

    public CaseLibraryAdapter(Context context, List<CaseInfo> datas) {
        super(context, datas);
        addItemViewDelegate(new NoPicCaseLibDelegate());
        addItemViewDelegate(new OnePicLibraryDelegate());
        addItemViewDelegate(new TwoPicLibraryDelegate());
        addItemViewDelegate(new ThreePicLibraryDelegate());
    }

    public void setData(List<CaseInfo> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void addData(List<CaseInfo> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearData() {
        mDatas.clear();
        notifyDataSetChanged();
    }



    /**
     * 是否显示审核状态
     * @param showCheck
     */
    public void showCheckStatus(boolean showCheck) {
        this.showCheck = showCheck;
    }

    class NoPicCaseLibDelegate implements ItemViewDelegate<CaseInfo> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_case_end;
        }

        @Override
        public boolean isForViewType(CaseInfo item, int position) {
            return item.getAttachInfoList().isEmpty();
        }

        @Override
        public void convert(ViewHolder holder, CaseInfo caseInfo, int position) {
            AutoUtils.auto(holder.getConvertView());
            TextView tvCaseContext = holder.getView(R.id.tv_case_context);
            ImageView ivBigOnlyOne = holder.getView(R.id.iv_big_only_one);
            TextView tvTime = holder.getView(R.id.tv_time);
            TextView tvPraise = holder.getView(R.id.tv_praise);
            TextView tvCheckStatus = holder.getView(R.id.tv_check_status);
            setCheckStatus(showCheck, caseInfo.getStatus(), tvCheckStatus);
            ivBigOnlyOne.setVisibility(View.GONE);
            // : 2016/9/9 创建时间
            tvTime.setText(StringUtil.getCaseInfoTime(caseInfo.getCreateTime()));
            tvPraise.setText(String.valueOf(caseInfo.getPraiseCount()));
            tvCaseContext.setText(caseInfo.getLibraryName());
        }
    }

    /**
     * 一张图片
     */
    class OnePicLibraryDelegate implements ItemViewDelegate<CaseInfo> {
        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_case_end;
        }
        @Override
        public boolean isForViewType(CaseInfo item, int position) {
            List<AttachInfo> attachInfoList = item.getAttachInfoList();
            if (!attachInfoList.isEmpty()&&attachInfoList.size() < 2) {
                return true;
            } else {
                return false;
            }
        }
        @Override
        public void convert(ViewHolder holder, CaseInfo caseInfo, int position) {

            TextView tvCaseContext = holder.getView(R.id.tv_case_context);
            ImageView ivBigOnlyOne = holder.getView(R.id.iv_big_only_one);
            TextView tvTime = holder.getView(R.id.tv_time);
            TextView tvPraise = holder.getView(R.id.tv_praise);
            TextView tvCheckStatus = holder.getView(R.id.tv_check_status);
            setCheckStatus(showCheck, caseInfo.getStatus(), tvCheckStatus);
            loadImage(caseInfo.getAttachInfoList().get(0).getFilePath(), ivBigOnlyOne);
            // : 2016/9/9 创建时间
            tvTime.setText(StringUtil.getCaseInfoTime(caseInfo.getCreateTime()));
            tvPraise.setText(String.valueOf(caseInfo.getPraiseCount()));
            tvCaseContext.setText(caseInfo.getLibraryName());
        }
    }

    /**
     * 两张图片  带视频的
     */
    class TwoPicLibraryDelegate implements ItemViewDelegate<CaseInfo> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_case_header;
        }

        @Override
        public boolean isForViewType(CaseInfo item, int position) {
          return  item.getAttachInfoList().size()==2;
        }

        @Override
        public void convert(ViewHolder holder, CaseInfo caseInfo, int position) {

            TextView tvCaseContext = holder.getView(R.id.tv_case_context);
            ImageView ivLeftOnlyOne = holder.getView(R.id.iv_left_only_one);
            TextView tvTime = holder.getView(R.id.tv_time);
            TextView tvPraise = holder.getView(R.id.tv_praise);
            loadImage(caseInfo.getAttachInfoList().get(0).getFilePath(), ivLeftOnlyOne);
            TextView tvCheckStatus = holder.getView(R.id.tv_check_status);
            setCheckStatus(showCheck, caseInfo.getStatus(), tvCheckStatus);
            // : 2016/9/9 创建时间
            tvTime.setText(StringUtil.getCaseInfoTime(caseInfo.getCreateTime()));
            tvPraise.setText(String.valueOf(caseInfo.getPraiseCount()));
            tvCaseContext.setText(caseInfo.getLibraryName());
        }
    }

    /**
     * 三张图片
     */
    class ThreePicLibraryDelegate implements ItemViewDelegate<CaseInfo> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_case_mid;
        }

        @Override
        public boolean isForViewType(CaseInfo item, int position) {
            List<AttachInfo> attachInfoList = item.getAttachInfoList();
            if (item.getAttachInfoList() != null && attachInfoList.size() > 2) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void convert(ViewHolder holder, CaseInfo caseInfo, int position) {
            TextView tvCaseContext = holder.getView(R.id.tv_case_context);
            ImageView ivCaseLeft = holder.getView(R.id.iv_case_left);
            ImageView ivCaseCenter = holder.getView(R.id.iv_case_center);
            ImageView ivCaseRight = holder.getView(R.id.iv_case_right);
            TextView tvTime = holder.getView(R.id.tv_time);
            TextView tvPraise = holder.getView(R.id.tv_praise);
            TextView tvCheckStatus = holder.getView(R.id.tv_check_status);
            setCheckStatus(showCheck, caseInfo.getStatus(), tvCheckStatus);
            int i = 0;
            for (AttachInfo info : caseInfo.getAttachInfoList()) {
                if (i <= 2) {
                    switch (i) {
                        case 0:
                            loadImage(info.getFilePath(), ivCaseLeft);
                            break;
                        case 1:
                            loadImage(info.getFilePath(), ivCaseCenter);
                            break;
                        case 2:
                            loadImage(info.getFilePath(), ivCaseRight);
                            break;
                    }
                    i++;
                }
            }
            // : 2016/9/9 创建时间
            tvTime.setText(StringUtil.getCaseInfoTime(caseInfo.getCreateTime()));
            tvPraise.setText(String.valueOf(caseInfo.getPraiseCount()));
            tvCaseContext.setText(caseInfo.getLibraryName());
        }
    }

    private void loadImage(String path, ImageView imageView) {
        Glide.with(mContext)
                .load(FileUtil.getFileUrl(path))
                .placeholder(R.drawable.default_img)
                .into(imageView);
    }


    private void setCheckStatus(boolean showCheck, int status, TextView tvCheckStatus) {
        String str = "";
        if (status == 1) {
            str = "通过";
        } else if (status == 2) {
            str = "待审核";
        } else {
            str = "未通过";
        }
        if (showCheck) {
            tvCheckStatus.setVisibility(View.VISIBLE);
            tvCheckStatus.setText(str);
        } else {
            tvCheckStatus.setVisibility(View.GONE);
        }
    }


}
