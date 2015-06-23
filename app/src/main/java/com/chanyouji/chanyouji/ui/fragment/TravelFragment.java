package com.chanyouji.chanyouji.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chanyouji.chanyouji.R;
import com.chanyouji.chanyouji.model.travel.Advert;
import com.chanyouji.chanyouji.model.travel.Adverts;
import com.chanyouji.chanyouji.model.travel.Travel;
import com.chanyouji.chanyouji.model.travel.Travels;
import com.chanyouji.chanyouji.ui.activity.DetailTravelActivity;
import com.chanyouji.chanyouji.ui.adapter.HeadTravelFragmentAdapter;
import com.chanyouji.chanyouji.ui.adapter.TravelListAdapter;
import com.chanyouji.chanyouji.util.RequestManger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class TravelFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2<ListView> {


    private PullToRefreshListView pullToRefreshListView;
    private int currentPage = 1;
    private TravelListAdapter travelListAdapter;
    private List<Travel> list;
    private int currentTravelId;

    public TravelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_travel, container, false);

        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //headview是一个viewpager，需要做如下的配置
        View myHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_travel_listview_head, null);
        // DEFAULT
        final ViewPager defaultViewpager = (ViewPager) myHeadView.findViewById(R.id.viewpager_default);
        final CircleIndicator defaultIndicator = (CircleIndicator) myHeadView.findViewById(R.id.indicator_default);

        //得到json的返回值
        //得到请求队列
        RequestQueue requestQueue = RequestManger.getInstance().getRequestQueue();
        StringRequest request = new StringRequest(
                "http://chanyouji.com/api/adverts.json?name=app_featured_banner_android&channel=wandoujiapromo",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            Adverts adverts = new Adverts();
                            adverts.parseJSON(response);
                            List<Advert> list = adverts.getList();
                            List<Fragment> fragments = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                HeadTravelFragment headTravelFragment = new HeadTravelFragment();
                                Bundle args = new Bundle();
                                args.putString("imgUrl", list.get(i).getImageurl());
                                headTravelFragment.setArguments(args);
                                fragments.add(headTravelFragment);
                            }
                            HeadTravelFragmentAdapter headTravelFragmentAdapter =
                                    new HeadTravelFragmentAdapter(getChildFragmentManager(), fragments);
                            defaultViewpager.setAdapter(headTravelFragmentAdapter);
                            defaultIndicator.setViewPager(defaultViewpager);
                        }

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


        //取pulltorefresh
        pullToRefreshListView = (PullToRefreshListView) getView().findViewById(R.id.fragment_travel_listview);


        //给pullToRefreshListView添加headview
        pullToRefreshListView.getRefreshableView().addHeaderView(myHeadView, null, false);

        //给pullToRefreshListView添加点击事件
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailTravelActivity.class);

                Log.d("position", position + "");
                position = position - pullToRefreshListView.getRefreshableView().getHeaderViewsCount();
                Log.d("position2", position + "");
                //准备传递给DetailTravelActivity的数据
                Travel travel = list.get(position);
                if (travel != null) {
                    intent.putExtra("travel", travel);
                }

                getActivity().startActivity(intent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        //下拉刷新和上拉加载
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(this);

        RequestQueue requestQueue = RequestManger.getInstance().getRequestQueue();
        StringRequest request = new StringRequest(
                "http://chanyouji.com/api/trips/featured.json?page=1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            Travels travels = new Travels();
                            travels.pareJSON(response);
                            list = travels.getList();
                            //记住最顶部的游记id，作为下拉刷新的判断
                            if (list != null) {
                                Travel travel = list.get(0);
                                if (travel != null) {
                                    currentTravelId = travel.getId();
                                }
                            }
                            travelListAdapter = new TravelListAdapter(list, getActivity());
                            pullToRefreshListView.setAdapter(travelListAdapter);
                        }

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

    /**
     * 下拉
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        RequestQueue requestQueue = RequestManger.getInstance().getRequestQueue();
        StringRequest request = new StringRequest(
                "http://chanyouji.com/api/trips/featured.json?page=1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //关闭刷新的操作
                        pullToRefreshListView.onRefreshComplete();
                        if (!TextUtils.isEmpty(response)) {
                            Travels travels = new Travels();
                            travels.pareJSON(response);
                            List<Travel> list1 = travels.getList();
                            if (list1 != null) {
                                Travel travel = list1.get(0);
                                if (travel != null) {
                                    int id = travel.getId();
                                    if (id != currentTravelId) {
                                        list.addAll(1, list1);
                                        travelListAdapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }

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


    /**
     * 上拉
     *
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        currentPage = currentPage + 1;
        RequestQueue requestQueue = RequestManger.getInstance().getRequestQueue();
        StringRequest request = new StringRequest(
                "http://chanyouji.com/api/trips/featured.json?page=" + currentPage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //关闭刷新的操作
                        pullToRefreshListView.onRefreshComplete();
                        if (!TextUtils.isEmpty(response)) {
                            Travels travels = new Travels();
                            travels.pareJSON(response);
                            List<Travel> list1 = travels.getList();
                            if (list1 != null) {
                                list.addAll(list1);
                                travelListAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_LONG).show();
                            }
                        }

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

}
