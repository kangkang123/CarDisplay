package cn.longchou.cardisplay.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.longchou.cardisplay.R;
import cn.longchou.cardisplay.adapter.CarParamsAdapter;
import cn.longchou.cardisplay.adapter.HomePagerAdapter;
import cn.longchou.cardisplay.adapter.HorizonTalListAdapter;
import cn.longchou.cardisplay.base.BaseActivity;
import cn.longchou.cardisplay.domain.CarDetail;
import cn.longchou.cardisplay.domain.CarList;
import cn.longchou.cardisplay.domain.CarParams;
import cn.longchou.cardisplay.global.Constant;
import cn.longchou.cardisplay.utils.UIUtils;
import cn.longchou.cardisplay.view.HorizontalListView;

/**
 * Created by kangkang on 2016/7/18.
 */
public class CarDetailActivity extends BaseActivity {

    private HorizontalListView mListView;
    public ImageView mPrice;
    public ImageView mFresh;
    public ListView mListParams;
    public RelativeLayout mMoreParams;
    public static DrawerLayout mDrawerLayout;
    public ViewPager mViewPager;
    public TextView mPosition;
    public TextView mCarName;
    public TextView mCarPice;
    public TextView mCarEmission;
    public TextView mCarMileage;
    public TextView mCarDate;
    private TextView mCity;
    public CarList.Cars cars;
    public List<CarList.Cars> list;
    public int pageIndex=1;
    public int rows=9;
    public List<CarDetail.CarImages> carImgs;

    @Override
    public void initView() {

        setContentView(R.layout.activity_car_detail);

        mListView = (HorizontalListView) findViewById(R.id.lv_horizon);
        mPrice = (ImageView) findViewById(R.id.iv_price_choose);

        mCity = (TextView) findViewById(R.id.tv_title_city_choose);

        mListParams = (ListView) findViewById(R.id.lv_car_detail_params);

        mMoreParams = (RelativeLayout) findViewById(R.id.rl_car_detail_more);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_screen);

        mViewPager = (ViewPager) findViewById(R.id.vp_car_detail);

        mPosition = (TextView) findViewById(R.id.tv_image_position);

        mCarName = (TextView) findViewById(R.id.tv_car_detail_name);
        mCarPice = (TextView) findViewById(R.id.tv_car_detail_price);
        mCarEmission = (TextView) findViewById(R.id.tv_car_detail_emission);
        mCarMileage = (TextView) findViewById(R.id.tv_car_detail_mileage);
        mCarDate = (TextView) findViewById(R.id.tv_car_detail_date);


        mFresh = (ImageView) findViewById(R.id.iv_my_title_login);


    }

    @Override
    public void initData() {

        mCity.setText(Constant.CityChoose);
        mPrice.setImageResource(R.drawable.back);
        Drawable drawable= getResources().getDrawable(R.drawable.touming);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mCity.setCompoundDrawables(null,null,drawable,null);

        cars= (CarList.Cars) getIntent().getExtras().getSerializable("car");
        mCarName.setText(cars.carName);
        mCarPice.setText(cars.carPrice);
        mCarEmission.setText(cars.emissionStd);
        mCarMileage.setText(cars.carMileage);
        mCarDate.setText(cars.carRegDate);

        //初始化关闭侧边栏
//        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //获取车辆详情
        getServerData();

        //获取相关车辆的信息
        getCarListData();

    }

    //获取车辆的信息
    private void getCarListData() {
        HttpUtils http=new HttpUtils();
        String url=Constant.RequestCarList;
        RequestParams params=new RequestParams();
        params.addQueryStringParameter("pageIndex",pageIndex+"");
        params.addQueryStringParameter("rows",rows+"");
        if(!"全国".equals(Constant.CityChoose)){
            params.addQueryStringParameter("city",Constant.CityChoose);
        }

        http.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String result=responseInfo.result;
                paraseCarListData(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {


            }
        });
    }

    private void paraseCarListData(String result) {
        Gson gson=new Gson();
        CarList carList = gson.fromJson(result, CarList.class);
        if(carList!=null)
        {
            if(carList.data!=null)
            {
                list = carList.data.cars;
                HorizonTalListAdapter adapter=new HorizonTalListAdapter(getApplicationContext(),list);
                mListView.setAdapter(adapter);
            }

        }

    }

    //获取车辆详情
    private void getServerData() {
        HttpUtils http=new HttpUtils();
        String url= Constant.RequestCarDetail+cars.carID;
        http.send(HttpRequest.HttpMethod.POST, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String result=responseInfo.result;
                Gson gson=new Gson();
                CarDetail carDetail = gson.fromJson(result, CarDetail.class);
                if(carDetail!=null)
                {
                    carImgs = carDetail.data.carImgs;
                    if(carImgs.size()>0)
                    {
                        paraseData(CarDetail.getImages(carImgs));
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void paraseData(List<String> carImags) {
        List<ImageView> images=new ArrayList<ImageView>();
        if(carImags.size()>0&&carImags!=null)
        {
            for(int i=0;i<carImags.size();i++){
                ImageView image=new ImageView(getApplicationContext());
                if(carImags.get(i)!=null)
                {
                    Glide.with(this).load(carImags.get(i)).placeholder(R.drawable.car_detail_default).into(image);
                    images.add(image);
                }
            }
            HomePagerAdapter imageAdapter=new HomePagerAdapter(CarDetailActivity.this,images,carImags,cars);
            mViewPager.setAdapter(imageAdapter);
            mPosition.setText("1/"+images.size());
        }

    }

    @Override
    public void initListener() {

        mMoreParams.setOnClickListener(this);
        mFresh.setOnClickListener(this);
        mPrice.setOnClickListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarList.Cars cars = list.get(position);
                getListData(cars);

            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mPosition.setText(position+1+"/"+carImgs.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //获取基本参数信息
                getParamsData();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void getListData(final CarList.Cars car) {
        HttpUtils http=new HttpUtils();
        String url= Constant.RequestCarDetail+car.carID;
        http.send(HttpRequest.HttpMethod.POST, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String result=responseInfo.result;
                Gson gson=new Gson();
                CarDetail carDetail = gson.fromJson(result, CarDetail.class);
                if(carDetail!=null)
                {
                    carImgs = carDetail.data.carImgs;
                    if(carImgs.size()>0)
                    {
                        ArrayList<String> carImags = (ArrayList<String>) CarDetail.getImages(carImgs);
                        Intent intent=new Intent(CarDetailActivity.this,ImageLookActivity.class);
                        intent.putStringArrayListExtra("carImags",carImags);
                        intent.putExtra("carName",car.carName);
                        intent.putExtra("carID",car.carID);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

                UIUtils.showToastSafe("信息详情超时,请重新获取。");
            }
        });
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.iv_price_choose:
                finish();
                break;
            case R.id.rl_car_detail_more:
                //获取基本参数信息
//                getParamsData();
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.iv_my_title_login:
                //获取车辆详情
                getServerData();

                //获取相关车辆的信息
                getCarListData();
                break;
        }

    }

    //获取基本参数信息
    private void getParamsData() {
        HttpUtils http=new HttpUtils();
        String url= Constant.RequestCarParams+cars.carID;
        http.send(HttpRequest.HttpMethod.POST, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String result=responseInfo.result;
                Gson gson=new Gson();
                CarParams carParams = gson.fromJson(result, CarParams.class);
                if(carParams!=null)
                {
                    List<CarParams> params = CarParams.getParams(carParams.data);
                    CarParamsAdapter paramsAdapter=new CarParamsAdapter(getApplicationContext(),params);
                    mListParams.setAdapter(paramsAdapter);
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
