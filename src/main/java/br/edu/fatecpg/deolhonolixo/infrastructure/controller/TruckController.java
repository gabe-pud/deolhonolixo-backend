package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.domain.TruckHistory;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckAlreadyRegisteredException;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckGeolocationNotFoundException;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckNotFoundException;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.*;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckFindRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckHistoryMapper;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/trucks")
@RequiredArgsConstructor
@Tag(
        name = "Caminões",
        description = "Endpoints responsáveis pelo registro e busca de caminões."
)
public class TruckController {
    private final TruckMapper truckMapper;
    private final TruckHistoryMapper truckHistoryMapper;
    private final TruckRegisterCase registerCase;
    private final TruckFindBylicensePlateCase findBylicensePlateCase;
    private final TruckFindAllCase truckFindAllCase;
    private final TruckHistoryFindAllCase findAllCase;
    private final TruckHistoryFindByIdCase findByIdCase;
    private final TruckHistoryGetLastGeolocationCase getLastGeolocationCase;


    @GetMapping("/")
    @Operation(
            summary = "Pesquisa por caminhões registrados",
            description = "Busca todos os caminhõoes no sistema e retorna suas informações."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exibindo caminhões encontrados")
    })
    public ResponseEntity<?> findTruck(){
        try {
            List<Truck> response = truckFindAllCase.execute();
            return ResponseEntity.status(200).body(
                    response.stream().map(truckMapper::toFindResponseDTO)
            );
        } catch (TruckNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @PostMapping("/register")
    @Operation(
            summary = "Registra um novo caminão",
            description = "Cria um novo caminhão no sistema e retorna uma mensagem de confirmação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro realizado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Caminhão já cadastrado"),
    })
    public ResponseEntity<?> register(@Valid @RequestBody TruckRegisterRequestDTO body){
        Truck truckDomain = truckMapper.toDomainFromRegisterRequestDTO(body);

        try {
            HashMap<String,String> response = registerCase.execute(truckDomain);
            return ResponseEntity.status(200).body(truckMapper.toRegisterResponseDTO(response));
        } catch (TruckAlreadyRegisteredException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/search")
    @Operation(
            summary = "Pesquisa por caminhões registrados",
            description = "Busca um caminhão no sistema e retorna suas informações."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Caminhão encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caminhão não encontrado"),
    })
    public ResponseEntity<?> findTruck(@Valid @RequestBody TruckFindRequestDTO body){
        Truck truckDomain = truckMapper.toDomainFromFindRequestDTO(body);

        try {
            Truck response = findBylicensePlateCase.execute(truckDomain);
            return ResponseEntity.status(200).body(truckMapper.toFindResponseDTO(response));
        } catch (TruckAlreadyRegisteredException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @GetMapping("/history")
    @Operation(
            summary = "Mostra o historico de todos os caminões",
            description = "Busca pelo historico de todos os caminhões e retorna em formato de lista."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "histórico exibido com sucesso"),
    })
    public ResponseEntity<?> truckHistory(){
        List<TruckHistory> response = findAllCase.execute();
        return ResponseEntity.status(200).body(response.stream().map(truckHistoryMapper::toHistoryFindResponseDTO));
    }

    @GetMapping("/history/{id}")
    @Operation(
            summary = "Mostra o historico do caminhão expecificado",
            description = "Busca pelo historico do caminhão e retorna em formato de lista."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "histórico exibido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caminhão não encontrado"),
    })
    public ResponseEntity<?> truckHistoryById(@PathVariable Long id){
        try {
            List<TruckHistory> response = findByIdCase.execute(id);
            return ResponseEntity.status(200).body(response.stream().map(truckHistoryMapper::toHistoryFindResponseDTO));
        } catch (TruckNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/geolocation/{id}")
    @Operation(
            summary = "Mostra a geolocalização do caminão expecificado",
            description = "Busca pelo historico do caminhão e retorna a geolocalização de sua ultima atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "geolocalização exibida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caminhão não encontrado"),
    })
    public ResponseEntity<?> truckGeolocation(@PathVariable Long id){
        try {
            TruckHistory response = getLastGeolocationCase.execute(id);
            return ResponseEntity.status(200).body(truckHistoryMapper.toHistoryGeolocationResponseDTO(response));
        } catch (TruckNotFoundException | TruckGeolocationNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
