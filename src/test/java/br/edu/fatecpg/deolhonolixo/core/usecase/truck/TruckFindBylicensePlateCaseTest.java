package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TruckFindBylicensePlateCaseTest {
    @Mock
    private TruckGateway truckGateway;
    @InjectMocks
    private TruckFindBylicensePlateCase useCase;

    @Test
    void shouldReturnTruckByLicensePlate() {
        String licensePlate = "ABC-1234";
        Truck expected = new Truck(1L, licensePlate, "ATIVO", null, null, "R1");
        when(truckGateway.findBylicensePlate(licensePlate)).thenReturn(expected);

        Truck result = useCase.execute(licensePlate);

        assertEquals(licensePlate, result.licensePlate());
        verify(truckGateway).findBylicensePlate(licensePlate);
    }
}
