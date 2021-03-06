package com.ruanyun.chezhiyi.commonlib.model;

import java.util.List;

/**
 * Created by czy on 2017/4/20.
 * 门店统计 营业额
 */

public class MenDianYinYeEInfo {


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
     * result : [{"accountType":0,"amount":925,"appType":0,"createTime":null,"hyCount":0,"memberAmount":260,"recordType":0,"reportDate":"2017-04-20","reportId":0,"reportType":0,"skCount":0,"skfsHjAmount":0,"skfsHykAmount":695,"skfsWxAmount":0,"skfsXjAmount":230,"skfsZfbAmount":0,"srxmCzAmount":0,"srxmHjAmount":0,"srxmTkAmount":0,"srxmXsAmount":230,"ssAmount":0,"startDate":"","storeNum":"","workAmount":410,"xfHyCount":0,"xfSkCount":0}]
     * totalCount : 0
     * totalPage : 0
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
         * accountType : 0
         * amount : 925
         * appType : 0
         * createTime : null
         * hyCount : 0
         * memberAmount : 260
         * recordType : 0
         * reportDate : 2017-04-20
         * reportId : 0
         * reportType : 0
         * skCount : 0
         * skfsHjAmount : 0
         * skfsHykAmount : 695
         * skfsWxAmount : 0
         * skfsXjAmount : 230
         * skfsZfbAmount : 0
         * srxmCzAmount : 0
         * srxmHjAmount : 0
         * srxmTkAmount : 0
         * srxmXsAmount : 230
         * ssAmount : 0
         * startDate :
         * storeNum :
         * workAmount : 410
         * xfHyCount : 0
         * xfSkCount : 0
         */

        private int accountType;
        private int appType;
        private Object createTime;
        private int hyCount;
        private double memberAmount;
        private int recordType;
        private String reportDate;
        private int reportId;
        private int reportType;
        private int skCount;
        private int skfsHjAmount;
        private double skfsHykAmount;
        private int skfsWxAmount;
        private double skfsXjAmount;
        private int skfsZfbAmount;
        private int srxmCzAmount;
        private int srxmHjAmount;
        private int srxmTkAmount;
        private double srxmXsAmount;
        private int ssAmount;
        private String startDate;
        private String storeNum;
        private int workAmount;
        private int xfHyCount;
        private int xfSkCount;

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public int getAppType() {
            return appType;
        }

        public void setAppType(int appType) {
            this.appType = appType;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getHyCount() {
            return hyCount;
        }

        public void setHyCount(int hyCount) {
            this.hyCount = hyCount;
        }

        public double getMemberAmount() {
            return memberAmount;
        }

        public void setMemberAmount(double memberAmount) {
            this.memberAmount = memberAmount;
        }

        public double getSkfsXjAmount() {
            return skfsXjAmount;
        }

        public void setSkfsXjAmount(double skfsXjAmount) {
            this.skfsXjAmount = skfsXjAmount;
        }

        public int getRecordType() {
            return recordType;
        }

        public void setRecordType(int recordType) {
            this.recordType = recordType;
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

        public int getSkCount() {
            return skCount;
        }

        public void setSkCount(int skCount) {
            this.skCount = skCount;
        }

        public int getSkfsHjAmount() {
            return skfsHjAmount;
        }

        public void setSkfsHjAmount(int skfsHjAmount) {
            this.skfsHjAmount = skfsHjAmount;
        }

        public double getSkfsHykAmount() {
            return skfsHykAmount;
        }

        public void setSkfsHykAmount(double skfsHykAmount) {
            this.skfsHykAmount = skfsHykAmount;
        }

        public int getSkfsWxAmount() {
            return skfsWxAmount;
        }

        public void setSkfsWxAmount(int skfsWxAmount) {
            this.skfsWxAmount = skfsWxAmount;
        }

        public int getSkfsZfbAmount() {
            return skfsZfbAmount;
        }

        public void setSkfsZfbAmount(int skfsZfbAmount) {
            this.skfsZfbAmount = skfsZfbAmount;
        }

        public int getSrxmCzAmount() {
            return srxmCzAmount;
        }

        public void setSrxmCzAmount(int srxmCzAmount) {
            this.srxmCzAmount = srxmCzAmount;
        }

        public int getSrxmHjAmount() {
            return srxmHjAmount;
        }

        public void setSrxmHjAmount(int srxmHjAmount) {
            this.srxmHjAmount = srxmHjAmount;
        }

        public int getSrxmTkAmount() {
            return srxmTkAmount;
        }

        public void setSrxmTkAmount(int srxmTkAmount) {
            this.srxmTkAmount = srxmTkAmount;
        }

        public double getSrxmXsAmount() {
            return srxmXsAmount;
        }

        public void setSrxmXsAmount(double srxmXsAmount) {
            this.srxmXsAmount = srxmXsAmount;
        }

        public int getSsAmount() {
            return ssAmount;
        }

        public void setSsAmount(int ssAmount) {
            this.ssAmount = ssAmount;
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

        public int getWorkAmount() {
            return workAmount;
        }

        public void setWorkAmount(int workAmount) {
            this.workAmount = workAmount;
        }

        public int getXfHyCount() {
            return xfHyCount;
        }

        public void setXfHyCount(int xfHyCount) {
            this.xfHyCount = xfHyCount;
        }

        public int getXfSkCount() {
            return xfSkCount;
        }

        public void setXfSkCount(int xfSkCount) {
            this.xfSkCount = xfSkCount;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "accountType=" + accountType +
                    ", appType=" + appType +
                    ", createTime=" + createTime +
                    ", hyCount=" + hyCount +
                    ", memberAmount=" + memberAmount +
                    ", recordType=" + recordType +
                    ", reportDate='" + reportDate + '\'' +
                    ", reportId=" + reportId +
                    ", reportType=" + reportType +
                    ", skCount=" + skCount +
                    ", skfsHjAmount=" + skfsHjAmount +
                    ", skfsHykAmount=" + skfsHykAmount +
                    ", skfsWxAmount=" + skfsWxAmount +
                    ", skfsXjAmount=" + skfsXjAmount +
                    ", skfsZfbAmount=" + skfsZfbAmount +
                    ", srxmCzAmount=" + srxmCzAmount +
                    ", srxmHjAmount=" + srxmHjAmount +
                    ", srxmTkAmount=" + srxmTkAmount +
                    ", srxmXsAmount=" + srxmXsAmount +
                    ", ssAmount=" + ssAmount +
                    ", startDate='" + startDate + '\'' +
                    ", storeNum='" + storeNum + '\'' +
                    ", workAmount=" + workAmount +
                    ", xfHyCount=" + xfHyCount +
                    ", xfSkCount=" + xfSkCount +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MenDianYinYeEInfo{" +
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
