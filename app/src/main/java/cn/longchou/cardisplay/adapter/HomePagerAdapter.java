package cn.longchou.cardisplay.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.longchou.cardisplay.activity.ImageLookActivity;
import cn.longchou.cardisplay.domain.CarList;

/**
 * 
* @Description: viewPager的的轮播条的填充
*
* @author kangkang 
*
* @date 2015年12月22日 下午11:18:45 
*
 */
public class HomePagerAdapter extends PagerAdapter {

	private ArrayList<String> carImags;
	private Context context;
	private CarList.Cars car;
	List<ImageView> images=new ArrayList<ImageView>();
	public HomePagerAdapter(Context context, List<ImageView> images, List<String> carImags, CarList.Cars car) {
		this.images=images;
		this.context=context;
		this.carImags=(ArrayList<String>) carImags;
		this.car=car;
	}


	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}

	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		final int pos=position % images.size();
		final ImageView view=images.get(pos);


		//当是详情页传进来的时候，点击图片显示大图
		if(carImags!=null)
		{
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent intent=new Intent(context,ImageLookActivity.class);
					intent.putExtra("image_index", position);
					intent.putStringArrayListExtra("carImags",carImags);
					intent.putExtra("carName",car.carName);
					intent.putExtra("carID",car.carID);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				}
			});
		}


		//判断容器中是否有内容，如果有内容的话就移除里面的内容
		ViewParent vp =view.getParent();  
        if (vp!=null){  
            ViewGroup parent = (ViewGroup)vp;  
            parent.removeView(view);  
        }  
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
