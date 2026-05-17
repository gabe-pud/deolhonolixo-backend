package br.edu.fatecpg.deolhonolixo.core.usecase.city;

import br.edu.fatecpg.deolhonolixo.core.domain.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.core.gateway.UrbanGeometryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrbanGeometryFindByNameCaseTest {
    @Mock
    private UrbanGeometryGateway gateway;
    @InjectMocks
    private UrbanGeometryFindByNameCase useCase;

    @Test
    void shouldReturnUrbanGeometryByName() {
        String name = "Centro";
        UrbanGeometry urbanGeometry = new UrbanGeometry("1", name, "Fatec City", List.<List<Double[]>>of(), "2026-05", "08:00", List.of("SEG"));

        when(gateway.findByName(name)).thenReturn(urbanGeometry);

        UrbanGeometry result = useCase.execute(name);

        assertEquals(urbanGeometry, result);
        verify(gateway).findByName(name);
    }
}