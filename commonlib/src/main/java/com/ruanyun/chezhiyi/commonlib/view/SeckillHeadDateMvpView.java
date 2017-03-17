package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.SeckillHeadInfo;

import java.util.List;

/**
 * Description ：秒杀视图 获取头部日期信息
 * <p/>
 * Created by hdl on 2016/9/13.
 */
public interface SeckillHeadDateMvpView {
    void showLoadingView(String msg);
    void dismissLoadingView();
    void showToast(String msg);
    void onHeadDateResult(List<SeckillHeadInfo> seckillHeadInfos);

}
