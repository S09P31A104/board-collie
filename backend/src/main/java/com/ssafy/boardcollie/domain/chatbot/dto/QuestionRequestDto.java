package com.ssafy.boardcollie.domain.chatbot.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDto {

    @NotNull
    private Long gameId;
    @NotNull
    private String prompt;
    @NotNull
    private String uuid;
}
