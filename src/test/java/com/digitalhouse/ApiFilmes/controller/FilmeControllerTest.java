package com.digitalhouse.ApiFilmes.controller;

import com.digitalhouse.ApiFilmes.model.Filme;
import com.digitalhouse.ApiFilmes.service.FilmeService;
import com.digitalhouse.ApiFilmes.suporte.BaseControllerTest;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.validation.constraints.AssertTrue;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class FilmeControllerTest extends BaseControllerTest {


    @SpyBean
    private  FilmeService filmeService;
    private Filme filmeComId;
    private Filme filme;

    @BeforeEach
    public void setup() {
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

    @Autowired
    FilmeControllerTest(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @Test
    @DisplayName("Deve encontrar um filme no sistema pois um filme é criado")
    void quando_criamosUmFilmeComAtributosValidos_entao_retornaFilmeComId() {
        Mockito.when(filmeService.create(filme)).thenReturn(filmeComId);
        filmeService.create(filme);
        Assertions.assertNotNull(filme.getId());
    }

    @Test
    @DisplayName("Deve encontrar filmes quando selecionamos todos")
    void quando_buscamosTodosOsFilmes_entao_retornaListaComFilmes() {
        Mockito.when(filmeService.selectAll()).thenReturn(List.of(filmeComId));
        List<Filme> filmes = filmeService.selectAll();
        Assertions.assertFalse(filmes.isEmpty());
    }

    @Test
    @DisplayName("Deve ter exception quando buscamos um filme depois de excluir-lo")
    void quando_excluimosUmFilme_entao_eleNaoApareceMaisNaConsulta() {
        Mockito.doNothing().when(filmeService).delete(filmeComId.getId());
        filmeService.delete(filmeComId.getId());
        Assertions.assertThrows(NoSuchElementException.class, () -> filmeService.select(filmeComId.getId()));

    }

}