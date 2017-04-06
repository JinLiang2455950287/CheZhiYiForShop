package com.ruanyun.chezhiyi.commonlib.util;


import com.ruanyun.chezhiyi.commonlib.data.api.ApiManager;

/**
 * Description:
 * author: zhangsan on 16/7/19 下午2:54.
 */
public final class C {
    public static final class HxApiUrl {
        /**
         * 1.4.1	好友列表
         **/
        public static final String URL_GET_CONTACT_LIST = "app/{userNum}/friend/list";
        /**
         * 1.3.2 获取用户所有分组
         **/
        public static final String URL_GET_CONTACT_GROUP = "app/{userNum}/usergroup/list";

        /**
         * 1.3.3	添加分组
         **/
        public static final String URL_ADD_CONTACT_GROUP = "app/{userNum}/usergroup/add";
        /**
         * 1.4.2	添加好友【通知】
         **/
        public static final String URL_ADD_CONTACT_FRIEND = "app/{userNum}/friend/add";
        /**
         * 1.4.3	添加好友【回调】
         **/
        public static final String URL_ADD_CONTACT_FRIEND_CALLBACK = "app/{userNum}/friend/addCallBack";
        /**
         * 1.4.4	删除好友
         **/
        public static final String URL_DELETE_CONTACT_FRIEND = "app/{userNum}/friend/delete";
        /**
         * 1.5.2	获取用户群组
         **/
        public static final String URL_GET_USER_GROUP_LIST = "app/{userNum}/usergroupinfo/list";
        /**
         * 1.5.3	创建群
         **/
        public static final String URL_ADD_GROUP = "app/{userNum}/usergroupinfo/add_user";
        /**
         * 1.5.4	删除群
         **/
        public static final String URL_GROUP_DELETE = "app/{userNum}/usergroupinfo/delete";
        /**
         * 1.5.5	获取群详情[单个]
         **/
        public static final String URL_GET_GROUP_DETAILS = "app/{userNum}/usergroupinfo/get";
        /**
         * 1.5.6	修改群信息
         **/
        public static final String URL_UPDATE_GROUP_DETAILS = "app/{userNum}/usergroupinfo/update";
        /**
         * 1.6.2	获取群组成员
         **/
        public static final String URL_GET_GROUP_MEMBER = "app/{userNum}/usergrouprelation/list";
        /**
         * 1.6.3	修改群名片
         **/
        public static final String URL_UPDATE_GROUP_NAME_CARD = "app/{userNum}/usergrouprelation/update";
        /**
         * 1.6.4	获取群名片
         **/
        public static final String URL_GET_GROUP_NAME_CARD = "app/{userNum}/usergrouprelation/get";
        /**
         * 1.6.5	退出群
         **/
        public static final String URL_QUIT_GROUP = "app/{userNum}/usergrouprelation/delete";
        /**
         * 1.6.6 加入群
         **/
        public static final String URL_GROUP_INVITE = "app/{userNum}/usergrouprelation/add";
        /**
         * 1.4.5	加入或移除黑名单
         **/
        public static final String URL_MODIFY_BLACK = "app/{userNum}/friend/modifyBlack";
        /**
         * 1.4.6查找陌生人添加好友 no_friend_list
         */
        public static final String URL_NO_FRIEND_LIST = "app/{userNum}/friend/no_friend_list";

        /**
         * 1.7.2	获取聊天记录
         **/
        public static final String URL_GET_MESSAGE_LIST = "app/{userNum}/chats/list";
        /**
         * 1.7.3 获取聊天记录的上下文
         **/
        public static final String URL_GET_MESSAGE_CONTEXT = "app/{userNum}/chats/content_list";

        /**
         * 1.9.2	用户详细信息
         **/
        public static final String URL_GET_USER_BY_NUM = "app/{userNum}/user/info";

        /**
         * 1.4.8	好友详细信息
         **/
        public static final String URL_GET_FRIEND_INFO_BY_NUM = "app/{userNum}/friend/info";

        /**
         * 1.4.4	修改好友备注
         **/
        public static final String URL_UPDATE_USER_MARK = "app/{userNum}/friend/update_nickname";

    }

    /**
     *
     */
    public static final class ApiUrl {
        /**
         * 1.8.2	获取公共字典表
         **/
        public static final String URL_GET_PARENTCODE = "app/external/staticdata/dictionary";
        /**
         * 1.8.3	获取需要缓存的版本号
         **/
        public static final String URL_GET_PARENTCODE_VERSION = "app/external/staticdata/version";
        /**
         * 1.7.2	用户登陆
         **/
        public static final String URL_LOGIN = "app/external/{loginName}/user/login";
        /**
         * 1.7.3	忘记密码
         **/
        public static final String URL_FORGET_PASSWORD = "app/external/{loginName}/user/forgetPassword";
        /**
         * 1.7.4	注册用户
         **/
        public static final String URL_REGISTER = "app/external/{loginName}/user/add";
        /**
         * 获取驾驶证信息
         **/
        public static final String URL_GET_USERLICENSE = "app/{userNum}/userlicense/get";
        /**
         * 添加或修改驾驶证信息
         **/
        public static final String URL_UPDATE_USERLICENSE = "app/{userNum}/userlicense/updateOrSave";

        /**
         * 1.8.6 获取车型信息
         **/
        public static final String URL_GET_CAR_MODEL = "app/external/staticdata/carModel";
        /**
         * 1.8.7	获取工单服务或技师技能
         **/
        public static final String URL_GET_PROJECT_TYPE = "app/external/staticdata/projectType";
        /**
         * 查看预约列表
         **/
        public static final String URL_GET_YUYUE_LIST = "app/{userNum}/make/list_confirm";

