package br.edu.fatecpg.deolhonolixo.infrastructure.controller;

import br.edu.fatecpg.deolhonolixo.core.domain.Truck;
import br.edu.fatecpg.deolhonolixo.core.usecase.truck.*;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.request.TruckRegisterRequestDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckFindResponseDTO;
import br.edu.fatecpg.deolhonolixo.infrastructure.dto.response.TruckRegisterResponseDTO;
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
    public ResponseEntity<List<TruckFindResponseDTO>> truckFindAll(){
        List<TruckFindResponseDTO> response = truckFindAllCase.execute()
                .stream()
                .map(truckMapper::toFindResponseDTO)
                .toList();

        return ResponseEntity.status(200).body(response);
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
    public ResponseEntity<TruckFindResponseDTO> truckFindOne(@PathVariable String licensePlate){
        Truck targetTruck = findBylicensePlateCase.execute(licensePlate);
        TruckFindResponseDTO response = truckMapper.toFindResponseDTO(targetTruck);

        return ResponseEntity.status(200).body(response);
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
    public ResponseEntity<TruckRegisterResponseDTO> truckRegister(@Valid @RequestBody TruckRegisterRequestDTO body){
        Truck truckDomain = truckMapper.toDomainFromRegisterRequestDTO(body);
        HashMap<String,String> response = registerCase.execute(truckDomain);

        return ResponseEntity.status(200).body(truckMapper.toRegisterResponseDTO(response));
    }
}