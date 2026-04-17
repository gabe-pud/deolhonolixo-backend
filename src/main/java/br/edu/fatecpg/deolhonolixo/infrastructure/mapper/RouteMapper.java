package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;


import br.edu.fatecpg.deolhonolixo.core.domain.city.Route;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.RouteResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.RouteDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    Route toDomain (RouteDocument doc);
    RouteResponseDTO toResponseDTO(Route r);
}
