package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TruckFindAllCaseTest {
    @Mock
    private TruckGateway truckGateway;
    @InjectMocks
    private TruckFindAllCase truckFindAllCase;

    @Test
    void shouldReturnListOfTrucksSuccessfully() {
        Truck truck1 = new Truck(1L, "ABC-1234", "ATIVO", LocalDateTime.now(), null, "ROTA-001");
        Truck truck2 = new Truck(2L, "XYZ-5678", "EM_MANUTENCAO", null, null, null);

        List<Truck> expectedTrucks = Arrays.asList(truck1, truck2);

        when(truckGateway.findAll()).thenReturn(expectedTrucks);

        List<Truck> result = truckFindAllCase.execute();

        assertAll("Validação da lista de caminhões",
                () -> assertNotNull(result, "A lista não deve ser nula"),
                () -> assertEquals(2, result.size(), "A lista deve conter 2 caminhões"),
                () -> assertEquals("ABC-1234", result.getFirst().licensePlate()),
                () -> assertEquals("ROTA-001", result.getFirst().routeId())
        );
        verify(truckGateway, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyList() {
        when(truckGateway.findAll()).thenReturn(Collections.emptyList());

        List<Truck> result = truckFindAllCase.execute();

        assertTrue(result.isEmpty());
        verify(truckGateway, times(1)).findAll();
    }
}
