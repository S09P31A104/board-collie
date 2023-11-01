package com.ssafy.boardcollie.domain.game.service;

import com.ssafy.boardcollie.domain.game.dto.GameDetailResponseDto;
import com.ssafy.boardcollie.domain.game.dto.GameResponseDto;
import com.ssafy.boardcollie.domain.game.dto.TagDto;
import com.ssafy.boardcollie.domain.game.entity.Game;
import com.ssafy.boardcollie.domain.game.entity.Tag;
import com.ssafy.boardcollie.domain.game.repository.GameRepository;
import com.ssafy.boardcollie.global.exception.GlobalRuntimeException;
import io.micrometer.core.instrument.Tags;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;


    @Override
    public List<GameResponseDto> getGamesByGameTitle(String searchKeyword, Integer numberOfPeople) {
        List<Game> games = gameRepository.findGamesBySearchCriteria(searchKeyword, numberOfPeople);

        return games.stream()
                .map(GameResponseDto::from)
                .toList();
    }

//    @Override
//    public GameDetailResponseDto getGameDetail(Long id) {
//        Game game = gameRepository.findById(id)
//                .orElseThrow(() -> new GlobalRuntimeException("해당 ID의 게임이 존재하지 않습니다.",
//                        HttpStatus.NOT_FOUND));
//
//        List<TagDto> tagDtos = game.getTags().stream()
//                .map(TagDto::from)
//                .toList();
//
//
//
//    }
}
