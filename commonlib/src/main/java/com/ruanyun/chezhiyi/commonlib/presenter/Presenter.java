package com.ruanyun.chezhiyi.commonlib.presenter;

/**
 * Description:
 * author: zhangsan on 16/7/19 下午2:44.
 */
public interface Presenter<V> {

    void attachView(V mvpView);

    void detachView();

    void onCancel();
}
