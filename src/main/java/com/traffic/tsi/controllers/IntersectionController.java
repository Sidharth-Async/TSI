package com.traffic.tsi.controllers;

import com.traffic.tsi.dtos.IntersectionRequest;
import com.traffic.tsi.entities.Intersection;
import com.traffic.tsi.services.IntersectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/intersections")
public class IntersectionController {
    private final IntersectionService intersectionService;

    public IntersectionController(IntersectionService intersectionService) {
        this.intersectionService = intersectionService;
    }

    // 1. CREATE: POST http://localhost:8080/api/intersections
    @PostMapping
    public ResponseEntity<Intersection> create(@RequestBody IntersectionRequest request) {
        Intersection newIntersection = intersectionService.createIntersection(request);
        return ResponseEntity.ok(newIntersection);
    }

    // 2. READ: GET http://localhost:8080/api/intersections
    @GetMapping
    public ResponseEntity<List<Intersection>> getMine() {
        return ResponseEntity.ok(intersectionService.getMyIntersections());
    }
}
