package com.ruanyun.chezhiyi.commonlib.util;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Description:
 * author: zhangsan on 16/11/29 上午11:18.
 */
public class TextRequestBody extends RequestBody {
      byte[] content;

    public TextRequestBody(String content){
        this.content=content.getBytes();
    }

    public TextRequestBody(byte [] textBytes){
        this.content=textBytes;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("text/plain");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        sink.write(content,0,content.length);
    }
}
