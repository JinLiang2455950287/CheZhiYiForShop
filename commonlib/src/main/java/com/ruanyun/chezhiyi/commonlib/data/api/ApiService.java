package com.ruanyun.chezhiyi.commonlib.data.api;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.*;
import com.ruanyun.chezhiyi.commonlib.model.params.*;
import com.ruanyun.chezhiyi.commonlib.model.AccountBookInfo;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.model.AdverListInfo;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.ArticleInfo;
import com.ruanyun.chezhiyi.commonlib.model.FeedBackInfo;
import com.ruanyun.chezhiyi.commonlib.model.ParentCodeInfo;

import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;
import com.ruanyun.chezhiyi.commonlib.model.CarInfo;
import com.ruanyun.chezhiyi.commonlib.model.CarModel;
import com.ruanyun.chezhiyi.commonlib.model.PraiseBase;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.PromotionInfo;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;
import com.ruanyun.chezhiyi.commonlib.model.StoreInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.UserLicenseInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.ActivityListParams;
import com.ruanyun.chezhiyi.commonlib.model.params.AccountBookParams;
import com.ruanyun.chezhiyi.commonlib.model.params.AddCommentParams;
import com.ruanyun.chezhiyi.commonlib.model.params.AddOrPraiseParams;
import com.ruanyun.chezhiyi.commonlib.model.params.ArticleListParams;
import com.ruanyun.chezhiyi.commonlib.model.params.FeedBackParams;
import com.ruanyun.chezhiyi.commonlib.model.params.GetCaseParams;
import com.ruanyun.chezhiyi.commonlib.model.params.LoginParams;
import com.ruanyun.chezhiyi.commonlib.model.params.MakeAppointmentParams;
import com.ruanyun.chezhiyi.commonlib.model.params.PageParamsBase;

import com.ruanyun.chezhiyi.commonlib.model.params.RecommentParams;
import com.ruanyun.chezhiyi.commonlib.model.params.RegisterParams;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Description:
 * author: zhangsan on 16/7/19 下午2:32.
 */
public interface ApiService {
    /**
     * 1.8.2	获取公共字典表
     **/
    @POST(C.ApiUrl.URL_GET_PARENTCODE)
    Call<ResultBase<List<ParentCodeInfo>>> getParentCodeInfo();

    /**
     * 1.8.3	获取需要缓存的版本号
     **/
    @POST(C.ApiUrl.URL_GET_PARENTCODE_VERSION)
    Call<ResultBase<List<ParentCodeInfo>>> getParentCodeVersion();

    /**
     * 1.7.2	用户登陆
     **/
    @POST(C.ApiUrl.URL_LOGIN)
    Call<ResultBase<User>> userLogin(@Path("loginName") String loginName, @Body LoginParams params);

    /**
     * 1.7.3	忘记密码
     **/
    @POST(C.ApiUrl.URL_FORGET_PASSWORD)
    Call<ResultBase<User>> forgetPassword(@Path("loginName") String loginName, @Body LoginParams params);

    /**
     * 1.7.4	注册用户
     **/
    @POST(C.ApiUrl.URL_REGISTER)
    Call<ResultBase> userRegister(@Path("loginName") String loginName, @Body RegisterParams params);

    /**
     * 1.8.6 获取车型信息
     **/
    @POST(C.ApiUrl.URL_GET_CAR_MODEL)
    Call<ResultBase<List<CarModel>>> getCarModels();

    /**
     * 1.8.7	获取工单服务或技师技能
     **/
    @POST(C.ApiUrl.URL_GET_PROJECT_TYPE)
    Call<ResultBase<List<ProjectType>>> getProjectType();

    /**
     * 获取驾驶证信息
     **/
    @POST(C.ApiUrl.URL_GET_USERLICENSE)
    Call<ResultBase<UserLicenseInfo>> getUserLicense(@Path("userNum") String userNum);

    /**
     * 添加或修改驾驶证信息
     *
     * @param userNum
     * @param bodyMap realName	            String	真实姓名【修改必须传值 不修改可以原值传入】
     *                licenseNum	        String	驾驶证号【修改必须传值 不修改可以原值传入】
     *                effectiveStartTime	String	有效开始时间格式为yyyy-MM-dd【修改必须传值 不修改可以原值传入】
     *                effectiveEndTime	    String	有效结束时间格式为yyyy-MM-dd【修改必须传值 不修改可以原值传入】
     *                cclzTime	            String	初次领证时间格式为yyyy-MM-dd【修改必须传值 不修改可以原值传入】
     *                address	            String	地址 【修改必须传值 不修改可以原值传入】
     *                obverseSidePhoto	    File	驾驶证正面照片 文件流【不修改为空】
     *                otherSidePhoto	    File	驾驶证反面照片文件流 【不修改为空】
     * @return
     */
    @Multipart
    @POST(C.ApiUrl.URL_UPDATE_USERLICENSE)
    Call<ResultBase<UserLicenseInfo>> updateOrSaveUserLicense(@Path("userNum") String userNum, @PartMap Map<String, RequestBody> bodyMap);

