package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.*;
import com.ruanyun.chezhiyi.commonlib.util.*;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Description ：添加服务商品列表
 * <p/>
 * Created by hdl on 2016/9/29.
 */

public class AddServiceGoodsAdapter extends CommonAdapter<ProductInfo> {

    public AddServiceGoodsAdapter(Context context, int layoutId, List<ProductInfo> datas) {
        super(context, layoutId, datas);
    }

    public void setData(List<ProductInfo> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }
    @Override
    protected void convert(ViewHolder holder, final ProductInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(item.getMainPhoto()), (ImageView) holder.getView(R.id.iv_photo), R.drawable.default_img);
        TextView tvType = holder.getView(R.id.tv_type);
        setTypeAddTime(tvType, item.getGoodsType());//goods类型
        holder.setText(R.id.tv_goods_name, item.getGoodsName());
        TextView tvMoney = holder.getView(R.id.tv_money);
        SpannableString spStr = new SpannableString(new StringBuilder().append("¥").append(item.getSalePrice()));
        spStr.setSpan(new RelativeSizeSpan(0.7f), 0 ,1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvMoney.setText(spStr);
        if(item.getGoodsCount()<0) item.setGoodsCount(0);//数量小于0，置为0
        holder.setText(R.id.edit_number, item.getGoodsCount()+"");
        ImageButton imbtnSub = holder.getView(R.id.imbtn_sub);
        ImageButton imbtnAdd = holder.getView(R.id.imbtn_add);
        if(item.getGoodsCount()==0){//数量为0时
            imbtnSub.setEnabled(false);
            imbtnSub.setImageResource(R.drawable.order_minus_disabled);
        }else {
            imbtnSub.setEnabled(true);
            imbtnSub.setImageResource(R.drawable.order_minus_normal);
        }
        imbtnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setGoodsCount(item.getGoodsCount()-1);
                EventBus.getDefault().post(new Event(C.EventKey.COUNT_PRODUCTINFO, item));
            }
        });
        imbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setGoodsCount(item.getGoodsCount()+1);
                EventBus.getDefault().post(new Event(C.EventKey.COUNT_PRODUCTINFO, item));
            }
        });
    }

    /**
     * 类型背景色
     * @param tvType
     * @param goodsType
     */
    private void setTypeAddTime(TextView tvType, String goodsType) {
        switch (goodsType) {
            case ProductInfo.GOODS_TYPE_TG_01://团购
            case ProductInfo.GOODS_TYPE_TG_02://团购
                tvType.setText("团购");
                tvType.setBackgroundResource(com.ruanyun.chezhiyi.commonlib.R.drawable.shape_tg_bg);
                break;
            case ProductInfo.GOODS_TYPE_CP_01://产品
                tvType.setText("产品");
                tvType.setBackgroundResource(com.ruanyun.chezhiyi.commonlib.R.drawable.shape_cp_bg);
                break;
        }
    }

    /**
     * 搜索符合条件的数据集合
     * @param contactList
     * @param s
     * @return
     */
    public List<ProductInfo> getSearchResult(List<ProductInfo> contactList, String s){
        List<ProductInfo> searchResult=new ArrayList<>();
        for(ProductInfo productInfo:contactList){
            if(productInfo.getGoodsName().contains(s)){
                searchResult.add(productInfo);
            }
        }
        return  searchResult;
    }
}
