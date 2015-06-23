package com.chanyouji.chanyouji.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanyouji.chanyouji.R;
import com.chanyouji.chanyouji.model.travel.Travel;
import com.chanyouji.chanyouji.model.travel.User;
import com.chanyouji.chanyouji.thread.CacheImageAsyncTask;

import java.util.List;

/**
 * Created by annuo on 2015/6/16.
 */
public class TravelListAdapter extends BaseAdapter {
    private List<Travel> list;
    private Context context;

    public TravelListAdapter(List<Travel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (list != null) {
            ret = list.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        Object ret = null;
        if (list != null) {
            ret = list.get(position);
        }
        return ret;
    }


    @Override
    public long getItemId(int position) {
        int ret = 0;
        if (list != null) {
            Travel travel = list.get(position);
            if (travel != null) {
                ret = travel.getId();
            }
        }
        return ret;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;
        /////////////////////////

        //1、视图复用,convertView转化
        if (convertView != null) {
            ret = convertView;
        } else {
            ret = LayoutInflater
                    .from(context)
                    .inflate(R.layout.fragment_travel_listview_item, parent, false);
        }

        ////////////////////////

        // 2. 处理 ViewHolder 减少 findViewById
        ViewHolder holder = (ViewHolder) ret.getTag();
        //初始化viewholder
        if (holder == null) {
            holder = new ViewHolder();
            holder.imgBg = (ImageView) ret.findViewById(R.id.fragment_travel_listview_bg);
            holder.txtName = (TextView) ret.findViewById(R.id.fragment_travel_listview_name);
            holder.txtStartTime = (TextView) ret.findViewById(R.id.fragment_travel_listview_starttime);
            holder.txtDays = (TextView) ret.findViewById(R.id.fragment_travel_listview_days);
            holder.txtImageCount = (TextView) ret.findViewById(R.id.fragment_travel_listview_imagecount);
            holder.imgIcon = (ImageView) ret.findViewById(R.id.fragment_travel_listview_icon);
            ret.setTag(holder);
        }

        /////////////////////////////
        // 3. 显示内容
        if (list != null) {
            Travel travel = list.get(position);
            if (travel != null) {

                //因为发现使用ollley竟然会出现内存溢出的现象，故而凡是出现图片的地方使用自定义的三级缓存
                holder.imgIcon.setImageResource(R.mipmap.thumbnail_a_default);
                holder.imgBg.setImageResource(R.mipmap.test);

                String frontcoverphotourl = travel.getFrontcoverphotourl();
                holder.imgBg.setTag(frontcoverphotourl);
                if (frontcoverphotourl != null) {
                        //todo 加载图片，并设置到imageview
                        CacheImageAsyncTask task = new CacheImageAsyncTask(holder.imgBg, context);
                        task.execute(frontcoverphotourl);
                    }


//                //todo 给rela设置背景，涉及到volley
//                ImageLoader imageLoader = RequestManger.getInstance().getImageLoader();
//                imageLoader.get(travel.getFrontcoverphotourl(),
//                        ImageLoader.getImageListener(holder.imgBg, R.mipmap.ic_launcher, R.mipmap.ic_launcher));

                holder.txtName.setText(travel.getName() + "");
                holder.txtStartTime.setText(travel.getStartdate() + "");
                holder.txtDays.setText(" / " + travel.getDays() + "天");
                holder.txtImageCount.setText(", " + travel.getPhotoscount() + "图");



                //todo 显示头像，设计volley
                User user = travel.getUser();
                if (user != null) {
                    String url = user.getImage();
                    //为了避免图片错乱的问题，需要给每一次加载图片时，imageview设置加载网址的tag
                    //通过tag传递给task，进行检查
                    holder.imgIcon.setTag(url);
                    if (url != null) {
                        //todo 加载图片，并设置到imageview
                        CacheImageAsyncTask task = new CacheImageAsyncTask(holder.imgIcon, context, "userico");
                        task.execute(url);
                    }
                }
            }

        }

        return ret;
    }

    public static class ViewHolder {
        public ImageView imgBg;
        public TextView txtName;
        public TextView txtStartTime;
        public TextView txtDays;
        public TextView txtImageCount;
        public ImageView imgIcon;

    }



}
