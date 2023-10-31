package com.ssafy.boardcollie.domain.game.service;

import com.ssafy.boardcollie.domain.game.dto.GameDetailResponseDto;
import com.ssafy.boardcollie.domain.game.dto.GameResponseDto;
import com.ssafy.boardcollie.domain.game.entity.Game;
import java.util.List;

public interface GameService {

    List<GameResponseDto> getGamesByGameTitle(String searchKeyword, Integer numberOfPeople);

    GameDetailResponseDto getGameDetail(Long id);

}
