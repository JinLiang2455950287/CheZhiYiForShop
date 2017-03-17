package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Description:
 * author: zhangsan on 16/10/27 上午9:13.
 */
@Entity
public class RemindInfo implements Parcelable {
    public static final int READ_NO = 2;
    public static final int READ_YES = 1;
    public static final int STATUS_PUSHED = 1;
    public static final int STATUS_UNPUSHED = 2;
//    "BY,YE,SR,HYLS"
    /**
     * "BY";保养提醒
     */
    public static final String REMIND_TYPE_MAINTAIN = "BY";//保养提醒
    /**
     * "SR";生日提醒
     */
    public static final String REMIND_TYPE_BIRTHDAY = "SR";//生日提醒
    /**
     * "YE";会员卡余额提醒
     */
    public static final String REMIND_TYPE_BALANCE = "YE";//会员卡余额提醒
    /**
     * "HYLS";会员流失提醒
     */
    public static final String REMIND_TYPE_MEMBERLOSE = "HYLS";//会员流失提醒
    /**
     * "XTTZ";系统提醒
     */
    public static final String REMIND_TYPE_SYSTEM = "XTTZ";//系统提醒
    /**
     * "DD";订单
     */
    public static final String REMIND_TYPE_ORDER = "DD";//订单
    /**
     * "DDTK";订单退款
     */
    public static final String REMIND_TYPE_ORDER_REFUND = "DDTK";//订单退款
    /**
     * "GD";工单
     */
    public static final String REMIND_TYPE_WORKORDER = "GD";//工单
    /**
     * "GDJS";工单结算
     */
    public static final String REMIND_TYPE_WORKORDER_BALANCE = "GDJS";//工单结算
    /**
     * "YY" 预约
     */
    public static final String REMIND_TYPE_BESPEAK = "YY";//预约
    /**
     * "YYQR";预约确认
     */
    public static final String REMIND_TYPE_BESPEAK_CONFIRM = "YYQR";//预约确认
    /**
     * "HD";活动报名
     */
    public static final String REIND_TYPE_CAMPAIGN = "HD";//活动报名
    /**
     * "ZC";众筹
     */
    public static final String REMIND_TYPE_CROWDFUNDING = "ZC";//众筹

    private int remindInfoId;
    /**
     * 编号【暂无使用】
     **/
    @Unique
    private String remindInfoNum;
    /**
     * 提醒类型 1保养提醒 2生日提醒 3会员卡余额提醒 4会员流失提醒 5系统提醒
     **/
    private String remindType;
    /**
     * 提醒标题
     **/
    private String remindTitle;
    /**
     * 提醒内容
     **/
    private String remindContent;
    /**
     * 推送用户userNum
     **/
    private String remindUserNum;
    /**
     * 创建时间
     **/
    private String createTime;
    /**  **/
    private String commonNum;
    /**
     * 公共content
     **/
    private String commconContent;
    /**
     * 是否推送 1推送 2未推送
     **/
    private int isPush;
    /**
     * 推送时间
     **/
    private String pushTime;

    /**
     * 是否已读 1是 2否
     */
    private int isRead;

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    /**
     * 获取图片资源
     *
     * @return
     */
    public int getResIdByType() {
        int imgResId = R.drawable.system_remind_default;
        switch (remindType) {
            case REMIND_TYPE_MAINTAIN:
                imgResId = R.drawable.system_remind_member_order_or_maintain;
                break;
            case REMIND_TYPE_BALANCE:
                imgResId = R.drawable.system_remind_lack_balance;
                break;
            case REMIND_TYPE_BIRTHDAY:
                imgResId = R.drawable.system_remind_member_birthday;
                break;
            case REMIND_TYPE_MEMBERLOSE:
                imgResId = R.drawable.system_remind_member_loss;
                break;
            case REMIND_TYPE_SYSTEM:
                imgResId = R.drawable.system_remind_case_evalution;
                break;
            case REMIND_TYPE_BESPEAK:
            case REMIND_TYPE_BESPEAK_CONFIRM:
                imgResId = R.drawable.system_remind_activity_come;
                break;
            case REMIND_TYPE_CROWDFUNDING:
                imgResId = R.drawable.system_remind_crowdfunding;
                break;
            case REMIND_TYPE_ORDER_REFUND:
            case REMIND_TYPE_ORDER:
                imgResId = R.drawable.system_remind_refund_account;
                break;
            case REMIND_TYPE_WORKORDER:
            case REMIND_TYPE_WORKORDER_BALANCE:
                imgResId = R.drawable.system_remind_order_divided;
                break;
            case REIND_TYPE_CAMPAIGN:
                imgResId = R.drawable.system_remind_activity_come;
                break;

        }
        return imgResId;
    }

