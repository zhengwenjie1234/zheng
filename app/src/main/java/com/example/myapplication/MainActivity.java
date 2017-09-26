package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.utils.XListView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private List<Bean.SubjectsBean.CastsBean> casts;
    private XListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=10&count=3
        lv = (XListView) findViewById(R.id.lv);
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);
        lv.setXListViewListener(this);
        new Thread(new Runnable() {
            private String json;

            @Override
            public void run() {
                String path = "https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=10&count=3";
                final String json = NetWork.getJson(path);
                Gson gson = new Gson();
                final Bean bean = gson.fromJson(json, Bean.class);
                runOnUiThread(new Runnable() {



                    @Override
                    public void run() {

                        //Toast.makeText(MainActivity.this, bean.toString(), Toast.LENGTH_SHORT).show();
                        List<Bean.SubjectsBean> subjects = bean.subjects;
                        casts = subjects.get(0).casts;
                        MyAdapter adapter = new MyAdapter();
                        lv.setAdapter(adapter);
                    }
                });

            }
        }).start();


    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return casts.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                holder = new ViewHolder();
                convertView = View.inflate(MainActivity.this, R.layout.item, null);
                holder.iv= (ImageView) convertView.findViewById(R.id.iv);
                holder.tv= (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(casts.get(position).avatars.large,holder.iv);
            holder.tv.setText(casts.get(position).name);
            return convertView;


        }
    }

    class ViewHolder{
        ImageView iv;
        TextView tv;
    }


}
