package com.ssafy.boardcollie.domain.game.dto;

import com.ssafy.boardcollie.domain.game.entity.Game;
import com.ssafy.boardcollie.domain.game.entity.Tag;
import java.util.List;
import java.util.stream.Collectors;
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

    public static GameDetailResponseDto from(Game game, List<Tag> tagList, List<Game> similarGameList) {
        List<TagDto> tagDtoList = tagList.stream()
                .map(TagDto::from)
                .collect(Collectors.toList());

        List<SimilarGameDto> similarGameDtoList = similarGameList.stream()
                .map(SimilarGameDto::from)
                .collect(Collectors.toList());

        return GameDetailResponseDto.builder()
                .gameId(game.getId())
                .gameImage(game.getGameImage())
                .qrImage(game.getQrImage())
                .gameTitleKor(game.getGameTitleKor())
                .gameTitleEng(game.getGameTitleEng())
                .minPlayer(game.getMinPlayer())
                .maxPlayer(game.getMaxPlayer())
                .playTime(game.getPlayTime())
                .gameEvaluation(game.getGameEvaluation())
                .tags(tagDtoList)
                .similarGame(similarGameDtoList)
                .build();
    }
}
