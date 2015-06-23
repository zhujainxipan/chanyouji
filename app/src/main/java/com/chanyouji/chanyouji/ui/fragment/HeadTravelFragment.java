package com.chanyouji.chanyouji.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.chanyouji.chanyouji.R;
import com.chanyouji.chanyouji.util.RequestManger;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadTravelFragment extends Fragment {


    public HeadTravelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_head_travel, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_head_travel_img);
        Bundle args = getArguments();
        if (args != null) {
            String url = args.getString("imgUrl");
            ImageLoader imageLoader = RequestManger.getInstance().getImageLoader();
            imageLoader.get(url,
                    ImageLoader.getImageListener(imageView, R.mipmap.test, R.mipmap.test));
        }

        return view;
    }


}
