package com.cristopherandre.goldenraspberryawardsapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cristopherandre.goldenraspberryawardsapi.dto.MovieDTO;
import com.cristopherandre.goldenraspberryawardsapi.model.Movie;

/**
 * @author Cristopher Andre
 */
@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MovieDTO toMovieDTO(Movie movie);

    Movie toMovie(MovieDTO movieDTO);

}
