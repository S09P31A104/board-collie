package com.ssafy.boardcollie.domain.game.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "game_relation")
@Getter
@NoArgsConstructor
public class GameRelation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_relation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "related_game_id")
    private Game relatedGame;

}
