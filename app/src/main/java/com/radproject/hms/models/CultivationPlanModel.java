package com.radproject.hms.models;

import java.util.ArrayList;

public class CultivationPlanModel {

    String Cultivation_ID;
    String Cultivation_CROP_ID;
    ArrayList<String> Cultivation_Farm_ID_list;
    String Cultivation_Start_date;
    String Cultivation_End_date;
    String Cultivation_Plan_name;
    String Status;

    public String getCultivation_ID() {
        return Cultivation_ID;
    }

    public void setCultivation_ID(String cultivation_ID) {
        Cultivation_ID = cultivation_ID;
    }

    public String getCultivation_CROP_ID() {
        return Cultivation_CROP_ID;
    }

    public void setCultivation_CROP_ID(String cultivation_CROP_ID) {
        Cultivation_CROP_ID = cultivation_CROP_ID;
    }

    public ArrayList<String> getCultivation_Farm_ID_list() {
        return Cultivation_Farm_ID_list;
    }

    public void setCultivation_Farm_ID_list(ArrayList<String> cultivation_Farm_ID_list) {
        Cultivation_Farm_ID_list = cultivation_Farm_ID_list;
    }

    public String getCultivation_Start_date() {
        return Cultivation_Start_date;
    }

    public void setCultivation_Start_date(String cultivation_Start_date) {
        Cultivation_Start_date = cultivation_Start_date;
    }

    public String getCultivation_End_date() {
        return Cultivation_End_date;
    }

    public void setCultivation_End_date(String cultivation_End_date) {
        Cultivation_End_date = cultivation_End_date;
    }

    public String getCultivation_Plan_name() {
        return Cultivation_Plan_name;
    }

    public void setCultivation_Plan_name(String cultivation_Plan_name) {
        Cultivation_Plan_name = cultivation_Plan_name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
