package com.ssafy.boardcollie.domain.game.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDetailResponseDto {
    private Long gameId;
    private String gameImage;
    private String qrImage;
    private String gameTitleKor;
    private String gameTitleEng;
    private int minPlayer;
    private int maxPlayer;
    private int playTime;
    private String gameEvaluation;
    private List<TagDto> tags;
    private List<SimilarGameDto> similarGame;
}
