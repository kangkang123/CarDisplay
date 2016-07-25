package cn.longchou.cardisplay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.longchou.cardisplay.R;
import cn.longchou.cardisplay.domain.CarList;

/**
 * Created by kangkang on 2016/7/19.
 */
public class HorizonTalListAdapter extends BaseAdapter {
    private Context context;
    private List<CarList.Cars> list;

    public HorizonTalListAdapter(Context context,List<CarList.Cars> list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context,R.layout.item_horizontal_listview,null);
            holder.image= (ImageView) convertView.findViewById(R.id.iv_iamge_horizontal);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        CarList.Cars cars = list.get(position);
        Glide.with(context).load(cars.carImgURL).placeholder(R.drawable.car_detail_default).into(holder.image);
        return convertView;
    }
    class ViewHolder{
        ImageView image;
    }
}
