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
public class Travels  implements Serializable {
    List<Travel> list;

    public List<Travel> getList() {
        return list;
    }

    public void setList(List<Travel> list) {
        this.list = list;
    }

    public void pareJSON(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Travel travel = new Travel();
                travel.pareJSON(jsonObject);
                list.add(travel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
