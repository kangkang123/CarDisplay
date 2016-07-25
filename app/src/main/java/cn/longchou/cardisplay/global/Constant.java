package cn.longchou.cardisplay.global;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangkang on 2016/7/19.
 * 保存常量
 */
public class Constant {

    public static String url="http://10.0.2.59:8080/";

    //获取所有的门店城市
    public static String RequestGetCity=url+"app/api/shop/v1.0/getAllStores";
    //车市列表
    public static String RequestCarList=url+"pad/api/car/list?";
    //车辆详情
    public static String RequestCarDetail=url+"pad/api/car/detail/";
    //车辆基本参数
    public static String RequestCarParams=url+"pad/api/car/detail/primary/";


    public static String CityChoose="全国";
    public static String PriceChoose="全部";


    public static List<String> getPrice(){
        List<String> price=new ArrayList<>();
        price.add("全部");
        price.add("5万以下");
        price.add("5-8万");
        price.add("8-12万");
        price.add("12-18万");
        price.add("18-22万");
        price.add("22-28万");
        price.add("28-50万");
        price.add("50万以上");
        return price;
    }

    //处理字符串
    public static String getHandleString(String string)
    {
        if(string.indexOf("以下") != -1)
        {
                String substring = string.substring(0, string.length()-3);
                return substring;
        }
        else if(string.indexOf("以上") != -1)
        {
                String substring = string.substring(0, string.length()-3);
                return substring+"-";
        }else{
                return string.substring(0, string.length()-1);
        }
    }
}
