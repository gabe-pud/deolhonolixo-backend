package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.UrbanGeometry;
import br.edu.fatecpg.deolhonolixo.core.usecase.city.UrbanGeometryFindAllCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.city.UrbanGeometryFindByNameCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.UrbanGeometryResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UrbanGeometryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrbanGeometryControllerTest {

    @Mock
    UrbanGeometryFindAllCase findAllCase;

    @Mock
    UrbanGeometryFindByNameCase findByNameCase;

    @Mock
    UrbanGeometryMapper mapper;

    @InjectMocks
    UrbanGeometryController controller;

    @Test
    void listarTodos_returns200() {
        UrbanGeometry domain = new UrbanGeometry("id1","Bairro", "Cidade", List.of(), "Periodo", "Hora", List.of("Seg"));
        UrbanGeometryResponseDTO dto = new UrbanGeometryResponseDTO("id1","Bairro","Cidade", List.of(),"Periodo","Hora", List.of("Seg"));

        when(findAllCase.execute()).thenReturn(List.of(domain));
        when(mapper.toResponseDTO(domain)).thenReturn(dto);

        var resp = controller.listarTodos();

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("Bairro", resp.getBody().get(0).name());
    }

    @Test
    void buscarPorNome_returns200() {
        UrbanGeometry domain = new UrbanGeometry("id2","Rua X", "Cidade", List.of(), "Periodo", "Hora", List.of("Ter"));
        UrbanGeometryResponseDTO dto = new UrbanGeometryResponseDTO("id2","Rua X","Cidade", List.of(),"Periodo","Hora", List.of("Ter"));

        when(findByNameCase.execute("Rua X")).thenReturn(domain);
        when(mapper.toResponseDTO(domain)).thenReturn(dto);

        var resp = controller.buscarPorNome("Rua X");

        assertEquals(200, resp.getStatusCode().value());
        assertEquals("Rua X", resp.getBody().name());
    }
}
