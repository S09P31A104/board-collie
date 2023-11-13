package com.ssafy.boardcollie.domain.game.service;

import com.ssafy.boardcollie.domain.game.dto.GameDetailResponseDto;
import com.ssafy.boardcollie.domain.game.dto.GameResponseDto;
import com.ssafy.boardcollie.domain.game.entity.Game;
import java.util.List;

public interface GameService {

    List<GameResponseDto> getGamesBySearchKeyword(String searchKeyword, Integer numberOfPeople, String searchType);

    GameDetailResponseDto getGameDetail(Long id);
}