        /**
         * 获取等候区数量
         **/
        public static final String URL_GET_WAITAREA_COUNT = "app/{userNum}/workorder/wait_count";

        /**
         * 查看权限表
         **/
        public static final String URL_GET_PERSSION = "app/{userNum}/buttons/list";

        /**
         * 获取客户余额
         **/
        public static final String URL_GET_CUSTOMERYUE = "app/{userNum}/user/center";

        /**
         * 预约处理
         **/
        public static final String URL_GET_YUYUE_DEAL = "app/{userNum}/make/confirm";
        /**
         * 技师端查看退款信息，当前列表为退款中信息
         **/
        public static final String URL_GET_TUIKUANG_LIST = "app/{userNum}/order/tuikuan/list";

        /**
         * 查看会员快查
         **/
        public static final String URL_GET_HUYUAN_LIST = "app/{userNum}/user/user_list";

        /**
         * 获取用户车辆信息
         **/
        public static final String URL_GET_CARINFO_LIST = "app/{userNum}/carinfo/list";

        /**
         * 添加或修改车辆信息
         **/
        public static final String URL_UPDATE_OR_SAVE_CARINFO = "app/{userNum}/carinfo/updateOrSave";

        /**
         * 获取车辆详细信息
         **/
        public static final String URL_GET_CARINFO = "app/{userNum}/carinfo/updateOrSave";

        /**
         * 删除车辆信息
         **/
        public static final String URL_DELETE_CARINFO = "app/{userNum}/carinfo/delete";
        /**
         * 1.9.4修改个人信息
         */
        public static final String URL_UPDATE_PERSONAL_INFO = "app/{userNum}/user/update";
        /**
         * 1.12.2文章列表/
         **/
        public static final String URL_ARCLE_LIST = "app/{userNum}/article/list";
        /**
         * 1.12.2产品、团购列表
         **/
        public static final String URL_PRODUCT_LIST = "app/{userNum}/goods/info/list";


        /**
         * 卡套餐app/{userNum}/package/list    获取卡套餐列表
         **/
        public static final String URL_CARDPACKEG_LIST = "app/{userNum}/package/list";

        /**
         * 卡套餐/app/{userNum}/package/info    获取卡套餐实体
         **/
        public static final String URL_CARDPACKEG_INFO = "app/{userNum}/package/info";

        /**
         * 1.12.2众筹列表
         **/
        public static final String URL_CROWD_FUNDING_LIST = "app/{userNum}/goods/crowd/list";
        /**
         * 1.12.2获取今日秒杀头部日期信息
         **/
        public static final String URL_GET_SECKILL_HEAD_INFO = "app/{userNum}/goods/seckill/list";
        /**
         * 1.12.2获取今日秒杀产品信息列表
         **/
        public static final String URL_GET_SECKILL_DETAIL_INFO = "app/{userNum}/goods/seckill/detail_list";
        /**
         * 1.11.2获取门店详细
         */
        public static final String URL_STORE_INFO = "app/{userNum}/store/info";
        /**
         * 获取记账本列表
         */
        public static final String URL_GET_ACCOUNT_BOOK_LIST = "app/{userNum}/cashbook/list";
        /**
         * 获取记账本总金额
         */
        public static final String URL_GET_ACCOUNT_BOOK_MONEY_AMOUNT = "app/{userNum}/cashbook/price_sum";
        /**
         * 添加或修改记账本
         */
        public static final String URL_ADD_UPDATA_ACCOUNT_BOOK = "app/{userNum}/cashbook/add_update";
        /**
         * 获取预约列表
         */
        public static final String URL_GET_BOOKING_LIST = "app/{userNum}/make/list";
        /**
         * 预约详细
         */
        public static final String URL_GET_BOOKING_INFO = "app/{userNum}/make/info";
        /**
         * 获取优惠券(司机端)
         */
        public static final String URL_GET_DISCOUNT_COUPON = "app/{userNum}/yhj/list";
        /**
         * 司机工单结算有效优惠券(司机端)
         */
        public static final String URL_GET_WORKORDER_COUPON = "app/{userNum}/yhj/list_work_order";
        /**
         * 获取优惠券(技师端)
         */
        public static final String URL_GET_TECHNICIAN_DISCOUNT_COUPON = "app/{userNum}/yhj/list_jishi";
        /**
         * 赠送优惠券(技师端)
         */
        public static final String URL_SEND_DISCOUNT_COUPON = "app/{userNum}/yhj/save";
        /**
         * 添加或修改记账本
         */
        public static final String URL_DELETE_ACCOUNT_BOOK = "app/{userNum}/cashbook/delCashBook";

        /**
         * 1.12.2获取案例
         */
        public static final String URL_GET_CASE = "app/{userNum}/caselibrary/list";

        /**
         * 1.12.3添加或修改案例
         */
        public static final String URL_CASE_ADD_UPDATE = "app/{userNum}/caselibrary/add_update";

        /**
         * 1.16.2添加反馈
         */
        public static final String URL_FEED_BACK = "app/{userNum}/feedback/save";

        /**
         * 1.10.9	获取所有图片信息
         */
        public static final String URL_ICON_INFO = "app/external/staticdata/home_icon";

        /**
         * 添加评论
         */
        public static final String URL_ADD_COMMENT = "app/{userNum}/comment/add";

        /**
         * 点赞或者取消点赞
         */
        public static final String URL_ADD_OR_DELETE_PRAISE = "app/{userNum}/praise/add_delete";
        /**
         * 获取是否已点赞
         **/
        public static final String URL_IS_PRAISED = "app/{userNum}/praise/info";

        public static final String URL_ARTICLE_DETAIL = "app/external/%s/%s/article";

