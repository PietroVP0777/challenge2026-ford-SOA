package br.com.challenge2026.challengeFord.controller;

import br.com.challenge2026.challengeFord.dto.ConsultaVeiculoDTO;
import br.com.challenge2026.challengeFord.dto.VeiculoDTO;
import br.com.challenge2026.challengeFord.model.Especificacoes;
import br.com.challenge2026.challengeFord.model.Veiculo;
import br.com.challenge2026.challengeFord.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@Tag(name = "Veículos", description = "Endpoints para gerenciamento de veículos")
public class VeiculoController {

    @Autowired
    VeiculoService service;


    @Operation(summary = "Listar veículos",
            description = "Retorna todos os veículos cadastrados")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum veículo encontrado")
    })
    @GetMapping
    public ResponseEntity<Page<VeiculoDTO>> listarVeiculos(
            @PageableDefault(size = 10,sort = "marca") Pageable pageable){
            return ResponseEntity.ok(service.listarVeiculos(pageable));
    }


    @Operation(summary = "Buscar Veículo",
            description = "Retorna um veículo específico cadastrado")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carro retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum veículo encontrado")
    })
    @GetMapping("/buscar")
    public ResponseEntity<VeiculoDTO> buscarVeiculo(@RequestParam String marca, @RequestParam String modelo, @RequestParam String versao){
        return ResponseEntity.ok(service.buscarVeiculo(marca, modelo, versao));
    }

    @Operation(summary = "Salvar veículo",
            description = "Salva um veículo no banco")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Veículo salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<VeiculoDTO> salvarVeiculo(@RequestBody @Valid Veiculo veiculo){

        return ResponseEntity.status(201).body(service.salvarVeiculo(veiculo));
    }

    @Operation(summary = "Consultar com IA",
            description = "Pesquise as especificações do veículo com IA")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/consultar")
    public ResponseEntity<List<Especificacoes>> consultarGemini(
            @RequestBody @Valid ConsultaVeiculoDTO dto) {

        return ResponseEntity.ok(service.consultarVeiculo(
                dto.marca(),
                dto.modelo(),
                dto.versao(),
                dto.prompt()
        ));
    }


}