    /**
     * 获取用户车辆信息
     **/
    @POST(C.ApiUrl.URL_GET_CARINFO_LIST)
    Call<ResultBase<List<CarInfo>>> getCarinfoList(@Path("userNum") String userNum);

    /**
     * 查看预约列表
     **/
    @POST(C.ApiUrl.URL_GET_YUYUE_LIST)
    Call<ResultBase<List<YuYueItemBean>>> getYuYueList(@Path("userNum") String userNum);

    /**
     * 主页面权限表
     */
    @POST(C.ApiUrl.URL_GET_PERSSION)
    Call<ResultBase<List<PerssionBean>>> getPerssion(@Path("userNum") String userNum);

    /**
     * 获取客户余额
     */
    @POST(C.ApiUrl.URL_GET_CUSTOMERYUE)
    Call<ResultBase<CustomerAccountModel>> getCusomerYue(@Path("userNum") String userNum);


    /**
     * 主页面等候区数量
     */
    @POST(C.ApiUrl.URL_GET_WAITAREA_COUNT)
    Call<ResultBase> getWaitAreaCount(@Path("userNum") String userNum, @Query("status") String status);

    /**
     * 预约处理
     */
    @POST(C.ApiUrl.URL_GET_YUYUE_DEAL)
    Call<ResultBase> getYuYueDeal(@Path("userNum") String userNum, @Query("isDownPayment") int isDownPayment, @Query("makeNum") String makeNum, @Body YuYueDealListParams project);

    /**
     * 技师端查看退款信息，当前列表为退款中信息
     **/
    @POST(C.ApiUrl.URL_GET_TUIKUANG_LIST)
    Call<ResultBase<List<TuiKuanInfo>>> getTuiKuanList(@Path("userNum") String userNum);

    /**
     * 添加或修改车辆信息
     **/
    @Multipart
    @POST(C.ApiUrl.URL_UPDATE_OR_SAVE_CARINFO)
    Call<ResultBase<List<CarInfo>>> updateOrSaveCarInfo(@Path("userNum") String userNum, @PartMap HashMap<String, RequestBody> map);

    /**
     * 获取车辆详细信息
     **/
    @FormUrlEncoded
    @POST(C.ApiUrl.URL_GET_CARINFO)
    Call<ResultBase<List<CarInfo>>> getCarInfo(@Path("userNum") String userNum, @Field("carNum") String carNum);

    /**
     * 删除车辆信息
     **/
    @FormUrlEncoded
    @POST(C.ApiUrl.URL_DELETE_CARINFO)
    Call<ResultBase> deleteCarInfo(@Path("userNum") String userNum, @Field("carNum") String carNum);

    /**
     * 修改个人信息
     */
    @Multipart
    @POST(C.ApiUrl.URL_UPDATE_PERSONAL_INFO)
    Call<ResultBase<User>> updatePersonalInfo(@Path("userNum") String userNum, @PartMap HashMap<String, RequestBody> bodyMap);

    /**
     * 1.12.2文章列表
     */
    @POST(C.ApiUrl.URL_ARCLE_LIST)
    Call<ResultBase<PageInfoBase<ArticleInfo>>> articleList(@Path("userNum") String userNum, @Body ArticleListParams params);

    /**
     * 查看会员快查列表
     **/
    @POST(C.ApiUrl.URL_GET_HUYUAN_LIST)
    Call<ResultBase<List<HuiYuanKuaiChaInfo>>> getHuiYuanList(@Path("userNum") String userNum, @Body MemberParams params);

    /**
     * 1.11.2获取门店详细
     */
    @FormUrlEncoded
    @POST(C.ApiUrl.URL_STORE_INFO)
    Call<ResultBase<StoreInfo>> storeInfo(@Path("userNum") String userNum, @Field("storeNum") String storeNum);

    /**
     * 1.12.2获取案例信息
     **/
    @POST(C.ApiUrl.URL_GET_CASE)
    Call<ResultBase<PageInfoBase<CaseInfo>>> getCaseInfo(@Path("userNum") String userNum, @Body GetCaseParams getCaseParams);

    /**
     * 1.12.3获取添加或修改案例信息
     *
     * @param userNum
     * @param bodyMap libraryId	Int	主键【修改时需要传值】
     *                libraryNum	String	案例编码【业务主键，修改时需要传值】
     *                libraryName	String	案例名称【不变动时，需要把原值传回后台】
     *                libraryIntroduce	String	案例介绍【不变动时，需要把原值传回后台】
     *                libraryType	String	案例库分类【读取服务类型一级】
     *                delAttachInfoId	Int【数组】	附件ID 附件数据结构（TAttachInfo） 表中的attachId【修改时用】
     *                attachInfoPic	File【图片流，多张图片】	案例图片
     *                storeNum	String	门店num【暂无使用】
     **/
    @Multipart
    @POST(C.ApiUrl.URL_CASE_ADD_UPDATE)
    Call<ResultBase> getCaseAddUpdateInfo(@Path("userNum") String userNum, @PartMap HashMap<String, RequestBody> bodyMap);

