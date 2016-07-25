package cn.longchou.cardisplay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.longchou.cardisplay.R;
import cn.longchou.cardisplay.domain.CarList;

/**
 * Created by kangkang on 2016/7/18.
 */
public class CarDisplayAdapter extends BaseAdapter {
    private Context context;
    private List<CarList.Cars> list;
    private int clickTemp = -1;

    public CarDisplayAdapter(List<CarList.Cars> data, Context context) {
        this.list = data;
        this.context = context;
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
        if(convertView==null)
        {
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_display_car,null);
            holder.mImageView= (ImageView) convertView.findViewById(R.id.iv_car_list_display);
            holder.mTextView= (TextView) convertView.findViewById(R.id.tv_car_list_des);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        // 点击改变选中listItem的背景色
        if (clickTemp == position) {
            convertView.setBackgroundColor(Color.argb(10,00,00,00));
        } else {
            convertView.setBackgroundColor(Color.rgb(255,255,255));
        }

        CarList.Cars des=list.get(position);
        holder.mTextView.setText(des.carName);
        Glide.with(context).load(des.carImgURL).placeholder(R.drawable.car_detail_default).into(holder.mImageView);
        return convertView;
    }
    class ViewHolder{
        ImageView mImageView;
        TextView mTextView;
    }


    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

}
