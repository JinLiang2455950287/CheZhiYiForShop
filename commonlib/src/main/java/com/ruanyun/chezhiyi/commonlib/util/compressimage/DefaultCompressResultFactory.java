package com.ruanyun.chezhiyi.commonlib.util.compressimage;

import com.ruanyun.chezhiyi.commonlib.util.FileRequestBody;
import com.ruanyun.imagepicker.bean.CompressImageInfoGetter;


import java.io.File;
import java.util.HashMap;

import okhttp3.RequestBody;

/**
 * Description:
 * author: zhangsan on 16/11/29 上午10:57.
 */
public final class DefaultCompressResultFactory extends CompressImageResultAdapter.Factory {

    @Override
    public CompressImageResultAdapter<HashMap<String, RequestBody>> creat() {
        return new CompressImageResultAdapter<HashMap<String, RequestBody>>() {

            @Override
            public HashMap<String, RequestBody> convert(CompressImageInfoGetter... compressImageInfoGetters) {
                HashMap<String, RequestBody> resultMap = new HashMap<>();
                for (CompressImageInfoGetter getter : compressImageInfoGetters) {
                    FileRequestBody requestBody = new FileRequestBody(FileRequestBody.TYPE_IMAGE, new File(getter.imageFilePath()));
                    resultMap.put(getImageMdStr(getter.requestPramsName(), getter.imageFileName()), requestBody);
                }

                return resultMap;
            }
        };
    }

    private String getImageMdStr(String paramsName, String fileName) {
        StringBuilder sb = new StringBuilder(paramsName)
                .append("\";filename=\"")
                .append(fileName);
        return sb.toString();
    }
}
