package com.ssafy.boardcollie.domain.game.dto;

import com.ssafy.boardcollie.domain.game.entity.Tag;
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
public class TagDto {

    private Long tagId;
    private String tagName;

    public static TagDto from(Tag tag){
        return TagDto.builder()
                .tagId(tag.getId())
                .tagName(tag.getTag_name_kor())
                .build();
    }

}
