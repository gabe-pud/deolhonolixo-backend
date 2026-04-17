package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.city.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.core.domain.city.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.UrbanGeometryResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.mongodb.UrbanGeometryDocument;
import org.springframework.stereotype.Component;


@Component
public class UrbanGeometryMapper {

    public UrbanGeometry toDomain(UrbanGeometryDocument doc){
        return new UrbanGeometry(
                doc.getId(), doc.getType(), doc.getName(),
                doc.getCity(), doc.getGeometry(), doc.getMetadata()
        );
    }
    public UrbanGeometryResponseDTO toResponseDTO(UrbanGeometry u) {
        return new UrbanGeometryResponseDTO(
                u.id(), u.type(), u.name(),
                u.city(), u.geometry(), u.metadata()
        );
    }


}
