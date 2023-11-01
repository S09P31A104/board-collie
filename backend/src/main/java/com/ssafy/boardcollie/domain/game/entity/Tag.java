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

    private String tagNameKor;
    private String tagNameEng;
    private String tagDescription;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<GameTag> gameTags;

}
