package com.traffic.tsi.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "traffic_logs")
public class TrafficLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "intersection_id", nullable = false)
    private Intersection intersection;

    // TRAFFIC DATA (For the Analytics Charts)
    private int vehicleCount;    // e.g. 50 cars
    private double averageSpeed; // e.g. 30 km/h (slower than limit = congestion!)

    // AUTOMATIC TIMESTAMP
    @CreationTimestamp
    private LocalDateTime timestamp;

    // CALCULATED FIELD (Density)
    // We don't necessarily need to store this, but we can calculate it.
    // Congestion Level = (vehicleCount * avgCarLength) / (roadLength * lanes)
    private double congestionLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getCongestionLevel() {
        return congestionLevel;
    }

    public void setCongestionLevel(double congestionLevel) {
        this.congestionLevel = congestionLevel;
    }
}