        /**
         * 我的 活动列表
         */
        public static final String URL_MY_ACTIVITY_LIST = "app/{userNum}/goods/activity/my_list";

        /**
         * 活动列表
         */
        public static final String URL_ACTIVITY_LIST = "app/{userNum}/goods/activity/list";

//        /**
//         * 活动详情
//         */
//        public static final String URL_ACTIVITY_DETAILED = ApiManager.API_URL+"app/external/%s/goods/%s/activity";

        /**
         * 广告列表
         */
        public static final String URL_ADVER_LIST = "app/{userNum}/adverinfo/list";

//        /**
//         * 广告详情
//         */
//        public static final String URL_ADVER_DETAILED = ApiManager.API_URL+"app/external/%s/%s/adverinfo";
        /**
         * 案例详情
         **/
        public static final String URL_CASELIB_DETAIL = "app/external/%s/%s/caselibrary";

        /**
         * 推荐项目和猜你喜欢
         */
        public static final String URL_RECOMMEND_LIST = "app/{userNum}/goods/recommend//list";
        /**
         * 获取促销列表
         */
        public static final String URL_PROMOTION_LIST = "app/{userNum}/goods/promotion/list";

        /**
         * 1.14.2	添加预约信息
         */
        public static final String URL_MAKE_APPOINTMENT = "app/{userNum}/make/add";

        /**
         * 常用电话列表
         */
        public static final String URL_COMMENT_USER_TELEPHONE = "app/{userNum}/commonphone/list";

        /**
         * 报名
         */
        public static final String URL_SIGN_ADD = "app/{userNum}/goods/activity/sign_add";

        /**
         * 店铺详细页  {userNum} 用户编号  {storeNum}店铺编号
         */
        public static final String WEB_URL_STORE = "app/external/%s/%s/store";

        /**
         * 活动详细页 {userNum}用户编号 {activityNum} 活动编号
         */
        public static final String WEB_URL_ACTIVITY = "app/external/%s/goods/%s/activity";
        /**
         * 1.17.3	广告详情【自定义广告详情】  {userNum}用户编号 {adverNum}广告编号
         */
        public static final String WEB_URL_ADVERINFO = "app/external/%s/%s/adverinfo";

        /**
         * 1.19.3	产品详细
         * 1 %s userNum    2 %s jsonArrayString
         * goodsNum 产品编号   goodsType 产品类型例如 秒杀 ，团购等 对应的为 订单的 goodsType
         * commonNum为主键 例如 秒杀 为秒杀主键  团购为团购主键 对应的为订单的 commonNum
         */
        public static final String WEB_URL_GOODSINFO = "app/external/%s/goods/info/%s?buyUserNum=%s";

        /**
         * 获取我的订单
         */
        public static final String URL_OREDR_LIST = "app/{userNum}/order/list";
        /**
         * 下单
         */
        public static final String URL_MAKE_OREDR = "app/{userNum}/order/add";

        /**
         * 秒杀详情
         */
        public static final String URL_SECKILL_DETAIL_INFO = "app/{userNum}/goods/seckill/detail_info";
        /**
         * 众筹详情
         */
        public static final String URL_CROWD_DETAIL_info = "app/{userNum}/goods/crowd/info";
        /**
         * 促销详情promotion
         */
        public static final String URL_PROMOTION_DETAIL_INFO = "app/{userNum}/goods/promotion/info";
        /**
         * 产品详情
         */
        public static final String URL_GOODS_DETAILS_INFO = "app/{userNum}/goods/info/info";

        /**
         * 1.12.4删除案例
         **/
        public static final String URL_DELETE_CASELIB = "app/{userNum}/caselibrary/delete";
        /**
         * 获取手机验证码
         */
        public static final String URL_GET_VERIFY = "app/external/{loginName}/user/send_msg";
        /**
         * 申请退款
         */
        public static final String URL_REFUND_APPLICATION = "app/{userNum}/order/tuikuan/add";
        /**
         * 获取订单退款原因
         */
        public static final String URL_TUIKUAN_INFO = "app/{userNum}/order/tuikuan/list";
        /**
         * 司机端或技师端获取我的工单
         */
        public static final String URL_GET_MY_WORKORDER_LIST = "app/{userNum}/workorder/my_list";
        /**
         * 扫描车牌获取预约信息
         */
        public static final String URL_SCAN_LICENSE_GET_BOOKING_INFO = "app/{userNum}/make/info_jiedai";
        /**
         * 获取空闲工位
         */
        public static final String URL_GET_WORKORDER_GONGWEI = "app/{userNum}/workorder/gongwei";
        /**
         * 获取空闲技师
         */
        public static final String URL_GET_LEISURE_TECHNICIAN = "app/{userNum}/workorder/jishi";
        /**
         * 获取已购并未消费的服务
         */
        public static final String URL_GET_NOT_SPENDING_SERVICE = "app/{userNum}/workorder/yi_gou_goods";
        /**
         * 提交接待工单
         */
        public static final String URL_SAVE_RECEPTION_WORKORDER = "app/{userNum}/workorder/jiedai_save";
        /**
         * 工单详情
         */
        public static final String URL_GET_WORKORDER_INFO = "app/{userNum}/workorder/info";
        /**
         * 获取工单商品
         */
        public static final String URL_GET_WORKORDER_GOODS_LIST = "app/{userNum}/workorder/goods_list";
        /**
         * 查看工位
         */
        public static final String URL_GET_STATION_LIST = "app/{userNum}/workorder/gongwei_list";
        /**
         * 等候区详情接单
         */
        public static final String URL_SAVE_ORDER_RECEIVING = "app/{userNum}/workorder/save_jiedan";
        /**
         * 代下单(添加商品)
         */
        public static final String URL_INSTEAD_ORDER = "app/{userNum}/workorder/save_daixiadan";
        /**
         * 1.29.1获取充值套餐
         */
        public static final String URL_RECHARGE_LIST = "app/{userNum}/account/list";
        /**
         * 1.29.2进行充值
         */
        public static final String URL_RECHARGE = "app/{userNum}/account/add";


