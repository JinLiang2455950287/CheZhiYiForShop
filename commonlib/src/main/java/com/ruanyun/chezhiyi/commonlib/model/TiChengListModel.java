package com.ruanyun.chezhiyi.commonlib.model;

import java.util.List;

/**
 * Created by czy on 2017/4/18.
 * 销售/施工 提成
 */

public class TiChengListModel {

    /**
     * assistList : []
     * commissionAmount : 5.5
     * commissionInfoId : 798
     * commissionInfoNum : TC99610000000798
     * commissionRecord : 1
     * commissionRecordName :
     * commissionType : 1
     * commonNum : 5.50
     * createTime : 2017-04-10 15:24:18
     * createUserNum : sys14900000010910
     * goodsName : 新增商品1,虚拟商品1
     * month :
     * nickName :
     * projectName : 机修
     * projectNum : 004000000000000
     * servicePlateNumber : 皖A99999
     * sgAmount : 0
     * startTime : null
     * sysDate : null
     * totalamount : 0
     * user : {"accountBalance":0,"auths":[],"carAllName":"","carId":0,"carInfo":null,"carMileage":"","carName":"","carNum":"","createTime":null,"createUserNum":"","customerName":"","customerNum":"","customerType":"","endDate":null,"friendNickName":"","friendStatus":0,"groupNum":"","isOrder":0,"isQcPerson":0,"labelCode":"","labelCodeName":"","labelName":"","linkTel":"12345678","littleSecretary":"","loginError":"","loginName":"13987654321","loginPass":"","nickName":"金良","personalNote":"","personalSign":"","projectNum":"","projectTypeList":[],"requestType":"","role":null,"roleName":"","scoreBalance":0,"storeNum":"","urls":[],"userBirth":null,"userId":0,"userInterest":"","userLevel":0,"userName":"","userNum":"sys14900000010910","userPhoto":"file//userphoto//348484352442102.jpg","userSex":0,"userStatus":0,"userType":0,"workStatus":""}
     * userName : 金良
     * userNum : sys97620000010902
     * workOrderNum : 20170408001893
     * xsAmount : 0
     */

    private double commissionAmount;
    private int commissionInfoId;
    private String commissionInfoNum;
    private int commissionRecord;
    private String commissionRecordName;
    private int commissionType;
    private String commonNum;
    private String createTime;
    private String createUserNum;
    private String goodsName;
    private String month;
    private String nickName;
    private String projectName;
    private String projectNum;
    private String servicePlateNumber;
    private int sgAmount;
    private Object startTime;
    private Object sysDate;
    private int totalamount;
    private UserBean user;
    private String userName;
    private String userNum;
    private String workOrderNum;
    private int xsAmount;
    private List<?> assistList;

    public double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public int getCommissionInfoId() {
        return commissionInfoId;
    }

    public void setCommissionInfoId(int commissionInfoId) {
        this.commissionInfoId = commissionInfoId;
    }

    public String getCommissionInfoNum() {
        return commissionInfoNum;
    }

    public void setCommissionInfoNum(String commissionInfoNum) {
        this.commissionInfoNum = commissionInfoNum;
    }

    public int getCommissionRecord() {
        return commissionRecord;
    }

    public void setCommissionRecord(int commissionRecord) {
        this.commissionRecord = commissionRecord;
    }

    public String getCommissionRecordName() {
        return commissionRecordName;
    }

    public void setCommissionRecordName(String commissionRecordName) {
        this.commissionRecordName = commissionRecordName;
    }

