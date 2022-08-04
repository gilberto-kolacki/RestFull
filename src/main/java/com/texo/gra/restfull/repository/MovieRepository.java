package com.texo.gra.restfull.repository;

import com.texo.gra.restfull.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "SELECT DISTINCT REGEXP_REPLACE(PRODUCERS, ' and ', ',') FROM TB_MOVIES WHERE WINNER = true", nativeQuery = true)
    Set<String> findWinnersProducers();

    @Query(value = "SELECT MOVIE_YEAR FROM TB_MOVIES WHERE PRODUCERS like CONCAT('%',:producer,'%') and WINNER = true", nativeQuery = true)
    Set<Integer> findYearsWinnersByProducer(@Param("producer") String producer);
}
