package com.ssafy.boardcollie.domain.game.controller;

import com.ssafy.boardcollie.domain.game.service.GameService;
import com.ssafy.boardcollie.global.response.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestParam(name = "people", required = false) Integer numberOfPeople) {
        return JsonResponse.ok("Game List 반환 성공",
                gameService.getGamesByGameTitle(searchKeyword, numberOfPeople));
    }

}
