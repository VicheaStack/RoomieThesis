package org.roomrental.group.RoomieHub.review;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.roomrental.group.RoomieHub.booking.Booking;
import org.roomrental.group.RoomieHub.room.Room;
import org.roomrental.group.RoomieHub.user.User;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "reviews", indexes = {
        @Index(name = "idx_room", columnList = "room_id"),
        @Index(name = "idx_renter", columnList = "renter_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id", nullable = false)
    private User renter;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rating;

    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @Column(columnDefinition = "TEXT")
    private String ownerResponse;

    @Builder.Default
    private Boolean isVerified = false;

    @Builder.Default
    private Boolean isFlagged = false;

    private String flagReason;

    @Builder.Default
    private Integer helpfulCount = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}