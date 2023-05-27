package com.radproject.hms.models;

import java.util.ArrayList;

public class ActivityModel {

    String activity_doc_id;
    String cultivation_id;
    String activity_name;
    String planned_date;
    ArrayList<CostModel> costList;

    public String getCultivation_id() {
        return cultivation_id;
    }

    public void setCultivation_id(String cultivation_id) {
        this.cultivation_id = cultivation_id;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getPlanned_date() {
        return planned_date;
    }

    public void setPlanned_date(String planned_date) {
        this.planned_date = planned_date;
    }

    public ArrayList<CostModel> getCostList() {
        return costList;
    }

    public void setCostList(ArrayList<CostModel> costList) {
        this.costList = costList;
    }

    public String getActivity_doc_id() {
        return activity_doc_id;
    }

    public void setActivity_doc_id(String activity_doc_id) {
        this.activity_doc_id = activity_doc_id;
    }
}
