package com.cristopherandre.goldenraspberryawardsapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnerIntervalDTO;
import com.cristopherandre.goldenraspberryawardsapi.model.GRAWinner;

/**
 * @author Cristopher Andre
 */
@Repository
public interface GRAWinnerRepository extends JpaRepository<GRAWinner, Long> {

    /*
     * SQL QUERY:
     * SELECT
     * P.NAME AS PRODUCER,
     * MIN(W.AWARD_YEAR) AS FIRST_AWARD,
     * MAX(W.AWARD_YEAR) AS LAST_AWARD,
     * (MAX(W.AWARD_YEAR) - MIN(W.AWARD_YEAR)) AS DIFF_BTW_AWARDS
     * FROM GRAWINNERS AS W
     * INNER JOIN MOVIES AS M ON M.ID = W.MOVIE_ID
     * INNER JOIN MOVIES_PRODUCERS AS MP ON MP.MOVIES_ID = M.ID
     * INNER JOIN PRODUCERS AS P ON P.ID = MP.PRODUCERS_ID
     * GROUP BY P.NAME
     * HAVING COUNT (M.TITLE) > 1
     * ORDER BY DIFF_BTW_AWARDS
     */
    @Query("SELECT new com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnerIntervalDTO( "
            + "p.name, (MAX(w.awardYear) - MIN(w.awardYear)), MIN(w.awardYear), MAX(w.awardYear)) "
            + "FROM grawinners AS w "
            + "INNER JOIN w.movie as m "
            + "INNER JOIN m.producers as p "
            + "GROUP BY p.name "
            + "HAVING COUNT (m.title) > 1 "
            + "ORDER BY (MAX(w.awardYear) - MIN(w.awardYear)) ")
    List<GRAWinnerIntervalDTO> findGRAWinnersInterval();

}
