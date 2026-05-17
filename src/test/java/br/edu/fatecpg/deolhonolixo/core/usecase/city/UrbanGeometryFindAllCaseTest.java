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
class UrbanGeometryFindAllCaseTest {
    @Mock
    private UrbanGeometryGateway gateway;
    @InjectMocks
    private UrbanGeometryFindAllCase useCase;

    @Test
    void shouldReturnAllUrbanGeometries() {
        List<UrbanGeometry> urbanGeometries = List.of(
                new UrbanGeometry("1", "Centro", "Fatec City", List.<List<Double[]>>of(), "2026-05", "08:00", List.of("SEG"))
        );
        when(gateway.findAll()).thenReturn(urbanGeometries);

        List<UrbanGeometry> result = useCase.execute();

        assertEquals(urbanGeometries, result);
        verify(gateway).findAll();
    }
}