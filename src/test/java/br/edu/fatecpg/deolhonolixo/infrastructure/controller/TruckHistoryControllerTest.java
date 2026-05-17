package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckHistoryFindAllCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckHistoryFindByIdCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckHistoryGetLastGeolocationCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckHistoryFindResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckHistoryGeolocationResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckHistoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckGeolocationNotFoundException;

@ExtendWith(MockitoExtension.class)
class TruckHistoryControllerTest {

    @Mock
    TruckHistoryMapper truckHistoryMapper;

    @Mock
    TruckHistoryFindAllCase findAllCase;

    @Mock
    TruckHistoryFindByIdCase findByIdCase;

    @Mock
    TruckHistoryGetLastGeolocationCase getLastGeolocationCase;

    @InjectMocks
    TruckHistoryController controller;

    @Test
    void truckHistory_returns200() {
        Instant now = Instant.now();
        TruckHistory domain = new TruckHistory("h1", now, "LP", 12.3, 45.6, 10);
        TruckHistoryFindResponseDTO.PositionDTO pos = new TruckHistoryFindResponseDTO.PositionDTO(45.6, 12.3);
        TruckHistoryFindResponseDTO.TelemetryDTO tel = new TruckHistoryFindResponseDTO.TelemetryDTO(10);
        TruckHistoryFindResponseDTO dto = new TruckHistoryFindResponseDTO(now, "LP", pos, tel);

        when(findAllCase.execute()).thenReturn(List.of(domain));
        when(truckHistoryMapper.toHistoryFindResponseDTO(domain)).thenReturn(dto);

        var resp = controller.truckHistory();

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("LP", resp.getBody().get(0).licensePlate());
    }

    @Test
    void truckHistoryById_returns200() {
        Instant now = Instant.now();
        TruckHistory domain = new TruckHistory("h2", now, "LP2", 1.1, 2.2, 5);
        TruckHistoryFindResponseDTO.PositionDTO pos = new TruckHistoryFindResponseDTO.PositionDTO(2.2, 1.1);
        TruckHistoryFindResponseDTO.TelemetryDTO tel = new TruckHistoryFindResponseDTO.TelemetryDTO(5);
        TruckHistoryFindResponseDTO dto = new TruckHistoryFindResponseDTO(now, "LP2", pos, tel);

        when(findByIdCase.execute(2L)).thenReturn(List.of(domain));
        when(truckHistoryMapper.toHistoryFindResponseDTO(domain)).thenReturn(dto);

        var resp = controller.truckHistoryById(2L);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("LP2", resp.getBody().get(0).licensePlate());
    }

    @Test
    void truckGeolocation_returns200() {
        Instant now = Instant.now();
        TruckHistory domain = new TruckHistory("h3", now, "LP3", 9.9, 8.8, 3);
        TruckHistoryGeolocationResponseDTO.PositionDTO pos = new TruckHistoryGeolocationResponseDTO.PositionDTO(8.8, 9.9);
        TruckHistoryGeolocationResponseDTO dto = new TruckHistoryGeolocationResponseDTO(now, pos);

        when(getLastGeolocationCase.execute(3L)).thenReturn(domain);
        when(truckHistoryMapper.toHistoryGeolocationResponseDTO(domain)).thenReturn(dto);

        var resp = controller.truckGeolocation(3L);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals(9.9, resp.getBody().position().longitude());
    }

    @Test
    void truckGeolocation_notFound_throws() {
        when(getLastGeolocationCase.execute(99L)).thenThrow(new TruckGeolocationNotFoundException());

        assertThrows(TruckGeolocationNotFoundException.class, () -> controller.truckGeolocation(99L));
    }
}
