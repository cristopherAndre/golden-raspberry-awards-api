package com.cristopherandre.goldenraspberryawardsapi.dto;

import com.cristopherandre.goldenraspberryawardsapi.model.Movie;

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
public class GRANomineeDTO {

    private Long id;
    private Integer awardYear;
    private Boolean isWinner;
    private Movie movie;

}
