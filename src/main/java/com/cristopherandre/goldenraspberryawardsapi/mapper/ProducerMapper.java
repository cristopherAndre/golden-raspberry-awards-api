package com.cristopherandre.goldenraspberryawardsapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cristopherandre.goldenraspberryawardsapi.dto.ProducerDTO;
import com.cristopherandre.goldenraspberryawardsapi.model.Producer;

@Mapper(componentModel = "spring")
public interface ProducerMapper {

    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    ProducerDTO toProducerDTO(Producer producer);

    Producer toProducer(ProducerDTO producerDTO);
    
}
