package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：首页的推荐项目类型
 * <p/>
 * Created by ycw on 2016/9/12.
 */
public class RecommentParams extends PageParamsBase {

    private int recommentProjectType;

    public int getRecommentProjectType() {
        return recommentProjectType;
    }

    /**
     * 1推荐项目   2猜你喜欢
     */
    public void setRecommentProjectType(int recommentProjectType) {
        this.recommentProjectType = recommentProjectType;
    }
}
