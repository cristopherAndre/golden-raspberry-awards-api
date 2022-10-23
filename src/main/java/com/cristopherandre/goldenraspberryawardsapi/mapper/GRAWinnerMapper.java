package com.cristopherandre.goldenraspberryawardsapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRAWinnerDTO;
import com.cristopherandre.goldenraspberryawardsapi.model.GRAWinner;

@Mapper(componentModel = "spring")
public interface GRAWinnerMapper {

    GRAWinnerMapper INSTANCE = Mappers.getMapper(GRAWinnerMapper.class);

    GRAWinnerDTO toGRAWinnerDTO(GRAWinner graWinner);

    GRAWinner toGRAWinner(GRAWinnerDTO graWinnerDTO);

}
