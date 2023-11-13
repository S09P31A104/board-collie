package com.ssafy.boardcollie.domain.game.repository;

import com.ssafy.boardcollie.domain.game.entity.Game;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT DISTINCT g FROM Game g " +
            "LEFT JOIN FETCH g.gameTags gt " +
            "LEFT JOIN FETCH gt.tag t " +
            "WHERE (:searchKeyword IS NULL OR g.gameTitleKor LIKE %:searchKeyword%) AND " +
            "(:numberOfPeople IS NULL OR (g.minPlayer <= :numberOfPeople AND g.maxPlayer >= :numberOfPeople))")
    List<Game> findGamesByGameTitle(@Param("searchKeyword") String searchKeyword,
            @Param("numberOfPeople") Integer numberOfPeople);

    @Query("SELECT DISTINCT g FROM Game g " +
            "LEFT JOIN FETCH g.gameTags gt " +
            "LEFT JOIN FETCH gt.tag t " +
            "LEFT JOIN FETCH g.gameRelations gr " +
            "LEFT JOIN FETCH gr.relatedGame rg " +
            "WHERE g.id = :gameId")
    Optional<Game> findGameDetailWithTagsAndSimilarGames(@Param("gameId") Long gameId);


    @Query("SELECT distinct g FROM Game g JOIN FETCH g.gameTags gt JOIN FETCH gt.tag t " +
        "WHERE g.id IN (SELECT g.id FROM Game g JOIN g.gameTags gt JOIN gt.tag t " +
        "WHERE t.tagNameKor = :tagName AND "+
            "(:numberOfPeople IS NULL) OR (g.minPlayer <= :numberOfPeople AND g.maxPlayer >= :numberOfPeople))")
    List<Game> findGamesByTagName(@Param("tagName") String tagName,
            @Param("numberOfPeople") Integer numberOfPeople);
}
