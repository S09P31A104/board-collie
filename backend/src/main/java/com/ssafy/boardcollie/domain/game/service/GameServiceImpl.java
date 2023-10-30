package com.ssafy.boardcollie.domain.game.service;

import com.ssafy.boardcollie.domain.game.entity.Game;
import com.ssafy.boardcollie.domain.game.repository.GameRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game getDetail(Long id) {
        return null;
    }
}
