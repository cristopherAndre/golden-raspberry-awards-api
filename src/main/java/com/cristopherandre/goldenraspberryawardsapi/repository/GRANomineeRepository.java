package com.cristopherandre.goldenraspberryawardsapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnerIntervalDTO;
import com.cristopherandre.goldenraspberryawardsapi.model.GRANominee;

/**
 * @author Cristopher Andre
 */
@Repository
public interface GRANomineeRepository extends JpaRepository<GRANominee, Long> {

    /*
     * SQL QUERY:
     * SELECT
     * P.NAME AS PRODUCER,
     * MIN(N.AWARD_YEAR) AS FIRST_AWARD,
     * MAX(N.AWARD_YEAR) AS LAST_AWARD,
     * (MAX(N.AWARD_YEAR) - MIN(N.AWARD_YEAR)) AS DIFF_BTW_AWARDS
     * FROM GRANOMINEES AS N
     * INNER JOIN MOVIES AS M ON M.ID = N.MOVIE_ID
     * INNER JOIN MOVIES_PRODUCERS AS MP ON MP.MOVIES_ID = M.ID
     * INNER JOIN PRODUCERS AS P ON P.ID = MP.PRODUCERS_ID
     * WHERE N.IS_WINNER = TRUE
     * GROUP BY P.NAME
     * HAVING COUNT (M.TITLE) > 1
     * ORDER BY DIFF_BTW_AWARDS
     */
    @Query("SELECT new com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnerIntervalDTO( "
            + "p.name, (MAX(n.awardYear) - MIN(n.awardYear)), MIN(n.awardYear), MAX(n.awardYear)) "
            + "FROM granominees AS n "
            + "INNER JOIN n.movie as m "
            + "INNER JOIN m.producers as p "
            + "WHERE n.isWinner = TRUE "
            + "GROUP BY p.name "
            + "HAVING COUNT (m.title) > 1 "
            + "ORDER BY (MAX(n.awardYear) - MIN(n.awardYear)) ")
    List<GRAWinnerIntervalDTO> findGRAWinnersInterval();

    public Optional<GRANominee> findByMovieId(Long id);

}
