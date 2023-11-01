package com.ssafy.boardcollie.domain.game.repository;

import com.ssafy.boardcollie.domain.game.entity.Game;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT DISTINCT g FROM Game g " +
            "LEFT JOIN FETCH g.gameTags gt " +
            "LEFT JOIN FETCH gt.tag t " +
            "WHERE (:searchKeyword IS NULL OR g.gameTitleKor LIKE %:searchKeyword%) AND " +
            "(:numberOfPeople IS NULL OR (g.minPlayer <= :numberOfPeople AND g.maxPlayer >= :numberOfPeople))")
    List<Game> findGamesBySearchCriteria(@Param("searchKeyword") String searchKeyword,
            @Param("numberOfPeople") Integer numberOfPeople);

    @Query("SELECT DISTINCT g FROM Game g " +
            "JOIN FETCH g.gameTags gt " +
            "JOIN FETCH gt.tag t " +
            "WHERE t.tagNameKor = :tagName")
    List<Game> findGamesByTagName(@Param("tagName") String tagName);

}
