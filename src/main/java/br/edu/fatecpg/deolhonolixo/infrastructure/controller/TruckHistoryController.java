package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckHistoryFindAllCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckHistoryFindByIdCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckHistoryGetLastGeolocationCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckHistoryFindResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckHistoryGeolocationResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckHistoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trucks")
@RequiredArgsConstructor
@Tag(
        name = "Histórico de caminhões",
        description = "Endpoints responsáveis pela busca de históricos e geolocalização dos caminhões cadastrados."
)
public class TruckHistoryController {
    private final TruckHistoryMapper truckHistoryMapper;
    private final TruckHistoryFindAllCase findAllCase;
    private final TruckHistoryFindByIdCase findByIdCase;
    private final TruckHistoryGetLastGeolocationCase getLastGeolocationCase;


    @GetMapping("/history")
    @Operation(
            summary = "Mostra o histórico de todos os caminhões",
            description = "Busca pelo histórico de todos os caminhões e retorna em formato de lista."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Histórico exibido com sucesso"),
    })
    public ResponseEntity<List<TruckHistoryFindResponseDTO>> truckHistory(){
        List<TruckHistoryFindResponseDTO> response = findAllCase.execute()
                .stream()
                .map(truckHistoryMapper::toHistoryFindResponseDTO)
                .toList();

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/history/{id}")
    @Operation(
            summary = "Mostra o histórico do caminhão expecificado",
            description = "Busca pelo histórico do caminhão e retorna em formato de lista."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Histórico exibido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caminhão não encontrado"),
    })
    public ResponseEntity<List<TruckHistoryFindResponseDTO>> truckHistoryById(@PathVariable Long id){
        List<TruckHistoryFindResponseDTO> response = findByIdCase.execute(id)
                .stream()
                .map(truckHistoryMapper::toHistoryFindResponseDTO)
                .toList();

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/geolocation/{id}")
    @Operation(
            summary = "Mostra a geolocalização do caminhão expecificado",
            description = "Busca pelo historico do caminhão e retorna a geolocalização de sua ultima atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Geolocalização exibida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caminhão não encontrado"),
    })
    public ResponseEntity<TruckHistoryGeolocationResponseDTO> truckGeolocation(@PathVariable Long id){
        TruckHistoryGeolocationResponseDTO response = truckHistoryMapper.toHistoryGeolocationResponseDTO(getLastGeolocationCase.execute(id));

        return ResponseEntity.status(200).body(response);
    }
}
