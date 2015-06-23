package com.chanyouji.chanyouji.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chanyouji.chanyouji.R;
import com.chanyouji.chanyouji.model.travel.Node;
import com.chanyouji.chanyouji.model.travel.Note;
import com.chanyouji.chanyouji.model.travel.Photo;
import com.chanyouji.chanyouji.model.travel.Travel;
import com.chanyouji.chanyouji.model.travel.TravelDetail;
import com.chanyouji.chanyouji.model.travel.TripDay;
import com.chanyouji.chanyouji.model.travel.User;
import com.chanyouji.chanyouji.model.travel.ZiDingYiTravel;
import com.chanyouji.chanyouji.thread.CacheImageAsyncTask;
import com.chanyouji.chanyouji.ui.adapter.DetailTravelBaseAdapter;
import com.chanyouji.chanyouji.ui.widget.KenBurnsView;
import com.chanyouji.chanyouji.util.RequestManger;
import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class DetailTravelActivity extends Activity {
    private StickyListHeadersListView stickyListHeadersListView;
    private View mFakeHeader;

    //渐变涉及的方法
    private static final String TAG = "NoBoringActionBarActivity";
    private int mActionBarHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private ListView mListView;
    private View mHeader;
    private View mPlaceHolderView;

    private TypedValue mTypedValue = new TypedValue();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();

        setContentView(R.layout.activity_detail_travel);


        stickyListHeadersListView = (StickyListHeadersListView) findViewById(R.id.list);
        KenBurnsView kenBurnsView = (KenBurnsView) findViewById(R.id.header_picture);
        ImageView imageView0 = kenBurnsView.getImageView0();
        ImageView imageView1 = kenBurnsView.getImageView1();
        mHeader = findViewById(R.id.header);

        mListView = stickyListHeadersListView.getWrappedList();



        //取到intent中传递过来的travel，填充headview的各部分
        ImageView userIcon = (ImageView) findViewById(R.id.user_icon);
        TextView headName = (TextView) findViewById(R.id.head_name);
        TextView headDetail = (TextView) findViewById(R.id.head_detail);

        Intent intent = getIntent();
        final Travel travel = (Travel) intent.getSerializableExtra("travel");

        if (travel != null) {

            //显示标题
            headName.setText(travel.getName());
            //显示其他信息
            headDetail.setText(travel.getStartdate() + " | " + travel.getDays() + "天, " + travel.getPhotoscount() + "图");

            //显示headview的背景0
            String headBgUrl = travel.getFrontcoverphotourl();
            imageView0.setTag(headBgUrl);
            if (headBgUrl != null) {
                CacheImageAsyncTask task = new CacheImageAsyncTask(imageView0, this);
                task.execute(headBgUrl);
            }


            //显示动态背景图片1
            User user1 = travel.getUser();
            if (user1 != null) {
                String url = user1.getImage();
                //为了避免图片错乱的问题，需要给每一次加载图片时，imageview设置加载网址的tag
                //通过tag传递给task，进行检查
                imageView1.setTag(url);
                if (url != null) {
                    CacheImageAsyncTask task = new CacheImageAsyncTask(imageView1, this);
                    task.execute(url);
                }
            }


            //显示头像
            User user = travel.getUser();
            if (user != null) {
                String url = user.getImage();
                //为了避免图片错乱的问题，需要给每一次加载图片时，imageview设置加载网址的tag
                //通过tag传递给task，进行检查
                userIcon.setTag(url);
                if (url != null) {
                    //todo 加载图片，并设置到imageview
                    CacheImageAsyncTask task = new CacheImageAsyncTask(userIcon, this, "userico");
                    task.execute(url);
                }
            }


            //getLayoutInflater().inflate(R.layout.view_header_placeholder, null);就无法达到我们想要的效果了
            mFakeHeader = getLayoutInflater().inflate(R.layout.view_header_placeholder, mListView, false);
            mListView.addHeaderView(mFakeHeader);
            View footView = getLayoutInflater().inflate(R.layout.detail_travel_list_footer, null);
            mListView.addFooterView(footView);

            /**
             * 移动header的位置
             * 当ListView滚动的时候，你必须移动header的位置让他和伪造的ListView header保持同步，
             * 注意移动到了actionbar的边界为止。
             * 移动题头.伴随着listview的滚动，你需要移动题头，以跟踪虚拟题头的移动。这些移动以ActionBar的高度为边界。
             * 一旦改为mlistview就会出现大的问题，stickyListHeadersListView中的方法就不会调用了
             */
            stickyListHeadersListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    int scrollY = getScrollY();
                    //sticky actionbar
                    mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
                }
            });

            //准备listview的数据
            int travelId = travel.getId();
            //得到json的返回值
            //得到请求队列
            RequestQueue requestQueue = RequestManger.getInstance().getRequestQueue();
            StringRequest request = new StringRequest(
                    "http://chanyouji.com/api/trips/" + travelId + ".json",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!TextUtils.isEmpty(response))
                                getZiDingYiList(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            );
            requestQueue.add(request);
        }

        //设置actionbar的样式
        setupActionBar();
    }







    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.mipmap.ic_transparent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_travel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //处理其他菜单点击事件
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取滚动位置
     * 获取Scroll位置
     * 布局文件搞定，需要计算出ListView的滚动位置
     * 特别提示，如果listview第一个可视视图位置大于1，需要计算虚拟视图的高度。
     * @return
     */
    public int getScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mFakeHeader.getHeight();
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }


    /**
     * 得到actionbar的高度
     * @return
     */
    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }
        getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());
        return mActionBarHeight;
    }


    private void getZiDingYiList(String response) {
        TravelDetail travelDetail = new TravelDetail();
        travelDetail.pareJSON(response);
        List<TripDay> tripdays = travelDetail.getTripdays();
        if (tripdays != null) {
            List<ZiDingYiTravel> ziDingYiTravels = new ArrayList<>();
            for (int i = 0; i < tripdays.size(); i++) {
                List<Node> nodes = tripdays.get(i).getNodes();
                String tripdate = tripdays.get(i).getTripdate();
                int day = tripdays.get(i).getDay();
                if (nodes != null) {
                    for (int j = 0; j < nodes.size(); j++) {
                        String entryname = nodes.get(j).getEntryname();
                        List<Note> notes = nodes.get(j).getNotes();
                        if (notes != null) {
                            for (int k = 0; k < notes.size(); k++) {
                                int id = notes.get(k).getId();
                                ZiDingYiTravel ziDingYiTravel = new ZiDingYiTravel();
                                if (day != 0) {
                                    ziDingYiTravel.setDay(day);
                                }
                                if (tripdate != null) {
                                    ziDingYiTravel.setTripdate(tripdate);
                                }
                                if (entryname != null && entryname != "" && entryname != "null") {
                                    ziDingYiTravel.setEntryname(entryname);
                                }
                                ziDingYiTravel.setId(id);
                                String description = notes.get(k).getDescription();
                                if (description != null && description != "" && description != "null") {
                                    ziDingYiTravel.setDescription(description);
                                }
                                Photo photo = notes.get(k).getPhoto();
                                if (photo != null) {
                                    String url = photo.getUrl();
                                    if (url != null) {
                                        ziDingYiTravel.setImgUrl(url);
                                    }
                                }
                                ziDingYiTravels.add(ziDingYiTravel);
                            }
                        }
                    }
                }
            }
            //给lsitview设置adapter
            DetailTravelBaseAdapter adapter = new DetailTravelBaseAdapter(DetailTravelActivity.this, ziDingYiTravels);
            stickyListHeadersListView.setAdapter(adapter);
        }
    }

}
