package cn.longchou.cardisplay;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.longchou.cardisplay.activity.CarDetailActivity;
import cn.longchou.cardisplay.adapter.CarDisplayAdapter;
import cn.longchou.cardisplay.adapter.MyHotBrandAdapter;
import cn.longchou.cardisplay.base.BaseActivity;
import cn.longchou.cardisplay.domain.CarCity;
import cn.longchou.cardisplay.domain.CarList;
import cn.longchou.cardisplay.global.Constant;
import cn.longchou.cardisplay.utils.UIUtils;
import cn.longchou.cardisplay.view.PullToRefreshView;

import static cn.longchou.cardisplay.domain.CarCity.getCarCity;

public class MainActivity extends BaseActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private GridView mGridView;
    private TextView mCity;
    private TextView mScreenCity;
    private ListView mListView;;
    private MyHotBrandAdapter cityAdapter;
    public static DrawerLayout mDrawerLayout;
    public CarDisplayAdapter adapter;
    public ImageView mPrice;
    public TextView mCityChoose;
    List<String> city;
    List<String> price;
    List<CarList.Cars> list=new ArrayList<>();

    //判断是否选中城市
    public boolean isCity=true;

    public int pageIndex=1;
    public int rows=9;
    public boolean isFresh=false;
    //筛选
    public boolean isScreen=false;
    ImageView mFresh;
    ImageView mUp;
    PullToRefreshView mPullToRefreshView;

    @Override
    public void initView() {

        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.gv_car_display);

        mCity = (TextView) findViewById(R.id.tv_title_city_choose);
        mScreenCity = (TextView) findViewById(R.id.tv_screen_choose);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_screen);

        mListView = (ListView) findViewById(R.id.lv_city_choose);

        mPrice = (ImageView) findViewById(R.id.iv_price_choose);

        mCityChoose = (TextView) findViewById(R.id.tv_title_city_choose);

        mFresh = (ImageView) findViewById(R.id.iv_my_title_login);
        mUp = (ImageView) findViewById(R.id.iv_car_list_up);

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);



    }

    @Override
    public void initData() {

        mPrice.setVisibility(View.VISIBLE);


        //初始化关闭侧边栏
//        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        getCarListData();

        getCityData();

    }

    //获取车辆的信息
    private void getCarListData() {
        HttpUtils http=new HttpUtils();
        String url=Constant.RequestCarList+pageIndex;

        RequestParams params=new RequestParams();
        params.addQueryStringParameter("rows","9");
        params.addQueryStringParameter("pageIndex",pageIndex+"");
        if(!"全国".equals(Constant.CityChoose)){
            params.addQueryStringParameter("city",Constant.CityChoose);
            UIUtils.showToastSafe(Constant.CityChoose);
        }
        if(!"全部".equals(Constant.PriceChoose)){
            String price=Constant.PriceChoose;
            String pr=Constant.getHandleString(price);
            UIUtils.showToastSafe(pr);
            params.addQueryStringParameter("carPrice",Constant.getHandleString(price));
        }

        http.send(HttpRequest.HttpMethod.GET, url,params, new RequestCallBack<String>() {
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
                List<CarList.Cars> cars=new ArrayList<>();
                cars=carList.data.cars;
                if(isFresh){
                    list.addAll(0,cars);
                }else{
                    list.addAll(cars);
                }
//                list = carList.data.cars;
                adapter = new CarDisplayAdapter(list,getApplicationContext());
                mGridView.setAdapter(adapter);
            }

        }

    }

    //获取所有的城市
    private void getCityData() {
        HttpUtils http=new HttpUtils();
        String url=Constant.RequestGetCity;
        http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result=responseInfo.result;
                Gson gson=new Gson();
                CarCity carCity = gson.fromJson(result, CarCity.class);
                List<CarCity.ShopList> shopList = carCity.shopList;
                city = CarCity.getCarCity(shopList);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    @Override
    public void initListener() {

        mCity.setOnClickListener(this);
        mPrice.setOnClickListener(this);
        mFresh.setOnClickListener(this);
        mUp.setOnClickListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //当选择城市的时候获取选中的城市
                if(isCity){
                    String cityChoose= city.get(position);
                    Constant.CityChoose=cityChoose;
                }//当选择价格的时候获取选中的价格
                else{
                    String cityChoose= price.get(position);
                    Constant.PriceChoose=cityChoose;

                }
                cityAdapter.setSeclection(position);
                cityAdapter.notifyDataSetChanged();
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSeclection(position);
                adapter.notifyDataSetChanged();
                Intent intent=new Intent(MainActivity.this,CarDetailActivity.class);
                CarList.Cars car= list.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("car",car);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                list.clear();
                pageIndex=1;
                getCarListData();
                mCityChoose.setText(Constant.CityChoose);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_city_choose:
                getCityData();
//                点击城市选择让城市变为true，重新填充数据
                mScreenCity.setText("城市");
                isCity=true;
                if(city.size()>0&&city!=null)
                {
                    cityAdapter = new MyHotBrandAdapter(getApplicationContext(),city,"city");
                    mListView.setAdapter(cityAdapter);
                }
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.iv_price_choose:
                price=Constant.getPrice();
                mScreenCity.setText("价格");
                //点击价格选择让城市变为false，重新填充数据
                isCity=false;
                cityAdapter = new MyHotBrandAdapter(getApplicationContext(),price,"price");
                mListView.setAdapter(cityAdapter);
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.iv_my_title_login:
                //刷新数据
                pageIndex=1;
                getCarListData();
                break;
            case R.id.iv_car_list_up:
                adapter = new CarDisplayAdapter(list,getApplicationContext());
                mGridView.setAdapter(adapter);
//                UIUtils.showToastSafe("10");
                break;
        }

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {

        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                System.out.println("上拉加载");
                pageIndex++;
                isFresh=false;
                getCarListData();
                adapter.notifyDataSetChanged();
                mPullToRefreshView.onFooterRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {

        mPullToRefreshView.postDelayed(new Runnable() {

            @Override
            public void run() {
                // 设置更新时间
                mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
                System.out.println("下拉更新");
                pageIndex++;
                isFresh=true;
                getCarListData();
                adapter.notifyDataSetChanged();
                mPullToRefreshView.onHeaderRefreshComplete();
            }
        }, 1000);
    }
}
