package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.model.CarModel;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msq on 2016/9/21.
 */
public class BrandChoicesAdapter extends CommonAdapter<CarModel> implements SectionIndexer {

    List<String> list = new ArrayList<>();
    SparseIntArray positionOfSection = new SparseIntArray();
    SparseIntArray sectionOfPosition = new SparseIntArray();

    public BrandChoicesAdapter(Context context, int layoutId, List<CarModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CarModel item, int position) {

        AutoUtils.auto(holder.getConvertView());

        LinearLayout linearLayout_char = holder.getView(R.id.linearLayout_char);

        //判断当前CarModeName首字母与上一个CarModeName首字母是否相同
        if(position > 0 && StringUtil.getUserLetter(getItem(position-1).getCarModelName()).equals(item.getCarModelName())){
            linearLayout_char.setVisibility(View.GONE);
        }else{
            linearLayout_char.setVisibility(View.VISIBLE);
        }

        holder.setText(R.id.tv_char, item.getPinyin());
        holder.setText(R.id.tv_brand, item.getCarModelName());

        ImageView image = holder.getView(R.id.iv_brand);
//        Glide.with(mContext)
//                .load(FileUtil.getFileUrl(item.Path()))
//                .error(R.drawable.default_img)
//                .placeholder(R.drawable.default_img)
//                .into(image);
    }

    @Override
    public Object[] getSections() {
        positionOfSection.clear();
        sectionOfPosition.clear();
        int count = getCount();
        list.clear();
        list.add("Search");
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        for (int i = 1; i < count; i++) {
            String letter = StringUtil.getUserLetter(getItem(i).getCarModelName());
            int section = list.size() - 1;
            if (list.get(section) != null && !list.get(section).equals(letter)) {
                list.add(letter);
                section++;
                positionOfSection.put(section, i);
            }
            sectionOfPosition.put(i, section);
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public int getPositionForSection(int i) {
        return positionOfSection.get(i);
    }

    @Override
    public int getSectionForPosition(int i) {
        return sectionOfPosition.get(i);
    }
}
