package com.traffic.tsi.services;

import com.traffic.tsi.entities.OptimizationSuggestion;
import com.traffic.tsi.entities.TrafficLog;
import com.traffic.tsi.repos.OptimizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OptimizationService {

    private final OptimizationRepository repository;
    private final RestTemplate restTemplate = new RestTemplate(); // Client to talk to Python

    // URL of your Python ML Service
    private final String ML_ENGINE_URL = "http://localhost:5000/predict";

    public OptimizationService(OptimizationRepository repository) {
        this.repository = repository;
    }

    public OptimizationSuggestion analyzeTraffic(TrafficLog log) {

        // 1. Prepare Data for Python
        Map<String, Object> requestToMl = new HashMap<>();
        requestToMl.put("vehicleCount", log.getVehicleCount());

        // 2. Default fallback values (in case Python is offline)
        int duration = 30;
        String priority = "Low";
        String action = "Standard Timing";

        try {
            // 3. CALL PYTHON ML ENGINE
            // We expect a Map (JSON) back
            Map<String, Object> response = restTemplate.postForObject(ML_ENGINE_URL, requestToMl, Map.class);

            if (response != null) {
                duration = (int) response.get("suggestedDuration");
                priority = (String) response.get("priorityLevel");
                action = (String) response.get("suggestedAction");
            }
        } catch (Exception e) {
            System.err.println("⚠️ ML Engine unreachable. Using fallback logic.");
        }

        // 4. Save the Suggestion
        OptimizationSuggestion suggestion = new OptimizationSuggestion();
        suggestion.setIntersection(log.getIntersection());
        suggestion.setSuggestedDuration(duration);
        suggestion.setPriority(priority);
        suggestion.setRecommendedAction(action);
        suggestion.setCreatedAt(LocalDateTime.now());

        return repository.save(suggestion);
    }
}