package com.casualchess.core.entity;

import com.casualchess.core.enums.GameStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "games")
public class GameEntity {

    @Id
    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @Column(name = "player_white", nullable = false)
    private Long playerWhite;

    @Column(name = "player_black", nullable = false)
    private Long playerBlack;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_status", nullable = false)
    private GameStatusEnum gameStatus;
}