    /**
     * 1.10.9	获取所有图片信息
     */
    @POST(C.ApiUrl.URL_ICON_INFO)
    Call<ResultBase<List<HomeIconInfo>>> getHomeIcon();

    /**
     * 添加评论
     */
    @POST(C.ApiUrl.URL_ADD_COMMENT)
    Call<ResultBase> addComment(@Path("userNum") String userNum, @Body AddCommentParams params);

    /**
     * 点赞或者取消点赞
     */
    @POST(C.ApiUrl.URL_ADD_OR_DELETE_PRAISE)
    Call<ResultBase> addOrDeletePraise(@Path("userNum") String userNum, @Body AddOrPraiseParams params);


    /**
     * 1.16.2添加反馈
     */
    @POST(C.ApiUrl.URL_FEED_BACK)
    Call<ResultBase<FeedBackInfo>> feedBackInfo(@Path("userNum") String userNum, @Body FeedBackParams feedBackParams);

    /**
     * 我的活动列表
     */
    @POST(C.ApiUrl.URL_MY_ACTIVITY_LIST)
    Call<ResultBase<PageInfoBase<ActivityInfo>>> myActivityListInfo(@Path("userNum") String userNum, @Body ActivityListParams activityListParams);


    /**
     * 活动列表
     */
    @POST(C.ApiUrl.URL_ACTIVITY_LIST)
    Call<ResultBase<List<ActivityInfo>>> activityListInfo(@Path("userNum") String userNum);


    /**
     * 广告列表
     */
    @POST(C.ApiUrl.URL_ADVER_LIST)
    Call<ResultBase<PageInfoBase<AdverListInfo>>> adverListInfo(@Path("userNum") String userNUm);

    /**
     * 获取是否已点赞
     **/
    @POST(C.ApiUrl.URL_IS_PRAISED)
    Call<ResultBase<PraiseBase>> isPraised(@Path("userNum") String userNum, @Body AddOrPraiseParams params);

    /**
     * 获取记账本列表
     */
    @POST(C.ApiUrl.URL_GET_ACCOUNT_BOOK_LIST)
    Call<ResultBase<PageInfoBase<AccountBookInfo>>> getAccountBookList(@Path("userNum") String userNum, @Body AccountBookParams params);

    /**
     * 获取记账本当月总金额
     */
    @POST(C.ApiUrl.URL_GET_ACCOUNT_BOOK_MONEY_AMOUNT)
    Call<ResultBase<BigDecimal>> getAccountBookMoneyAmount(@Path("userNum") String userNum, @Body AccountBookParams params);

    /**
     * 添加或修改记账本
     */
    @Multipart
    @POST(C.ApiUrl.URL_ADD_UPDATA_ACCOUNT_BOOK)
    Call<ResultBase<PageInfoBase<AccountBookInfo>>> addUpdataAccountBook(@Path("userNum") String userNum, @PartMap HashMap<String, RequestBody> bodyMap);

    /**
     * 删除记账本
     */
    @POST(C.ApiUrl.URL_DELETE_ACCOUNT_BOOK)
    Call<ResultBase> deleteAccountBook(@Path("userNum") String userNum, @Query("bookNum") String bookNum);

    /**
     * 产品、团购列表
     */
    @POST(C.ApiUrl.URL_PRODUCT_LIST)
    Call<ResultBase<PageInfoBase<ProductInfo>>> getProductList(@Path("userNum") String userNum, @Body ProductGroupPurchaseParams params);

    /**
     * 卡套餐列表
     */
    @POST(C.ApiUrl.URL_CARDPACKEG_LIST)
    Call<ResultBase<List<CardPackageListModel>>> getCardPackegList(@Path("userNum") String userNum);
//    Call<ResultBase>  getCardPackegList(@Path("userNum") String userNum);

    /**
     * 卡套餐详情
     */
    @POST(C.ApiUrl.URL_CARDPACKEG_INFO)
    Call<ResultBase<CardPackageDetailInfo>> getCardPackegInfo(@Path("userNum") String userNum, @Query("packageNum") String packageNum);

    /**
     * 施工/销售 提成
     */
    @POST(C.ApiUrl.URL_TICHENG_INFO)
    Call<TiChengInfoModel> getTiChengInfo(@Path("userNum") String userNum, @Query("month") String month, @Query("type") int type);

    /**
     * 施工/销售list 提成
     */
    @POST(C.ApiUrl.URL_TICHENG_LIST)
    Call<ResultBase<List<TiChengListModel>>> getTiChengList(@Path("userNum") String userNum, @Query("pageNum") int pageNum, @Query("commissionType") int commissionType, @Query("month") String month);

