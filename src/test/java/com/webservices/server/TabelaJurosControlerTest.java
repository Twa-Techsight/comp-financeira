package com.webservices.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.ContextWebApplicationJUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twa.financeira.dto.TabelaJurosRequestDTO;
import com.twa.financeira.dto.TabelaJurosUpdateDTO;
import com.twa.financeira.entity.TabelaJuros;
import com.twa.financeira.repository.TabelaJurosRepositorio;
import com.twa.financeira.repository.impl.TabelaJurosRepositorioImpl;
import com.twa.financeira.service.TabelaJurosService;
import com.webservices.resouces.TabelaJurosResource;

import jakarta.servlet.http.HttpServletResponse;

@SpringBootTest
public class TabelaJurosControlerTest extends ContextWebApplicationJUnit {

    @Autowired
    @Qualifier("tabelaJurosControler")
    private TabelaJurosResource controler;

    @Mock
    private HttpServletResponse httpResponse;

    @Autowired
    @Qualifier("tabelaJurosServiceImpl")
    private TabelaJurosService tabelaJurosService;

    @Autowired
    private ObjectMapper modelMapper;

    private TabelaJurosRepositorio tabelaJurosRepository;

    @BeforeEach
    public void initMocks() {
	tabelaJurosRepository = Mockito.mock(TabelaJurosRepositorioImpl.class);
	ReflectionTestUtils.setField(controler, "mapper", modelMapper);
	ReflectionTestUtils.setField(controler, "service", tabelaJurosService);
	ReflectionTestUtils.setField(tabelaJurosService, "dao", tabelaJurosRepository);
    }

    @Test
    @DisplayName(value = "Listar TabelaJuroses")
    void listTest() {

	var empresas = criarTabelaJuross();

	when(tabelaJurosRepository.findAll(Mockito.eq(TabelaJuros.class), Mockito.anyMap(), Mockito.anyInt(), Mockito.anyInt()))
		.thenReturn(empresas);
	var response = controler.list(1L, 0, 10);
	assertNotNull(response);
	assertEquals(2, response.getBody().getCollection().size());
	assertEquals(1L, response.getBody().getCollection().get(0).getId());
    }

    @Test
    @DisplayName(value = "Listar empresas vazias")
    void listVaziasTest() {
	
	when(tabelaJurosRepository.findAll(Mockito.eq(TabelaJuros.class), Mockito.anyMap(), Mockito.anyInt(), Mockito.anyInt()))
	.thenReturn(Arrays.asList());
	var response = controler.list(1l, 0, 10);
	assertNotNull(response);
	assertEquals(0, response.getBody().getCollection().size());
	
    }

    @Test
    @DisplayName(value = "get {id} tabelaJuros")
    void getTest() {
	var tabelaJuros = criarTabelaJuros();
	when(tabelaJurosRepository.findOneById(Mockito.eq(TabelaJuros.class), Mockito.anyLong(), Mockito.anyMap()))
		.thenReturn(tabelaJuros);
	
	var response = controler.get(1L);
	
	assertNotNull(response);
	assertEquals(1L, response.getBody().getData().getId());
    }

    @Test
    @DisplayName(value = "save tabelaJuros")
    void saveTest() {
	var empresaRequest = criarTabelaJurosRequest();
	var tabelaJuros = criarTabelaJuros();
	when(tabelaJurosRepository.save(Mockito.any(TabelaJuros.class))).thenReturn(tabelaJuros);
	var response = controler.save(empresaRequest, httpResponse);
	assertNotNull(response);
	assertEquals(1L, response.getBody().getData().getId());
    }

    @Test
    @DisplayName(value = "edit tabelaJuros, encontrou")
    void editTest() {
	var empresaUpdate = criarTabelaJurosUpdate();
	var tabelaJuros = criarTabelaJuros();
	tabelaJuros.setFinanceiraId(200l);
	when(tabelaJurosRepository.update(Mockito.any(TabelaJuros.class))).thenReturn(tabelaJuros);
	when(tabelaJurosRepository.count(Mockito.eq(TabelaJuros.class), Mockito.anyMap())).thenReturn(1);
	var response = controler.edit(1L, empresaUpdate);
	assertNotNull(response);
	assertEquals(1L, response.getBody().getData().getId());
    }

    @Test
    @DisplayName(value = "edit tabelaJuros, não encontrou")
    void editNaoEncontrouTest() {
	var empresaUpdate = criarTabelaJurosUpdate();

	when(tabelaJurosRepository.count(Mockito.eq(TabelaJuros.class), Mockito.anyMap())).thenReturn(0);
	var throwable = Assertions.assertThrows(RuntimeException.class, () -> {
	    controler.edit(1L, empresaUpdate);
	});
	assertEquals("OBJECT_NOT_FOUND", throwable.getMessage());
    }
    
    @Test
    @DisplayName(value = "delete tabelaJuros, encontrou")
    void deleteTest() {
	var tabelaJuros = criarTabelaJuros();
	when(tabelaJurosRepository.deleteById(Mockito.eq(TabelaJuros.class), Mockito.anyLong(),Mockito.anyMap())).thenReturn(tabelaJuros);
	when(tabelaJurosRepository.findOneById(Mockito.eq(TabelaJuros.class), Mockito.anyLong(), Mockito.anyMap())).thenReturn(tabelaJuros);
	var response = controler.delete(1L);
	assertNotNull(response);
	assertEquals(1L, response.getBody().getData().getId());
    }

    @Test
    @DisplayName(value = "delete tabelaJuros, não encontrou")
    void deleteNaoEncontrouTest() {
	when(tabelaJurosRepository.deleteById(Mockito.eq(TabelaJuros.class), Mockito.anyLong(), Mockito.eq(null))).thenReturn(null);
	var response = controler.delete(1L);
	assertNotNull(response);
	assertEquals(null, response.getBody().getData());
    }

    private TabelaJurosUpdateDTO criarTabelaJurosUpdate() {
	var tabelaJuros = new TabelaJurosUpdateDTO();
	return tabelaJuros;
    }

    private TabelaJurosRequestDTO criarTabelaJurosRequest() {
	var tabelaJuros = new TabelaJurosRequestDTO();
	tabelaJuros.setFinanceiraId(100l);
	return tabelaJuros;
    }

    private TabelaJuros criarTabelaJuros() {
	var tabelaJuros01 = new TabelaJuros(1L);
	tabelaJuros01.setFinanceiraId(100l);
	return tabelaJuros01;
    }

    private List<TabelaJuros> criarTabelaJuross() {
	var tabelaJuros01 = new TabelaJuros(1L);
	tabelaJuros01.setFinanceiraId(100l);
	var empresa02 = new TabelaJuros(2L);
	empresa02.setFinanceiraId(100l);
	return Arrays.asList(tabelaJuros01, empresa02);
    }
}