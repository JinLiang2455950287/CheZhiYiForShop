package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description:
 * author: zhangsan on 16/9/9 上午11:20.
 */
public class ArticleListParams extends PageParamsBase {
    /**文章分类  **/
    private String articleType ;

    private Integer isHome;

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public Integer getIsHome() {
        return isHome;
    }

    public void setIsHome(Integer isHome) {
        this.isHome = isHome;
    }
}
