package br.com.challenge2026.challengeFord.service;

import br.com.challenge2026.challengeFord.model.Especificacoes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeminiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
    }

    public List<Especificacoes> gerarEspecificacoes(String marca,
                                                    String modelo,
                                                    String versao,
                                                    String pedidoUsuario) {

        try {
            String prompt = """
                    Você é um especialista automotivo.

                    Retorne APENAS JSON puro.
                    Sem markdown.
                    Sem explicações.
                    Sem ```json

                    Formato obrigatório:

                    [
                      {
                        "nome": "motor",
                        "valor": "3.0 V6 Biturbo"
                      },
                      {
                        "nome": "torque",
                        "valor": "583 Nm"
                      }
                    ]

                    Veículo:
                    %s %s %s

                    Pedido do usuário:
                    %s
                    """.formatted(marca, modelo, versao, pedidoUsuario);

            String requestBody = """
                    {
                      "contents": [
                        {
                          "parts": [
                            {
                              "text": %s
                            }
                          ]
                        }
                      ]
                    }
                    """.formatted(escapeJson(prompt));

            String resposta = webClient.post()
                    .uri("/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            String jsonLimpo = extrairTextoResposta(resposta);

            return objectMapper.readValue(
                    jsonLimpo,
                    new TypeReference<List<Especificacoes>>() {}
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private String extrairTextoResposta(String respostaApi) throws Exception {

        JsonNode root = objectMapper.readTree(respostaApi);

        String texto = root
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();

        return texto
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }

    private String escapeJson(String texto) {
        return "\"" +
                texto.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "") +
                "\"";
    }
}