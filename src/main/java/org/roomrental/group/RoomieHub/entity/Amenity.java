package org.roomrental.group.RoomieHub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "amenities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amenityId;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String iconClass;

    private String category = "general";

    private Boolean isActive = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "amenities")
    private List<Room> rooms = new ArrayList<>();
}