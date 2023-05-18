package com.radproject.hms.models;

import java.io.Serializable;

public class CropModel implements Serializable {
    String crop_name;
    int crop_number;
    int estimate_days;

    public CropModel() {

    }

    public CropModel(String crop_name, int crop_number, int estimate_days) {
        this.crop_name = crop_name;
        this.crop_number = crop_number;
        this.estimate_days = estimate_days;
    }

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public int getCrop_number() {
        return crop_number;
    }

    public void setCrop_number(int crop_number) {
        this.crop_number = crop_number;
    }

    public int getEstimate_days() {
        return estimate_days;
    }

    public void setEstimate_days(int estimate_days) {
        this.estimate_days = estimate_days;
    }

    @Override
    public String toString() {
        return "CropModel{" +
                "crop_name='" + crop_name + '\'' +
                '}';
    }
}
