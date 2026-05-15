package br.com.challenge2026.challengeFord.repository;

import br.com.challenge2026.challengeFord.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Optional<Veiculo> findByMarcaAndModeloAndVersao(String marca, String modelo, String versao);
}
