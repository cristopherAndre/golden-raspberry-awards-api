package com.cristopherandre.goldenraspberryawardsapi.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cristopher Andre
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GRAWinnersMinMaxDTO {

    private List<GRAWinnerIntervalDTO> min = new ArrayList<>();
    private List<GRAWinnerIntervalDTO> max = new ArrayList<>();

}