    /**
     * 施工/销售详情 提成
     */
    @POST(C.ApiUrl.URL_TICHENG_INFO)
    Call<ResultBase> getTiChengDetailList(@Path("userNum") String userNum, @Query("month") String month, @Query("type") int type);

    /**
     * 门店统计 会员统计
     */
    @POST(C.ApiUrl.URL_MENGDIAN_HUIYUAN)
    Call<ResultBase<MenDianHuiYuanInfo>> getMenDianHuiYuan(@Path("userNum") String userNum, @Query("startDate") String startDate, @Query("reportDate") String reportDate);

    /**
     * 门店统计 工单统计
     */
    @POST(C.ApiUrl.URL_MENGDIAN_GONGDAN)
    Call<ResultBase<MenDianGongDanInfo>> getMenDianGongDan(@Path("userNum") String userNum, @Query("startDate") String startDate, @Query("reportDate") String reportDate);

    /**
     * 门店统计 服务Detail商品
     */
    @POST(C.ApiUrl.URL_MENGDIAN_GONGDANDETAIL)
    Call<ResultBase<MenDianGongDanDetailInfo>> getGongDanDetailInfo(@Path("userNum") String userNum, @Query("startDate") String startDate, @Query("reportDate") String reportDate);


    /**
     * 门店统计 营业额统计
     */
    @POST(C.ApiUrl.URL_MENGDIAN_YINYEE)
    Call<ResultBase<MenDianYinYeEInfo>> getMenDianYinYeE(@Path("userNum") String userNum, @Query("startDate") String startDate, @Query("reportDate") String reportDate, @Query("appType") int appType);


    /**
     * 门店统计 服务商品
     */
    @POST(C.ApiUrl.URL_MENGDIAN_SERVICEGOODS)
    Call<ResultBase<MenDianServiceGoodsInfo>> getServiceGoods(@Path("userNum") String userNum, @Query("startDate") String startDate, @Query("reportDate") String reportDate);


    /**
     * 限时促销列表
     */
    @POST(C.ApiUrl.URL_PROMOTION_LIST)
    Call<ResultBase<PageInfoBase<PromotionInfo>>> getPromotionList(@Path("userNum") String userNum, @Body GetPromotionParams params);

    /**
     * 众筹列表
     */
    @POST(C.ApiUrl.URL_CROWD_FUNDING_LIST)
    Call<ResultBase<PageInfoBase<CrowdFundingInfo>>> getCrowdFundingList(@Path("userNum") String userNum, @Body CrowdFundingParams params);

    /**
     * 秒杀头部日期
     */
    @POST(C.ApiUrl.URL_GET_SECKILL_HEAD_INFO)
    Call<ResultBase<List<SeckillHeadInfo>>> getSeckillHeadInfo(@Path("userNum") String userNum);

    /**
     * 秒杀产品信息列表
     */
    @POST(C.ApiUrl.URL_GET_SECKILL_DETAIL_INFO)
    Call<ResultBase<PageInfoBase<SeckillDetailInfo>>> getSeckillInfo(@Path("userNum") String userNum, @Body SeckillDetaillParams params);

    /**
     * 获取预约列表
     */
    @POST(C.ApiUrl.URL_GET_BOOKING_LIST)
    Call<ResultBase<PageInfoBase<AppointmentInfo>>> getBookingList(@Path("userNum") String userNum, @Body BookingListParams params);

    /**
     * 根据预约编号获取预约详细
     */
    @POST(C.ApiUrl.URL_GET_BOOKING_INFO)
    Call<ResultBase<AppointmentInfo>> getBookingInfo(@Path("userNum") String userNum, @Query("makeNum") String makeNum);

    /**
     * 获取优惠券(司机端)
     */
    @POST(C.ApiUrl.URL_GET_DISCOUNT_COUPON)
    Call<ResultBase<List<OrderGoodsInfo>>> getClientDiscountCoupon(@Path("userNum") String userNum, @Body DiscountCouponParams params);

    /**
     * 司机工单结算有效优惠券(司机端)
     */
    @POST(C.ApiUrl.URL_GET_WORKORDER_COUPON)
    Call<ResultBase<List<OrderGoodsInfo>>> getClientWorkOrderCoupon(@Path("userNum") String userNum, @Query("workOrderNumString") String workOrderNumString);

    /**
     * 获取优惠券(技师端)
     */
    @POST(C.ApiUrl.URL_GET_TECHNICIAN_DISCOUNT_COUPON)
    Call<ResultBase<List<ProductInfo>>> getTechnicianDiscountCoupon(@Path("userNum") String userNum, @Query("type") String type);

    /**
     * 赠送优惠券(技师端)
     */
    @POST(C.ApiUrl.URL_SEND_DISCOUNT_COUPON)
    Call<ResultBase> sendDiscountCoupon(@Path("userNum") String userNum, @Body SendDiscountCouponParams params);

