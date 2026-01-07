package com.traffic.tsi.services;

import com.traffic.tsi.dtos.IntersectionRequest;
import com.traffic.tsi.entities.Intersection;
import com.traffic.tsi.entities.User;
import com.traffic.tsi.enums.IntersectionType;
import com.traffic.tsi.repos.IntersectionRepository;
import com.traffic.tsi.repos.user_Repo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntersectionService {
    private final IntersectionRepository intersectionRepository;
    private final user_Repo userRepository;

    public IntersectionService(IntersectionRepository intersectionRepository, user_Repo userRepository) {
        this.intersectionRepository = intersectionRepository;
        this.userRepository = userRepository;
    }

    public Intersection createIntersection(IntersectionRequest request) {
        // 1. Get the currently logged-in user's EMAIL from the security context
        // (This was set in the JWT Filter)
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("DEBUG: Email from Token: '" + email + "'");

        boolean exists = userRepository.existsByEmail(email);
        System.out.println("DEBUG: Does this email exist in DB? " + exists);
        // 2. Find the full User Entity from the DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Create the Intersection Object
        Intersection intersection = new Intersection();
        intersection.setName(request.getName());
        intersection.setLatitude(request.getLatitude());
        intersection.setLongitude(request.getLongitude());
        // Force Java to see the correct Enum on both sides
        intersection.setType(request.getType());

        // 4. LINK IT: This Intersection belongs to this User
        intersection.setCreatedBy(user);

        return intersectionRepository.save(intersection);
    }

    public List<Intersection> getMyIntersections() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch only intersections created by this user
        return intersectionRepository.findByCreatedBy_Userid(user.getUserid());
    }
}
