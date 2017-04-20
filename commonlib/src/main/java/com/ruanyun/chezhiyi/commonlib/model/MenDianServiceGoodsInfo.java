package com.ruanyun.chezhiyi.commonlib.model;

import java.util.List;

/**
 * Created by czy on 2017/4/20.
 * 门店统计 服务商品
 */

public class MenDianServiceGoodsInfo {

    /**
     * currentPage : 0
     * firstOfPage : 1
     * flag :
     * next : 0
     * numPerPage : 10
     * order :
     * orderBy :
     * pageNum : 1
     * pre : 0
     * result : [{"createTime":null,"goodsName":"balabala","goodsNum":"SP22780000000364","reportDate":"","reportId":0,"reportType":0,"saleAmount":2600,"saleCount":13,"startDate":"","storeNum":""},{"createTime":null,"goodsName":"SP032802","goodsNum":"SP65660000000345","reportDate":"","reportId":0,"reportType":0,"saleAmount":48,"saleCount":8,"startDate":"","storeNum":""},{"createTime":null,"goodsName":"SPcg01","goodsNum":"SP24290000000347","reportDate":"","reportId":0,"reportType":0,"saleAmount":4,"saleCount":1,"startDate":"","storeNum":""},{"createTime":null,"goodsName":"SPcg02","goodsNum":"SP65710000000348","reportDate":"","reportId":0,"reportType":0,"saleAmount":80,"saleCount":20,"startDate":"","storeNum":""},{"createTime":null,"goodsName":"你看我好吃吗","goodsNum":"SP59310000000365","reportDate":"","reportId":0,"reportType":0,"saleAmount":460,"saleCount":2,"startDate":"","storeNum":""},{"createTime":null,"goodsName":"保养商品1","goodsNum":"SP33550000000342","reportDate":"","reportId":0,"reportType":0,"saleAmount":65,"saleCount":13,"startDate":"","storeNum":""},{"createTime":null,"goodsName":"商品0408","goodsNum":"SP28750000000361","reportDate":"","reportId":0,"reportType":0,"saleAmount":2520,"saleCount":14,"startDate":"","storeNum":""},{"createTime":null,"goodsName":"商品1115","goodsNum":"SP25830000000352","reportDate":"","reportId":0,"reportType":0,"saleAmount":420,"saleCount":21,"startDate":"","storeNum":""},{"createTime":null,"goodsName":"商品3","goodsNum":"SP68990000000346","reportDate":"","reportId":0,"reportType":0,"saleAmount":105,"saleCount":7,"startDate":"","storeNum":""},{"createTime":null,"goodsName":"常规商品1","goodsNum":"SP75560000000341","reportDate":"","reportId":0,"reportType":0,"saleAmount":2310,"saleCount":33,"startDate":"","storeNum":""}]
     * totalCount : 21
     * totalPage : 3
     */

    private int currentPage;
    private int firstOfPage;
    private String flag;
    private int next;
    private int numPerPage;
    private String order;
    private String orderBy;
    private int pageNum;
    private int pre;
    private int totalCount;
    private int totalPage;
    private List<ResultBean> result;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getFirstOfPage() {
        return firstOfPage;
    }

    public void setFirstOfPage(int firstOfPage) {
        this.firstOfPage = firstOfPage;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPre() {
        return pre;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * createTime : null
         * goodsName : balabala
         * goodsNum : SP22780000000364
         * reportDate :
         * reportId : 0
         * reportType : 0
         * saleAmount : 2600
         * saleCount : 13
         * startDate :
         * storeNum :
         */

        private Object createTime;
        private String goodsName;
        private String goodsNum;
        private String reportDate;
        private int reportId;
        private int reportType;
        private int saleAmount;
        private int saleCount;
        private String startDate;
        private String storeNum;

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }

        public String getReportDate() {
            return reportDate;
        }

        public void setReportDate(String reportDate) {
            this.reportDate = reportDate;
        }

        public int getReportId() {
            return reportId;
        }

        public void setReportId(int reportId) {
            this.reportId = reportId;
        }

        public int getReportType() {
            return reportType;
        }

        public void setReportType(int reportType) {
            this.reportType = reportType;
        }

        public int getSaleAmount() {
            return saleAmount;
        }

        public void setSaleAmount(int saleAmount) {
            this.saleAmount = saleAmount;
        }

        public int getSaleCount() {
            return saleCount;
        }

        public void setSaleCount(int saleCount) {
            this.saleCount = saleCount;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getStoreNum() {
            return storeNum;
        }

        public void setStoreNum(String storeNum) {
            this.storeNum = storeNum;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "createTime=" + createTime +
                    ", goodsName='" + goodsName + '\'' +
                    ", goodsNum='" + goodsNum + '\'' +
                    ", reportDate='" + reportDate + '\'' +
                    ", reportId=" + reportId +
                    ", reportType=" + reportType +
                    ", saleAmount=" + saleAmount +
                    ", saleCount=" + saleCount +
                    ", startDate='" + startDate + '\'' +
                    ", storeNum='" + storeNum + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MenDianServiceGoodsInfo{" +
                "currentPage=" + currentPage +
                ", firstOfPage=" + firstOfPage +
                ", flag='" + flag + '\'' +
                ", next=" + next +
                ", numPerPage=" + numPerPage +
                ", order='" + order + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", pageNum=" + pageNum +
                ", pre=" + pre +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", result=" + result +
                '}';
    }
}
