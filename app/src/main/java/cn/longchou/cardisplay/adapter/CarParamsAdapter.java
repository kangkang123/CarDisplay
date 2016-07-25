package cn.longchou.cardisplay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.longchou.cardisplay.R;
import cn.longchou.cardisplay.domain.CarParams;

/**
 * Created by kangkang on 2016/7/19.
 */
public class CarParamsAdapter extends BaseAdapter {
    private Context context;
    private List<CarParams> list;

    public CarParamsAdapter(Context context, List<CarParams> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CarParams getItem(int position) {
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
            convertView=View.inflate(context, R.layout.item_list_params_details, null);
            holder.mName = (TextView) convertView.findViewById(R.id.tv_item_params_detail_name);
            holder.mContent = (TextView) convertView.findViewById(R.id.tv_item_params_detail_content);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        CarParams item = list.get(position);
        holder.mName.setText(item.key);
        holder.mContent.setText(item.value);
        return convertView;
    }

    class ViewHolder{
        private TextView mName;
        private TextView mContent;
    }
}
