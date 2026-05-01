package org.roomrental.group.RoomieHub.photos;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.roomrental.group.RoomieHub.room.Room;

import java.time.LocalDateTime;

@Entity
@Table(name = "photos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false, length = 500)
    private String photoUrl;

    private String caption;

    @Builder.Default
    private Boolean isPrimary = false;

    @Builder.Default
    private Integer displayOrder = 0;

    @CreationTimestamp
    private LocalDateTime uploadedAt;
}