package com.ruanyun.chezhiyi.commonlib.util.compressimage;

import com.ruanyun.imagepicker.bean.CompressImageInfoGetter;

public interface CompressImageProxyService {
    /**
      * 获取压缩图片请求task类
     * 如果上传图片请求requetbody使用同一 参数名 globalparamsname不能为空 否则 在传入的参数实体类中自行设置自定义参数名
      *@author zhangsan
      *@date   16/11/30 下午3:07
      */
    CompressImageTask getCompressTask(String globalParamsName,CompressImageInfoGetter... getters);

    //CompressImageTask getCompressTasks(List<CompressImageInfoGetter> getters, String globalParamsName);
}