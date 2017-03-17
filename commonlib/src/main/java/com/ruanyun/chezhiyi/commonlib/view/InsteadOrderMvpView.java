package com.ruanyun.chezhiyi.commonlib.view;


import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;

/**
 * Description ：代下单(添加商品)
 * <p/>
 * Created by hdl on 2016/10/10.
 */

public interface InsteadOrderMvpView extends MvpView {

    void showLoadingView();

    void dismissLoadingView();

    void showInsteadOrderTip(String msg);

    void showInsteadOrderSuccess(ResultBase resultBase);

}
