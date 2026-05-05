package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Tour {
    private long tourId;
    private String title;
    private String description;
    private String country;
    private String city;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private int availableSeats;
    private Long hotelId;      // nullable
    private Long programId;    // nullable
    private LocalDateTime createdAt;

    public Tour() {}

    public Tour(long tourId, String title, String description, String country, String city,
                BigDecimal price, LocalDate startDate, LocalDate endDate,
                int availableSeats, Long hotelId, Long programId, LocalDateTime createdAt) {
        this.tourId = tourId;
        this.title = title;
        this.description = description;
        this.country = country;
        this.city = city;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availableSeats = availableSeats;
        this.hotelId = hotelId;
        this.programId = programId;
        this.createdAt = createdAt;
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "tourId=" + tourId +
                ", title='" + title + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", availableSeats=" + availableSeats +
                '}';
    }

    public Date getStartDateAsDate() {
        // Преобразуем LocalDate (без времени) в старый java.util.Date
        if (this.startDate == null) {
            return null;
        }
        return Date.from(this.startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getEndDateAsDate() {
        // Преобразуем LocalDate (без времени) в старый java.util.Date
        if (this.endDate == null) {
            return null;
        }
        return Date.from(this.endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
