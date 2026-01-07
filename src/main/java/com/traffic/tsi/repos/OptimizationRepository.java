package com.traffic.tsi.repos;

import com.traffic.tsi.entities.OptimizationSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OptimizationRepository extends JpaRepository<OptimizationSuggestion, Long> {
    List<OptimizationSuggestion> findByIntersectionId(Long intersectionId);

    OptimizationSuggestion findTopByIntersection_IdOrderByCreatedAtDesc(Long intersectionId);
}