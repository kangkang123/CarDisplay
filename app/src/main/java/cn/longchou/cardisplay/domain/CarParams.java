package cn.longchou.cardisplay.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangkang on 2016/7/19.
 * 车辆的基本参数
 */
public class CarParams {

    public String key;
    public String value;

    public CarParams(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * brandName : 大众
     * carModel : 轿车
     * carName : 上海大众 大众POLO 2014 1.4 手自一体 舒适版
     * carType : 轿车
     * drivingForm : 前置前驱
     * engineDescribe : 1.4L L4 90PS 多点电喷汽油发动机
     * gearboxDescribe : 手自一体变速器(AMT)
     * guidingPrice : 9.89
     * manufactureName : 上海大众
     * modelYear : 2014
     * seatsNum : 5
     * sellName : 1.4 手自一体 舒适版
     * sellYear : 2014
     * seriesName : POLO
     */

    public DataBean data;
    /**
     * data : {"brandName":"大众","carModel":"轿车","carName":"上海大众 大众POLO 2014 1.4 手自一体 舒适版","carType":"轿车","drivingForm":"前置前驱","engineDescribe":"1.4L L4 90PS 多点电喷汽油发动机","gearboxDescribe":"手自一体变速器(AMT)","guidingPrice":"9.89","manufactureName":"上海大众","modelYear":2014,"seatsNum":"5","sellName":"1.4 手自一体 舒适版","sellYear":2014,"seriesName":"POLO"}
     * errorCode : 0
     * errorText :
     */

    public int errorCode;
    public String errorText;

    public static class DataBean {
        public String brandName;
        public String carModel;
        public String carName;
        public String carType;
        public String drivingForm;
        public String engineDescribe;
        public String gearboxDescribe;
        public String guidingPrice;
        public String manufactureName;
        public String modelYear;
        public String seatsNum;
        public String sellName;
        public String sellYear;
        public String seriesName;
    }

    public static List<CarParams> getParams(DataBean data){

        List<CarParams> list=new ArrayList<>();
        list.add(new CarParams("厂家",data.manufactureName));
        list.add(new CarParams("品牌",data.brandName));
        list.add(new CarParams("车系",data.seriesName));
        list.add(new CarParams("车型",data.carName));
        list.add(new CarParams("销售名称",data.sellName));
        list.add(new CarParams("车款",data.modelYear));
        list.add(new CarParams("车辆类型",data.carType));
        list.add(new CarParams("指导价格",data.guidingPrice));
        list.add(new CarParams("上市年份",data.sellYear));
        list.add(new CarParams("发动机描述",data.engineDescribe));
        list.add(new CarParams("变速器描述",data.gearboxDescribe));
        list.add(new CarParams("车身形式",data.carModel));
        list.add(new CarParams("座位个数(个)",data.seatsNum));
        return list;
    }
}
