package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by czy on 2017/4/18.
 * 销售/施工提成info
 */

public class TiChengInfoModel {

    /**
     * userNum : sys97620000010902
     * map : {"commonPercent":"+100%","month":"201704","type":1,"commonNumber":36.8}
     * year : 2017
     * month : 04
     * type : 1
     */

    private String userNum;
    private MapBean map;
    private String year;
    private String month;
    private int type;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class MapBean {
        /**
         * commonPercent : +100%
         * month : 201704
         * type : 1
         * commonNumber : 36.8
         */

        private String commonPercent;
        private String month;
        private int type;
        private double commonNumber;

        public String getCommonPercent() {
            return commonPercent;
        }

        public void setCommonPercent(String commonPercent) {
            this.commonPercent = commonPercent;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getCommonNumber() {
            return commonNumber;
        }

        public void setCommonNumber(double commonNumber) {
            this.commonNumber = commonNumber;
        }

        @Override
        public String toString() {
            return "MapBean{" +
                    "commonPercent='" + commonPercent + '\'' +
                    ", month='" + month + '\'' +
                    ", type=" + type +
                    ", commonNumber=" + commonNumber +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TiChengInfoModel{" +
                "userNum='" + userNum + '\'' +
                ", map=" + map +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", type=" + type +
                '}';
    }
}
