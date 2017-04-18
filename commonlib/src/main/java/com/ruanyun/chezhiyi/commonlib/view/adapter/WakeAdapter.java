package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.YuYueItemBean;
import com.ruanyun.chezhiyi.commonlib.util.CommentUtils;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;


/**
 * 提醒 Adapter
 * 2017/3/9
 * jin
 */
public class WakeAdapter extends CommonAdapter<String> {


    public WakeAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }


    public void setData(List<String> datas) {
        mDatas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, final String item, final int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl("http://201703/4_58c1f4ec634b70.jpg"),
                (CircleImageView) holder.getView(R.id.head_picCir), R.drawable.default_img);

        TextView tvcartype = holder.getView(R.id.tv_cartype);
        TextView tvname = holder.getView(R.id.tv_name);
        TextView tvcarNumber = holder.getView(R.id.tv_carNumber);
        TextView tvdetail = holder.getView(R.id.tv_detail);
        TextView tvdetail2 = holder.getView(R.id.tv_detail2);
        TextView tvtuisong = holder.getView(R.id.tv_tuisong);
//        StringBuffer prjectBuffer = new StringBuffer();
//        List<ProjectType> projectTypes = new Gson().fromJson(item.getProjectNum(), new TypeToken<List<ProjectType>>() {
//        }.getType());
//        for (int i = 0, size = projectTypes.size(); i < size; i++) {
//            prjectBuffer.append(projectTypes.get(i).getProjectName());
//            if (i != projectTypes.size()-1) {
//                prjectBuffer.append(",");
//            }
//        }
        tvname.setText("张三");
        tvcartype.setText("丰田");

        if (CommentUtils.wakeType.equals("wait_project")) {//项目保养提醒
            tvcarNumber.setText("皖 K99999");
            tvdetail.setText("上次保养时间：2016-06-02");
            tvdetail2.setText("最新历程：15000km");
        } else if (CommentUtils.wakeType.equals("wait_huiyuan")) {//会员流失提醒
            tvcarNumber.setText("皖 K99999");
            tvdetail.setText("上次到店时间：2016-06-02");
            tvdetail2.setText("流失时间：20天");
        } else if (CommentUtils.wakeType.equals("wait_birthday")) {//会员生日提醒
            tvcarNumber.setText("皖 K99999");
            tvdetail.setText("出生日期：2016-06-02");
            tvdetail2.setText("出生时间：8天后");
        } else {//余额不足提醒
            tvcarNumber.setText("皖 K99999");
            tvdetail.setText("账号余额：¥20.00");
            tvdetail2.setVisibility(View.GONE);
        }


        //推送
//        tvtuisong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onProductBuyClickListener.onProductBuyItemClick(item);
//            }
//        });
    }

    public interface OnProductBuyClickListener {
        void onProductBuyItemClick(YuYueItemBean yuYueItemBean);
    }

    // click callback
    OnProductBuyClickListener onProductBuyClickListener;

    public void setOnPopupClickListener(OnProductBuyClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }
}
