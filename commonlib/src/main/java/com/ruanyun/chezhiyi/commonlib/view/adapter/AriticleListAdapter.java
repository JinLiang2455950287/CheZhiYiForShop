package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.ArticleInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description:
 * author: zhangsan on 16/9/8 下午8:39.
 */
public class AriticleListAdapter extends CommonAdapter<ArticleInfo> {

    public AriticleListAdapter(Context context, int layoutId, List<ArticleInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ArticleInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());
        TextView tvSummary=holder.getView(R.id.tv_summary);
        tvSummary.setText(item.getSummary());
        tvSummary.getBackground().setAlpha(80);
        //holder.setText(R.id.tv_summary,);
        holder.setText(R.id.tv_title,item.getTitle());
        holder.setText(R.id.tv_create_time, StringUtil.getArtileTime(item.getCreateTime()));
        holder.setText(R.id.tv_article_type,item.getArticleTypeName());
        ImageView image=holder.getView(R.id.img_pic);
        Glide.with(mContext)
                .load(FileUtil.getFileUrl(item.getMainPhoto()))
                .error(R.drawable.default_img)
                .placeholder(R.drawable.default_img)
                .into(image);
        holder.setText(R.id.tv_thumbNum,String.valueOf(item.getPraiseCount()));
    }


    public void refresh(List<ArticleInfo> articleInfoList){
        for(ArticleInfo articleInfo:articleInfoList) {
            articleInfo.setArticleTypeName(App.getInstance().beanCacheHelper.getArticleTypeName(articleInfo.getArticleType()));
        }
        this.mDatas=articleInfoList;
        notifyDataSetChanged();
    }

    public void loadMore(List<ArticleInfo> articleInfoList){
        for(ArticleInfo articleInfo:articleInfoList) {
            articleInfo.setArticleTypeName(App.getInstance().beanCacheHelper.getArticleTypeName(articleInfo.getArticleType()));
        }
        this.mDatas.addAll(articleInfoList);
        notifyDataSetChanged();
    }
}
