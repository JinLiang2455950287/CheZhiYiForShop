package com.ruanyun.chezhiyi.commonlib.base;

import java.util.List;

/**
 * Description:
 * author: jery on 2016/4/29 10:26.
 */
public  class PageInfoBase<T> {
    private int currentPage;
    private int pageNum;
    private int totalPage;
    private List<T> result;
    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "PageInfoBase{" +
                "currentPage=" + currentPage +
                ", pageNum=" + pageNum +
                ", totalPage=" + totalPage +
                ", result=" + result +
                ", totalCount=" + totalCount +
                '}';
    }
}
