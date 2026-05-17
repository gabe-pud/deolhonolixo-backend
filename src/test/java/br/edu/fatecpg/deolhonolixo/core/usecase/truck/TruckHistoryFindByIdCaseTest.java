package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckHistoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TruckHistoryFindByIdCaseTest {
    @Mock
    private TruckHistoryGateway historyGateway;
    @Mock
    private TruckGateway truckGateway;
    @InjectMocks
    private TruckHistoryFindByIdCase useCase;

    @Test
    void shouldFindHistoryByTruckId() {
        Long id = 1L;
        String plate = "ABC-1234";
        Truck truck = new Truck(id, plate, "ACTIVE", null, null, "R1");

        when(truckGateway.findById(id)).thenReturn(truck);
        when(historyGateway.findByLicencePlate(plate)).thenReturn(List.of());

        useCase.execute(id);

        verify(truckGateway).findById(id);
        verify(historyGateway).findByLicencePlate(plate);
    }
}
