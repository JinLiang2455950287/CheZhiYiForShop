package com.ruanyun.chezhiyi.commonlib.model.params;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hdl
 * @ClassName: ${file_name}
 * @Description:
 * @date ${date}${time}
 */
public class AddUpDataBookParams {
    /**单条编号【修改时必须传值】**/
    private String bookNum;
    /**日期【yyyy-MM-dd】 【在修改状态时 如果值没有变更也需要传值到后台】**/
    private Date bookDate;
    /**金额【java 对应的金钱类型 小数点两位 】【在修改状态时 如果值没有变更也需要传值到后台**/
    private BigDecimal bookPrice;
    /**备注**/
    private String remark;
    /**类型  TprojectType 工单服务和技师技能 数据结构 一级数据 的 业务主键【projectNum】**/
    private String projectNum;
    /**附件ID 附件数据结构（TAttachInfo） 表中的attachId【修改时用，删除图片ID】**/
    private int[] delAttachInfoId;
    /**多张图片【在修改时只传递添加的图片流】**/
    private File bookCashPic;
}
