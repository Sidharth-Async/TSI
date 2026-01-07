package com.traffic.tsi.entities;

import jakarta.persistence.*;
import com.traffic.tsi.enums.IntersectionType;

@Entity
@Table(name = "intersections")
public class Intersection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private double latitude;

    private double longitude;

    @Enumerated(EnumType.STRING)
    private IntersectionType type;

    private int speedLimit;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }
}
