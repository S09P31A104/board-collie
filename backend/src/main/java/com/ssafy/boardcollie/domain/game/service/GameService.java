package com.ssafy.boardcollie.domain.game.service;

import com.ssafy.boardcollie.domain.game.entity.Game;
import java.util.List;

public interface GameService {

    List<Game> getAllGames();

    Game getDetail(Long id);

}