    /**
     * 1.17.2	推荐项目和猜你喜欢
     *
     * @param userNum
     * @param recommentProjectType Int	1推荐项目   2猜你喜欢
     * @return
     */
    @POST(C.ApiUrl.URL_RECOMMEND_LIST)
    Call<ResultBase<PageInfoBase<RecommendInfo>>> getRecommendList(@Path("userNum") String userNum, @Body RecommentParams recommentProjectType);

    /**
     * 1.14.2	添加预约信息
     */
    @POST(C.ApiUrl.URL_MAKE_APPOINTMENT)
    Call<ResultBase<AppointmentInfo>> makeAppointment(@Path("userNum") String userNum, @Body MakeAppointmentParams promotionParams);

    /**
     * 常用电话列表
     */
    @POST(C.ApiUrl.URL_COMMENT_USER_TELEPHONE)
    Call<ResultBase<PageInfoBase<CommentUserTelephoneInfo>>> getCommentUserTelephoneList(@Path("userNum") String userNum, @Body PageParamsBase pageParamsBase);


    /**
     * 1.14.2	报名
     */
    @POST(C.ApiUrl.URL_SIGN_ADD)
    Call<ResultBase<SignAddInfo>> signAdd(@Path("userNum") String userNum, @Body SignAddParams signAddParams);

    /**
     * 获取我的订单
     **/
    @POST(C.ApiUrl.URL_OREDR_LIST)
    Call<ResultBase<PageInfoBase<OrderInfo>>> orderList(@Path("userNum") String userNum, @Body OrderListParams orderListParams);

    /**
     * 秒杀详情
     **/
    @POST(C.ApiUrl.URL_SECKILL_DETAIL_INFO)
    Call<ResultBase<SeckillDetailInfo>> seckillDetailInfo(@Path("userNum") String userNum, @Query("seckillDetailNum") String seckillDetailNum);

    /**
     * 众筹详情
     **/
    @POST(C.ApiUrl.URL_CROWD_DETAIL_info)
    Call<ResultBase<CrowdFundingInfo>> crowdFundingInfo(@Path("userNum") String userNum, @Query("crowdNum") String crowdNum);

    /**
     * 促销详情promotion
     */

    @POST(C.ApiUrl.URL_PROMOTION_DETAIL_INFO)
    Call<ResultBase<PromotionInfo>> promotionDetailInfo(@Path("userNum") String userNum, @Query("promotionInfoNum") String promotionInfoNum);

    /**
     * 产品详情
     **/
    @POST(C.ApiUrl.URL_GOODS_DETAILS_INFO)
    Call<ResultBase<ProductInfo>> productDetailInfo(@Path("userNum") String userNum, @Query("goodsNum") String goodsNum);

    /**
     * 下单
     **/
    @POST(C.ApiUrl.URL_MAKE_OREDR)
    Call<ResultBase<OrderInfo>> makeOrder(@Path("userNum") String userNum, @Body MakeOrderParams makeOrderParams);

    /**
     * 获取订单退款原因
     **/
    @POST(C.ApiUrl.URL_TUIKUAN_INFO)
    Call<ResultBase<List<RefundInfo>>> tuikuanList(@Path("userNum") String userNum, @Query("orderNum") String orderNum);

    /**
     * 获取验证码
     **/
    @POST(C.ApiUrl.URL_GET_VERIFY)
    Call<ResultBase> getVerifyCody(@Path("loginName") String loginName, @Body VerifyCodyParams params);


    /**
     * 1.12.4	删除案例
     **/
    @POST(C.ApiUrl.URL_DELETE_CASELIB)
    Call<ResultBase> deleteCaselib(@Path("userNum") String userNum, @Query("libraryNum") String libraryNum);

    /**
     * 申请退款
     **/
    @POST(C.ApiUrl.URL_REFUND_APPLICATION)
    Call<ResultBase> refundApplication(@Path("userNum") String userNum, @Body RefudApplicationParams params);

    /**
     * 司机端或技师端获取我的工单 或者等候区的工单 或者 代下单工单
     **/
    @POST(C.ApiUrl.URL_GET_MY_WORKORDER_LIST)
    Call<ResultBase<PageInfoBase<WorkOrderInfo>>> getMyWorkOrderList(@Path("userNum") String userNum, @Body MyWorkOrderParams params);

    /**
     * 扫描车牌获取预约信息
     **/
    @POST(C.ApiUrl.URL_SCAN_LICENSE_GET_BOOKING_INFO)
    Call<ResultBase<CarBookingInfo>> scanLicenseGetBookingInfo(@Path("userNum") String userNum, @Query("carAllName") String carAllName);

    /**
     * 获取空闲工位
     **/
    @POST(C.ApiUrl.URL_GET_WORKORDER_GONGWEI)
    Call<ResultBase<List<WorkBayInfo>>> getWorkOrderGongWei(@Path("userNum") String userNum, @Query("projectNum") String projectNum);

