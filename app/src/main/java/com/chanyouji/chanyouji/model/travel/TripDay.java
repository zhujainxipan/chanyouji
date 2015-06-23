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
public class TripDay implements Serializable {
    private int id;
    private String tripdate;
    private int day;
    private int updatedat;
    private Destination destination;
    private List<Node> nodes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTripdate() {
        return tripdate;
    }

    public void setTripdate(String tripdate) {
        this.tripdate = tripdate;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(int updatedat) {
        this.updatedat = updatedat;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void pareJSON(JSONObject object) {
        try {
            tripdate = object.getString("trip_date");
            day = object.getInt("day");
            JSONArray jsonArray = object.getJSONArray("nodes");
            nodes = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Node node = new Node();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                node.pareJSON(jsonObject);
                nodes.add(node);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
