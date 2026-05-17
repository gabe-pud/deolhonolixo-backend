package br.edu.fatecpg.deolhonolixo.core.usecase.truck;

import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.core.gateway.TruckHistoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TruckHistoryFindAllCaseTest {
    @Mock
    private TruckHistoryGateway historyGateway;
    @InjectMocks
    private TruckHistoryFindAllCase useCase;

    @Test
    void shouldReturnAllHistory() {
        List<TruckHistory> historyList = List.of(
                new TruckHistory("69e810d7bac673f468c0f63f", Instant.now(), "ABC-1234", -46.0, -23.0, 0)
        );
        when(historyGateway.findAll()).thenReturn(historyList);

        List<TruckHistory> result = useCase.execute();

        assertEquals(1, result.size());
        verify(historyGateway).findAll();
    }
}
