package com.example.album_admin.model;

/**
 * Created by eis-01 on 31/3/17.
 */

public class Event {


    String spe_id,spe_name;

    public Event() {
    }

    public String getSpe_id() {
        return spe_id;
    }

    public void setSpe_id(String spe_id) {
        this.spe_id = spe_id;
    }

    public String getSpe_name() {
        return spe_name;
    }

    public void setSpe_name(String spe_name) {
        this.spe_name = spe_name;
    }

    @Override
    public String toString() {
        return "Event{" +
                "spe_id='" + spe_id + '\'' +
                ", spe_name='" + spe_name + '\'' +
                '}';
    }
}
