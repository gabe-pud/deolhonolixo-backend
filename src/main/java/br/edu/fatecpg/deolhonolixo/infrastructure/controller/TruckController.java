package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckAlreadyRegisteredException;
import br.edu.fatecpg.deolhonolixo.core.domain.exception.TruckNotFoundException;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.*;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckRegisterRequestDTO;
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
        name = "Caminhões",
        description = "Endpoints responsáveis pelo registro e busca de caminhões."
)
public class TruckController {
    private final TruckMapper truckMapper;
    private final TruckRegisterCase registerCase;
    private final TruckFindBylicensePlateCase findBylicensePlateCase;
    private final TruckFindAllCase truckFindAllCase;


    @GetMapping("")
    @Operation(
            summary = "Pesquisa por caminhões registrados",
            description = "Busca todos os caminhõoes no sistema e retorna suas informações."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exibindo caminhões encontrados")
    })
    public ResponseEntity<?> truckFindAll(){
        try {
            List<Truck> response = truckFindAllCase.execute();
            return ResponseEntity.status(200).body(
                    response.stream().map(truckMapper::toFindResponseDTO)
            );
        } catch (TruckNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/{licensePlate}")
    @Operation(
            summary = "Pesquisa por caminhões registrados",
            description = "Busca um caminhão no sistema e retorna suas informações."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Caminhão encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caminhão não encontrado"),
    })
    public ResponseEntity<?> truckFindOne(@PathVariable String licensePlate){
        try {
            Truck response = findBylicensePlateCase.execute(licensePlate);
            return ResponseEntity.status(200).body(truckMapper.toFindResponseDTO(response));
        } catch (TruckAlreadyRegisteredException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    @Operation(
            summary = "Registra um novo caminhão",
            description = "Cria um novo caminhão no sistema e retorna uma mensagem de confirmação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro realizado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Caminhão já cadastrado"),
    })
    public ResponseEntity<?> truckRegister(@Valid @RequestBody TruckRegisterRequestDTO body){
        Truck truckDomain = truckMapper.toDomainFromRegisterRequestDTO(body);

        try {
            HashMap<String,String> response = registerCase.execute(truckDomain);
            return ResponseEntity.status(200).body(truckMapper.toRegisterResponseDTO(response));
        } catch (TruckAlreadyRegisteredException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}