    /**
     * 获取工单商品
     **/
    @POST(C.ApiUrl.URL_GET_WORKORDER_GOODS_LIST)
    Call<ResultBase<List<OrderGoodsInfo>>> getWorkorderGoodsList(@Path("userNum") String userNum, @Query("workOrderNum") String workOrderNum);

    /**
     * 获取空闲技师
     **/
    @POST(C.ApiUrl.URL_GET_LEISURE_TECHNICIAN)
    Call<ResultBase<List<User>>> getLeisureTechnician(@Path("userNum") String userNum, @Query("projectNum") String projectNum);

    /**
     * 获取已购并未消费的服务
     **/
    @POST(C.ApiUrl.URL_GET_NOT_SPENDING_SERVICE)
    Call<ResultBase<List<OrderGoodsInfo>>> getNotSpendingService(@Path("userNum") String userNum, @Body NotSpendingServiceParams params);

    /**
     * 提交接待工单(resultJosnString为json格式)
     **/
    @FormUrlEncoded
    @POST(C.ApiUrl.URL_SAVE_RECEPTION_WORKORDER)
    Call<ResultBase> saveReceptionWorkorder(@Path("userNum") String userNum, @Field("resultJosnString") String resultJosnString);

    /**
     * 工单详情
     **/
    @POST(C.ApiUrl.URL_GET_WORKORDER_INFO)
    Call<ResultBase<WorkOrderInfo>> getWorkorderInfo(@Path("userNum") String userNum, @Query("workOrderNum") String workOrderNum);

    /**
     * 1.29.1获取充值套餐
     **/
    @POST(C.ApiUrl.URL_RECHARGE_LIST)
    Call<ResultBase<PageInfoBase<RechargeListInfo>>> rechargeList(@Path("userNum") String userNum, @Body RechargeListParams params);

    /**
     * 1.29.2进行充值
     **/
    @POST(C.ApiUrl.URL_RECHARGE)
    Call<ResultBase<RechangeInfo>> rechanger(@Path("userNum") String userNum, @Body RechangeParams params);

    /**
     * 查看工位
     **/
    @POST(C.ApiUrl.URL_GET_STATION_LIST)
    Call<ResultBase<List<WorkBayInfo>>> getStationList(@Path("userNum") String userNum);

    /**
     * 等候区详情接单
     **/
    @POST(C.ApiUrl.URL_SAVE_ORDER_RECEIVING)
    Call<ResultBase> saveOrderReceiving(@Path("userNum") String userNum, @Body OrderReceivingParams params);

    /**
     * 取消预约
     **/
    @POST(C.ApiUrl.URL_CANCEL_MAKE)
    Call<ResultBase> cancelMake(@Path("userNum") String userNum, @Body CancelMakeParams params);

    /**
     * 1.16.2	获取通知通告
     **/
    @POST(C.ApiUrl.URL_NOTICE_INFO)
    Call<ResultBase<NoticeInfo>> getNoticeInfo(@Path("userNum") String userNum);

    /**
     * 1.16.3	获取通知通告(新)
     **/
    @POST(C.ApiUrl.URL_NOTICE_INFO_NEW)
    Call<ResultBase<List<GongGaoInfo>>> getGongGaoInfo(@Path("userNum") String userNum);


    /**
     * 获取今日预约数量
     **/
    @POST(C.ApiUrl.URL_APPOINTMENT_COUNT)
    Call<ResultBase> getAppointMentCount(@Path("userNum") String userNum);

    /**
     * 退款申请数量
     **/
    @POST(C.ApiUrl.URL_REPAY_COUNT)
    Call<ResultBase> getRePayCount(@Path("userNum") String userNum);

    /**
     * 退款审核
     **/
    @POST(C.ApiUrl.URL_REPAY_EXAMINE)
    Call<ResultBase> getRePayExanine(@Path("userNum") String userNum, @Query("refundNum") String refundNum, @Query("status") int status, @Query("remark") String remark);

    /**
     * 代下单(添加商品)
     **/
    @POST(C.ApiUrl.URL_INSTEAD_ORDER)
    Call<ResultBase> insteadOrder(@Path("userNum") String userNum, @Body InsteadOrderParams params);

    /**
     * 1.8.3用户第三方登陆
     **/
    @POST(C.ApiUrl.URL_LOGIN_THIRD)
    Call<ResultBase<User>> loginThird(@Path("loginName") String loginName, @Query("thirdType") int thirdType);

    /**
     * 1.8.6	第三方绑定或注册用户
     **/
    @Multipart
    @POST(C.ApiUrl.URL_ADD_THIRD)
    Call<ResultBase<User>> addThird(@Path("loginName") String loginName, @PartMap HashMap<String, RequestBody> map);

    /**
     * 1.9.5	第三方登陆解绑
     **/
    @POST(C.ApiUrl.URL_DEL_THIRD)
    Call<ResultBase> delThird(@Path("userNum") String userNum, @Query("thirdType") int thirdType);

