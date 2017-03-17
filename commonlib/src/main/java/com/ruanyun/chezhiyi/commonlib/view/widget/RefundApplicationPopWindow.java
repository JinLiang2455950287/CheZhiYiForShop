package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.ParentCodeInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;

import java.util.List;

import butterknife.ButterKnife;


/**
 * 申请退款 弹出框
 * Created by Sxq on 2016/9/18.
 */
public abstract class RefundApplicationPopWindow extends PopupWindow implements PopupWindow.OnDismissListener {
    private Context mContext;
    private MyAdapter adapter;
    private int last_item = -1;
    List<ParentCodeInfo> infoList;

    public RefundApplicationPopWindow(Context context) {
        super(context);
        this.mContext = context;
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
        //读取字典表
        infoList = App.getInstance().beanCacheHelper.getLocalParentCodes(C.ParentCode.REFUND_APPLICATION_REASON);
        adapter = new MyAdapter(infoList);
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
                String reasons = infoList.get(position).getItemName();
                last_item = position;
                getReasons(reasons);
                dismiss();
            }
        });
        setContentView(view);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setOnDismissListener(this);
    }

    protected abstract void getReasons(String reasons);


    private class MyAdapter extends BaseAdapter {
        private List<ParentCodeInfo> list;

        public MyAdapter(List<ParentCodeInfo> infoList) {
            this.list = infoList;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_refund_application, null);
                viewHolder.tvReasons = (TextView) convertView.findViewById(R.id.tv_reasons);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (last_item == position) {
                viewHolder.tvReasons.setSelected(true);
            }
            viewHolder.tvReasons.setText(list.get(position).getItemName());
            return convertView;
        }

    }

    class ViewHolder {
        TextView tvReasons;
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

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }
}

