package com.ruanyun.chezhiyi.commonlib.model;

import java.util.List;

/**
 * Created by 创智 on 2017/3/10.
 * 公告
 */

public class GongGaoInfo {
    /**
     * content : 2017年公司春节放假安排的通知
     公司全体员工：
     2017年春节即将来临，根据国务院办公厅通知，并结合我公司实际情况与各项工作安排，经公司领导决定，现将春节放假时间安排如下：
     1月26号（腊月二十九）开始放假，到2月3号（正月初七），共计：9天。2月4号（正月初八）正式上班。节假日期间，请各部门负责人提前组织好放假前安全检查。放假期间回家探亲或者外出游玩的员工应当注意出行安全。
     祝大家新年快乐，“鸡”祥如意，开门大吉！
     特此通知！

     * createTime : 2017-01-24 09:21:35
     * flag1 :
     * flag2 :
     * flag3 :
     * infoId : 24
     * pubTime : 2017-01-24 09:21:39
     * startDate : null
     * status : 1
     * storeNum : st30390000000011
     * title : 2017年欧威店春节放假安排的通知
     * user : {"accountBalance":0,"auths":[],"carAllName":"","carId":0,"carInfo":null,"carMileage":"","carName":"","carNum":"","createTime":null,"createUserNum":"","customerType":"","endDate":null,"friendNickName":"","friendStatus":0,"groupNum":"","isOrder":0,"isQcPerson":0,"labelCode":"","labelCodeName":"","labelName":"","linkTel":"","littleSecretary":"","loginError":"","loginName":"admin","loginPass":"","nickName":"系统管理员","personalNote":"","personalSign":"","projectNum":"","projectTypeList":[],"requestType":"","role":null,"roleName":"","scoreBalance":0,"storeNum":"","urls":[],"userBirth":null,"userId":0,"userInterest":"","userLevel":0,"userName":"","userNum":"sys46370000010001","userPhoto":"file//userphoto//21443369921430.png","userSex":1,"userStatus":0,"userType":0,"workStatus":""}
     * userNum : sys46370000010001
     */

    private String content;
    private String createTime;
    private String flag1;
    private String flag2;
    private String flag3;
    private int infoId;
    private String pubTime;
    private Object startDate;
    private int status;
    private String storeNum;
    private String title;
    private UserBean user;
    private String userNum;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public Object getStartDate() {
        return startDate;
    }

    public void setStartDate(Object startDate) {
        this.startDate = startDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
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
         * linkTel :
         * littleSecretary :
         * loginError :
         * loginName : admin
         * loginPass :
         * nickName : 系统管理员
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
         * userNum : sys46370000010001
         * userPhoto : file//userphoto//21443369921430.png
         * userSex : 1
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
    }

