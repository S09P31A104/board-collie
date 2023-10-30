package com.ssafy.boardcollie.domain.game.repository;

import com.ssafy.boardcollie.domain.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
