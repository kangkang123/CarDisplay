package cn.longchou.cardisplay.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kangkang on 2016/7/20.
 * 车辆列表
 */
public class CarList {

    public CarData data;
    public int errorCode;
    public String errorText;

    public class CarData{
        public List<Cars> cars;
        public int carCount;
    }
    public class Cars implements Serializable{
        public String carBrand;
        public String carDesc;
        public String carID;
        public String carImgURL;
        public String carMileage;
        public String carName;
        public String carPrice;
        public String carRegDate;
        public String carSeries;
        public String carSubscription;
        public String carType;
        public String emissionStd;
        public String isFinance;
        public String isLCCar;
    }
}
