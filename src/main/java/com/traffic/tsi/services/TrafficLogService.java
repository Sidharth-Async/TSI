package com.traffic.tsi.services;

import com.traffic.tsi.dtos.TrafficLogRequest;
import com.traffic.tsi.entities.Intersection;
import com.traffic.tsi.entities.TrafficLog;


import com.traffic.tsi.repos.IntersectionRepository;
import com.traffic.tsi.repos.TrafficLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrafficLogService {

    private final TrafficLogRepository trafficLogRepository;
    private final IntersectionRepository intersectionRepository;

    public TrafficLogService(TrafficLogRepository trafficLogRepository,
                             IntersectionRepository intersectionRepository) {
        this.trafficLogRepository = trafficLogRepository;
        this.intersectionRepository = intersectionRepository;
    }

    public TrafficLog logTraffic(TrafficLogRequest request) {
        // 1. FIX: Look up Intersection instead of Road
        // We treat 'roadId' from Python as 'intersectionId'
        Intersection intersection = intersectionRepository.findById(request.getRoadId())
                .orElseThrow(() -> new RuntimeException("Intersection not found with ID: " + request.getRoadId()));

        // 2. Create the Log
        TrafficLog log = new TrafficLog();
        log.setIntersection(intersection); // <--- Link to Intersection
        log.setVehicleCount(request.getVehicleCount());
        log.setAverageSpeed(request.getAverageSpeed());
        log.setTimestamp(LocalDateTime.now());

        // 3. Save
        return trafficLogRepository.save(log);
    }

    // ... keep getTrafficHistory method as is ...
    public java.util.List<TrafficLog> getTrafficHistory(Long roadId) {
        // You might need to update this to findByIntersectionId too
        return trafficLogRepository.findByIntersectionId(roadId);
    }
}