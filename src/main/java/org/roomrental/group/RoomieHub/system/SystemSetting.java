package org.roomrental.group.RoomieHub.system;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.roomrental.group.RoomieHub.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "system_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settingId;

    @Column(unique = true, nullable = false, length = 100)
    private String settingKey;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String settingValue;

    @Builder.Default
    private String dataType = "string";

    @Builder.Default
    private String category = "general";

    private String description;

    @Builder.Default
    private Boolean isPublic = false;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}