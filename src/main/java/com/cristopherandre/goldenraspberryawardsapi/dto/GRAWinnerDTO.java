package com.cristopherandre.goldenraspberryawardsapi.dto;

import com.cristopherandre.goldenraspberryawardsapi.model.Movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GRAWinnerDTO {

    private Long id;
    private Integer awardYear;
    private Movie movie;

}
