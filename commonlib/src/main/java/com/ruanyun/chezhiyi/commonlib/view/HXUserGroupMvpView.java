package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;

import java.util.List;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/8/10.
 */
public interface HXUserGroupMvpView extends MvpView {

    void onSuccess(ResultBase<List<HxUserGroup>> resultBase);

    void onError(ResultBase<List<HxUserGroup>> resultBase, int errorCode);

    void onFail(String msg);

    void onResult();
}
