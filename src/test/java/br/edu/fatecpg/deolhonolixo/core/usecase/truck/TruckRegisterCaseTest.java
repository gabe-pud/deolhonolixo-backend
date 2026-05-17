package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckAlreadyRegisteredException;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckNotFoundException;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TruckRegisterCaseTest {
    @Mock
    private TruckGateway truckGateway;
    @InjectMocks
    private TruckRegisterCase useCase;

    @Test
    void shouldSaveWhenTruckNotFound() {
        Truck truck = new Truck(null, "NEW-1234", "ACTIVE", null, null, null);

        doThrow(new TruckNotFoundException()).when(truckGateway).existsBylicensePlate(truck);

        useCase.execute(truck);

        verify(truckGateway).save(truck);
    }

    @Test
    void shouldThrowExceptionWhenTruckAlreadyExists() {
        Truck truck = new Truck(null, "OLD-1234", "ACTIVE", null, null, null);

        doNothing().when(truckGateway).existsBylicensePlate(truck);

        assertThrows(TruckAlreadyRegisteredException.class, () -> useCase.execute(truck));
        verify(truckGateway, never()).save(any());
    }
}
