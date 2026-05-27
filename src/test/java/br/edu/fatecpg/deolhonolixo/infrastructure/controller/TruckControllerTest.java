package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckFindAllCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckFindBylicensePlateCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckRegisterCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckUpdateCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckUpdateRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckFindResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckRegisterResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckNotFoundException;

@ExtendWith(MockitoExtension.class)
class TruckControllerTest {

    @Mock
    TruckMapper truckMapper;

    @Mock
    TruckRegisterCase registerCase;

    @Mock
    TruckFindBylicensePlateCase findBylicensePlateCase;

    @Mock
    TruckFindAllCase truckFindAllCase;

    @Mock
    TruckUpdateCase truckUpdateCase;

    @InjectMocks
    TruckController controller;

    @Test
    void truckFindAll_returns200() {
        Truck domain = new Truck(1L, "ABC1D23", "OK", null, null, null);
        TruckFindResponseDTO dto = new TruckFindResponseDTO(1L, "ABC1D23", "OK", null, null, null);

        when(truckFindAllCase.execute()).thenReturn(List.of(domain));
        when(truckMapper.toFindResponseDTO(domain)).thenReturn(dto);

        var resp = controller.truckFindAll();

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("ABC1D23", resp.getBody().get(0).licensePlate());
    }

    @Test
    void truckFindOne_returns200() {
        Truck domain = new Truck(2L, "XYZ1234", "OK", null, null, null);
        TruckFindResponseDTO dto = new TruckFindResponseDTO(2L, "XYZ1234", "OK", null, null, null);

        when(findBylicensePlateCase.execute("XYZ1234")).thenReturn(domain);
        when(truckMapper.toFindResponseDTO(domain)).thenReturn(dto);

        var resp = controller.truckFindOne("XYZ1234");

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("XYZ1234", resp.getBody().licensePlate());
    }

    @Test
    void truckRegister_returns200() {
        TruckRegisterRequestDTO request = new TruckRegisterRequestDTO("NEWPLATE");
        HashMap<String,String> respMap = new HashMap<>();
        respMap.put("licensePlate","NEWPLATE");
        TruckRegisterResponseDTO respDto = new TruckRegisterResponseDTO("NEWPLATE");

        when(truckMapper.toDomainFromRegisterRequestDTO(request)).thenReturn(new Truck(null, "NEWPLATE", "OK", null, null, null));
        when(registerCase.execute(new Truck(null, "NEWPLATE", "OK", null, null, null))).thenReturn(respMap);
        when(truckMapper.toRegisterResponseDTO(respMap)).thenReturn(respDto);

        var resp = controller.truckRegister(request);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("NEWPLATE", resp.getBody().licensePlate());
    }

    @Test
    void truckFindOne_notFound_throws() {
        when(findBylicensePlateCase.execute("NOPE")).thenThrow(new TruckNotFoundException());

        assertThrows(TruckNotFoundException.class, () -> controller.truckFindOne("NOPE"));
    }

    @Test
    void truckUpdate_returns200() {
        TruckUpdateRequestDTO request = new TruckUpdateRequestDTO(
                "EM_ROTA",
                LocalDateTime.of(2026, 5, 25, 8, 0),
                LocalDateTime.of(2026, 5, 25, 18, 0),
                "PG-LIXO-042"
        );
        Truck domain = new Truck(null, null, "EM_ROTA", request.routeStart(), request.routeEnd(), "PG-LIXO-042");
        Truck updated = new Truck(3L, "ABC1234", "EM_ROTA", request.routeStart(), request.routeEnd(), "PG-LIXO-042");
        TruckFindResponseDTO dto = new TruckFindResponseDTO(3L, "ABC1234", "EM_ROTA", null, null, "PG-LIXO-042");

        when(truckMapper.toDomainFromUpdateRequestDTO(request)).thenReturn(domain);
        when(truckUpdateCase.execute("ABC1234", domain)).thenReturn(updated);
        when(truckMapper.toFindResponseDTO(updated)).thenReturn(dto);

        var resp = controller.truckUpdate("ABC1234", request);

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("ABC1234", resp.getBody().licensePlate());
    }
}
