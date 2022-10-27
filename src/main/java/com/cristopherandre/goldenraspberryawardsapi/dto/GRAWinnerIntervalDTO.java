package com.cristopherandre.goldenraspberryawardsapi.dto;

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
public class GRAWinnerIntervalDTO {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

}
