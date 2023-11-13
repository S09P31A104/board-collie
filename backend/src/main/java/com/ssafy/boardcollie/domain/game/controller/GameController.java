package com.ssafy.boardcollie.domain.game.controller;

import com.ssafy.boardcollie.domain.game.dto.GameDetailResponseDto;
import com.ssafy.boardcollie.domain.game.service.GameService;
import com.ssafy.boardcollie.global.response.JsonResponse;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s1/game")
public class GameController {

    private final GameService gameService;

    @GetMapping()
    public ResponseEntity<?> getGameListByGameTitle(
            @RequestParam(name = "q", required = false) String searchKeyword,
            @RequestParam(name = "people", required = false) Integer numberOfPeople,
            @RequestParam(name="type, required=false") String searchType) {

        return JsonResponse.ok("Game List 반환 성공",
                gameService.getGamesBySearchKeyword(searchKeyword, numberOfPeople, searchType));
    }

    @GetMapping("/detail/{gameId}")
    public ResponseEntity<?> getGameDetail(@PathVariable Long gameId) {

        GameDetailResponseDto gameDetail = gameService.getGameDetail(gameId);
        return JsonResponse.ok("게임 디테일 반환 성공!",gameDetail);

    }

}
