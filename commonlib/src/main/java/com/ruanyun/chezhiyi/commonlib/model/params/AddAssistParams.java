package com.ruanyun.chezhiyi.commonlib.model.params;

import com.ruanyun.chezhiyi.commonlib.model.AssistUserInfo;

/**
 * Description ：添加助手参数
 * <p/>
 * Created by ycw on 2016/10/12.
 */
public class AddAssistParams {
    private String workOrderNum;//	 String  	工单编号
    private String assistUserNums;//	 String  	协助人员编号
    private String assistUserNames;//	 String  	协助人员姓名

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getAssistUserNums() {
        return assistUserNums;
    }

    public void setAssistUserNums(String assistUserNums) {
        this.assistUserNums = assistUserNums;
    }

    public String getAssistUserNames() {
        return assistUserNames;
    }

    public void setAssistUserNames(String assistUserNames) {
        this.assistUserNames = assistUserNames;
    }
}
