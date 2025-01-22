package com.casualchess.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class MoveId implements Serializable {

    @Column(name = "game_id")
    private UUID gameId;

    @Column(name = "move_number")
    private int moveNumber;
}
