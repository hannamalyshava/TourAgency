package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Booking {
    private long bookingId;
    private long userId;
    private long tourId;
    private BigDecimal totalPrice;
    private int people;
    private LocalDateTime createdAt;
    private String status; // PENDING, CONFIRMED, CANCELLED
    private String tourTitle; // Название тура (для отображения)

    public Booking() {}

    public Booking(long bookingId, long userId, long tourId, BigDecimal totalPrice, int people,
                   LocalDateTime createdAt, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.tourId = tourId;
        this.totalPrice = totalPrice;
        this.people = people;
        this.createdAt = createdAt;
        this.status = status;
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTourTitle() {
        return tourTitle;
    }

    public void setTourTitle(String tourTitle) {
        this.tourTitle = tourTitle;
    }

    // Вспомогательный метод для JSTL
    public java.util.Date getCreatedAtAsDate() {
        if (this.createdAt == null) {
            return null;
        }
        return java.sql.Timestamp.valueOf(this.createdAt);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", tourId=" + tourId +
                ", totalPrice=" + totalPrice +
                ", people=" + people +
                ", createdAt=" + createdAt +
                ", status='" + status + '\'' +
                '}';
    }
}
