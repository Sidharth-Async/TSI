package com.traffic.tsi.dtos;

public class RoadSegmentRequest {

    private String name;            // e.g. "Main Street"
    private double lengthInMeters;  // e.g. 500.0
    private int speedLimit;         // e.g. 60
    private int lanes;              // e.g. 2

    // These link to your existing Intersections
    private Long startIntersectionId;
    private Long endIntersectionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLengthInMeters() {
        return lengthInMeters;
    }

    public void setLengthInMeters(double lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public int getLanes() {
        return lanes;
    }

    public void setLanes(int lanes) {
        this.lanes = lanes;
    }

    public Long getStartIntersectionId() {
        return startIntersectionId;
    }

    public void setStartIntersectionId(Long startIntersectionId) {
        this.startIntersectionId = startIntersectionId;
    }

    public Long getEndIntersectionId() {
        return endIntersectionId;
    }

    public void setEndIntersectionId(Long endIntersectionId) {
        this.endIntersectionId = endIntersectionId;
    }
}
