package com.ruanyun.chezhiyi.commonlib.view;


import com.ruanyun.chezhiyi.commonlib.model.MenDianServiceGoodsInfo;

/**
 * Created by czy on 2017/4/20
 * 工单统计
 */

public interface HuiYuanServiceGoodTongJiView {
    void getServiceGoodsSuccess(MenDianServiceGoodsInfo resultBase);

    void cancelServiceGoodsTiChengErr();
}
