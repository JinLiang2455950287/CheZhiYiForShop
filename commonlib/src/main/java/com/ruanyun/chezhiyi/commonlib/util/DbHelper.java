package com.ruanyun.chezhiyi.commonlib.util;


import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.data.db.AccountInfoDao;
import com.ruanyun.chezhiyi.commonlib.data.db.CarModelDao;
import com.ruanyun.chezhiyi.commonlib.data.db.DaoSession;
import com.ruanyun.chezhiyi.commonlib.data.db.HomeIconInfoDao;
import com.ruanyun.chezhiyi.commonlib.data.db.HxGroupDao;
import com.ruanyun.chezhiyi.commonlib.data.db.HxUserDao;
import com.ruanyun.chezhiyi.commonlib.data.db.HxUserGroupDao;
import com.ruanyun.chezhiyi.commonlib.data.db.ParentCodeInfoDao;
import com.ruanyun.chezhiyi.commonlib.data.db.ProjectTypeDao;
import com.ruanyun.chezhiyi.commonlib.data.db.RemindInfoDao;
import com.ruanyun.chezhiyi.commonlib.model.AccountInfo;
import com.ruanyun.chezhiyi.commonlib.model.CarModel;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.model.HxGroup;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;
import com.ruanyun.chezhiyi.commonlib.model.ParentCodeInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.RemindInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;

import org.greenrobot.greendao.query.Query;

import java.util.List;


/**
 * Description:
 * author: zhangsan on 16/7/28 上午10:18.
 */
