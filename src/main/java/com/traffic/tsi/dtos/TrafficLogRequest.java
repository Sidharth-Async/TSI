package com.traffic.tsi.dtos;

public class TrafficLogRequest {
    private Long roadId;
    private int vehicleCount;
    private double averageSpeed; // In km/h

    // Manual Getters and Setters
    public Long getRoadId() { return roadId; }
    public void setRoadId(Long roadId) { this.roadId = roadId; }

    public int getVehicleCount() { return vehicleCount; }
    public void setVehicleCount(int vehicleCount) { this.vehicleCount = vehicleCount; }

    public double getAverageSpeed() { return averageSpeed; }
    public void setAverageSpeed(double averageSpeed) { this.averageSpeed = averageSpeed; }
}
