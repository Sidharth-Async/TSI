package com.traffic.tsi.services;

import com.traffic.tsi.dtos.RoadSegmentRequest;
import com.traffic.tsi.entities.Intersection;
import com.traffic.tsi.entities.RoadSegment;
import com.traffic.tsi.entities.User;
import com.traffic.tsi.repos.IntersectionRepository;
import com.traffic.tsi.repos.RoadSegmentRepository;
import com.traffic.tsi.repos.user_Repo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadSegmentService {

    private final RoadSegmentRepository roadRepository;
    private final IntersectionRepository intersectionRepository;
    private final user_Repo userRepository;

    public RoadSegmentService(RoadSegmentRepository roadRepository, IntersectionRepository intersectionRepository,user_Repo userRepository){
        this.intersectionRepository = intersectionRepository;
        this.roadRepository = roadRepository;
        this.userRepository = userRepository;
    }

    public RoadSegment createRoad(RoadSegmentRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Fetch the Start Node
        Intersection startNode = intersectionRepository.findById(request.getStartIntersectionId())
                .orElseThrow(() -> new RuntimeException("Start Intersection not found"));

        // 3. Fetch the End Node
        Intersection endNode = intersectionRepository.findById(request.getEndIntersectionId())
                .orElseThrow(() -> new RuntimeException("End Intersection not found"));

        // 4. Create the Road
        RoadSegment road = new RoadSegment();
        road.setName(request.getName());
        road.setLengthInMeters(request.getLengthInMeters());
        road.setSpeedLimit(request.getSpeedLimit());
        road.setLanes(request.getLanes());

        // 5. Connect the Graph
        road.setStartIntersection(startNode);
        road.setEndIntersection(endNode);
        road.setCreatedBy(user);

        return roadRepository.save(road);
    }

    public List<RoadSegment> getMyRoads() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        return roadRepository.findByCreatedBy_Userid(user.getUserid());
    }

}

