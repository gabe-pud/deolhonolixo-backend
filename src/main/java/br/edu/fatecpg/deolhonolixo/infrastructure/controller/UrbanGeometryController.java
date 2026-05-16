package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.usecase.city.UrbanGeometryFindAllCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.city.UrbanGeometryFindByNameCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.UrbanGeometryResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.UrbanGeometryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/urban-geometry")
@RequiredArgsConstructor
@Tag(
        name = "Urban Geometry",
        description = "Endpoints responsáveis por fornecer informações sobre bairros e ruas de Praia Grande."
)
public class UrbanGeometryController {
    private final UrbanGeometryFindAllCase findAllCase;
    private final UrbanGeometryFindByNameCase findByNameCase;
    private final UrbanGeometryMapper mapper;

    @GetMapping
    @Operation(
            summary = "Lista todos os bairros e ruas",
            description = "Retorna uma lista com todos os bairros e ruas de Praia Grande cadastrados no banco."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<UrbanGeometryResponseDTO>> listarTodos() {
        List<UrbanGeometryResponseDTO> response = findAllCase.execute()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}")
    @Operation(
            summary = "Busca bairro ou rua por nome",
            description = "Retorna as informações de um bairro ou rua específico pelo nome."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bairro ou rua encontrado"),
            @ApiResponse(responseCode = "404", description = "Bairro ou rua não encontrado")
    })
    public ResponseEntity<UrbanGeometryResponseDTO> buscarPorNome(@PathVariable String name) {
        UrbanGeometryResponseDTO response = mapper.toResponseDTO(findByNameCase.execute(name));

        return ResponseEntity.ok(response);
    }
}