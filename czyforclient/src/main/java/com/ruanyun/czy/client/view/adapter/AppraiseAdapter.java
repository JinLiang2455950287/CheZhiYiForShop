package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.view.widget.EmojiFiltrationTextWatcher;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * description 评价的数据adapter
 * Created by ycw 
 * date 2016/5/17
 */
public class AppraiseAdapter extends CommonAdapter<OrderGoodsInfo> {

    public AppraiseAdapter(Context context, List<OrderGoodsInfo> datas, int layoutId) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final OrderGoodsInfo orderGoodInfo, int position) {

        AutoUtils.auto(holder.getConvertView());
        TextView tvGoodsName = holder.getView(R.id.tv_goods_name);
        tvGoodsName.setText(orderGoodInfo.getGoodsName());
        RatingBar ratingBarGoods = holder.getView(R.id.ratingBar_goods);
        final EditText editAppraiseContent = holder.getView(R.id.edit_appraise_content);

        editAppraiseContent.setVisibility(View.GONE);
        editAppraiseContent.addTextChangedListener(new EmojiFiltrationTextWatcher(editAppraiseContent) {
            @Override
            public void emojiFiltAfterTextChanged(Editable s) {
                super.emojiFiltAfterTextChanged(s);
                String goodsContent = s.toString().trim();
                orderGoodInfo.setContent(goodsContent);
            }
        });
        /*editAppraiseContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        ratingBarGoods.setOnRatingBarChangeListener(null);
        ratingBarGoods.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0.0) {
                    editAppraiseContent.setVisibility(View.GONE);
                } else {
                    editAppraiseContent.setVisibility(View.VISIBLE);
                }
                orderGoodInfo.setLevel(rating);
            }
        });

        if (0f != orderGoodInfo.getLevel()) {
            editAppraiseContent.setVisibility(View.VISIBLE);
            ratingBarGoods.setRating( orderGoodInfo.getLevel());
        } else {
            ratingBarGoods.setRating( 0f );
        }
        editAppraiseContent.setText( orderGoodInfo.getContent() );

    }

    public List<OrderGoodsInfo> getData() {
        return mDatas;
    }
}
