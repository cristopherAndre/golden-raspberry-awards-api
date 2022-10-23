package com.cristopherandre.goldenraspberryawardsapi.dto;

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
    
}
