package br.com.challenge2026.challengeFord.service;

import br.com.challenge2026.challengeFord.dto.VeiculoDTO;
import br.com.challenge2026.challengeFord.model.Especificacoes;
import br.com.challenge2026.challengeFord.model.Veiculo;
import br.com.challenge2026.challengeFord.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    VeiculoRepository repository;

    @Autowired
    GeminiService geminiService;

    public Page<VeiculoDTO> listarVeiculos(Pageable pageable){
        return repository.findAll(pageable).map(v -> new VeiculoDTO(v.getMarca(),v.getModelo(),v.getVersao(),v.getEspecificacoesList()));
    }

    public VeiculoDTO buscarVeiculo(String marca, String modelo, String versao){
        return repository.findByMarcaAndModeloAndVersao(marca, modelo, versao).map(v -> new VeiculoDTO(v.getMarca(),v.getModelo(),v.getVersao(),v.getEspecificacoesList())).orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }

    @Transactional
    public VeiculoDTO salvarVeiculo(Veiculo veiculo) {

        if(veiculo.getMarca() == null || veiculo.getMarca().isBlank()){
            throw new IllegalArgumentException("Marca do veículo é obrigatória");
        }

        if (veiculo.getModelo() == null || veiculo.getModelo().isBlank()) {
            throw new IllegalArgumentException("Modelo é obrigatório");
        }

        if (veiculo.getVersao() == null || veiculo.getVersao().isBlank()) {
            throw new IllegalArgumentException("Versão é obrigatória");
        }

        if (veiculo.getEspecificacoesList() == null) {
            veiculo.setEspecificacoesList(new ArrayList<>());
        }

        if(repository.findByMarcaAndModeloAndVersao(veiculo.getMarca(), veiculo.getModelo(), veiculo.getVersao()).isPresent()){
            throw new IllegalArgumentException("Veículo já está salvo no banco!");
        }
        Veiculo salvo = repository.save(veiculo);
        return new VeiculoDTO(salvo.getMarca(), salvo.getModelo(), salvo.getVersao(), salvo.getEspecificacoesList());
    }

    @Transactional
    public List<Especificacoes> consultarVeiculo(
            String marca,
            String modelo,
            String versao,
            String prompt) {

        List<Especificacoes> novasSpecs =
                geminiService.gerarEspecificacoes(
                        marca,
                        modelo,
                        versao,
                        prompt
                );

        Optional<Veiculo> optional =
                repository.findByMarcaAndModeloAndVersao(
                        marca,
                        modelo,
                        versao
                );
        Veiculo veiculo;

        if (optional.isPresent()) {

            veiculo = optional.get();

        } else {

            veiculo = new Veiculo();
            veiculo.setMarca(marca);
            veiculo.setModelo(modelo);
            veiculo.setVersao(versao);
            veiculo.setEspecificacoesList(new ArrayList<>());
        }

        for (Especificacoes nova : novasSpecs) {

            boolean existe =
                    veiculo.getEspecificacoesList()
                            .stream()
                            .anyMatch(e ->
                                    e.getNome().equalsIgnoreCase(nova.getNome())
                            );

            if (!existe) {
                veiculo.getEspecificacoesList().add(nova);
            }
        }

        Veiculo salvo = repository.save(veiculo);

        return salvo.getEspecificacoesList();
    }


}
