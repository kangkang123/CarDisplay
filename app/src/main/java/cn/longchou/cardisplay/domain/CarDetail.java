package cn.longchou.cardisplay.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangkang on 2016/7/19.
 */
public class CarDetail {

    public CarDetailData data;

    public class CarDetailData{
        public CarBaseInfo carBaseInfo;
        public List<CarImages> carImgs;
    }

    public class CarBaseInfo{
        public String annualEffectivePeriod;
        public String compulsoryInsurance;
        public String drivingMode;
        public String fuelType;
        public String gearboxType;
        public String insideColor;
        public String outsideColor;
        public String owner;
        public String usageCharacteristic;
    }

    public class CarImages{
        public String imgUrl;
    }

    //获取车辆详情的所有的图片
    public static List<String> getImages(List<CarImages> carImgs){
        List<String> list=new ArrayList<>();
        for(int i=0;i<carImgs.size();i++)
        {
            CarImages url=carImgs.get(i);
            list.add(url.imgUrl);
        }
        return list;
    }
}
