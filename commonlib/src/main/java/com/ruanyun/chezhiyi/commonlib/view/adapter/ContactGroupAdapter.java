package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;

import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * author: jery on 2016/7/27 11:25.
 */
public class ContactGroupAdapter extends MultiItemTypeAdapter<HxUser> {
    public ContactGroupAdapter(Context context, List<HxUser> datas) {
        super(context, datas);
        addItemViewDelegate(new HxContactGroupDelegate());
        addItemViewDelegate(new HxContactDelegate());
    }
    public  void setData(List<HxUser> users){
        this.mDatas=users;
    }
    public void setContactListClickListener(ContactListClickListener listener){

    }
    interface ContactListClickListener {
      void onGroupClick(int position);
      void onContactClick(int postion);
    }

    public List<HxUser> getSearchResult(List<User> contactList, String s){
        List<HxUser> searchResult=new ArrayList<>();
        for(User user:contactList){
            if(user.getNickName().contains(s)){
                HxUser hxUser = new HxUser();
                hxUser.setTypeId(HxUser.TYPE_USER);
                hxUser.setUserNick(user.getNickName());
                hxUser.setGroupNum(user.getGroupNum());
                hxUser.setUserNum(user.getUserNum());
                searchResult.add(hxUser);
            }
        }
        return  searchResult;
    }
}
