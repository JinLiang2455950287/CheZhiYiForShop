package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ： 保存车辆里程数
 * <p/>
 * Created by ycw on 2016/11/28.
 */
public class CarMileageParams {
    private  String carAllName;     //	String	车牌号
    private  String carMileage;     //	Integer	里程数，不允许输入小数点

    public String getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(String carMileage) {
        this.carMileage = carMileage;
    }

    public String getCarAllName() {
        return carAllName;
    }

    public void setCarAllName(String carAllName) {
        this.carAllName = carAllName;
    }
}
