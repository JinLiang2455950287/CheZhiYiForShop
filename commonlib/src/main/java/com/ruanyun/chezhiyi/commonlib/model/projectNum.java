package com.ruanyun.chezhiyi.commonlib.model;

import java.util.List;

/**
 * Created by czy on 2017/4/19.
 */

public class projectNum {
//    "projectNum":"[\n  {\n    \"isMake\" : 0,\n    \"sortNum\" : 3,\n    \"childProjectTypeList\" : [\n      {\n        \"isMake\" : 0,\n        \"sortNum\" : 1,\n        \"childProjectTypeList\" : [\n\n        ],\n        \"projectNum\" : \"003004000000000\",\n        \"parentNum\" : \"003000000000000\",\n        \"projectName\" : \"CSBY01\",\n        \"projectAllName\" : \"服务项目\\/保养\\/CSBY01\"\n      }\n    ],\n    \"projectNum\" : \"003000000000000\",\n    \"parentNum\" : \"000000\",\n    \"projectName\" : \"保养\",\n    \"projectAllName\" : \"服务项目\\/保养\"\n  }\n]",
    private int isMake;
    private int sortNum;
    private List<childProjectTypeList> childProjectTypeList;

}
