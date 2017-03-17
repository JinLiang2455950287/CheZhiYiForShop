package com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;


import java.util.ArrayList;
import java.util.List;

/**
 * Description ：我的群组
 * <p/>
 * Created by ycw on 2016/8/10.
 */
public class EaseGroupAdapter extends CommonAdapter<HxUserGroup> implements Filterable {


    public EaseGroupAdapter(Context context, int layoutId, List<HxUserGroup> datas) {
        super(context, layoutId, datas);

    }

    public void setData(List<HxUserGroup> data){
        mDatas = data;
    }


    @Override
    protected void convert(ViewHolder viewHolder, HxUserGroup hxUserGroup, int position) {
        viewHolder.setText(R.id.tv_group_name, hxUserGroup.getGroupName());
        final CircleImageView imageView=viewHolder.getView(R.id.iv_group_head);
        Glide.with(mContext)
                .load(FileUtil.getFileUrl(hxUserGroup.getGroupPath()))
                .error(R.drawable.icon_my_groups)
                .into(imageView);
    }

    public List<HxUserGroup> getSearchResult(String s){
        List<HxUserGroup> searchResult=new ArrayList<>();
        for(HxUserGroup group:mDatas){
            if(group.getGroupName().contains(s)){
                searchResult.add(group);
            }
        }
        return searchResult;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
    class GroupFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
          List<HxUserGroup> result= (List<HxUserGroup>) results.values;
            if(result!=null&&result.size()>0){
                mDatas.clear();
                mDatas.addAll(result);
                notifyDataSetChanged();
            }else {
                notifyDataSetInvalidated();
            }
        }
    }
}
