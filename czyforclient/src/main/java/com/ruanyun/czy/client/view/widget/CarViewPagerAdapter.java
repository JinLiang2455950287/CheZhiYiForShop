package com.ruanyun.czy.client.view.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruanyun.chezhiyi.commonlib.model.CarInfo;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.czy.client.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

public class CarViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "CarViewPagerAdapter";
    // private LinkedList<View> mViewCache = null;
    private Context mContext;
    private static final int VIEW_TYPE_ADD = 321;
    // private static final int VIEW_TYPE_INFO=123;
    private SparseArray<View> mViewCache = null;
    private List<CarInfo> carInfoList;
    private LayoutInflater layoutInflater;

    public CarViewPagerAdapter(Context context, List<CarInfo> carInfoList) {
        mViewCache = new SparseArray<>();
        this.mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
        // mViewCache.put(VIEW_TYPE_INFO,View.inflate(mContext,R.layout.layout_view_carinfo,null));
        this.carInfoList = carInfoList;

    }

    public void removeItem(CarInfo carInfo) {
        carInfoList.remove(carInfo);
        mViewCache.remove(carInfo.hashCode());
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //ViewHolder holder = null;
        View convertView;
        CarInfo carinfo = carInfoList.get(position);
        if (carinfo.isAdd()) {//是添加项
            convertView = mViewCache.get(VIEW_TYPE_ADD);
            if (null == convertView) {
                convertView = layoutInflater.inflate(R.layout.layout_view_addcar, container, false);

                mViewCache.put(VIEW_TYPE_ADD, convertView);
            }
        } else {
            if (null == mViewCache.get(carinfo.hashCode())) {
                convertView = layoutInflater.inflate(R.layout.layout_view_carinfo, container,false);

            } else {
                convertView = mViewCache.get(carinfo.hashCode());
            }
            CarInfoView carInfoView = (CarInfoView) convertView;
            if (!TextUtils.isEmpty(carinfo.getCarModelNum())) {
                carInfoView.setCarName(getCarBandName(carinfo.getCarModelNum()));
                carInfoView.setCarTypeName(getCarSeriaName(carinfo.getCarModelNum()));
            }
            String picPath = carinfo.getPicPath();
            if (!TextUtils.isEmpty(picPath) && picPath.startsWith("file")) {
                carInfoView.setCarPhoto(FileUtil.getFileUrl(picPath));
            } else {
                carInfoView.setCarPhoto(picPath);
            }
        }
        AutoUtils.auto(convertView);
        container.addView(convertView);
        return convertView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogX.d(TAG, "destroyItem" + position);
        container.removeView((View) object);
        if (position < carInfoList.size() && !carInfoList.get(position).isAdd() && mViewCache.indexOfValue((View) object) == -1) {
            mViewCache.put(carInfoList.get(position).hashCode(), (View) object);
        }
        // mViewCache.put(position,(View) object);
    }

    @Override
    public int getCount() {
        return carInfoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    private String getCarBandAllName(String modelNum) {
        return DbHelper.getInstance().getCarModelNameByNum(modelNum);
    }

    /**
     * 获取车辆品牌名称
     *
     * @author zhangsan
     * @date 16/9/28 上午9:55
     */
    public String getCarBandName(String modelNum) {
        return getCarBandNameFromAllName(getCarBandAllName(modelNum));
    }

    private String getCarBandNameFromAllName(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.substring(0, text.indexOf("-"));
        }
        return "";
    }

    /**
     * 获取车系名称
     *
     * @author zhangsan
     * @date 16/9/28 上午9:54
     */
    private String getCarSeriaName(String modelNum) {
        String text = getCarBandAllName(modelNum);
        if (!TextUtils.isEmpty(text)) {
            text = text.substring(text.indexOf("-") + 1, text.length()).replaceAll("-", "");
        }
        return text;
    }
}