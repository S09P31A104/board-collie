package com.ssafy.boardcollie.domain.game.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s1/game")
public class GameController {

    @GetMapping
    public ResponseEntity<?> getGameList(){
        return null;
    }

}
