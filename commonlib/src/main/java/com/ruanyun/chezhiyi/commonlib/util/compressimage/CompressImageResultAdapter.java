package com.ruanyun.chezhiyi.commonlib.util.compressimage;

import com.ruanyun.imagepicker.bean.CompressImageInfoGetter;

/**
 * Description:
 * author: zhangsan on 16/11/28 下午5:06.
 */
public interface CompressImageResultAdapter<T> {

   T convert(CompressImageInfoGetter... compressImageInfoGetters);

  abstract class Factory{
      public abstract CompressImageResultAdapter<?> creat();
  }
}
