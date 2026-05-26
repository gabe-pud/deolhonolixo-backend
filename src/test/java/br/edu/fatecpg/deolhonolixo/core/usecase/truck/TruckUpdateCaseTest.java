package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TruckUpdateCaseTest {

    @Mock
    private TruckGateway truckGateway;

    @InjectMocks
    private TruckUpdateCase useCase;

    @Test
    void shouldUpdateTruckFields() {
        Truck existingTruck = new Truck(1L, "ABC1234", "LIVRE", null, null, null);
        Truck payload = new Truck(null, null, "EM_ROTA",
                LocalDateTime.of(2026, 5, 25, 8, 0),
                LocalDateTime.of(2026, 5, 25, 18, 0),
                "PG-LIXO-042");

        when(truckGateway.findBylicensePlate("ABC1234")).thenReturn(existingTruck);

        Truck result = useCase.execute("ABC1234", payload);

        assertEquals(1L, result.id());
        assertEquals("ABC1234", result.licensePlate());
        assertEquals("EM_ROTA", result.status());
        assertEquals("PG-LIXO-042", result.routeId());
        verify(truckGateway).save(result);
    }
}