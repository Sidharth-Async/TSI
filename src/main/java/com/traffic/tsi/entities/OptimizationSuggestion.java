package com.traffic.tsi.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "optimization_suggestions")
public class OptimizationSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "intersection_id", nullable = false)
    private Intersection intersection;

    // What is the problem? (e.g. "High Congestion Detected")
    private String issueType;

    // What is the solution? (e.g. "Increase Green Light Duration by 20s")
    private String recommendedAction;

    private int suggestedDuration;

    // Priority level (LOW, MEDIUM, CRITICAL)
    private String priority;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public int getSuggestedDuration() {
        return suggestedDuration;
    }
    public void setSuggestedDuration(int suggestedDuration) {
        this.suggestedDuration = suggestedDuration;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Intersection getIntersection() {
        return intersection;
    }
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }
    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }
    public String getRecommendedAction() { return recommendedAction; }
    public void setRecommendedAction(String recommendedAction) { this.recommendedAction = recommendedAction; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}