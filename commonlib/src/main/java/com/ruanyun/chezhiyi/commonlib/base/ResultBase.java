package com.ruanyun.chezhiyi.commonlib.base;

/**
 * Description:
 * author: zhangsan on 16/7/20 下午5:11.
 */
public  class ResultBase<T> {
    private int result;

    private String msg;

    private T obj;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
