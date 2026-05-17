package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckHistoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TruckHistoryGetLastGeolocationCaseTest {
    @Mock
    private TruckHistoryGateway historyGateway;
    @Mock
    private TruckGateway truckGateway;
    @InjectMocks
    private TruckHistoryGetLastGeolocationCase useCase;

    @Test
    void shouldGetLastGeolocationByTruckId() {
        Long id = 10L;
        Truck truck = new Truck(id, "XYZ-9999", "ACTIVE", null, null, "R2");

        when(truckGateway.findById(id)).thenReturn(truck);

        useCase.execute(id);

        verify(historyGateway).getLastGeolocation("XYZ-9999");
    }
}
