package org.roomrental.group.RoomieHub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "owner_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerProfile {

    @Id
    @Column(name = "user_id") // Use user_id as the PK name
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // This tells Hibernate to use the PK from the User object
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    private Integer totalListings = 0;

    @Builder.Default
    private Integer ratingCount = 0;

    @Builder.Default
    private Double totalRating = 0.0;
}