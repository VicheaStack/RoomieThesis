package org.roomrental.group.RoomieHub.favorite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.roomrental.group.RoomieHub.user.User;
import org.roomrental.group.RoomieHub.room.Room;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorites", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"renter_id", "room_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id", nullable = false)
    private User renter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Explicit constructor
    public Favorite(User renter, Room room) {
        this.renter = renter;
        this.room = room;
    }
}