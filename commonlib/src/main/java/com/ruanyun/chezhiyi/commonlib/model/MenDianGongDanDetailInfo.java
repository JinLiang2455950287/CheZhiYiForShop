package com.ruanyun.chezhiyi.commonlib.model;

import java.util.List;

/**
 * Created by czy on 2017/4/20.
 * 门店工单详情列表页面
 */

public class MenDianGongDanDetailInfo {

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
     * result : [{"service_user_num":"sys37570000010932","create_time":"2017-04-19 15:34:22","project_name":"机修","child_project_num":"[{\"childProjectTypeList\":[],\"parentNum\":\"004000000000000\",\"projectAllName\":\"服务项目/机修/机修1\",\"projectName\":\"机修1\",\"projectNum\":\"004005000000000\",\"isMake\":1,\"isParent\":false,\"isSelected\":true,\"projectId\":187,\"sortNum\":2}]","service_user_name":"gaoyuan","service_plate_number":"皖AZE238"},{"service_user_num":"sys37570000010932","create_time":"2017-04-19 14:47:49","project_name":"美容","child_project_num":"[{\"isMake\":0,\"sortNum\":2,\"childProjectTypeList\":[],\"projectNum\":\"005007000000000\",\"parentNum\":\"005000000000000\",\"projectName\":\"美容1\",\"projectAllName\":\"服务项目/美容/美容1\"}]","service_user_name":"gaoyuan","service_plate_number":"皖AZE238"},{"service_user_num":"sys37570000010932","create_time":"2017-04-19 10:44:08","project_name":"常规","child_project_num":null,"service_user_name":"gaoyuan","service_plate_number":"皖AZE238"},{"service_user_num":"sys37570000010932","create_time":"2017-04-19 10:41:54","project_name":"常规","child_project_num":null,"service_user_name":"gaoyuan","service_plate_number":"皖AZE238"},{"service_user_num":"sys93640000010940","create_time":"2017-04-19 10:35:20","project_name":"机修","child_project_num":null,"service_user_name":"Allen","service_plate_number":"皖K99999"},{"service_user_num":"sys37570000010932","create_time":"2017-04-19 10:17:17","project_name":"机修","child_project_num":"[{\"childProjectTypeList\":[],\"parentNum\":\"004000000000000\",\"projectAllName\":\"服务项目/机修/机修1\",\"projectName\":\"机修1\",\"projectNum\":\"004005000000000\",\"isMake\":1,\"isParent\":false,\"isSelected\":true,\"projectId\":187,\"sortNum\":2}]","service_user_name":"gaoyuan","service_plate_number":"皖AZE238"},{"service_user_num":"sys37570000010932","create_time":"2017-04-19 09:53:14","project_name":"常规","child_project_num":null,"service_user_name":"gaoyuan","service_plate_number":"皖AZE238"},{"service_user_num":"sys37570000010932","create_time":"2017-04-19 09:47:43","project_name":"常规","child_project_num":null,"service_user_name":"gaoyuan","service_plate_number":"皖AZE238"},{"service_user_num":"sys37570000010932","create_time":"2017-04-19 09:30:56","project_name":"常规","child_project_num":null,"service_user_name":"gaoyuan","service_plate_number":"皖AZE238"},{"service_user_num":"sys37570000010932","create_time":"2017-04-19 09:02:19","project_name":"机修","child_project_num":null,"service_user_name":"gaoyuan","service_plate_number":"皖AZE238"}]
     * totalCount : 12
     * totalPage : 2
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
         * service_user_num : sys37570000010932
         * create_time : 2017-04-19 15:34:22
         * project_name : 机修
         * child_project_num : [{"childProjectTypeList":[],"parentNum":"004000000000000","projectAllName":"服务项目/机修/机修1","projectName":"机修1","projectNum":"004005000000000","isMake":1,"isParent":false,"isSelected":true,"projectId":187,"sortNum":2}]
         * service_user_name : gaoyuan
         * service_plate_number : 皖AZE238
         */

        private String service_user_num;
        private String create_time;
        private String project_name;
        private String child_project_num;
        private String service_user_name;
        private String service_plate_number;

        public String getService_user_num() {
            return service_user_num;
        }

        public void setService_user_num(String service_user_num) {
            this.service_user_num = service_user_num;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getChild_project_num() {
            return child_project_num;
        }

        public void setChild_project_num(String child_project_num) {
            this.child_project_num = child_project_num;
        }

        public String getService_user_name() {
            return service_user_name;
        }

        public void setService_user_name(String service_user_name) {
            this.service_user_name = service_user_name;
        }

        public String getService_plate_number() {
            return service_plate_number;
        }

        public void setService_plate_number(String service_plate_number) {
            this.service_plate_number = service_plate_number;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "service_user_num='" + service_user_num + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", project_name='" + project_name + '\'' +
                    ", child_project_num='" + child_project_num + '\'' +
                    ", service_user_name='" + service_user_name + '\'' +
                    ", service_plate_number='" + service_plate_number + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MenDianGongDanDetailInfo{" +
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

