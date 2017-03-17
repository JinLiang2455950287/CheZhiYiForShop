package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.ParentCodeInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;


import java.util.List;

import butterknife.ButterKnife;

/**
 * Description:
 * author: zhangsan on 16/9/27 下午2:49.
 */
public class ParentCodeChoosePopWindow extends PopupWindow implements PopupWindow.OnDismissListener {
    private Context mContext;
    private ParentCodeAdapter adapter;
    private String title;
    private String parentCode;

    public ParentCodeChoosePopWindow(Context context, String title, String parentCode) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.parentCode = parentCode;
        initView();
    }

    private void initView() {
        setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color
                .transparent)));
        setOutsideTouchable(false);
        setFocusable(true);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.popupwindow_anim_style);
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_refund_application, null);
        TextView tvCancel = ButterKnife.findById(view, R.id.tv_cancel);
        ListView listView = ButterKnife.findById(view, R.id.list);
        TextView tvTitle = ButterKnife.findById(view, R.id.tv_title);
        tvTitle.setText(title);
        //读取字典表
        List<ParentCodeInfo> list = App.getInstance().beanCacheHelper.getLocalParentCodes(parentCode);
        adapter = new ParentCodeAdapter(mContext, R.layout.list_item_choose_parentcode, list);
        listView.setAdapter(adapter);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parentCodeResult != null) {
                    parentCodeResult.onParentCodeResult(adapter.getItem(position));
                }
                adapter.clickPositon=position;
                adapter.notifyDataSetChanged();
                dismiss();
            }
        });
        setContentView(view);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setOnDismissListener(this);
    }

    public void showBottom(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.6f);
        //   5.1  有效
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public void setParentCodeResult(onParentCodeResult parentCodeResult) {
        this.parentCodeResult = parentCodeResult;
    }

    private onParentCodeResult parentCodeResult;

    public interface onParentCodeResult {
        void onParentCodeResult(ParentCodeInfo parentCodeInfo);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }

    class ParentCodeAdapter extends CommonAdapter<ParentCodeInfo> {
        public int clickPositon = -1;

        public ParentCodeAdapter(Context context, int layoutId, List<ParentCodeInfo> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ParentCodeInfo parentCodeInfo, int position) {
            TextView tvContent = holder.getView(R.id.tv_content);
            tvContent.setText(parentCodeInfo.getItemName());
            if (clickPositon == position) {
                parentCodeInfo.setSelected(true);
            } else {
                parentCodeInfo.setSelected(false);
            }
            tvContent.setSelected(parentCodeInfo.getSelected());

        }
    }
}
