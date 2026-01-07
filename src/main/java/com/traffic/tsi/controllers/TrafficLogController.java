package com.traffic.tsi.controllers;

import com.traffic.tsi.dtos.TrafficLogRequest;
import com.traffic.tsi.entities.Intersection;
import com.traffic.tsi.entities.TrafficLog;
import com.traffic.tsi.entities.OptimizationSuggestion;
import com.traffic.tsi.repos.IntersectionRepository;
import com.traffic.tsi.repos.TrafficLogRepository;
import com.traffic.tsi.repos.OptimizationRepository;
import com.traffic.tsi.services.OptimizationService;
import com.traffic.tsi.services.TrafficLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/traffic")
@CrossOrigin(origins = "http://localhost:5173")
public class TrafficLogController {

    private final TrafficLogService trafficService;
    private final OptimizationService optimizationService;
    private final IntersectionRepository intersectionRepository;
    private final TrafficLogRepository trafficLogRepository;
    private final OptimizationRepository optimizationRepository;

    // ✅ ADDED THIS FIELD (Was missing in your snippet)
    private final SimpMessagingTemplate messagingTemplate;

    public TrafficLogController(TrafficLogService trafficService,
                                OptimizationService optimizationService,
                                IntersectionRepository intersectionRepository,
                                TrafficLogRepository trafficLogRepository,
                                OptimizationRepository optimizationRepository,
                                SimpMessagingTemplate messagingTemplate) { // ✅ Injected here
        this.trafficService = trafficService;
        this.optimizationService = optimizationService;
        this.intersectionRepository = intersectionRepository;
        this.trafficLogRepository = trafficLogRepository;
        this.optimizationRepository = optimizationRepository;
        this.messagingTemplate = messagingTemplate; // ✅ Assigned here
    }

    @PostMapping("/log")
    public ResponseEntity<?> logTraffic(@RequestBody TrafficLogRequest request) {
        try {
            // 1. Save Log
            TrafficLog log = trafficService.logTraffic(request);

            // 2. Run AI
            optimizationService.analyzeTraffic(log);

            // 3. Prepare Real-Time Update Data
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("id", log.getIntersection().getId());
            updateData.put("vehicleCount", log.getVehicleCount());
            updateData.put("speed", log.getAverageSpeed());

            // Fetch latest AI suggestion
            OptimizationSuggestion suggestion = optimizationRepository
                    .findTopByIntersection_IdOrderByCreatedAtDesc(log.getIntersection().getId());

            if (suggestion != null) {
                // ✅ FIXED METHOD NAMES (Matching your Entity)
                updateData.put("aiDecision", suggestion.getRecommendedAction());
                updateData.put("aiDuration", suggestion.getSuggestedDuration());
                updateData.put("priority", suggestion.getPriority());
            }

            // 4. BROADCAST TO WEBSOCKET
            // Sent to anyone listening on "/topic/traffic-updates"
            messagingTemplate.convertAndSend("/topic/traffic-updates", (Object) updateData);

            return ResponseEntity.ok("Logged, Optimized & Broadcasted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/live-map")
    public ResponseEntity<List<Map<String, Object>>> getLiveMapData() {
        List<Intersection> intersections = intersectionRepository.findAll();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Intersection intersection : intersections) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", intersection.getId());
            data.put("name", intersection.getName());
            data.put("lat", intersection.getLatitude());
            data.put("lng", intersection.getLongitude());

            // 1. Get Latest Traffic Log
            TrafficLog latestLog = trafficLogRepository.findTopByIntersection_IdOrderByTimestampDesc(intersection.getId());
            if (latestLog != null) {
                data.put("vehicleCount", latestLog.getVehicleCount());
                data.put("speed", latestLog.getAverageSpeed());
            } else {
                data.put("vehicleCount", 0);
                data.put("speed", 0);
            }

            // 2. Get Latest AI Suggestion
            OptimizationSuggestion latestSuggestion = optimizationRepository.findTopByIntersection_IdOrderByCreatedAtDesc(intersection.getId());

            if (latestSuggestion != null) {
                // ✅ FIXED METHOD NAMES HERE TOO
                data.put("aiDecision", latestSuggestion.getRecommendedAction());
                data.put("aiDuration", latestSuggestion.getSuggestedDuration());
                data.put("priority", latestSuggestion.getPriority());
            } else {
                data.put("aiDecision", null);
            }

            responseList.add(data);
        }
        return ResponseEntity.ok(responseList);
    }
}