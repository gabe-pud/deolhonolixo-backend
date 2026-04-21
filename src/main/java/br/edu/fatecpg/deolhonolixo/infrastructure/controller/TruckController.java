package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckAlreadyRegisteredException;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckSearchCase;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckRegisterCase;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckSearchRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.mapper.TruckMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/trucks")
@RequiredArgsConstructor
@Tag(
        name = "Caminões",
        description = "Endpoints responsáveis pelo registro e busca de caminões."
)
public class TruckController {
    private final TruckMapper truckMapper;
    private final TruckRegisterCase registerCase;
    private final TruckSearchCase searchCase;

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
            description = "Busca um caminhão no sistema e retorna sua placa e rota designada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Caminhão encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caminhão não encontrado"),
    })
    public ResponseEntity<?> findTruck(@Valid @RequestBody TruckSearchRequestDTO body){
        Truck truckDomain = truckMapper.toDomainFromSearchRequestDTO(body);

        try {
            HashMap<String,String> response = searchCase.execute(truckDomain);
            return ResponseEntity.status(200).body(truckMapper.toSearcResponseDTO(response));
        } catch (TruckAlreadyRegisteredException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
