package com.casualchess.core.entity;

import com.casualchess.core.enums.PlayerEnum;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "moves")
public class MoveEntity {

    @EmbeddedId
    private MoveId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "player", nullable = false)
    private PlayerEnum player;

    @Column(name = "move_notation", nullable = false, length = 10)
    private String moveNotation;

    @Column(name = "move_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime moveAt;
}

