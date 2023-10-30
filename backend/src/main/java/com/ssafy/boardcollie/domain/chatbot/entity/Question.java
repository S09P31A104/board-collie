package com.ssafy.boardcollie.domain.chatbot.entity;

import com.ssafy.boardcollie.global.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;
    @Column(name = "question")
    private String question;
    @Column(name = "game_id")
    private Long gameId;

}
