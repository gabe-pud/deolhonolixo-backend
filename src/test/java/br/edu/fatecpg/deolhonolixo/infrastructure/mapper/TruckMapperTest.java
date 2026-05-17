package br.edu.fatecpg.deolhonolixo.infrastructure.mapper;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckFindRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckFindResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckRegisterResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.persistence.postgres.TruckJpaEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TruckMapperTest {
    private final TruckMapper mapper = Mappers.getMapper(TruckMapper.class);

    @Test
    void shouldMapDomainToJpaEntity() {
        LocalDateTime routeStart = LocalDateTime.of(2026, 5, 17, 10, 30);
        LocalDateTime routeEnd = LocalDateTime.of(2026, 5, 17, 12, 45);
        Truck truck = new Truck(7L, "ABC-1234", "ACTIVE", routeStart, routeEnd, "R1");

        TruckJpaEntity result = mapper.toJpaFromDomain(truck);

        assertAll(
                () -> assertEquals(truck.id(), result.getId()),
                () -> assertEquals(truck.licensePlate(), result.getLicensePlate()),
                () -> assertEquals(truck.status(), result.getStatus()),
                () -> assertEquals(truck.routeStart(), result.getRouteStart()),
                () -> assertEquals(truck.routeEnd(), result.getRouteEnd()),
                () -> assertEquals(truck.routeId(), result.getRouteId())
        );
    }

    @Test
    void shouldMapJpaEntityToDomain() {
        LocalDateTime routeStart = LocalDateTime.of(2026, 5, 17, 10, 30);
        LocalDateTime routeEnd = LocalDateTime.of(2026, 5, 17, 12, 45);
        TruckJpaEntity entity = new TruckJpaEntity(7L, "ABC-1234", "ACTIVE", routeStart, routeEnd, "R1");

        Truck result = mapper.toDomainFromJpa(entity);

        assertAll(
                () -> assertEquals(entity.getId(), result.id()),
                () -> assertEquals(entity.getLicensePlate(), result.licensePlate()),
                () -> assertEquals(entity.getStatus(), result.status()),
                () -> assertEquals(entity.getRouteStart(), result.routeStart()),
                () -> assertEquals(entity.getRouteEnd(), result.routeEnd()),
                () -> assertEquals(entity.getRouteId(), result.routeId())
        );
    }

    @Test
    void shouldMapRegisterRequestToDomain() {
        TruckRegisterRequestDTO dto = new TruckRegisterRequestDTO("ABC-1234");

        Truck result = mapper.toDomainFromRegisterRequestDTO(dto);

        assertAll(
                () -> assertNull(result.id()),
                () -> assertEquals("ABC-1234", result.licensePlate()),
                () -> assertNull(result.status()),
                () -> assertNull(result.routeStart()),
                () -> assertNull(result.routeEnd()),
                () -> assertNull(result.routeId())
        );
    }

    @Test
    void shouldMapRegisterResponseDto() {
        HashMap<String, String> response = new HashMap<>();
        response.put("licensePlate", "ABC-1234");

        TruckRegisterResponseDTO result = mapper.toRegisterResponseDTO(response);

        assertEquals("ABC-1234", result.licensePlate());
    }

    @Test
    void shouldMapFindRequestToDomain() {
        TruckFindRequestDTO dto = new TruckFindRequestDTO("ABC-1234");

        Truck result = mapper.toDomainFromFindRequestDTO(dto);

        assertEquals("ABC-1234", result.licensePlate());
    }

    @Test
    void shouldMapDomainToFindResponseDto() {
        LocalDateTime routeStart = LocalDateTime.of(2026, 5, 17, 10, 30);
        LocalDateTime routeEnd = LocalDateTime.of(2026, 5, 17, 12, 45);
        Truck truck = new Truck(7L, "ABC-1234", "ACTIVE", routeStart, routeEnd, "R1");

        TruckFindResponseDTO result = mapper.toFindResponseDTO(truck);

        assertAll(
                () -> assertEquals(truck.id(), result.id()),
                () -> assertEquals(truck.licensePlate(), result.licensePlate()),
                () -> assertEquals(truck.status(), result.status()),
            () -> assertEquals(Date.from(routeStart.atZone(ZoneOffset.UTC).toInstant()), result.routeStart()),
            () -> assertEquals(Date.from(routeEnd.atZone(ZoneOffset.UTC).toInstant()), result.routeEnd()),
                () -> assertEquals(truck.routeId(), result.routeId())
        );
    }
}