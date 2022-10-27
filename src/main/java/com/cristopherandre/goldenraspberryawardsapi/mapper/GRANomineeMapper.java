package com.cristopherandre.goldenraspberryawardsapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cristopherandre.goldenraspberryawardsapi.dto.GRANomineeDTO;
import com.cristopherandre.goldenraspberryawardsapi.model.GRANominee;

/**
 * @author Cristopher Andre
 */
@Mapper(componentModel = "spring")
public interface GRANomineeMapper {

    GRANomineeMapper INSTANCE = Mappers.getMapper(GRANomineeMapper.class);

    GRANomineeDTO toGRANomineeDTO(GRANominee graNominee);

    GRANominee toGRANominee(GRANomineeDTO graNomineeDTO);

}