    /**
     * 1.9.6	获取已绑定的第三方登陆
     **/
    @POST(C.ApiUrl.URL_THIRD_LIST)
    Call<ResultBase<List<ThirdInfo>>> listThird(@Path("userNum") String userNum);

    /**
     * 1.31.3	账号支付
     **/
    @POST(C.ApiUrl.URL_ACCOUNT_PAY)
    Call<ResultBase> accountPay(@Path("userNum") String userNum, @Body AccountParams accountParams);

    /**
     * 取消订单【修改订单状态】
     **/
    @POST(C.ApiUrl.URL_ORDER_STATUS_UPDATE)
    Call<ResultBase> updateOrderStatus(@Path("userNum") String userNum, @Body UpOrderStatusParams statusParams);

    /**
     * 获取用户账号信息
     **/
    @POST(C.ApiUrl.URL_USER_ACCOUNT_INFO)
    Call<ResultBase<AccountMoneyInfo>> getUserAccountInfo(@Path("userNum") String userNum);

    /**
     * 根据活动编号用户编号获取报名信息
     **/
    @POST(C.ApiUrl.URL_ACTIVITY_INFO)
    Call<ResultBase<ActivitySignInfo>> getActivityInfo(@Path("userNum") String userNum, @Query("activityNum") String activityNum);

    /**
     * 添加协助技师
     **/
    @POST(C.ApiUrl.URL_ADD_ASSIST)
    Call<ResultBase> addAssist(@Path("userNum") String userNum, @Body AddAssistParams params);

    /**
     * 获取工单协助技师
     **/
    @POST(C.ApiUrl.URL_GET_ASSIST_LIST)
    Call<ResultBase<List<AssistUserInfo>>> getAssistList(@Path("userNum") String userNum, @Query("workOrderNum") String workOrderNum);

    /**
     * 取消报名
     **/
    @POST(C.ApiUrl.URL_DEL_ACTIVITY)
    Call<ResultBase> delActivity(@Path("userNum") String userNum, @Query("activityNum") String activityNum);


    /**
     * 获取流水记录
     **/
    @POST(C.ApiUrl.URL_RECORD)
    Call<ResultBase<PageInfoBase<RecordListInfo>>> recordListinfo(@Path("userNum") String userNum, @Body RecordListParams params);

    /**
     * 1.13.6	编辑案列
     **/
    @POST(C.ApiUrl.URL_CASE_DETAIL_INFO)
    Call<ResultBase<CaseInfo>> getCaseInfoDetailByNum(@Path("userNum") String userNum, @Query("libraryNum") String libraryNum);

    /**
     * 订单评价
     **/
    @POST(C.ApiUrl.URL_ADD_ORDER_COMMENT)
    Call<ResultBase> addOrderComment(@Path("userNum") String userNum, @Body AddOrderCommentParams params);

    /**
     * 1.32.1	金额+积分兑换
     */
    @POST(C.ApiUrl.URL_ADD_ORDER)
    Call<ResultBase<OrderInfo>> exchangeWithMoney(@Path("userNum") String userNum, @Body ExchangeParams params);

    /**
     * 1.32.2	纯积分兑换
     */
    @POST(C.ApiUrl.URL_ADD_SCORE_ORDER)
    Call<ResultBase<OrderInfo>> exchangeOnlyScore(@Path("userNum") String userNum, @Body ExchangeParams params);


    /**
     * 1.34.2	微信支付
     */
    @POST(C.ApiUrl.URL_WEIXIN_ORDER)
    Call<ResultBase<WeiXinOrderInfo>> weixinDoOrder(@Query("orderNum") String orderNum);

    /**
     * 设置交易密码
     *
     * @author zhangsan
     * @date 16/10/20 下午7:53
     */
    @POST(C.ApiUrl.URL_SET_TRADE_PWD)
    Call<ResultBase> setTradePwd(@Path("userNum") String userNum, @Body SetTradePwdParams pwdParams);

    /**
     * 订单详情
     *
     * @author zhangsan
     * @date 16/10/20 下午7:55
     */
    @FormUrlEncoded
    @POST(C.ApiUrl.URL_ORDER_DETAIL)
    Call<ResultBase<OrderInfo>> getOrderDetail(@Path("userNum") String userNum, @Field("orderNum") String orderNum);

    /**
     * 1.34.1	获取支付宝key
     */
    @POST(C.ApiUrl.URL_ALIPAY_ORDER)
    Call<ResultBase<String>> alipayConfig(@Path("userNum") String userNum);

    /**
     * 分享回调
     */
    @POST("app/{userNum}/score/share")
    Call<ResultBase> shareCallBack(@Path("userNum") String userNum);

    /**
     * 我的活动的详情
     *
     * @param userNum
     * @param activityNum 活动编号
     * @return
     */
    @POST("app/{userNum}/goods/activity/sign_info")
    Call<ResultBase<ActivitySignInfo>> signInfo(@Path("userNum") String userNum, @Query("activityNum") String activityNum);


