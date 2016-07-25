package cn.longchou.cardisplay.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangkang on 2016/7/20.
 */
public class CarCity {
    public List<ShopList> shopList;
    public class ShopList{
        public String shopAddress;
        public String shopName;
    }

    //获取城市
    public static List<String> getCarCity(List<ShopList> shopList){
        List<String> list=new ArrayList<>();
        list.add("全国");
        for(int i=0;i<shopList.size();i++){
            ShopList shop = shopList.get(i);
            list.add(shop.shopName);
        }
        return list;
    }
}
