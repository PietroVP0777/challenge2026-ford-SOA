package br.com.challenge2026.challengeFord.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "veiculos",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"marca", "modelo", "versao"}
        ))
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;

    private String modelo;

    private String versao;

    @ElementCollection
    @CollectionTable(
            name = "veiculo_especificacoes",
            joinColumns = @JoinColumn(name = "veiculo_id")
    )
    private List<Especificacoes> especificacoesList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getVersao() {
        return versao;
    }

    public List<Especificacoes> getEspecificacoesList() {
        return especificacoesList;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public void setEspecificacoesList(List<Especificacoes> especificacoesList) {
        this.especificacoesList = especificacoesList;
    }
}