package cn.longchou.cardisplay.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.longchou.cardisplay.R;
import cn.longchou.cardisplay.adapter.HomePagerAdapter;
import cn.longchou.cardisplay.base.BaseActivity;
import cn.longchou.cardisplay.domain.CarDetail;
import cn.longchou.cardisplay.global.Constant;
import cn.longchou.cardisplay.utils.UIUtils;

/**
 * Created by kangkang on 2016/7/19.
 */
public class ImageLookActivity extends BaseActivity {

    ImageView mLeft;
    ImageView mRight;
    ViewPager mViewPager;
    TextView mPosition;
    TextView mTitle;
    TextView mChoose;
    ArrayList<String> carImags;
    int position;
    String carName;
    String carID;
    ImageView mFresh;
    ImageView mLogo;
    ImageView mPrice;
    public List<CarDetail.CarImages> carImgs;

    @Override
    public void initView() {
        setContentView(R.layout.activity_image_look);

        mLeft = (ImageView) findViewById(R.id.iv_image_left);
        mRight = (ImageView) findViewById(R.id.iv_image_right);
        mPosition = (TextView) findViewById(R.id.tv_image_position);
        mTitle = (TextView) findViewById(R.id.tv_my_news_title);
        mChoose = (TextView) findViewById(R.id.tv_title_city_choose);

        mViewPager = (ViewPager) findViewById(R.id.vp_image_look);


        mFresh = (ImageView) findViewById(R.id.iv_my_title_login);
        mLogo = (ImageView) findViewById(R.id.iv_my_news_title);
        mPrice = (ImageView) findViewById(R.id.iv_price_choose);

    }

    @Override
    public void initData() {

        mChoose.setVisibility(View.GONE);
        mLogo.setVisibility(View.INVISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        mPrice.setImageResource(R.drawable.back);

        Intent intent = getIntent();
        if(intent!=null)
        {
            carImags= intent.getStringArrayListExtra("carImags");
            position= intent.getIntExtra("image_index", -1);
            carName=intent.getStringExtra("carName");
            carID=intent.getStringExtra("carID");
            mTitle.setText(carName);
        }

        List<ImageView> images=new ArrayList<ImageView>();
        for(int i=0;i<carImags.size();i++){
            ImageView image=new ImageView(getApplicationContext());
            Glide.with(this).load(carImags.get(i)).placeholder(R.drawable.car_detail_default).into(image);
            images.add(image);
        }
        HomePagerAdapter adapter=new HomePagerAdapter(ImageLookActivity.this,images,null,null);
        mViewPager.setAdapter(adapter);


        if(position!=-1){
            mViewPager.setCurrentItem(position);
            mPosition.setText(position+1+"/"+images.size());
        }else{
            mPosition.setText("1/"+images.size());
        }

    }

    @Override
    public void initListener() {

        mLeft.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mFresh.setOnClickListener(this);
        mPrice.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int pos=position+1;
                if(carImags.size()>0)
                {
                    mPosition.setText(pos+"/"+carImags.size());
                }else{
                    mPosition.setText(pos+"/"+carImgs.size());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void processClick(View v) {

        switch (v.getId()){

            case R.id.iv_image_left:
                int current=mViewPager.getCurrentItem();
                if(current>1){
                    mViewPager.setCurrentItem(current-1);
                }else{
                    UIUtils.showToastSafe("已经是第一张了");
                }
                break;
            case R.id.iv_image_right:
                int cur=mViewPager.getCurrentItem();
                if(cur<carImags.size()-1){
                    mViewPager.setCurrentItem(cur+1);
                }else{
                    UIUtils.showToastSafe("已经是最后一张");
                }
                break;
            case R.id.iv_price_choose:
                finish();
                break;
            case R.id.iv_my_title_login:
                //刷新数据
                getServerData();
                break;
        }
    }

    //获取车辆详情
    private void getServerData() {
        HttpUtils http=new HttpUtils();
        String url= Constant.RequestCarDetail+carID;
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
        for(int i=0;i<carImags.size();i++){
            ImageView image=new ImageView(getApplicationContext());
            Glide.with(this).load(carImags.get(i)).placeholder(R.drawable.car_detail_default).into(image);
            images.add(image);
        }

        HomePagerAdapter adapter=new HomePagerAdapter(ImageLookActivity.this,images,null,null);
        mViewPager.setAdapter(adapter);
        mPosition.setText("1/"+images.size());
    }
}