    /**
     * 1.36.1	销售提成/施工提成/用户数
     *
     * @param userNum
     * @return
     */
    @POST("app/{userNum}/report/index")
    Call<ResultBase<ReportInfo>> report(@Path("userNum") String userNum);

    /**
     * 获取消息提醒列表接口
     **/
    @POST(C.ApiUrl.URL_SYSTEM_REMIND)
    Call<ResultBase<PageInfoBase<RemindInfo>>> getSystemRemindList(@Path("userNum") String userNum, @Body SystemRemindParams params);


    /**
     * 分配提成
     */
    @POST("app/{userNum}/workorder/get_tcfp")
    Call<ResultBase<WorkOrderCommissionInfo>> getDistrubuteUser(@Path("userNum") String userNum, @Query("workOrderNum") String workOrderNum);

    /**
     * 保存提成分配信息
     **/
    @POST("app/{userNum}/workorder/save_tcfp")
    Call<ResultBase> saveDistrubution(@Path("userNum") String userNum, @Body SaveDistrubutionParams params);

    /**
     * 修改工单状态
     **/
    @POST("app/{userNum}/workorder/update_status")
    Call<ResultBase> updateStatus(@Path("userNum") String userNum, @Body UpdateStatusParams params);

    /**
     * 1.29.5	快速洗车
     **/
    @POST("app/{userNum}/workorder/save_car_wash")
    Call<ResultBase> saveWashCar(@Path("userNum") String userNum, @Body SaveWashCarParams params);

    /**
     * 工单结算
     **/
    @POST("app/{userNum}/workorder/get_jiesuan")
    Call<ResultBase<JieSuanInfo>> getJieSuan(@Path("userNum") String userNum, @Query("workOrderNumString") String workOrderNumString);

    /**
     * 生成工单结算订单
     **/
    @FormUrlEncoded
    @POST("app/{userNum}/order/add_jiesuan")
    Call<ResultBase<OrderInfo>> addJieSuan(@Path("userNum") String userNum, @Field("jsonArrayString") String jsonArrayString);

    /**
     * shop工单结算
     **/
    @FormUrlEncoded
    @POST("app/{userNum}/order/add_jiesuan")
    Call<ResultBase<OrderInfo>> addJieSuan2(@Path("userNum") String userNum, @Field("jsonArrayString") String jsonArrayString, @Query("payType") int payType);


    /**
     * 1.8.8	校验验证码
     *
     * @param loginName
     * @param params
     * @return
     */
    @POST("app/external/{loginName}/user/validate")
    Call<ResultBase<Object>> validate(@Header("Cookie") String Cookie, @Path("loginName") String loginName, @Body VerifyCodyParams params);

    /**
     * 保存车辆里程数
     *
     * @param userNum
     * @param params
     * @return
     */
    @POST("app/{userNum}/carinfo/saveCarMileage")
    Call<ResultBase> saveCarMileage(@Path("userNum") String userNum, @Body CarMileageParams params);

    /**
     * 1.41	设为已读消息
     *
     * @param userNum
     * @param remindInfoId
     * @return
     */
    @FormUrlEncoded
    @POST("app/{userNum}/remind/update")
    Call<ResultBase> updateRemind(@Path("userNum") String userNum, @Field("remindInfoId") String remindInfoId);

    /**
     * 特殊查询
     *
     * @param userNum
     * @param commonNums
     * @return
     */
    @FormUrlEncoded
    @POST("app/{userNum}/order/common_list")
    Call<ResultBase<List<OrderGoodsInfo>>> commonList(@Path("userNum") String userNum, @Field("commonNums") String commonNums);


    /**
     * 技师端获取我的工单数量
     *
     * @param userNum
     * @return
     */
    @POST("app/{userNum}/workorder/my_list_count")
    Call<ResultBase<MyListCount>> myListCount(@Path("userNum") String userNum);


    /**
     * 删除订单
     *
     * @param userNum
     * @return {userNum}	String	用户编号
     * orderNum	String	订单编号
     */
    @FormUrlEncoded
    @POST("app/{userNum}/order/del")
    Call<ResultBase> delOrder(@Path("userNum") String userNum, @Field("orderNum") String orderNum);


    /**
     * 用户协议
     *
     * @param userNum
     * @return {userNum}	String	用户编号
     * type	String	订单编号
     */
    @FormUrlEncoded
    @POST("app/{userNum}/store/agreement")
    Call<ResultBase<AgreementContentInfo>> agreement(@Path("userNum") String userNum, @Field("type") String type);


    /**
     * 订单支付校验
     */
    @FormUrlEncoded
    @POST("app/{userNum}/order/valite_order")
    Call<ResultBase> valiteOrder(@Path("userNum") String userNum, @Field("orderNum") String orderNum);


}
