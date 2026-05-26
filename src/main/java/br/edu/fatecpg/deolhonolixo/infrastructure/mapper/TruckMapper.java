package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckFindRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckUpdateRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckFindResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckRegisterResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.TruckJpaEntity;
import org.mapstruct.Mapper;

import java.util.HashMap;

@Mapper(componentModel = "spring")
public interface TruckMapper {
    TruckJpaEntity toJpaFromDomain(Truck truck);
    Truck toDomainFromJpa(TruckJpaEntity truckJpa);
    Truck toDomainFromRegisterRequestDTO(TruckRegisterRequestDTO dto);
    Truck toDomainFromUpdateRequestDTO(TruckUpdateRequestDTO dto);
    TruckRegisterResponseDTO toRegisterResponseDTO(HashMap<String,String> response);
    Truck toDomainFromFindRequestDTO(TruckFindRequestDTO dto);
    TruckFindResponseDTO toFindResponseDTO(Truck response);
}
