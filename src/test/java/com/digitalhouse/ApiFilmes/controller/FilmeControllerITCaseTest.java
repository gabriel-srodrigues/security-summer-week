package com.digitalhouse.ApiFilmes.controller;

import com.digitalhouse.ApiFilmes.model.Filme;
import com.digitalhouse.ApiFilmes.service.FilmeService;
import com.digitalhouse.ApiFilmes.suporte.BaseControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource(value = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmeControllerITCaseTest extends BaseControllerTest {
    private Filme filmeComId;
    private Filme filme;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setupEach() {
        filme = new Filme();
        filme.setTitulo(TITULO);
        filme.setDescricao(DESCRICAO);
        filme.setUrlImg(URL_IMAGEM);

        filmeComId = new Filme();
        filmeComId.setId(1);
        filmeComId.setTitulo(TITULO);
        filmeComId.setDescricao(DESCRICAO);
        filmeComId.setUrlImg(URL_IMAGEM);
    }

    @Test
    void mockMvcConfigurado() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "USER")
    void quando_tentamosAcessarListaDeFilmesComAutenticacao_entao_retornaListaDeFilmes() throws Exception {
        this.mockMvc.perform(get("/filme")).andExpect(status().isOk());
    }

    @Test
    void quando_tentamosAcessarListaDeFilmesSemAutenticacao_entao_retornaNaoAutorizado() throws Exception {
        this.mockMvc.perform(get("/filme")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = "USER")
    void quando_tentamosCriarFilmeComAutenticacao_entao_retornaFilme() throws Exception {
        this.mockMvc
                .perform(post("/filme")
                        .content(toJsonString(filme))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(filmeComId)));
    }

    @Test
    void quando_tentamosAcessarSemAutenticacao_entao_retornaAcessoNegado() throws Exception {
        this.mockMvc
                .perform(post("/filme")
                        .content(toJsonString(filme))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}