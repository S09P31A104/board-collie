package com.ssafy.boardcollie.domain.game.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@NoArgsConstructor
public class Game {

    @Id
    @Column(name = "game_id")
    private Long id;
    @Column(name = "game_title_kor")
    private String gameTitleKor;
    @Column(name = "game_title_eng")
    private String gameTitleEng;
    @Column(name = "game_min_player")
    private Integer minPlayer;
    @Column(name = "game_max_player")
    private Integer maxPlayer;
    @Column(name = "game_time")
    private Integer playTime;
    private String gameImage;
    private String qrImage;
    private String gameDetail;
    private String gameEvaluation;

    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.JOIN)
    private List<GameTag> gameTags;

    public List<Tag> getTags() {
        return gameTags.stream().map(GameTag::getTag).collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "game")
    private Set<GameRelation> gameRelations = new HashSet<>();
}