        /**
         * 取消预约
         */
        public static final String URL_CANCEL_MAKE = "app/{userNum}/make/update";

        /**
         * 1.16.2	获取通知通告
         */
        public static final String URL_NOTICE_INFO = "app/{userNum}/notice/info";

        /**
         * 1.16.2	获取通知通告(新接口)/app/{userNum}/notice/list
         */
        public static final String URL_NOTICE_INFO_NEW = "app/{userNum}/notice/list";

        /**
         * 获取今日预约数量/app/{userNum}/make/list_confirm_count
         */
        public static final String URL_APPOINTMENT_COUNT = "app/{userNum}/make/list_confirm_count";

        /**
         * 退款审核
         */
        public static final String URL_REPAY_EXAMINE = "app/{userNum}/order/tuikuan/examine";

        /**
         * 退款申请数量
         */
        public static final String URL_REPAY_COUNT = "app/{userNum}/order/tuikuan/list_count";

        /**
         * 1.8.3用户第三方登陆
         */
        public static final String URL_LOGIN_THIRD = "app/external/{loginName}/user/login_third";
        /**
         * 获取流水记录
         */
        public static final String URL_RECORD = "app/{userNum}/user/center_record";
        /**
         * 1.8.6	第三方绑定或注册用户
         */
        public static final String URL_ADD_THIRD = "app/external/{loginName}/user/add_third";

        /**
         * 账号支付
         */
        public static final String URL_ACCOUNT_PAY = "app/{userNum}/account/pay";

        /**
         * 取消订单【修改订单状态】
         */
        public static final String URL_ORDER_STATUS_UPDATE = "app/{userNum}/order/update";

        /**
         * 获取用户账号信息
         */
        public static final String URL_USER_ACCOUNT_INFO = "app/{userNum}/user/center";

        /**
         * 根据活动编号用户编号获取报名信息
         */
        public static final String URL_ACTIVITY_INFO = "app/{userNum}/goods/activity/sign_info";

        /**
         * 添加协助技师
         */
        public static final String URL_ADD_ASSIST = "app/{userNum}/workorder/assist_save";

        /**
         * 获取 工单 协助技师
         */
        public static final String URL_GET_ASSIST_LIST = "app/{userNum}/workorder/assist_list";

        /**
         * 取消报名
         */
        public static final String URL_DEL_ACTIVITY = "app/{userNum}/goods/activity/del";

        /**
         * 1.33	签到
         */
        public static final String URL_SIGN = "app/external/%s/sign";
        /**
         * 分享好友
         */
        public static final String URL_SHARE = "app/external/%s/share";
        /**
         * 下载页
         */
        public static final String URL_DOWNLOAD = "app/external/%s/download";

        /**
         * 获取案例详情
         **/
        public static final String URL_CASE_DETAIL_INFO = "app/{userNum}/caselibrary/info";

        /**
         * 订单评价
         */
        public static final String URL_ADD_ORDER_COMMENT = "app/{userNum}/comment/add_order";
        /**
         * 1.9.5	第三方登陆解绑
         */
        public static final String URL_DEL_THIRD = "app/{userNum}/user/third_delete";
        /**
         * 1.9.6	获取已绑定的第三方登陆
         */
        public static final String URL_THIRD_LIST = "app/{userNum}/user/third_list";

        /**
         * 1.32.1	金额+积分兑换
         */
        public static final String URL_ADD_ORDER = "app/{userNum}/order/add";

        /**
         * 1.32.2	纯积分兑换
         */
        public static final String URL_ADD_SCORE_ORDER = "app/{userNum}/score/pay";

        /**
         * 微信支付
         */
        public static final String URL_WEIXIN_ORDER = "app/pay/weixinDoOrder";
        /**
         * 1.9.7	设置支付密码/忘记支付密码重置
         **/
        public static final String URL_SET_TRADE_PWD = "app/{userNum}/user/update_pay_pwd";
        /**
         * 订单详情
         **/
        public static final String URL_ORDER_DETAIL = "app/{userNum}/order/info";

        /**
         * 微信支付
         */
        public static final String URL_ALIPAY_ORDER = "app/{userNum}/payinfo/alipayConfig";
        /**
         * 系统消息列表接口
         **/
        public static final String URL_SYSTEM_REMIND = "app/{userNum}/remind/list";


        /**
         * 1.36.2	销售提成/施工提成/用户数详情页
         * {userNum}	String	用户编号
         * month	 String	月份，默认传当前月 格式 201610
         * type	 Integer	1销售提成 2施工提成 3用户数
         */
        public static final String WEB_URL_COUNT = "app/external/%s/js_report_detail?month=%s&type=%s";

        /**
         * 1.36.3	销售提成/施工提成月份列表
         */
        public static final String WEB_URL_COUNT_LIST = "app/external/%s/js_report_list?year=%s&type=%s";

        /**
         * 1.36.4	营业额统计
         * reportType	Integer	统计类型，1表示日报2表示周报3表示月报，默认传1表示当日报表
         */
        public static final String WEB_URL_JS_REPORT_YYE = "app/external/%s/js_report_yye?reportType=%s";

        /**
         * 1.36.5	会员统计
         */
        public static final String WEB_URL_JS_REPORT_HY = "app/external/%s/js_report_hy";