public class DbHelper {
    private static Context mContext;
    private static DbHelper instance;
    private AccountInfoDao accountInfoDao;
    private HxUserDao hxUserDao;
    private HxGroupDao hxGroupDao;
    private ParentCodeInfoDao parentCodeInfoDao;
    private HxUserGroupDao hxUserGroupDao;
    private HomeIconInfoDao homeIconInfoDao;
    private ProjectTypeDao projectTypeDao;
    private CarModelDao carModelDao;
    private RemindInfoDao remindInfoDao;
    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper();
            if (mContext == null) {
                mContext = context;
            }
            DaoSession session = App.getDaoSession(mContext);
            instance.accountInfoDao = session.getAccountInfoDao();
            instance.hxGroupDao=session.getHxGroupDao();
            instance.hxUserDao=session.getHxUserDao();
            instance.parentCodeInfoDao=session.getParentCodeInfoDao();
            instance.hxUserGroupDao=session.getHxUserGroupDao();
            instance.homeIconInfoDao=session.getHomeIconInfoDao();
            instance.projectTypeDao=session.getProjectTypeDao();
            instance.carModelDao=session.getCarModelDao();
            instance.remindInfoDao=session.getRemindInfoDao();
        }

        return instance;
    }

    public static DbHelper getInstance() {
        return instance;
    }

    public void insertAccountInfo(AccountInfo info) {
        accountInfoDao.insertOrReplace(info);
    }

    public void delAccountInfo(AccountInfo info) {
        accountInfoDao.delete(info);
    }

    public AccountInfo getAccountInfoByName(String loginName) {
        if (TextUtils.isEmpty(loginName))
            return null;
        Query<AccountInfo> queryBuilder = accountInfoDao
                .queryBuilder()
                .where(AccountInfoDao.Properties.LoginName.eq(loginName))
                .build();
        return queryBuilder.unique();
    }


    /**
     * 将用户添加到数据库
     * @param user
     */
    public void insertUser(User user) {
        if (getUserByNum(user.getUserNum()) == null) {
            HxUser hxUser = new HxUser();
            hxUser.setUserNum(user.getUserNum());
            if (!TextUtils.isEmpty(user.getFriendNickName())) {
                hxUser.setUserNick(user.getFriendNickName());
            } else {
                hxUser.setUserNick(user.getNickName());
            }
            hxUser.setUserPhoto(FileUtil.getFileUrl(user.getUserPhoto()));
            insertHxUser(hxUser);
        }
    }


    /**
      * 添加环信用户
      *@author zhangsan
      *@date   16/7/28 下午4:04
      */
    public void insertHxUser(HxUser hxUser){
        hxUserDao.insertOrReplace(hxUser);
    }
    /**
     * 添加环信用户
     *@author zhangsan
     *@date   16/7/28 下午4:04
     */
    public void insertHxUsers(List<HxUser> userList){
        hxUserDao.insertOrReplaceInTx(userList);
    }

    public List<HxUser> getContactList(){
        Query<HxUser> query=hxUserDao.queryBuilder()
                .build();
        return  query.list();
    }

    /**
     * 获取用户信息
     * @param userNum
     * @return
     */
    public HxUser getUserByNum(String userNum){
        Query<HxUser> query=hxUserDao.queryBuilder().
                where(HxUserDao.Properties.UserNum.eq(userNum))
                .build();
        return query.unique();
    }

    public void delHxUsers(){
        hxUserDao.deleteAll();
    }

    public void insertHxGroup(List<HxGroup> groupList){
        hxGroupDao.insertOrReplaceInTx(groupList);
    }
    /**
      * 获取好友分组
      *@author zhangsan
      *@date   16/7/28 下午4:12
      */
    public List<HxGroup> getContactGroup(){
        Query<HxGroup> query=hxGroupDao.queryBuilder().build();

        return  query.list();
    }


    public void delAllGroup(){
        hxGroupDao.deleteAll();
    }


    public void insertParentCode(ParentCodeInfo parentCodeInfo){
        parentCodeInfoDao.insertOrReplace(parentCodeInfo);
    }

    public void insertParentCodes(List<ParentCodeInfo> codeInfoList){
        parentCodeInfoDao.deleteAll();
        parentCodeInfoDao.insertOrReplaceInTx(codeInfoList);
    }

    public List<ParentCodeInfo> getParentCodeList(String parentCodeName){
        Query<ParentCodeInfo> query=parentCodeInfoDao.queryBuilder()
                .where(ParentCodeInfoDao.Properties.ParentCode.eq(parentCodeName))
                .build();
        return  query.list();
    }

    public List<ParentCodeInfo> getParentCodeList(){
        Query<ParentCodeInfo> query=parentCodeInfoDao.queryBuilder()
                .build();
        return  query.list();
    }

    public String getParentName(String itemCode,String parentCode){
        Query<ParentCodeInfo> query=parentCodeInfoDao.queryBuilder()
       .where(ParentCodeInfoDao.Properties.ItemCode.eq(itemCode),
               ParentCodeInfoDao.Properties.ParentCode.eq(parentCode))
                .build();

        ParentCodeInfo info=query.unique();
        return  info==null?"":info.getItemName();
    }

    public void inserServiceTypes(List<ProjectType> projectTypes){
        projectTypeDao.deleteAll();
        projectTypeDao.insertOrReplaceInTx(projectTypes);
    }


    @Nullable
    public ProjectType getServiceType(String projectNum){
        Query<ProjectType> query=projectTypeDao.queryBuilder()
                .where(ProjectTypeDao.Properties.ProjectNum.eq(projectNum))
                .where(ProjectTypeDao.Properties.ParentNum.eq("000000"))
                .build();
        ProjectType projectType=query.unique();
        return projectType;
    }

    /**
      * 根据parentnum获取服务项名称
      *@author zhangsan
      *@date   16/10/17 上午8:58
      */
    public String getServiceTypeName(String parentNum){
        Query<ProjectType> query=projectTypeDao.queryBuilder()
                .where(ProjectTypeDao.Properties.ProjectNum.eq(parentNum))
                .where(ProjectTypeDao.Properties.ParentNum.eq("000000"))
                .build();
        List<ProjectType> projectTypes=query.list();
        return projectTypes.isEmpty()?"":projectTypes.get(0).getProjectName();
    }
    /**
      * 获取服务大项分类列表
      *@author zhangsan
      *@date   16/11/4 上午11:22
      */
    public List<ProjectType> getSeviceTypes(){
    Query<ProjectType> query=projectTypeDao.queryBuilder()
            .where(ProjectTypeDao.Properties.ParentNum.eq("000000"))
            .build();
        return  query.list();
    }
    /**
      * 获取服务大项分类数量
      *@author zhangsan
      *@date   16/11/4 上午11:23
      */
    public int getServiceTypeCount(){
        return getSeviceTypes().size();
    }

    /**
     * 获取一级服务类的名称
     * @return
     */
    public String getParentSeviceTypeName(String projectNum) {
        ProjectType projectType = getServiceType(projectNum);
        return projectType == null ? "" : projectType.getProjectName();
    }

    /**
     * 获取所有可以预约的一级 数据 集
     * @return
     */
    public List<ProjectType> getMakeAbleSeviceTypes() {
        Query<ProjectType> query = projectTypeDao.queryBuilder()
                .where(ProjectTypeDao.Properties.ParentNum.eq("000000"), ProjectTypeDao
                        .Properties.IsMake.eq(1))
                .build();
        return query.list();
    }

    public List<ProjectType> getAllSeviceTypes(){
    Query<ProjectType> query=projectTypeDao.queryBuilder()
            .build();
        return  query.list();
    }

    /**
     * 获取所有可以预约的数据集
     * @return
     */
    public List<ProjectType> getAllMakeAbleSeviceTypes(){
    Query<ProjectType> query=projectTypeDao.queryBuilder()
            .where(ProjectTypeDao.Properties.IsMake.notEq(2))
            .build();
        return  query.list();
    }

    public void insertHomeIcon(HomeIconInfo homeIconInfo){
        homeIconInfoDao.insertOrReplace(homeIconInfo);
    }

    public void insertHomeIcons(List<HomeIconInfo> homeIconInfoList) {
        if (homeIconInfoList == null || homeIconInfoList.size() == 0) return;
        homeIconInfoDao.deleteAll();
        homeIconInfoDao.insertOrReplaceInTx(homeIconInfoList);
        PrefUtility.put(C.PrefName.PREF_HAS_HOME_ICON, true);
    }

    /**
     * 获取图标
     * @param ModuleType 模块号
     * @return
     */
    public List<HomeIconInfo> getHomeIconInfoList(int ModuleType){
        Query<HomeIconInfo> query=homeIconInfoDao.queryBuilder()
                .where(HomeIconInfoDao.Properties.ModuleType.eq(ModuleType))
                .build();
        return  query.list();
    }

    /**
     * 获取图标
     * @return
     */
    public List<HomeIconInfo> getAllHomeIconList(){
        Query<HomeIconInfo> query=homeIconInfoDao.queryBuilder()
                .build();
        return  query.list();
    }


      /**
        * 获取实体类缓存版本号
        *@author zhangsan
        *@date   16/9/5 下午6:01
        */
    public String getBeanVersion(String parentCodeName){
        Query<ParentCodeInfo> query=parentCodeInfoDao.queryBuilder()
                .where(ParentCodeInfoDao.Properties.ItemName.eq(parentCodeName))
                .build();
        ParentCodeInfo info=query.unique();
        return null==info?"0":info.getItemCode();
    }


    public void saveGroups(List<HxUserGroup> groups){
        hxUserGroupDao.insertOrReplaceInTx(groups);
    }

    public void saveGroup(HxUserGroup group){
        hxUserGroupDao.insertOrReplace(group);
    }
    /**
      * 根据环信编号查询群信息
      *@author zhangsan
      *@date   16/9/5 下午6:09
      */
    public HxUserGroup getGroupByHxNum(String hxGroupNum){
        Query<HxUserGroup> query=hxUserGroupDao.queryBuilder()
                .where(HxUserGroupDao.Properties.HuanxinNum.eq(hxGroupNum))
                .build();
        return query.unique();
    }
    //public  List<ParentCodeInfo>

    public void saveCarModels(List<CarModel> carModels){
        carModelDao.insertOrReplaceInTx(carModels);
    }

    public List<CarModel> getAllCarModel(){
        Query<CarModel> query=carModelDao.queryBuilder().build();
        return query.list();
    }

    public String getCarModelNameByNum(String carModelNum){
        Query<CarModel> query=carModelDao.queryBuilder()
                .where(CarModelDao.Properties.CarModelNum.eq(carModelNum))
                .build();
        CarModel model= query.unique();
        return model==null?"":model.getCarModelAllName();
    }


    /**
     * 获取所有系统消息
     * @return
     */
    public List<RemindInfo> getAllRemindInfos(){
        Query<RemindInfo> query=remindInfoDao.queryBuilder().build();
        return  query.list();
    }

    public void insertRemindInfo(RemindInfo remindInfo){
        remindInfoDao.insertOrReplace(remindInfo);
    }

    public void insertRemindInfos(List<RemindInfo> remindInfoList){
        remindInfoDao.insertOrReplaceInTx(remindInfoList);
    }
}
