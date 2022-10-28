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

    // SQL QUERY:
    // SELECT producer as producer, award_year as awardYear, next_award_year as
    // nextAwardYear, (next_award_year - award_year) AS awardYearDiff
    // FROM (
    // SELECT producer, award_year,
    // (SELECT Min(award_year) FROM(
    // SELECT p.NAME AS producer, n.award_year AS award_year
    // FROM granominees AS n
    // INNER JOIN movies AS m ON m.id = n.movie_id
    // INNER JOIN movies_producers AS mp ON mp.movies_id = m.id
    // INNER JOIN producers AS p ON p.id = mp.producers_id
    // WHERE n.is_winner = true ) AS t2
    // WHERE t2.producer = t1.producer AND t2.award_year > t1.award_year) AS
    // next_award_year
    // FROM (
    // SELECT p.NAME AS producer, n.award_year AS award_year
    // FROM granominees AS n
    // INNER JOIN movies AS m ON m.id = n.movie_id
    // INNER JOIN movies_producers AS mp ON mp.movies_id = m.id
    // INNER JOIN producers AS p ON p.id = mp.producers_id
    // WHERE n.is_winner = true) AS t1)
    // AS t
    // WHERE next_award_year IS NOT NULL
    @Query(nativeQuery = true)
    List<GRAWinnerIntervalDTO> findGRAWinnersInterval();

    public Optional<GRANominee> findByMovieId(Long id);

}
