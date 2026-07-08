
package in.ssf.booking.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import in.ssf.booking.enums.BookingStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long providerId;

    @Column(nullable = false)
    private Long serviceId;

    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false)
    private LocalTime bookingTime;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 1000)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatusEnum status = BookingStatusEnum.PENDING;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
