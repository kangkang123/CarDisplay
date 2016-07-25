package cn.longchou.cardisplay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.longchou.cardisplay.R;
import cn.longchou.cardisplay.global.Constant;


/**
 * @author kangkang
 * @Description: 品牌的adapter
 * @date 2016年2月18日 下午2:02:54
 */
public class MyHotBrandAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private String choose;
    private int clickTemp = -1;

    public MyHotBrandAdapter(Context context, List<String> list, String choose) {
        this.context = context;
        this.list = list;
        this.choose = choose;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_dl_my_buy, null);
            holder.choose = (TextView) convertView.findViewById(R.id.tv_my_buy_right_choose);
            holder.tick = (ImageView) convertView.findViewById(R.id.iv_my_buy_right_check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String item = getItem(position);
        holder.choose.setText(item);



        //如果是城市的话
        if (choose.equals("city")) {
            if (item.equals(Constant.CityChoose)) {
                holder.tick.setVisibility(View.VISIBLE);
                clickTemp=position;
            } else {
                holder.tick.setVisibility(View.INVISIBLE);
            }
        }else  if (choose.equals("price")) {
            if (item.equals(Constant.PriceChoose)) {
                holder.tick.setVisibility(View.VISIBLE);
                clickTemp=position;
            } else {
                holder.tick.setVisibility(View.INVISIBLE);
            }
        }

        // 点击改变选中listItem的背景色
        if (clickTemp == position) {
            convertView.setBackgroundColor(Color.argb(10,00,00,00));
        } else {
            convertView.setBackgroundColor(Color.rgb(255,255,255));
        }

        return convertView;
    }

    class ViewHolder {
        private TextView choose;
        private ImageView tick;
    }

    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

}
