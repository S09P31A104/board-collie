package com.ssafy.boardcollie.domain.game.dto;

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
public class SimilarGameDto {
    private Long gameId;
    private String gameTitleKor;
    private String gameImage;
}
