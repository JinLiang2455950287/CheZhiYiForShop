package com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.widget.TextView;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.ChatMessage;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Description:
 * author: zhangsan on 16/8/20 下午3:48.
 */
public class SearchMessageAdapter extends CommonAdapter<ChatMessage> {
    private String searchStr;
    static final String PATTERN="\\w%s\\w";
    public SearchMessageAdapter(Context context, int layoutId, List<ChatMessage> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ChatMessage chatMessage, int position) {
        CircleImageView avatar = holder.getView(R.id.img_avatar);
        TextView tvSearch = holder.getView(R.id.tv_chat_text);
       // ImageUtil.loadImage(mContext, chatMessage.getUserAvatar(), avatar);
        holder.setText(R.id.tv_user_nick, chatMessage.getUserNick());
        tvSearch.setText(getStringStyle(chatMessage.getHuanxinSendMsg(), searchStr));
    }

    /**
      * 查找搜索字符串在文本里出现的索引
      *@author zhangsan
      *@date   16/8/23 上午10:48
      */
    private List<Integer> getSearchTextIndexs(String s, String searchStr) {
        List<Integer> indexs = new ArrayList<>();
        int index = s.indexOf(searchStr);
        while (index>0) {
            indexs.add(index);
            //String subString = s.substring(index + 1, s.length());
            //index = subString.indexOf(searchStr);
           index= s.indexOf(searchStr,index+1);
        }
    /*    String str= String.format(PATTERN,searchStr);
        Pattern p=Pattern.compile(str);
        Matcher matcher=p.matcher(s);
        while (matcher.find()){
            indexs.add(matcher.start());
        }*/
        return indexs;
    }

    /**
      * 根据索引标记文字变为蓝色
      *@author zhangsan
      *@date   16/8/23 上午10:50
      */
    private SpannableStringBuilder getStringStyle(String s, String searchStr) {
        List<Integer> indexs = getSearchTextIndexs(s, searchStr);
        SpannableStringBuilder sp = new SpannableStringBuilder(s);
        for (int i : indexs) {
            sp.setSpan(new BackgroundColorSpan(mContext.getResources().getColor(R.color.theme_color_default)), i, i + searchStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sp;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public void setData(List<ChatMessage> messages){
        this.mDatas=messages;
    }

    public void addData(List<ChatMessage> messages){
        this.mDatas.addAll(messages);
    }
}
