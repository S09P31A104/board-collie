package com.ssafy.boardcollie.domain.game.dto;


import com.ssafy.boardcollie.domain.game.entity.Game;
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
public class GameResponseDto {
    private Long gameId;
    private String gameTitle;
    private String gameImage;
    private List<TagDto> gameTag;

    public static GameResponseDto from(Game game) {
        List<TagDto> tagDtos = game.getTags().stream()
                .map(TagDto::from)
                .collect(Collectors.toList());

        return GameResponseDto.builder()
                .gameId(game.getId())
                .gameTitle(game.getGameTitleKor())
                .gameImage(game.getGameImage())
                .gameTag(tagDtos)
                .build();
    }
}
