package com.cristopherandre.goldenraspberryawardsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {

    private Long id;
    private String title;
    
}