    public int getRemindInfoId() {
        return remindInfoId;
    }

    public void setRemindInfoId(int remindInfoId) {
        this.remindInfoId = remindInfoId;
    }

    public String getRemindInfoNum() {
        return remindInfoNum;
    }

    public void setRemindInfoNum(String remindInfoNum) {
        this.remindInfoNum = remindInfoNum;
    }

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }

    public String getRemindTitle() {
        return remindTitle;
    }

    public void setRemindTitle(String remindTitle) {
        this.remindTitle = remindTitle;
    }

    public String getRemindContent() {
        return remindContent;
    }

    public void setRemindContent(String remindContent) {
        this.remindContent = remindContent;
    }

    public String getRemindUserNum() {
        return remindUserNum;
    }

    public void setRemindUserNum(String remindUserNum) {
        this.remindUserNum = remindUserNum;
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

    public String getCommconContent() {
        return commconContent;
    }

    public void setCommconContent(String commconContent) {
        this.commconContent = commconContent;
    }

    public int getIsPush() {
        return isPush;
    }

    public void setIsPush(int isPush) {
        this.isPush = isPush;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getCreateTimeFormat() {
        if (TextUtils.isEmpty(createTime)) return "";
        return StringUtil.getTimeStrFromFormatStr("MM-dd HH:mm", createTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.remindInfoId);
        dest.writeString(this.remindInfoNum);
        dest.writeString(this.remindType);
        dest.writeString(this.remindTitle);
        dest.writeString(this.remindContent);
        dest.writeString(this.remindUserNum);
        dest.writeString(this.createTime);
        dest.writeString(this.commonNum);
        dest.writeString(this.commconContent);
        dest.writeInt(this.isPush);
        dest.writeString(this.pushTime);
    }

    public RemindInfo() {
    }

    protected RemindInfo(Parcel in) {
        this.remindInfoId = in.readInt();
        this.remindInfoNum = in.readString();
        this.remindType = in.readString();
        this.remindTitle = in.readString();
        this.remindContent = in.readString();
        this.remindUserNum = in.readString();
        this.createTime = in.readString();
        this.commonNum = in.readString();
        this.commconContent = in.readString();
        this.isPush = in.readInt();
        this.pushTime = in.readString();
    }

    @Generated(hash = 1589077638)
    public RemindInfo(int remindInfoId, String remindInfoNum, String remindType, String remindTitle,
                      String remindContent, String remindUserNum, String createTime, String commonNum,
                      String commconContent, int isPush, String pushTime, int isRead) {
        this.remindInfoId = remindInfoId;
        this.remindInfoNum = remindInfoNum;
        this.remindType = remindType;
        this.remindTitle = remindTitle;
        this.remindContent = remindContent;
        this.remindUserNum = remindUserNum;
        this.createTime = createTime;
        this.commonNum = commonNum;
        this.commconContent = commconContent;
        this.isPush = isPush;
        this.pushTime = pushTime;
        this.isRead = isRead;
    }

    public static final Parcelable.Creator<RemindInfo> CREATOR = new Parcelable.Creator<RemindInfo>() {
        @Override
        public RemindInfo createFromParcel(Parcel source) {
            return new RemindInfo(source);
        }

        @Override
        public RemindInfo[] newArray(int size) {
            return new RemindInfo[size];
        }
    };

    @Override
    public String toString() {
        return "RemindInfo{" +
                "remindInfoId=" + remindInfoId +
                ", remindInfoNum='" + remindInfoNum + '\'' +
                ", remindType='" + remindType + '\'' +
                ", remindTitle='" + remindTitle + '\'' +
                ", remindContent='" + remindContent + '\'' +
                ", remindUserNum='" + remindUserNum + '\'' +
                ", createTime='" + createTime + '\'' +
                ", commonNum='" + commonNum + '\'' +
                ", commconContent='" + commconContent + '\'' +
                ", isPush=" + isPush +
                ", pushTime='" + pushTime + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}