        /**
         * 1.36.6	新增客户
         */
        public static final String WEB_URL_JS_REPORT_XZKH = "app/external/%s/js_report_xzkh";

        /**
         * 1.36.7	销售商品
         */
        public static final String WEB_URL_JS_REPORT_XSSP = "app/external/%s/js_report_xssp";

        /**
         * 1.39	技师端提醒详情
         * reportDate	String	提醒生成时间 list中获取直接传入
         * remindType	String 	提醒类型 list中获取直接传入
         * commonNum	String	公共num list中获取直接传入
         * numPerPage	Integer	传固定值100
         */
        public static final String WEB_URL_JS_REMIND_INFO = "app/external/%s/js_remind_info?createTime=%s&remindType=%s&commonNum=%s&numPerPage=100";


        /**
         * 1.40	系统通知详情
         * remindType	String 	提醒类型 list中获取直接传入
         * remindInfoNum	String	提醒编号 list中获取直接传入
         */
        public static final String WEB_URL_JS_REMIND_INFO_SYSTEM = "app/external/%s/js_remind_info?remindType=%s&remindInfoNum=%s";

        /**
         * 1.40	系统通知公告
         */
        public static final String WEB_URL_NOTICE_INFO = "app/external/%s/getNoticeDetails?infoId=%d";

    }


    public static final class ParentCode {
        /**
         * 字典表版本
         **/
        public static final String DICTIONARY = "DICTIONARY";
        /**
         * 车型表版本
         **/
        public static final String CARMODEL = "CARMODEL";
        /**
         * APK类型
         **/
        public static final String APK_TYPE = "APK_TYPE";
        /**
         * APP权限
         **/
        public static final String APP_AUTH = "APP_AUTH";
        /**
         * 权限访问类型
         **/
        public static final String AUTH_REQUEST_TYPE = "AUTH_REQUEST_TYPE";
        /**
         * 权限类型
         **/
        public static final String AUTH_TYPE = "AUTH_TYPE";
        /**
         * 车辆颜色
         **/
        public static final String CAR_COLOR = "CAR_COLOR";
        /**
         * 操作日志类型
         **/
        public static final String OPERA_TYPE = "OPERA_TYPE";
        /**
         * 静态数据版本控制
         **/
        public static final String STATICDATA_VERSION = "STATICDATA_VERSION";
        /**
         * 性别
         **/
        public static final String USERSEX = "USERSEX";
        /**
         * 用户状态
         **/
        public static final String USERSTATUS = "USERSTATUS";
        /**
         * 用户类型
         **/
        public static final String USER_TYPE = "USER_TYPE";
        /**
         * 技师端分组
         **/
        public static final String USER_GROUP = "USER_GROUP";
        /**
         * 图标管理
         **/
        public static final String MODULE_TYPE = "MODULE_TYPE";
        /**
         * 文章分类
         **/
        public static final String ARTICLE_TYPE = "ARTICLE_TYPE";
        /**
         * 订单状态分类
         */
        public static final String ORDER_STATUS_TYPE = "T_ORDER_INFO_ORDER_STATUS";
        /**
         * 订单申请退款原因
         **/
        public static final String REFUND_APPLICATION_REASON = "T_ORDER_REFUND_APPLICATION_REASON";

        /**
         *
         */
        public static final String RECOMMENT_PROJECT_GOODS_TYPE = "T_GOODS_RECOMMENT_PROJECT_GOODS_TYPE";
        /**
         * 订单类型 字典表 t_order_info_order_type
         */
        public static final String ORDER_INFO_TYPE = "T_ORDER_INFO_ORDER_TYPE";
        /**
         * 工作状态
         */
        public static final String WORK_STATUS = "WORK_STATUS";
        /**
         * 工单状态
         */
        public static final String WORK_ORDER_STATUS = "WORK_ORDER_STATUS";


        /**
         * 操作类型
         */
        //ACCOUNT_RECORD_TYPE
        public static final String ACCOUNT_RECORD_TYPE = "ACCOUNT_RECORD_TYPE";

        /**
         * 账户类型
         */
        public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";

        /**
         * 个人兴趣爱好
         **/
        public static final String USER_INTEREST = "USER_INTEREST";

        /**
         * 技师标签
         */
        public static final String USER_LABEL = "USER_LABEL";
        /**
         * 支付方式
         */
        public static final String PAY_METHOD = "T_ORDER_INFO_PAY_METHOD";
    }

    /**
     * 图标管理的类型
     */
    public static final class ModuleType {
        //  模块类型
        public static final int MODULE_TYPE_CLIENT_HOME = 1;// 司机首页 1
        public static final int MODULE_TYPE_CLIENT_FIND = 2;//司机发现2
        public static final int MODULE_TYPE_CLIENT_MINE = 3;//司机我的 3
        public static final int MODULE_TYPE_SHOP_HOME = 4;//技师端首页4
        public static final int MODULE_TYPE_SHOP_COMMERCE = 5;//技师端电商5
        public static final int MODULE_TYPE_SHOP_MINE = 6;//技师端我的6

    }

    /**
     * 订单商品的类型
     */
    public static final class OrderType {
        /**
         * 团购
         */
        public static final String ORDER_TYPE_TG = "TG";
        /**
         * 产品
         */
        public static final String ORDER_TYPE_CP = "CP";
        /**
         * 秒杀
         */
        public static final String ORDER_TYPE_MS = "MS";
        /**
         * 促销
         */
        public static final String ORDER_TYPE_CX = "CX";
        /**
         * 活动
         */
        public static final String ORDER_TYPE_HD = "HD";
        /**
         * 工单
         */
        public static final String ORDER_TYPE_GD = "GD";
        /**
         * 预约定金
         */
        public static final String ORDER_TYPE_DJ = "DJ";
        /**
         * 众筹
         */
        public static final String ORDER_TYPE_ZC = "ZC";
        /**
         * 充值
         */
        public static final String ORDER_TYPE_RECHARGE = "CZ";
        /**
         * 积分
         */
        public static final String ORDER_TYPE_JF = "JF";
        /**
         * 优惠劵类型
         **/
        public static final String ORDER_TYPE_YHQ = "YHQ";
    }


