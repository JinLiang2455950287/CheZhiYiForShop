package com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by Sxq on 2016/8/23.
 */
public class EaseAddFirendListAdapter extends CommonAdapter<User> {

    private String searchStr;

    public EaseAddFirendListAdapter(Context context, int layoutId, List<User> datas) {
        super(context, layoutId, datas);
    }


    @Override
    protected void convert(ViewHolder viewHolder, User user, int position) {
        TextView tvNickName = viewHolder.getView(R.id.tv_nick_name);
        TextView tvPhone = viewHolder.getView(R.id.tv_phone);
        CircleImageView imgPhoto = viewHolder.getView(R.id.img_photo);
        tvNickName.setText(getTextString(searchStr, user.getNickName()));
        tvPhone.setText(getTextString(searchStr, "(" + user.getLoginName() + ")"));
//        Glide.with(mContext).load(user.getUserPhoto()).error(R.drawable.ease_default_avatar).into(imgPhoto);
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(user.getUserPhoto()), imgPhoto, R.drawable.icon_new_friends);
    }

    public void addData(List<User> userList) {
        this.mDatas.addAll(userList);
    }

    public void setData(List<User> data) {
        mDatas = data;
    }

    /**
     * @param searchStr
     * @param srcStr
     * @return
     */
    public SpannableStringBuilder getTextString (String searchStr, String srcStr) {
        SpannableStringBuilder builder = new SpannableStringBuilder(srcStr);
        if (TextUtils.isEmpty(srcStr)) return builder;
        if (TextUtils.isEmpty(searchStr)) return builder;
        int startIndex = srcStr.indexOf(searchStr);
        int endIndex = searchStr.length() + startIndex;
        if (AppUtility.isNotEmpty(searchStr) && startIndex >= 0) {
            builder.setSpan(
                    new ForegroundColorSpan(mContext.getResources().getColor(R.color.theme_color_default)),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }
}
