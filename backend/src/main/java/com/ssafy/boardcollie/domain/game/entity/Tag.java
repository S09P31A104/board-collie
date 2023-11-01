package com.ssafy.boardcollie.domain.game.entity;

import java.util.List;
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
public class Tag {
    @Id
    @Column(name = "tag_id")
    private Long id;

    private String tag_name_kor;
    private String tag_name_eng;

    @Column(name = "tag_description", columnDefinition = "text")
    private String tag_description;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<GameTag> gameTags;

}
