package com.ruanyun.chezhiyi.commonlib.model.params;

import java.util.List;

/**
 * Created by czy on 2017/3/20.
 */

public class YuYueDealListParams {
    private String project;

    public YuYueDealListParams() {

    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "YuYueDealListParams{" +
                "project='" + project + '\'' +
                '}';
    }
}
