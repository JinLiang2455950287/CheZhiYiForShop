package com.ruanyun.chezhiyi.commonlib.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.commonlib.model.User;

/**
 * Description:本地化存储用户信息
 * author: jery on 2016/5/30 11:36.
 */
public class UserInfoSaver {
    private static Gson mGson;

    private static Gson create(){
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

    /**
     * 获取用户信息
     * @return
     */
    public static User getUserInfo() {
        String content = PrefUtility.get(C.PrefName.PREF_LOGIN_USER_INFO, "");
        if (!TextUtils.isEmpty(content)) {
            return create().fromJson(content, User.class);
        } else {
            return new User();
        }
    }

    /**
     * 保存用户信息到本地
     * @param userInfo
     */
    public static void saveUserInfo(User userInfo) {
        String content = create().toJson(userInfo);
        PrefUtility.put(C.PrefName.PREF_LOGIN_USER_INFO, content);
    }
}