    public static final class PrefName {
        /**
         * 保存登录名
         **/
        public static final String PREF_LOGIN_NAME = "login_name";
        /**
         * 保存登录用户信息
         **/
        public static final String PREF_LOGIN_USER_INFO = "login_user_info";
        /**
         * 保存是否登陆过
         **/
        public static final String PREF_IS_LOGIN = "is_login";
        /**
         * 保存车型信息
         **/
        public static final String PREF_CARMODEL = "CarModel";
        /**
         * 工单服务和技师技能
         **/
        public static final String PREF_PROJECTTYPE = "ProjectType";
        /**
         * 保存置顶回话id
         **/
        public static final String PREF_TOP_CHAT = "top_chat_num";
        /**
         * 消息免打扰
         **/
        public static final String PREF_NO_INTERRUPTION = "no_interruption";
        /**
         * 店铺的信息
         */
        public static final String PREF_STORE_INFO = "store_info";
        public static final String PREF_PUSH_MSG = "pref_push_msg";
        public static final String REMINDTYPE = "remindtype";
        public static final String PREF_AUTHORE = "authore";
        public static final String PRE_IS_FIRSTIN = "pre_is_firstin";
        public static final String PREF_HAS_HOME_ICON = "pref_has_home_icon"; // 有无图标
    }

    public static final class IntentKey {
        public static final String SEARCH_MSG_RESULT = "search_msg_result";
        public static final String GROUP_MEMBERS = "group_members";
        public static final String GROUP_NUM = "group_num";
        public static final String GROUP_INFO = "group_info";
        public static final String GROUP_NAME = "group_name";
        public static final String PICK_CONTACT_PAGE_TYPE = "page_type";
        public static final String SEARCH_MSG_TYPE = "search_msg_type";
        public static final String SEARCH_MSG_NUM = "search_msg_num";
        public static final String SEARCH_MSG_INFO = "search_msg_info";
        public static final String AMOUNT = "amount";//订单付款金额
        public static final String PROJECTNUM = "projectnum";//服务工单一级projectNum
        public static final String WORK_ORDER_NUM_STRING = "work_order_num_string";//工单编号，用”,”拼接
        public static final String PROJECTTYPE_INFO = "projecttype_info";//添加服务商品或服务项目
        public static final String PRODUCTINFO_INFO = "productinfo_info";//产品、团购、优惠券
        public static final String WORKORDER_NUM = "workorder_num";//订单编号
        /**
         * GROUP_NAME
         * 传 user
         */
        public static final String USER_INFO = "user_info";
        public static final String VERIFY_PH_ONE_TYPE = "verify_phone_type";
        public static final String PHONE = "phone";
        public static final String UPDATE_TYPE = "update_type";
        public static final String CASE_INFO = "caseInfo";
        public static final String ARTICLE_TYPE = "article_type";
        public static final String ARTICLE_INFO = "article_info";
        public static final String UPDATE_SIGNATURE = "signature";
        public static final String UPDATE_NICKNAME = "update_nickname";

        public static final String MY_CROWD_FUNDING_TYPE = "my_crowd_funding_type";//我的众筹type
        public static final String BOOKING_MAKENUM = "makeNum";//预约编号
        public static final String MY_BOOKING_TYPE = "my_booking_type";
        public static final String INSTEAD_ORDER_TYPE = "instead_order_type";//代下单
        public static final String DISCOUNT_COUPON_TYPE = "discount_coupon_type";
        public static final String ACTIVITY_TYPE = "activity_type";
        public static final String CASE_LIB_INFO = "case_info_lib";
        public static final String CASE_LIB_TYPE = "caselib_type";
        public static final String WEBVIEW_URL = "webview_url";
        public static final String WEBVIEW_TITLE = "webview_title";
        // public static final String PROJECT_TYPE="project_type";
        public static final String TITLE = "title";
        public static final String WEB_URL = "web_url";
        public static final String DESLATITUDE = "desLatitude";
        public static final String DESLONGTITUDE = "desLongtitude";
        public static final String ADDRESS = "address";
        public static final String ACTIVITY_INFO = "activity_info";
        public static final String TOPBAR_TITLE = "topbar_title";

        public static final String ORDER_TYPE = "order_type";

        public static final String SECKILLMAININFONUM = "seckillMainInfoNum";
        public static final String ORDER_INFO = "order_info";
        public static final String GOODS_INFO = "goods_info";
        public static final String ORDER_GOODS_INFO = "order_goods_info";
        public static final String CHOOSE_CARMODEL_TYPE = "choose_carmodel_type";
        public static final String WORKORDER_INFO = "workorder_info";
        public static final String APPOINTMENT_INFO = "appointment_info";
        public static final String USER_NUM = "user_num";
        //public static final String HOME_ICON_LINK_LIST = "home_icon_link";
        public static final String WORK_ORDER_STATUS_STRING = "work_order_status_string";
        public static final String PAY_PAGE_TYPE = "pay_page_type";//支付页的类型
        /**
         * 支付类型  3 商城购物消费 4 支付预约定金消费 5 活动报名消费 6 工单结算消费
         */
        public static final String RECORD_TYPE = "record_type";
        /**
         * 显示活动取消
         */
        public static final String ACTIVITY_CANCELABLE = "activity_cacelable";
        public static final String ACCOUNT_TYPE = "accountType";//账户类型
        public static final String INTEGRAL = "integral";//积分
        public static final String BALANCE = "balance";//余额


