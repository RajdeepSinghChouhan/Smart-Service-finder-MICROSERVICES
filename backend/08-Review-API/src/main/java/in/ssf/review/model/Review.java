
package in.ssf.review.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    @NotNull
    @Column(nullable = false)
    private Long providerId;

    @NotNull
    @Column(nullable = false)
    private Long serviceId;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rating;

    @NotBlank
    @Size(min = 5, max = 500)
    @Column(nullable = false, length = 500)
    private String comment;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
