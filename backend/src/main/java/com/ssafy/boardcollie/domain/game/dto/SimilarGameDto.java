package com.ssafy.boardcollie.domain.game.dto;

import com.ssafy.boardcollie.domain.game.entity.Game;
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

    public static SimilarGameDto from(Game game){
        return SimilarGameDto.builder()
                .gameId(game.getId())
                .gameTitleKor(game.getGameTitleKor())
                .gameImage(game.getGameImage())
                .build();
    }
}
