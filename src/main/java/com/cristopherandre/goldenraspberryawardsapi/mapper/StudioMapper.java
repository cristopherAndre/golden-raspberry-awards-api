package com.cristopherandre.goldenraspberryawardsapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cristopherandre.goldenraspberryawardsapi.dto.StudioDTO;
import com.cristopherandre.goldenraspberryawardsapi.model.Studio;

@Mapper(componentModel = "spring")
public interface StudioMapper {

    StudioMapper INSTANCE = Mappers.getMapper(StudioMapper.class);

    StudioDTO toStudioDTO(Studio studio);

    Studio toStudio(StudioDTO studioDTO);
    
}
