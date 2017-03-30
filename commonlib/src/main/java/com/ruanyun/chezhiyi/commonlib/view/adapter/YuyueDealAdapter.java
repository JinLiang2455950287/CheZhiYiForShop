package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.commonlib.R;
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
public class YuyueDealAdapter extends CommonAdapter<ProjectType> {

    public YuyueDealAdapter(Context context, int layoutId, List<ProjectType> datas) {
        super(context, layoutId, datas);
    }


    public void setData(List<ProjectType> datas) {
        mDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, final ProjectType item, final int position) {
        AutoUtils.auto(holder.getConvertView());
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl("http://201703/4_58c1f4ec634b70.jpg"),
//                (ImageView) holder.getView(R.id.iv_product_photo), R.drawable.default_img);
        if (item.getProjectAllSelectName() == "null" || TextUtils.isEmpty(item.getProjectAllSelectName())) {
            holder.setText(R.id.tv_project, item.getProjectName());
        } else {
            holder.setText(R.id.tv_project, item.getProjectName() + "(" + item.getProjectAllSelectName() + ")");
        }
        holder.setText(R.id.tv_projectNumber, "预约项目" + (position + 1) + "：");
        final EditText dealwith_btn = holder.getView(R.id.tv_money);
        //editText的回掉
        dealwith_btn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String isPay = dealwith_btn.getText().toString().trim();
                if (TextUtils.isEmpty(isPay)) return;
                if (Integer.parseInt(isPay) > 0) {
                    item.setYuMoney(isPay);
                    onProductIsPayClickListener.onProductIsPayItemClick(1);
                }

//                if (isPay.length() == 1 && !isPay.substring(0, 1).endsWith("0"))

            }
        });
    }

    public interface OnProductIsPayClickListener {
        void onProductIsPayItemClick(int isDownPayment);
    }

    // click callback
    OnProductIsPayClickListener onProductIsPayClickListener;

    public void setIsPayClickListener(OnProductIsPayClickListener onProductIsPayClickListener) {
        this.onProductIsPayClickListener = onProductIsPayClickListener;
    }
}
