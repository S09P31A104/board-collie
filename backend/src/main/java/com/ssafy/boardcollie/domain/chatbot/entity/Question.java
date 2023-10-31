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
    @Column(name = "question")
    private String question;
    @Column(name = "game_id")
    private Long gameId;

    @Builder
    private Question(String question, Long gameId) {
        this.question = question;
        this.gameId = gameId;
    }

    public static Question createQuestion(String question, Long gameId) {
        return Question.builder()
                       .question(question)
                       .gameId(gameId)
                       .build();
    }
}
