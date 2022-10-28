package com.cristopherandre.goldenraspberryawardsapi.model;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnerIntervalDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cristopher Andre
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedNativeQuery(name = "GRANominee.findGRAWinnersInterval", query = "SELECT producer as producer, award_year as previousWin, next_award_year as followingWin, (next_award_year - award_year) AS intervalWin "
                + "FROM ( "
                + "SELECT producer, award_year, "
                + "(SELECT Min(award_year) FROM( "
                + "SELECT p.NAME AS producer, n.award_year AS award_year "
                + "FROM granominees AS n "
                + "INNER JOIN movies AS m ON m.id = n.movie_id "
                + "INNER JOIN movies_producers AS mp ON mp.movies_id = m.id "
                + "INNER JOIN producers AS p ON p.id = mp.producers_id "
                + "WHERE n.is_winner = true ) AS t2 "
                + "WHERE t2.producer = t1.producer AND t2.award_year > t1.award_year) AS next_award_year "
                + "FROM ( "
                + "SELECT p.NAME AS producer, n.award_year AS award_year "
                + "FROM granominees AS n "
                + "INNER JOIN movies AS m ON m.id = n.movie_id "
                + "INNER JOIN movies_producers AS mp ON mp.movies_id = m.id "
                + "INNER JOIN producers AS p ON p.id = mp.producers_id "
                + "WHERE n.is_winner = true)  AS t1) "
                + "AS t  "
                + "WHERE next_award_year IS NOT NULL ", resultSetMapping = "Mapping.GRAWinnerIntervalDTO")
@SqlResultSetMapping(name = "Mapping.GRAWinnerIntervalDTO", classes = @ConstructorResult(targetClass = GRAWinnerIntervalDTO.class, columns = {
                @ColumnResult(name = "producer", type = String.class),
                @ColumnResult(name = "intervalWin", type = Integer.class),
                @ColumnResult(name = "previousWin", type = Integer.class),
                @ColumnResult(name = "followingWin", type = Integer.class) }))
@Entity(name = "granominees")
public class GRANominee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private Integer awardYear;

        @Column(nullable = false)
        private Boolean isWinner;

        @OneToOne(fetch = FetchType.EAGER)
        private Movie movie;

}
