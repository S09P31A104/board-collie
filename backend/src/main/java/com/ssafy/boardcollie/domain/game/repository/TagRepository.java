package com.ssafy.boardcollie.domain.game.repository;


import com.ssafy.boardcollie.domain.game.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
