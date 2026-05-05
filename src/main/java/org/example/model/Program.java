package org.example.model;

import java.time.LocalDateTime;

public class Program {
    private Long programId;
    private String name;
    private String description;
    private Integer  duration;
    private LocalDateTime createdAt;

    public Program() {
    }

    public Program(Long programId, String name, String description, Integer  duration, LocalDateTime createdAt) {
        this.programId = programId;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.createdAt = createdAt;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer  getDuration() {
        return duration;
    }

    public void setDuration(Integer  duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programId=" + programId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", createdAt=" + createdAt +
                '}';
    }
}
