package org.roomrental.group.RoomieHub.room;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.roomrental.group.RoomieHub.booking.Booking;
import org.roomrental.group.RoomieHub.amenity.Amenity;
import org.roomrental.group.RoomieHub.photos.Photo;
import org.roomrental.group.RoomieHub.review.Review;
import org.roomrental.group.RoomieHub.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "price_per_night", nullable = false)
    private Double pricePerNight;

    @Column(nullable = false)
    private String location;

    private Double latitude;
    private Double longitude;

    @Column(length = 50)
    private String roomType = "single";

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoomStatus status = RoomStatus.AVAILABLE;

    private Integer sizeSqft;

    private Integer maxOccupancy = 1;

    private Boolean hasPrivateBathroom = false;

    private Boolean isFurnished = false;

    private Boolean isVerified = false;

    private Integer totalViews = 0;

    private Integer totalBookings = 0;

    private Double averageRating = 0.0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "room_amenities",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Review> reviews = new ArrayList<>();
}