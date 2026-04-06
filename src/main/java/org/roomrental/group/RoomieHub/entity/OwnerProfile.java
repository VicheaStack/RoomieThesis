package org.roomrental.group.RoomieHub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "owner_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerProfile {

    @Id
    @Column(name = "owner_id")
    private Long ownerId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "owner_id")
    private User user;

    @Builder.Default
    private Integer totalListings = 0;

    @Builder.Default
    private Double averageRating = 0.0;
}