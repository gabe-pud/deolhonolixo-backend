package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.gateway.TruckHistoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TruckHistorySaveCaseTest {
    @Mock
    private TruckHistoryGateway historyGateway;
    @InjectMocks
    private TruckHistorySaveCase useCase;

    @Test
    void shouldSaveHistorySuccessfully() {
        String plate = "KKK-0000";
        Double lat = -23.0;
        Double lon = -46.0;

        useCase.execute(plate, lon, lat);

        verify(historyGateway).saveGeolocation(argThat(history ->
                history.licensePlate().equals(plate) &&
                        history.latitude().equals(lat) &&
                        history.longitude().equals(lon)
        ));
    }
}
