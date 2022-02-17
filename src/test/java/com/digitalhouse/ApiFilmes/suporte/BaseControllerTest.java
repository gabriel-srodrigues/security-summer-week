package com.digitalhouse.ApiFilmes.suporte;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseControllerTest {
    public static final String TITULO = "Esposa de mentirinha";
    public static final String DESCRICAO = "Um dentista para lá de maluco pede a sua assistente que finja ser sua esposa.";
    public static final String URL_IMAGEM = "http://digitalhouse.com.br/filmes-em-cartaz";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
