package com.ruanyun.chezhiyi.commonlib.model;



/**
 * Description ：扫描车牌获取的预约信息
 * <p/>
 * Created by zhangsan on 2016/9/26.
 */

public class CarBookingInfo {
    private CarInfo carInfo;//车辆信息 返回对象 TCarInfo车辆信息数据结构    【如果该对象为空 说明该车为非会员】
    private User customerUser;//客户信息用户数据结构【车辆信息不为空 该对象必须有值】
    private AppointmentInfo makeInfo;//预约信息 预约

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public User getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(User customerUser) {
        this.customerUser = customerUser;
    }

    public AppointmentInfo getMakeInfo() {
        return makeInfo;
    }

    public void setMakeInfo(AppointmentInfo makeInfo) {
        this.makeInfo = makeInfo;
    }

    @Override
    public String toString() {
        return "CarBookingInfo{" +
                "carInfo=" + carInfo +
                ", customerUser=" + customerUser +
                ", makeInfo=" + makeInfo +
                '}';
    }
}
