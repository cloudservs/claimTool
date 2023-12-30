package com.cloudservs.claimtool.domain.claim;

import java.util.Date;

public class UnitDetails {
    int index;
    String unit; // UNit no, CLaim Type, Invoice Id etc
    String type; // unitType, Facility Type, Type
    String name; // Name
    double area;
    Date date;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
