package com.ruanyun.chezhiyi.commonlib.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;

import java.util.List;

/**
 * Created by czy on 2017/4/25.
 * 员工/工位选择
 */

public class SingleChoiceGongWeiAdapter extends RecyclerView.Adapter<SingleChoiceGongWeiAdapter.InternalViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<WorkBayInfo> data;
    /**
     * 默认为-1，没有选择任何item
     */
    private int currentCheckedItemPosition;
    OnItemClickListener onItemClickListener;

    public SingleChoiceGongWeiAdapter(Context context, List<WorkBayInfo> data) {
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
        currentCheckedItemPosition = -1;
    }

    public WorkBayInfo getItem(int position) {
        return data.get(position);
    }

    @Override
    public InternalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InternalViewHolder(layoutInflater.inflate(R.layout.adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(InternalViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position, getItemId(position));
                }
            }
        });

        if (currentCheckedItemPosition == position) {
//            holder.radioButton.setChecked(true);
            holder.radioButton.setBackgroundResource(R.drawable.checkbox_pressed);
        } else {
//            holder.radioButton.setChecked(false);
            holder.radioButton.setBackgroundResource(R.drawable.checkbox_normal);
        }
        holder.textView.setText(getItem(position).getWorkbayName() + "");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setDefaultCheckedItemPosition(int position) {
        currentCheckedItemPosition = position;
    }

    public int getCheckedItemPosition() {
        return currentCheckedItemPosition;
    }

    public void check(int position) {
        setDefaultCheckedItemPosition(position);
        notifyDataSetChanged();
    }

    public void setData(List<WorkBayInfo> newData) {
        data = newData;
    }


    public class InternalViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView radioButton;

        public InternalViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_title);
            radioButton = (ImageView) itemView.findViewById(R.id.radioButton);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, long id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}