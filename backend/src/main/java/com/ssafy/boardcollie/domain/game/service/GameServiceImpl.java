package com.ssafy.boardcollie.domain.game.service;

import com.ssafy.boardcollie.domain.game.dto.GameResponseDto;
import com.ssafy.boardcollie.domain.game.entity.Game;
import com.ssafy.boardcollie.domain.game.repository.GameRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;


    @Override
    public List<GameResponseDto> getGamesByGameTitle(String searchKeyword, Integer numberOfPeople) {
        List<Game> games = gameRepository.findGamesBySearchCriteria(searchKeyword, numberOfPeople);

        List<GameResponseDto> gameResponseDtos = games.stream()
                .map(GameResponseDto::from)
                .collect(Collectors.toList());
        return gameResponseDtos;
    }

    @Override
    public Game getDetail(Long id) {
        return null;
    }
}