        /**
         *
         */
        public static final String THIRD_TYPE = "third_type";
        public static final String THIRD_NUM = "third_num";
        public static final String THIRD_ICON = "third_icon";
        public static final String THIRD_NAME = "third_name";
        public static final String GOODS_NAME = "goods_name";
        public static final String IS_DUIHUAN = "is_duihuan";
        public static final String SUBMIT_EXCHANGE_TYPE = "submit_exchange_type";
        public static final String PROJECT_NUMS = "project_nums";
        public static final String PAY_PASSWORD = "password";
        public static final String NEED_SHARE = "need_share";
        public static final String ACTIVITY_NUM = "activity_num";
        public static final String EDIT_CONTEXT = "edit_context";
        public static final String IS_GOODS_SHARE = "isGoodsMsg";
        public static final String GOODS_JSON = "goodsJson";
        public static final String GOODS_URL = "goodsUrl";
        public static final String WEB_COUNT_TYPE = "web_count_type";
        public static final String JIESUAN_INFO = "jiesuan_info";
        public static final String SEND_TO_FRIEND = "send_to_friend";
        public static final String DISCOUNT_COUPON_LIST = "discount_coupon_list";
        public static final String DISCOUNT_COUPON_SELECT = "discount_coupon_select";
        public static final String PICK_ASSIST_USER = "pick_assist_user";
        public static final String ADDUPDATA = "AddUpData";
        public static final String ACCOUNT_BOOK_INFO = "AccountBookInfo";
        public static final String STAIR_PROJECT_TYPES = "StairprojectTypes";
        public static final String NOTICEINFO = "noticeinfo";
        public static final String SYSTEM_TYPE = "system_type";
        public static final String SHARE_CP_NAME = "share_cp_name";
        public static final String SHARE_CP_IMAGE = "share_cp_image ";
        public static final String SHARE_CP_SUBTITLE = "share_cp_subtitle";
        public static final String DAY_TIME = "day_time";
        public static final String SECKILLINFO = "seckillinfo";
        public static final String ACTIVITY_OPTION = "activity_option";
    }


    public static final class EventKey {
        public static final String KEY_REFRESH_LIST = "refresh_list";
        public static final String EXIT_GROUP = "exit_group";
        public static final String KEY_USER_PROFILE = "user_profile";
        public static final String KEY_USER_SETTING = "user_setting";
        public static final String KEY_USER_APPLY = "user_apply";
        public static final String KEY_CREATE_GROUP = "creat_group";
        public static final String KEY_INVITE_SUCCESS = "invite_member_success";
        public static final String UPDATE_USER_INFO = "update_user_info";//刷新数据
        public static final String DISCOUNT_COUPON_NUMBER = "discount_coupon_number";//优惠券数量
        public static final String DISCOUNT_COUPON_LIST = "discount_coupon_list";//优惠券集合
        public static final String COUNT_PRODUCTINFO = "count_productinfo";//带数量商品
        public static final String COUNT_PRODUCTINFO_LIST = "count_productinfo_list";//带数量商品集合
        public static final String ADD_PROJECT = "add_project";//添加服务大项
        public static final String EXIT = "exit";
        public static final String ACCOUNT_MONEY = "account_money";
        public static final String CLOSE_PAY_ACTIVITY = "close_pay_activity";
        public static final String KEY_USER_SECRETARY = "user_secretary";
        public static final String KEY_SEARCH_CASELIB = "search_case_lib";//搜索案例库
        public static final String WORK_ORDER_REFRESH = "work_order_refresh";
        public static final String DISCOUNT_COUPON_MAP = "discount_coupon_map";
        public static final String KEY_USER_INFO = "key_user_info";
        public static final String REFRESH_DISTRUBUTION = "refresh_distrubution";
        public static final String REFRESH_WORKORDER_LIST = "refresh_workorder_list";
        public static final String WORK_ORDER_LIST_FINISH = "work_order_list_finish";
        public static final String REFRESH_WAIT_AREA = "refresh_wait_area";
        public static final String REMINDTYPE = "remindtype";
        public static final String KEY_GET_USER_INFO = "key_get_user_info";
        public static final String TO_BOOKINR_WATIE = "to_bookinr_watie";
        public static final String UPDATE_WORKER_NUMBER = "update_worker_number";
        public static final String CLEAR_FINISH = "clear_finish";
        public static final String NEW_FRENDS_INVITATION = "new_friends_invitation";
        public static final String NEW_FRENDS_ACCEPT = "new_frends_accept";// 新朋友同意通知
        public static final String KEY_REFRESH_WEB = "key_refresh_web";
        public static final String DEL_ACTIVITY = "del_activity";
    }

    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKgyyVfMMyI+eb4/\n" +
            "qvh13V/mjhbjibxWSC7bsMpsimFQs25xWa/TUg/xxxtOUvE+kpfcGrU4dnqGSb1C" +
            "gKOBBMZFB9l/knICNvDIaUCGKgXhIEeCoMNrriSLvgqLYNWDSLxlG78CX0U0DPUR" +
            "tAnYKdyiM0GEYdiU3AWtawLoJvXXAgMBAAECgYEAj0cr8ol1KX+Uz3aG2r30hSPt" +
            "+Bt+EhxhwuDL+bN6ManAH52ZK8jUx9ZKqL0QtIZ787bAXY0qh/bqy7jhweY+vJDU" +
            "z+P4TWlKi0LQMPdiiJAQch5XYd0XT8wNG1oYQoV/kpDBawdoes6i1XXyBbJtncSv" +
            "uNXX60p9DgYGq4r0RNkCQQDZpKbWHMK9IlQuPYLbOW3Jen9Nhe/cdQlhpaybeK0c" +
            "c6J/8bQSP7nDTdldIx733o/rGwh0CwetBhsasaxIdNcbAkEAxddZAyTVkz8sNSlx" +
            "y+KRJ/2YaarLd+LcWpfGnb1HmT3O7tXX0GEgextlv/yQ4uk8Xup9BjqgtYoKa3Mj" +
            "tQPb9QJASC1Ift1O37ZQwHz5toYXGKgdK7JtV2kg0VdGJNEiZ7sZeuDHXP3xNwnM" +
            "Ogz5qi4PAGMYrJMz2neZ6YJepjDJXQJANOOM+DownlyVBOE/erHm/DnfNOBj5HjR" +
            "T6ajgifHrD/Jwi+IsjxE4qRAX0K1uPOBIB6vraqeq+lrP65YLVwKwQJBAINArjZB" +
            "o4OxzIVgV817VqXI+C54RRW7hQI4twDV2uSnSPUj4GM97quBcme1+bZKEIlx3rWG" +
            "73Y2GreAtQrCPp4=";


