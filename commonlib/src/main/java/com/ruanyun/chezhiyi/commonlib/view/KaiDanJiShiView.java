package com.ruanyun.chezhiyi.commonlib.view;


import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;

import java.util.List;

/**
 * Created by czy on 2017/4/25.
 * 技师
 */

public interface KaiDanJiShiView {
    void getKaiDanJiShiSuccess(List<User> jishiList);

    void cancelKaiDanJiShiErr();
}
