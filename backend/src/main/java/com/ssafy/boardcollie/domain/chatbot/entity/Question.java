package com.ssafy.boardcollie.domain.chatbot.entity;

import com.ssafy.boardcollie.global.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;
    @Column(name = "question", columnDefinition = "text")
    private String question;
    @Column(name = "game_id")
    private Long gameId;
    @Column(name = "answer", columnDefinition = "text")
    private String answer;

    @Builder
    private Question(String question, Long gameId, String answer) {
        this.question = question;
        this.gameId = gameId;
        this.answer = answer;
    }

    public static Question createQuestion(String question, Long gameId, String answer) {
        return Question.builder()
                       .question(question)
                       .gameId(gameId)
                       .answer(answer)
                       .build();
    }
}
