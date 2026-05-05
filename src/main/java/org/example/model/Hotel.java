package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Hotel {
    private Long hotelId;
    private String name;
    private int stars;
    private String description;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public Hotel() {
    }

    public Hotel(Long hotelId, String name, int stars, String description, BigDecimal price, LocalDateTime createdAt) {
        this.hotelId = hotelId;
        this.name = name;
        this.stars = stars;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", stars=" + stars +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                '}';
    }
}
