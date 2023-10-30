package com.ssafy.boardcollie.domain.game.entity;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Game {

    @Id @Column(name = "game_id")
    private Long id;
    private String game_title;
    private Integer min_people;
    private Integer max_people;
    private Integer play_time;
    private Integer age;
    private String game_image;
    private String qr_image;

    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<GameTag> gameTags;

    public List<Tag> getTags() {
        return gameTags.stream().map(GameTag::getTag).collect(Collectors.toList());
    }
}
