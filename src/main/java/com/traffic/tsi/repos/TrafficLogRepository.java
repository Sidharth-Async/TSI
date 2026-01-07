package com.traffic.tsi.repos;

import com.traffic.tsi.entities.TrafficLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrafficLogRepository extends JpaRepository<TrafficLog, Long> {
    List<TrafficLog> findByIntersectionId(Long intersectionId);

    TrafficLog findTopByIntersection_IdOrderByTimestampDesc(Long intersectionId);
}