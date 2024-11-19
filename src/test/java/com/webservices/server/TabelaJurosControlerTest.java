package com.webservices.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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
    @DisplayName(value = "get {id} tabelaJuros")
    void getTest() {
	var tabelaJuros = criarEmpresa();
	when(tabelaJurosRepository.findOneById(Mockito.eq(TabelaJuros.class), Mockito.anyLong(), Mockito.anyMap()))
		.thenReturn(tabelaJuros);
	
	var response = controler.get(1L);
	
	assertNotNull(response);
	assertEquals(1L, response.getBody().getData().getId());
    }

  

    @Test
    @DisplayName(value = "edit tabelaJuros, encontrou")
    void editTest() {
	var empresaUpdate = criarEmpresaUpdate();
	var tabelaJuros = criarEmpresa();
	tabelaJuros.setNome("Teste Atualizado");
	when(tabelaJurosRepository.update(Mockito.any(TabelaJuros.class))).thenReturn(tabelaJuros);
	when(tabelaJurosRepository.count(Mockito.eq(TabelaJuros.class), Mockito.anyMap())).thenReturn(1);
	var response = controler.update(1L, empresaUpdate);
	assertNotNull(response);
	assertEquals(1L, response.getBody().getData().getId());
    }

    @Test
    @DisplayName(value = "edit tabelaJuros, não encontrou")
    void editNaoEncontrouTest() {
	var empresaUpdate = criarEmpresaUpdate();

	when(tabelaJurosRepository.count(Mockito.eq(TabelaJuros.class), Mockito.anyMap())).thenReturn(0);
	var throwable = Assertions.assertThrows(RuntimeException.class, () -> {
	    controler.update(1L, empresaUpdate);
	});
	assertEquals("OBJECT_NOT_FOUND", throwable.getMessage());
    }
    
    private TabelaJurosUpdateDTO criarEmpresaUpdate() {
	var tabelaJuros = new TabelaJurosUpdateDTO();
	return tabelaJuros;
    }

    @SuppressWarnings("unused")
    private TabelaJurosRequestDTO criarEmpresaRequest() {
	var tabelaJuros = new TabelaJurosRequestDTO();
	tabelaJuros.setNome("Teste 01");
	return tabelaJuros;
    }

    private TabelaJuros criarEmpresa() {
	var tabelaJuros01 = new TabelaJuros(1L);
	tabelaJuros01.setNome("Teste 01");
	return tabelaJuros01;
    }
}