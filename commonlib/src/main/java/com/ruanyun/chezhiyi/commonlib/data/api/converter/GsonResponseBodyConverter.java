package com.ruanyun.chezhiyi.commonlib.data.api.converter;

import com.google.gson.TypeAdapter;
import com.ruanyun.chezhiyi.commonlib.util.Base64;
import com.ruanyun.chezhiyi.commonlib.util.RSAUtils;

import java.io.IOException;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final TypeAdapter<T> adapter;

  GsonResponseBodyConverter(TypeAdapter<T> adapter) {
    this.adapter = adapter;
  }

  @Override public T convert(ResponseBody value) throws IOException {
    BufferedSource bufferedSource = Okio.buffer(value.source());
    String tempStr ="";
    try {
      tempStr= RSAUtils.deCodeKey(bufferedSource.readUtf8());
      tempStr= new String(Base64.decode(tempStr),"UTF-8");

      return  adapter.fromJson(tempStr);
      //return adapter.fromJson(value.charStream());
    } finally {
      //value.close();
      bufferedSource.close();
    }
  }
}