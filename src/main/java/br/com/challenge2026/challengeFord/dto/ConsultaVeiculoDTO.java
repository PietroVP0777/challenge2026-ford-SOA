package br.com.challenge2026.challengeFord.dto;

import jakarta.validation.constraints.NotBlank;

public record ConsultaVeiculoDTO(
        @NotBlank
        String marca,

        @NotBlank
        String modelo,

        @NotBlank
        String versao,
        String prompt
) {
}