package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * 获取流水记录 参数
 * Created by wp on 2016/10/13.
 */
public class RecordListParams extends PageParamsBase {
    /**账户类型【字典表读取ACCOUNT_TYPE】 1账户余额 2支付宝 3微信 4到店支付 5积分账户 **/
    private int accountType;
    /**
     * 账户类型 将要查询accountType 的值“,”拼接传入
     */
    private String accountTypeString;
//    /**
//     * 操作类型【字典表读取ACCOUNT_RECORD_TYPE】 1充值  2退款 3商城购物消费
//     * 4支付预约定金消费 5活动报名消费 6工单结算消费   11 积分消费 12购买赠送积分 13 签到送积分
//     */
//    private int recordType;

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getAccountTypeString() {
        return accountTypeString;
    }

    public void setAccountTypeString(String accountTypeString) {
        this.accountTypeString = accountTypeString;
    }

//    public int getRecordType() {
//        return recordType;
//    }
//
//    public void setRecordType(int recordType) {
//        this.recordType = recordType;
//    }
}
