package com.traffic.tsi.repos;

import com.traffic.tsi.entities.Intersection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntersectionRepository extends JpaRepository<Intersection, Long> {

    List<Intersection> findByCreatedBy_Userid(String userId);
}
