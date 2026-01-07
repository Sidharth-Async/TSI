package com.traffic.tsi.dtos;

import com.traffic.tsi.enums.IntersectionType;

public class IntersectionRequest {
    private String name;       // e.g. "Times Square"
    private double latitude;   // e.g. 40.7580
    private double longitude;  // e.g. -73.9855
    private IntersectionType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public IntersectionType getType() {
        return type;
    }

    public void setType(IntersectionType type) {
        this.type = type;
    }
}
