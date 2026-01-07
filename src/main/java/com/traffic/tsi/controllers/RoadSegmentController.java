package com.traffic.tsi.controllers;

import com.traffic.tsi.dtos.RoadSegmentRequest;
import com.traffic.tsi.entities.RoadSegment;
import com.traffic.tsi.services.RoadSegmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/roads")
public class RoadSegmentController {

    private final RoadSegmentService roadService;

    public RoadSegmentController(RoadSegmentService roadService) {
        this.roadService = roadService;
    }

    @PostMapping
    public ResponseEntity<RoadSegment> create(@RequestBody RoadSegmentRequest request) {
        return ResponseEntity.ok(roadService.createRoad(request));
    }

    @GetMapping
    public ResponseEntity<List<RoadSegment>> getMine() {
        return ResponseEntity.ok(roadService.getMyRoads());
    }
}