    public static final class PayType {

        /**
         * 支付宝    S
         */
        // 商户PID
        public static final String PARTNER = "2016092701982262";

        public static final String NOTIFY_URL = ApiManager.API_URL + "app/pay/aliyunReturn";

        // 商户收款账号
        public static final String SELLER = PARTNER;

        // 商户的私钥
        public static final String RSA_PRIVATE =
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL0LrH1QrUn0TOyAPgABJKg7VMhOYv0XgiwgR2cz0i/jHTrZ9lup95Nxyr0vZEQc7JNgbRALmBYh/eCo16a80rjI4DZey6KEtDTdLX+ex9ff585J7qmqsnmRYzrG0eMXyVnGoS7yDe7+2ejaxaRH60WRFMh8ORB4fe5M0sZDJ6lBAgMBAAECgYEAl/epanePRhMAP2H79S72E9LWHct9oazjNKuiGqE6xsoQtj1+4ELqxXlFG/3b4TFLMFHkenCHiss6df3klaHW1dXDT5aBuxSVUB9Ilm/3oEETF/BtEXE8zV/0BBMCV8HBPuEPEwtU2II7Vl72B/toFv9qolnsReRTMA4df3Cb120CQQD7F6Ti2vvY8mWkiohD8GARV0+OsoLpfAcTvKa0uEGZxPGQVESpJuICRVWspHY/p51dgYTnzt/YQuqLQMQJePMXAkEAwL2TMMzEed687tcs5q+QD5WbXooyTICBDiEDEgttqbnm5W2aVWGy9Dug1BD2+syHzqZ+QKSjT6JzpNDCJVXdZwJANSD0n5esUGRBw4YYfR+ghCrQR6ZX8uVubeut7nFKWCoG3v4lpRD4I3jePvBxoNhrgLyGDbKFRLIsBJkIoeR4swJAOwTR4YuSTk8qQ2iYUztHPb5uCREAPfTLj+wD6K6fMe2/Cln5fFOiXK81nDplpn6ix2CsIFdJusTf7IQQLyaP2QJBAOIeZsIvf0mGxsundtV69ofgqGvTmrKYdkjXlVlYM9Vmb8jZnVPaFcf5Eez+5IIpNG793vWGz1dzKRAThPYzyT0=";

        // 支付宝的公钥，无需修改该值
        public static final String RSA_PUBLIC =
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
        /**支付宝   E   */

        /**
         * 微信支付  S
         */
        // APP_ID 替换为你的应用从官方网站申请到的合法appId
        public static final String APP_ID = "wxf70e8e90de438680";
        //     商户号
        public static final String PARTNER_ID = "1395545802";
        /** 微信支付 E */


        /** 支付方式   */
        /**
         * 余额支付
         */
        public static final int PAYTYPE_BALANCE = 0;
        /**
         * 支付宝支付
         */
        public static final int PAYTYPE_ALIPAY = 1;
        /**
         * 微信支付
         */
        public static final int PAYTYPE_WECHAT = 2;
    }


    public static final String USETYPE_CUSTOMER = "31";//客户
    public static final String USETYPE_WORKMATE = "2";//同事
    public static final String USETYPE_DRIVER = "3";//司机

    public static final String WORKSTATE_NOT_BUSY = "GZZ_1";//工作中-空闲
    public static final String WORKSTATE_BUSY = "GZZ_2";//工作中-繁忙
    public static final String WORKSTATE_REST = "XXZ_1";//休息中

    public static final int USER_SEX_UNKNOW = 0;//未知
    public static final int USER_SEX_MAN = 1;//男
    public static final int USER_SEX_FEMALE = 2;//女

    /**
     * 扫码的授权序列号
     */
    public static final String SN = "UR265PUNJVVYPW1YYT32YYH3R";//

    /**
     * 第三方登录的类型
     */
    public class ThirdType {

        public static final int TYPE_QQ = 1;
        public static final int TYPE_WECHAT = 2;
        public static final int TYPE_SINA = 3;
    }


    /**
     * 提成的类型
     */
    public class CountType {

        /**
         * 施工提成
         */
        public static final String EXECUTION = "2";
        /**
         * 销售提成
         */
        public static final String MARKET = "1";
        /**
         * 用户数
         */
        public static final String NUMBER = "3";
    }
}


