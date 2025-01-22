package com.casualchess.core.repository;

import com.casualchess.core.entity.MoveEntity;
import com.casualchess.core.entity.MoveId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveRepository extends JpaRepository<MoveEntity, MoveId> {
}
