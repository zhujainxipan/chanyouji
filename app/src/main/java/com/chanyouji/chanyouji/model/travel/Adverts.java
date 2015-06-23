package com.chanyouji.chanyouji.model.travel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by annuo on 2015/6/15.
 */
public class Adverts implements Serializable {
    List<Advert> list;

    public List<Advert> getList() {
        return list;
    }

    public void setList(List<Advert> list) {
        this.list = list;
    }

    public void parseJSON(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Advert advert = new Advert();
                advert.parseJSON(jsonObject);
                list.add(advert);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
