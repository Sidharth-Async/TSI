package com.traffic.tsi.repos;

import com.traffic.tsi.entities.RoadSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadSegmentRepository extends JpaRepository<RoadSegment, Long> {
    List<RoadSegment> findByCreatedBy_Userid(String userId);
}
