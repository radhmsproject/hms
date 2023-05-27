package com.radproject.hms.models;

public class CostModel {

    String Cost_Item;
    String Unit_of_Measurement;
    int Number_of_Units;
    double unit_cost;

    public String getCost_Item() {
        return Cost_Item;
    }

    public void setCost_Item(String cost_Item) {
        Cost_Item = cost_Item;
    }

    public String getUnit_of_Measurement() {
        return Unit_of_Measurement;
    }

    public void setUnit_of_Measurement(String unit_of_Measurement) {
        Unit_of_Measurement = unit_of_Measurement;
    }

    public int getNumber_of_Units() {
        return Number_of_Units;
    }

    public void setNumber_of_Units(int number_of_Units) {
        Number_of_Units = number_of_Units;
    }

    public double getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(double unit_cost) {
        this.unit_cost = unit_cost;
    }
}
