package com.ruanyun.chezhiyi.commonlib.model;

import java.math.BigDecimal;

/**流水记录数据结构TAccountRecord
 * Created by wp on 2016/10/13.
 */
public class RecordListInfo {
    /** 主键**/
    private  int userRecordId;
    /**业务编号 **/
    private String userRecordNum;
    /** 用户编号**/
    private String userNum;
    /** 	账户类型【字典表读取ACCOUNT_TYPE】 1账户余额 2支付宝 3微信 4到店支付 5积分账户**/
    private int accountType;
    /**操作类型【字典表读取ACCOUNT_RECORD_TYPE】 1充值  2退款 3商城购物消费 4支付预约定金消费 5活动报名消费 6工单结算消费   11 积分消费 12购买赠送积分 13 签到送积分 **/
    private int recordType;
   /** 排序值（升序）**/
    private BigDecimal recordAmount;
    /**账户余额 **/
    private BigDecimal amountBalance;
    /** 创建时间**/
    private String createTime;
    /** 公共num**/
    private String commonNum;
    public int getUserRecordId() {
        return userRecordId;
    }

    public void setUserRecordId(int userRecordId) {
        this.userRecordId = userRecordId;
    }

    public String getUserRecordNum() {
        return userRecordNum;
    }

    public void setUserRecordNum(String userRecordNum) {
        this.userRecordNum = userRecordNum;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public BigDecimal getRecordAmount() {
        return recordAmount;
    }

    public void setRecordAmount(BigDecimal recordAmount) {
        this.recordAmount = recordAmount;
    }

    public BigDecimal getAmountBalance() {
        return amountBalance;
    }

    public void setAmountBalance(BigDecimal amountBalance) {
        this.amountBalance = amountBalance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommonNum() {
        return commonNum;
    }

    public void setCommonNum(String commonNum) {
        this.commonNum = commonNum;
    }
}