    @Override
    public String toString() {
        return "GongGaoInfo{" +
                "content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", flag1='" + flag1 + '\'' +
                ", flag2='" + flag2 + '\'' +
                ", flag3='" + flag3 + '\'' +
                ", infoId=" + infoId +
                ", pubTime='" + pubTime + '\'' +
                ", startDate=" + startDate +
                ", status=" + status +
                ", storeNum='" + storeNum + '\'' +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", userNum='" + userNum + '\'' +
                '}';
    }
//
//    /**
//     * access_token :
//     * msg :
//     * obj : [{"content":"2017年公司春节放假安排的通知\r\n公司全体员工：\r\n  2017年春节即将来临，根据国务院办公厅通知，并结合我公司实际情况与各项工作安排，经公司领导决定，现将春节放假时间安排如下：\r\n  1月26号（腊月二十九）开始放假，到2月3号（正月初七），共计：9天。2月4号（正月初八）正式上班。节假日期间，请各部门负责人提前组织好放假前安全检查。放假期间回家探亲或者外出游玩的员工应当注意出行安全。\r\n  祝大家新年快乐，\u201c鸡\u201d祥如意，开门大吉！\r\n  特此通知！\r\n","createTime":"2017-01-24 09:21:35","flag1":"","flag2":"","flag3":"","infoId":24,"pubTime":"2017-01-24 09:21:39","startDate":null,"status":1,"storeNum":"st30390000000011","title":"2017年欧威店春节放假安排的通知","user":{"accountBalance":0,"auths":[],"carAllName":"","carId":0,"carInfo":null,"carMileage":"","carName":"","carNum":"","createTime":null,"createUserNum":"","customerType":"","endDate":null,"friendNickName":"","friendStatus":0,"groupNum":"","isOrder":0,"isQcPerson":0,"labelCode":"","labelCodeName":"","labelName":"","linkTel":"","littleSecretary":"","loginError":"","loginName":"admin","loginPass":"","nickName":"系统管理员","personalNote":"","personalSign":"","projectNum":"","projectTypeList":[],"requestType":"","role":null,"roleName":"","scoreBalance":0,"storeNum":"","urls":[],"userBirth":null,"userId":0,"userInterest":"","userLevel":0,"userName":"","userNum":"sys46370000010001","userPhoto":"file//userphoto//21443369921430.png","userSex":1,"userStatus":0,"userType":0,"workStatus":""},"userNum":"sys46370000010001"},{"content":"2013年，吉利欧洲研发中心成立，沃尔沃主导与协同吉利研发CMA基础架构模块，这种模块的最大特点就是灵活、软硬件都可叠代升级，可以搭载纯电、油电、插电，也可以搭载传统能源，电子架构技术领先，人机交互智能，最终可升级为完全无人驾驶。吉利汽车、沃尔沃汽车两个汽车公司都可以使用CMA基础架构模块进行各自汽车产品的研发设计，双方的汽车产品标准不同，设计不同，配置不同，消费群体不同。简单地讲，CMA架构就是吉利汽车公司与沃尔沃汽车公司联合打造的互联网汽车基础架构，与传统汽车底盘概念完全不同。吉利汽车基于CMA基础架构推出的全新品牌LYNK & CO已于上月在欧洲发布，媒体和消费者给与了高度评价，该品牌下首款车型将于明年第四季度面世。\r\n李书福：我对互联网汽车的一些思考\r\n\r\n吉利汽车旗下全新品牌LYNK&CO应互联网而生\r\n我可以毫不夸张地说，沃尔沃汽车公司的自动驾驶技术是当今世界上最先进的技术，沃尔沃公司研发的XC90、S90汽车产品是全球唯一大批量商业化的高度辅助驾驶汽车，实现了时速0-130公里的智能辅助驾驶。自动驾驶的目的首先是为了安全，无论是电动汽车还是传统汽车，安全是最基本的前提，这一点全世界没有第二个汽车公司可以与沃尔沃相抗衡。美国优步与沃尔沃合作开发无人车并由沃尔沃提供基础车型；全球知名汽车安全零部件公司奥特利夫与沃尔沃合作，联合研发完全无人驾驶技术等等，都足以说明沃尔沃在无人驾驶技术方面的全球地位。即便这样，我们依然认为前方的道路充满了挑战。\r\n李书福：我对互联网汽车的一些思考\r\n\r\nS90已经实现时速0-130公里的智能辅助驾驶\r\n变革是需要条件的，革命是需要环境的。汽车行业的变革虽然已经开始，但条件还需要继续成熟，环境还需要不断改善。汽车行业的变革是需要时间的，而且可能反复并充满不确定性。理想可以吹得天花乱坠，可以充满无穷想象，但现实依然残酷无情。商场就是战场，眼泪不能解决问题。吉利造汽车是从自己投资建设技师学院及工程师学院开始培养人才的，近二十年来在技师、工程师培养上大量投入。沃尔沃汽车公司虽然做了大量基础性工作，虽然在汽车及交通领域互联互通技术上取得了大量研究成果，但以后的路还很长，面临的挑战依然非常严峻。当然，我们充满信心，我们坚信，世上无难事，只要肯登攀。\r\n\r\n\r\n\r\n","createTime":"2017-01-03 17:12:53","flag1":"","flag2":"","flag3":"","infoId":23,"pubTime":"2017-01-03 17:13:22","startDate":null,"status":1,"storeNum":"st30390000000011","title":"吉利欧洲研发中心成立","user":{"accountBalance":0,"auths":[],"carAllName":"","carId":0,"carInfo":null,"carMileage":"","carName":"","carNum":"","createTime":null,"createUserNum":"","customerType":"","endDate":null,"friendNickName":"","friendStatus":0,"groupNum":"","isOrder":0,"isQcPerson":0,"labelCode":"","labelCodeName":"","labelName":"","linkTel":"","littleSecretary":"","loginError":"","loginName":"admin","loginPass":"","nickName":"系统管理员","personalNote":"","personalSign":"","projectNum":"","projectTypeList":[],"requestType":"","role":null,"roleName":"","scoreBalance":0,"storeNum":"","urls":[],"userBirth":null,"userId":0,"userInterest":"","userLevel":0,"userName":"","userNum":"sys46370000010001","userPhoto":"file//userphoto//21443369921430.png","userSex":1,"userStatus":0,"userType":0,"workStatus":""},"userNum":"sys46370000010001"}]
//     * result : 1
//     */
//
//    private String access_token;
//    private String msg;
//    private int result;
//    private List<ObjBean> obj;
//
//    public String getAccess_token() {
//        return access_token;
//    }
//
//    public void setAccess_token(String access_token) {
//        this.access_token = access_token;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public int getResult() {
//        return result;
//    }
//
//    public void setResult(int result) {
//        this.result = result;
//    }
//
//    public List<ObjBean> getObj() {
//        return obj;
//    }
//
//    public void setObj(List<ObjBean> obj) {
//        this.obj = obj;
//    }
//
//    public static class ObjBean {
//        /**
//         * content : 2017年公司春节放假安排的通知
//         公司全体员工：
//         2017年春节即将来临，根据国务院办公厅通知，并结合我公司实际情况与各项工作安排，经公司领导决定，现将春节放假时间安排如下：
//         1月26号（腊月二十九）开始放假，到2月3号（正月初七），共计：9天。2月4号（正月初八）正式上班。节假日期间，请各部门负责人提前组织好放假前安全检查。放假期间回家探亲或者外出游玩的员工应当注意出行安全。
//         祝大家新年快乐，“鸡”祥如意，开门大吉！
//         特此通知！
//
//         * createTime : 2017-01-24 09:21:35
//         * flag1 :
//         * flag2 :
//         * flag3 :
//         * infoId : 24
//         * pubTime : 2017-01-24 09:21:39
//         * startDate : null
//         * status : 1
//         * storeNum : st30390000000011
//         * title : 2017年欧威店春节放假安排的通知
//         * user : {"accountBalance":0,"auths":[],"carAllName":"","carId":0,"carInfo":null,"carMileage":"","carName":"","carNum":"","createTime":null,"createUserNum":"","customerType":"","endDate":null,"friendNickName":"","friendStatus":0,"groupNum":"","isOrder":0,"isQcPerson":0,"labelCode":"","labelCodeName":"","labelName":"","linkTel":"","littleSecretary":"","loginError":"","loginName":"admin","loginPass":"","nickName":"系统管理员","personalNote":"","personalSign":"","projectNum":"","projectTypeList":[],"requestType":"","role":null,"roleName":"","scoreBalance":0,"storeNum":"","urls":[],"userBirth":null,"userId":0,"userInterest":"","userLevel":0,"userName":"","userNum":"sys46370000010001","userPhoto":"file//userphoto//21443369921430.png","userSex":1,"userStatus":0,"userType":0,"workStatus":""}
//         * userNum : sys46370000010001
//         */
//
//        private String content;
//        private String createTime;
//        private String flag1;
//        private String flag2;
//        private String flag3;
//        private int infoId;
//        private String pubTime;
//        private Object startDate;
//        private int status;
//        private String storeNum;
//        private String title;
//        private UserBean user;
//        private String userNum;
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//
//        public String getCreateTime() {
//            return createTime;
//        }
//
//        public void setCreateTime(String createTime) {
//            this.createTime = createTime;
//        }
//
//        public String getFlag1() {
//            return flag1;
//        }
//
//        public void setFlag1(String flag1) {
//            this.flag1 = flag1;
//        }
//
//        public String getFlag2() {
//            return flag2;
//        }
//
//        public void setFlag2(String flag2) {
//            this.flag2 = flag2;
//        }
//
//        public String getFlag3() {
//            return flag3;
//        }
//
//        public void setFlag3(String flag3) {
//            this.flag3 = flag3;
//        }
//
//        public int getInfoId() {
//            return infoId;
//        }
//
//        public void setInfoId(int infoId) {
//            this.infoId = infoId;
//        }
//
//        public String getPubTime() {
//            return pubTime;
//        }
//
//        public void setPubTime(String pubTime) {
//            this.pubTime = pubTime;
//        }
//
//        public Object getStartDate() {
//            return startDate;
//        }
//
//        public void setStartDate(Object startDate) {
//            this.startDate = startDate;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//
//        public String getStoreNum() {
//            return storeNum;
//        }
//
//        public void setStoreNum(String storeNum) {
//            this.storeNum = storeNum;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public UserBean getUser() {
//            return user;
//        }
//
//        public void setUser(UserBean user) {
//            this.user = user;
//        }
//
//        public String getUserNum() {
//            return userNum;
//        }
//
//        public void setUserNum(String userNum) {
//            this.userNum = userNum;
//        }
//
//        public static class UserBean {
//            /**
//             * accountBalance : 0
//             * auths : []
//             * carAllName :
//             * carId : 0
//             * carInfo : null
//             * carMileage :
//             * carName :
//             * carNum :
//             * createTime : null
//             * createUserNum :
//             * customerType :
//             * endDate : null
//             * friendNickName :
//             * friendStatus : 0
//             * groupNum :
//             * isOrder : 0
//             * isQcPerson : 0
//             * labelCode :
//             * labelCodeName :
//             * labelName :
//             * linkTel :
//             * littleSecretary :
//             * loginError :
//             * loginName : admin
//             * loginPass :
//             * nickName : 系统管理员
//             * personalNote :
//             * personalSign :
//             * projectNum :
//             * projectTypeList : []
//             * requestType :
//             * role : null
//             * roleName :
//             * scoreBalance : 0
//             * storeNum :
//             * urls : []
//             * userBirth : null
//             * userId : 0
//             * userInterest :
//             * userLevel : 0
//             * userName :
//             * userNum : sys46370000010001
//             * userPhoto : file//userphoto//21443369921430.png
//             * userSex : 1
//             * userStatus : 0
//             * userType : 0
//             * workStatus :
//             */
//
//            private int accountBalance;
//            private String carAllName;
//            private int carId;
//            private Object carInfo;
//            private String carMileage;
//            private String carName;
//            private String carNum;
//            private Object createTime;
//            private String createUserNum;
//            private String customerType;
//            private Object endDate;
//            private String friendNickName;
//            private int friendStatus;
//            private String groupNum;
//            private int isOrder;
//            private int isQcPerson;
//            private String labelCode;
//            private String labelCodeName;
//            private String labelName;
//            private String linkTel;
//            private String littleSecretary;
//            private String loginError;
//            private String loginName;
//            private String loginPass;
//            private String nickName;
//            private String personalNote;
//            private String personalSign;
//            private String projectNum;
//            private String requestType;
//            private Object role;
//            private String roleName;
//            private int scoreBalance;
//            private String storeNum;
//            private Object userBirth;
//            private int userId;
//            private String userInterest;
//            private int userLevel;
//            private String userName;
//            private String userNum;
//            private String userPhoto;
//            private int userSex;
//            private int userStatus;
//            private int userType;
//            private String workStatus;
//            private List<?> auths;
//            private List<?> projectTypeList;
//            private List<?> urls;
//
//            public int getAccountBalance() {
//                return accountBalance;
//            }
//
//            public void setAccountBalance(int accountBalance) {
//                this.accountBalance = accountBalance;
//            }
//
//            public String getCarAllName() {
//                return carAllName;
//            }
//
//            public void setCarAllName(String carAllName) {
//                this.carAllName = carAllName;
//            }
//
//            public int getCarId() {
//                return carId;
//            }
//
//            public void setCarId(int carId) {
//                this.carId = carId;
//            }
//
//            public Object getCarInfo() {
//                return carInfo;
//            }
//
//            public void setCarInfo(Object carInfo) {
//                this.carInfo = carInfo;
//            }
//
//            public String getCarMileage() {
//                return carMileage;
//            }
//
//            public void setCarMileage(String carMileage) {
//                this.carMileage = carMileage;
//            }
//
//            public String getCarName() {
//                return carName;
//            }
//
//            public void setCarName(String carName) {
//                this.carName = carName;
//            }
//
//            public String getCarNum() {
//                return carNum;
//            }
//
//            public void setCarNum(String carNum) {
//                this.carNum = carNum;
//            }
//
//            public Object getCreateTime() {
//                return createTime;
//            }
//
//            public void setCreateTime(Object createTime) {
//                this.createTime = createTime;
//            }
//
//            public String getCreateUserNum() {
//                return createUserNum;
//            }
//
//            public void setCreateUserNum(String createUserNum) {
//                this.createUserNum = createUserNum;
//            }
//
//            public String getCustomerType() {
//                return customerType;
//            }
//
//            public void setCustomerType(String customerType) {
//                this.customerType = customerType;
//            }
//
//            public Object getEndDate() {
//                return endDate;
//            }
//
//            public void setEndDate(Object endDate) {
//                this.endDate = endDate;
//            }
//
//            public String getFriendNickName() {
//                return friendNickName;
//            }
//
//            public void setFriendNickName(String friendNickName) {
//                this.friendNickName = friendNickName;
//            }
//
//            public int getFriendStatus() {
//                return friendStatus;
//            }
//
//            public void setFriendStatus(int friendStatus) {
//                this.friendStatus = friendStatus;
//            }
//
//            public String getGroupNum() {
//                return groupNum;
//            }
//
//            public void setGroupNum(String groupNum) {
//                this.groupNum = groupNum;
//            }
//
//            public int getIsOrder() {
//                return isOrder;
//            }
//
//            public void setIsOrder(int isOrder) {
//                this.isOrder = isOrder;
//            }
//
//            public int getIsQcPerson() {
//                return isQcPerson;
//            }
//
//            public void setIsQcPerson(int isQcPerson) {
//                this.isQcPerson = isQcPerson;
//            }
//
//            public String getLabelCode() {
//                return labelCode;
//            }
//
//            public void setLabelCode(String labelCode) {
//                this.labelCode = labelCode;
//            }
//
//            public String getLabelCodeName() {
//                return labelCodeName;
//            }
//
//            public void setLabelCodeName(String labelCodeName) {
//                this.labelCodeName = labelCodeName;
//            }
//
//            public String getLabelName() {
//                return labelName;
//            }
//
//            public void setLabelName(String labelName) {
//                this.labelName = labelName;
//            }
//
//            public String getLinkTel() {
//                return linkTel;
//            }
//
//            public void setLinkTel(String linkTel) {
//                this.linkTel = linkTel;
//            }
//
//            public String getLittleSecretary() {
//                return littleSecretary;
//            }
//
//            public void setLittleSecretary(String littleSecretary) {
//                this.littleSecretary = littleSecretary;
//            }
//
//            public String getLoginError() {
//                return loginError;
//            }
//
//            public void setLoginError(String loginError) {
//                this.loginError = loginError;
//            }
//
//            public String getLoginName() {
//                return loginName;
//            }
//
//            public void setLoginName(String loginName) {
//                this.loginName = loginName;
//            }
//
//            public String getLoginPass() {
//                return loginPass;
//            }
//
//            public void setLoginPass(String loginPass) {
//                this.loginPass = loginPass;
//            }
//
//            public String getNickName() {
//                return nickName;
//            }
//
//            public void setNickName(String nickName) {
//                this.nickName = nickName;
//            }
//
//            public String getPersonalNote() {
//                return personalNote;
//            }
//
//            public void setPersonalNote(String personalNote) {
//                this.personalNote = personalNote;
//            }
//
//            public String getPersonalSign() {
//                return personalSign;
//            }
//
//            public void setPersonalSign(String personalSign) {
//                this.personalSign = personalSign;
//            }
//
//            public String getProjectNum() {
//                return projectNum;
//            }
//
//            public void setProjectNum(String projectNum) {
//                this.projectNum = projectNum;
//            }
//
//            public String getRequestType() {
//                return requestType;
//            }
//
//            public void setRequestType(String requestType) {
//                this.requestType = requestType;
//            }
//
//            public Object getRole() {
//                return role;
//            }
//
//            public void setRole(Object role) {
//                this.role = role;
//            }
//
//            public String getRoleName() {
//                return roleName;
//            }
//
//            public void setRoleName(String roleName) {
//                this.roleName = roleName;
//            }
//
//            public int getScoreBalance() {
//                return scoreBalance;
//            }
//
//            public void setScoreBalance(int scoreBalance) {
//                this.scoreBalance = scoreBalance;
//            }
//
//            public String getStoreNum() {
//                return storeNum;
//            }
//
//            public void setStoreNum(String storeNum) {
//                this.storeNum = storeNum;
//            }
//
//            public Object getUserBirth() {
//                return userBirth;
//            }
//
//            public void setUserBirth(Object userBirth) {
//                this.userBirth = userBirth;
//            }
//
//            public int getUserId() {
//                return userId;
//            }
//
//            public void setUserId(int userId) {
//                this.userId = userId;
//            }
//
//            public String getUserInterest() {
//                return userInterest;
//            }
//
//            public void setUserInterest(String userInterest) {
//                this.userInterest = userInterest;
//            }
//
//            public int getUserLevel() {
//                return userLevel;
//            }
//
//            public void setUserLevel(int userLevel) {
//                this.userLevel = userLevel;
//            }
//
//            public String getUserName() {
//                return userName;
//            }
//
//            public void setUserName(String userName) {
//                this.userName = userName;
//            }
//
//            public String getUserNum() {
//                return userNum;
//            }
//
//            public void setUserNum(String userNum) {
//                this.userNum = userNum;
//            }
//
//            public String getUserPhoto() {
//                return userPhoto;
//            }
//
//            public void setUserPhoto(String userPhoto) {
//                this.userPhoto = userPhoto;
//            }
//
//            public int getUserSex() {
//                return userSex;
//            }
//
//            public void setUserSex(int userSex) {
//                this.userSex = userSex;
//            }
//
//            public int getUserStatus() {
//                return userStatus;
//            }
//
//            public void setUserStatus(int userStatus) {
//                this.userStatus = userStatus;
//            }
//
//            public int getUserType() {
//                return userType;
//            }
//
//            public void setUserType(int userType) {
//                this.userType = userType;
//            }
//
//            public String getWorkStatus() {
//                return workStatus;
//            }
//
//            public void setWorkStatus(String workStatus) {
//                this.workStatus = workStatus;
//            }
//
//            public List<?> getAuths() {
//                return auths;
//            }
//
//            public void setAuths(List<?> auths) {
//                this.auths = auths;
//            }
//
//            public List<?> getProjectTypeList() {
//                return projectTypeList;
//            }
//
//            public void setProjectTypeList(List<?> projectTypeList) {
//                this.projectTypeList = projectTypeList;
//            }
//
//            public List<?> getUrls() {
//                return urls;
//            }
//
//            public void setUrls(List<?> urls) {
//                this.urls = urls;
//            }
//
//        }
//
//
//    }
}