    public int getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
    }

    public String getCommonNum() {
        return commonNum;
    }

    public void setCommonNum(String commonNum) {
        this.commonNum = commonNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserNum() {
        return createUserNum;
    }

    public void setCreateUserNum(String createUserNum) {
        this.createUserNum = createUserNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getServicePlateNumber() {
        return servicePlateNumber;
    }

    public void setServicePlateNumber(String servicePlateNumber) {
        this.servicePlateNumber = servicePlateNumber;
    }

    public int getSgAmount() {
        return sgAmount;
    }

    public void setSgAmount(int sgAmount) {
        this.sgAmount = sgAmount;
    }

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    public Object getSysDate() {
        return sysDate;
    }

    public void setSysDate(Object sysDate) {
        this.sysDate = sysDate;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(int totalamount) {
        this.totalamount = totalamount;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public int getXsAmount() {
        return xsAmount;
    }

    public void setXsAmount(int xsAmount) {
        this.xsAmount = xsAmount;
    }

    public List<?> getAssistList() {
        return assistList;
    }

    public void setAssistList(List<?> assistList) {
        this.assistList = assistList;
    }

    public static class UserBean {
        /**
         * accountBalance : 0
         * auths : []
         * carAllName :
         * carId : 0
         * carInfo : null
         * carMileage :
         * carName :
         * carNum :
         * createTime : null
         * createUserNum :
         * customerName :
         * customerNum :
         * customerType :
         * endDate : null
         * friendNickName :
         * friendStatus : 0
         * groupNum :
         * isOrder : 0
         * isQcPerson : 0
         * labelCode :
         * labelCodeName :
         * labelName :
         * linkTel : 12345678
         * littleSecretary :
         * loginError :
         * loginName : 13987654321
         * loginPass :
         * nickName : 金良
         * personalNote :
         * personalSign :
         * projectNum :
         * projectTypeList : []
         * requestType :
         * role : null
         * roleName :
         * scoreBalance : 0
         * storeNum :
         * urls : []
         * userBirth : null
         * userId : 0
         * userInterest :
         * userLevel : 0
         * userName :
         * userNum : sys14900000010910
         * userPhoto : file//userphoto//348484352442102.jpg
         * userSex : 0
         * userStatus : 0
         * userType : 0
         * workStatus :
         */

        private int accountBalance;
        private String carAllName;
        private int carId;
        private Object carInfo;
        private String carMileage;
        private String carName;
        private String carNum;
        private Object createTime;
        private String createUserNum;
        private String customerName;
        private String customerNum;
        private String customerType;
        private Object endDate;
        private String friendNickName;
        private int friendStatus;
        private String groupNum;
        private int isOrder;
        private int isQcPerson;
        private String labelCode;
        private String labelCodeName;
        private String labelName;
        private String linkTel;
        private String littleSecretary;
        private String loginError;
        private String loginName;
        private String loginPass;
        private String nickName;
        private String personalNote;
        private String personalSign;
        private String projectNum;
        private String requestType;
        private Object role;
        private String roleName;
        private int scoreBalance;
        private String storeNum;
        private Object userBirth;
        private int userId;
        private String userInterest;
        private int userLevel;
        private String userName;
        private String userNum;
        private String userPhoto;
        private int userSex;
        private int userStatus;
        private int userType;
        private String workStatus;
        private List<?> auths;
        private List<?> projectTypeList;
        private List<?> urls;

        public int getAccountBalance() {
            return accountBalance;
        }

        public void setAccountBalance(int accountBalance) {
            this.accountBalance = accountBalance;
        }

        public String getCarAllName() {
            return carAllName;
        }

        public void setCarAllName(String carAllName) {
            this.carAllName = carAllName;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public Object getCarInfo() {
            return carInfo;
        }

        public void setCarInfo(Object carInfo) {
            this.carInfo = carInfo;
        }

        public String getCarMileage() {
            return carMileage;
        }

        public void setCarMileage(String carMileage) {
            this.carMileage = carMileage;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getCarNum() {
            return carNum;
        }

        public void setCarNum(String carNum) {
            this.carNum = carNum;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getCreateUserNum() {
            return createUserNum;
        }

        public void setCreateUserNum(String createUserNum) {
            this.createUserNum = createUserNum;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerNum() {
            return customerNum;
        }

        public void setCustomerNum(String customerNum) {
            this.customerNum = customerNum;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public String getFriendNickName() {
            return friendNickName;
        }

        public void setFriendNickName(String friendNickName) {
            this.friendNickName = friendNickName;
        }

        public int getFriendStatus() {
            return friendStatus;
        }

        public void setFriendStatus(int friendStatus) {
            this.friendStatus = friendStatus;
        }

        public String getGroupNum() {
            return groupNum;
        }

        public void setGroupNum(String groupNum) {
            this.groupNum = groupNum;
        }

        public int getIsOrder() {
            return isOrder;
        }

        public void setIsOrder(int isOrder) {
            this.isOrder = isOrder;
        }

        public int getIsQcPerson() {
            return isQcPerson;
        }

        public void setIsQcPerson(int isQcPerson) {
            this.isQcPerson = isQcPerson;
        }

        public String getLabelCode() {
            return labelCode;
        }

        public void setLabelCode(String labelCode) {
            this.labelCode = labelCode;
        }

        public String getLabelCodeName() {
            return labelCodeName;
        }

        public void setLabelCodeName(String labelCodeName) {
            this.labelCodeName = labelCodeName;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public String getLinkTel() {
            return linkTel;
        }

        public void setLinkTel(String linkTel) {
            this.linkTel = linkTel;
        }

        public String getLittleSecretary() {
            return littleSecretary;
        }

        public void setLittleSecretary(String littleSecretary) {
            this.littleSecretary = littleSecretary;
        }

        public String getLoginError() {
            return loginError;
        }

        public void setLoginError(String loginError) {
            this.loginError = loginError;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getLoginPass() {
            return loginPass;
        }

        public void setLoginPass(String loginPass) {
            this.loginPass = loginPass;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPersonalNote() {
            return personalNote;
        }

        public void setPersonalNote(String personalNote) {
            this.personalNote = personalNote;
        }

        public String getPersonalSign() {
            return personalSign;
        }

        public void setPersonalSign(String personalSign) {
            this.personalSign = personalSign;
        }

        public String getProjectNum() {
            return projectNum;
        }

        public void setProjectNum(String projectNum) {
            this.projectNum = projectNum;
        }

        public String getRequestType() {
            return requestType;
        }

        public void setRequestType(String requestType) {
            this.requestType = requestType;
        }

        public Object getRole() {
            return role;
        }

        public void setRole(Object role) {
            this.role = role;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public int getScoreBalance() {
            return scoreBalance;
        }

        public void setScoreBalance(int scoreBalance) {
            this.scoreBalance = scoreBalance;
        }

        public String getStoreNum() {
            return storeNum;
        }

        public void setStoreNum(String storeNum) {
            this.storeNum = storeNum;
        }

        public Object getUserBirth() {
            return userBirth;
        }

        public void setUserBirth(Object userBirth) {
            this.userBirth = userBirth;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserInterest() {
            return userInterest;
        }

        public void setUserInterest(String userInterest) {
            this.userInterest = userInterest;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserNum() {
            return userNum;
        }

        public void setUserNum(String userNum) {
            this.userNum = userNum;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }

        public int getUserSex() {
            return userSex;
        }

        public void setUserSex(int userSex) {
            this.userSex = userSex;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(String workStatus) {
            this.workStatus = workStatus;
        }

        public List<?> getAuths() {
            return auths;
        }

        public void setAuths(List<?> auths) {
            this.auths = auths;
        }

        public List<?> getProjectTypeList() {
            return projectTypeList;
        }

        public void setProjectTypeList(List<?> projectTypeList) {
            this.projectTypeList = projectTypeList;
        }

        public List<?> getUrls() {
            return urls;
        }

        public void setUrls(List<?> urls) {
            this.urls = urls;
        }

        @Override
        public String toString() {
            return "UserBean{" +
                    "accountBalance=" + accountBalance +
                    ", carAllName='" + carAllName + '\'' +
                    ", carId=" + carId +
                    ", carInfo=" + carInfo +
                    ", carMileage='" + carMileage + '\'' +
                    ", carName='" + carName + '\'' +
                    ", carNum='" + carNum + '\'' +
                    ", createTime=" + createTime +
                    ", createUserNum='" + createUserNum + '\'' +
                    ", customerName='" + customerName + '\'' +
                    ", customerNum='" + customerNum + '\'' +
                    ", customerType='" + customerType + '\'' +
                    ", endDate=" + endDate +
                    ", friendNickName='" + friendNickName + '\'' +
                    ", friendStatus=" + friendStatus +
                    ", groupNum='" + groupNum + '\'' +
                    ", isOrder=" + isOrder +
                    ", isQcPerson=" + isQcPerson +
                    ", labelCode='" + labelCode + '\'' +
                    ", labelCodeName='" + labelCodeName + '\'' +
                    ", labelName='" + labelName + '\'' +
                    ", linkTel='" + linkTel + '\'' +
                    ", littleSecretary='" + littleSecretary + '\'' +
                    ", loginError='" + loginError + '\'' +
                    ", loginName='" + loginName + '\'' +
                    ", loginPass='" + loginPass + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", personalNote='" + personalNote + '\'' +
                    ", personalSign='" + personalSign + '\'' +
                    ", projectNum='" + projectNum + '\'' +
                    ", requestType='" + requestType + '\'' +
                    ", role=" + role +
                    ", roleName='" + roleName + '\'' +
                    ", scoreBalance=" + scoreBalance +
                    ", storeNum='" + storeNum + '\'' +
                    ", userBirth=" + userBirth +
                    ", userId=" + userId +
                    ", userInterest='" + userInterest + '\'' +
                    ", userLevel=" + userLevel +
                    ", userName='" + userName + '\'' +
                    ", userNum='" + userNum + '\'' +
                    ", userPhoto='" + userPhoto + '\'' +
                    ", userSex=" + userSex +
                    ", userStatus=" + userStatus +
                    ", userType=" + userType +
                    ", workStatus='" + workStatus + '\'' +
                    ", auths=" + auths +
                    ", projectTypeList=" + projectTypeList +
                    ", urls=" + urls +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TiChengListModel{" +
                "commissionAmount=" + commissionAmount +
                ", commissionInfoId=" + commissionInfoId +
                ", commissionInfoNum='" + commissionInfoNum + '\'' +
                ", commissionRecord=" + commissionRecord +
                ", commissionRecordName='" + commissionRecordName + '\'' +
                ", commissionType=" + commissionType +
                ", commonNum='" + commonNum + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createUserNum='" + createUserNum + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", month='" + month + '\'' +
                ", nickName='" + nickName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectNum='" + projectNum + '\'' +
                ", servicePlateNumber='" + servicePlateNumber + '\'' +
                ", sgAmount=" + sgAmount +
                ", startTime=" + startTime +
                ", sysDate=" + sysDate +
                ", totalamount=" + totalamount +
                ", user=" + user +
                ", userName='" + userName + '\'' +
                ", userNum='" + userNum + '\'' +
                ", workOrderNum='" + workOrderNum + '\'' +
                ", xsAmount=" + xsAmount +
                ", assistList=" + assistList +
                '}';
    }
}
