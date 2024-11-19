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
import com.twa.financeira.dto.FinanceiraRequestDTO;
import com.twa.financeira.dto.FinanceiraUpdateDTO;
import com.twa.financeira.entity.Financeira;
import com.twa.financeira.repository.FinanceiraRepositorio;
import com.twa.financeira.repository.impl.FinanceiraRepositorioImpl;
import com.twa.financeira.service.FinanceiraService;
import com.webservices.resouces.FinanceiraResource;

import jakarta.servlet.http.HttpServletResponse;

@SpringBootTest
public class FinanceiraControlerTest extends ContextWebApplicationJUnit {

    @Autowired
    @Qualifier("financeiraControler")
    private FinanceiraResource controler;

    @Mock
    private HttpServletResponse httpResponse;

    @Autowired
    @Qualifier("financeiraServiceImpl")
    private FinanceiraService financeiraService;

    @Autowired
    private ObjectMapper modelMapper;

    private FinanceiraRepositorio financeiraRepository;

    @BeforeEach
    public void initMocks() {
	financeiraRepository = Mockito.mock(FinanceiraRepositorioImpl.class);
	ReflectionTestUtils.setField(controler, "mapper", modelMapper);
	ReflectionTestUtils.setField(controler, "service", financeiraService);
	ReflectionTestUtils.setField(financeiraService, "dao", financeiraRepository);
    }

    @Test
    @DisplayName(value = "get {id} financeira")
    void getTest() {
	var financeira = criarEmpresa();
	when(financeiraRepository.findOneById(Mockito.eq(Financeira.class), Mockito.anyLong(), Mockito.anyMap()))
		.thenReturn(financeira);
	
	var response = controler.get(1L);
	
	assertNotNull(response);
	assertEquals(1L, response.getBody().getData().getId());
    }

    @Test
    @DisplayName(value = "save financeira")
    void saveTest() {
	var empresaRequest = criarEmpresaRequest();
	var financeira = criarEmpresa();
	when(financeiraRepository.save(Mockito.any(Financeira.class))).thenReturn(financeira);
	var response = controler.save(empresaRequest, httpResponse);
	assertNotNull(response);
	assertEquals(1L, response.getBody().getData().getId());
    }

    @Test
    @DisplayName(value = "edit financeira, encontrou")
    void editTest() {
	var empresaUpdate = criarEmpresaUpdate();
	var financeira = criarEmpresa();
	financeira.setNome("Teste Atualizado");
	when(financeiraRepository.update(Mockito.any(Financeira.class))).thenReturn(financeira);
	when(financeiraRepository.count(Mockito.eq(Financeira.class), Mockito.anyMap())).thenReturn(1);
	var response = controler.update(1L, empresaUpdate);
	assertNotNull(response);
	assertEquals(1L, response.getBody().getData().getId());
    }

    @Test
    @DisplayName(value = "edit financeira, não encontrou")
    void editNaoEncontrouTest() {
	var empresaUpdate = criarEmpresaUpdate();

	when(financeiraRepository.count(Mockito.eq(Financeira.class), Mockito.anyMap())).thenReturn(0);
	var throwable = Assertions.assertThrows(RuntimeException.class, () -> {
	    controler.update(1L, empresaUpdate);
	});
	assertEquals("OBJECT_NOT_FOUND", throwable.getMessage());
    }
    
    @Test
    @DisplayName(value = "delete financeira, encontrou")
    void deleteTest() {
	var financeira = criarEmpresa();
	when(financeiraRepository.deleteById(Mockito.eq(Financeira.class), Mockito.anyLong(),Mockito.anyMap())).thenReturn(financeira);
	when(financeiraRepository.findOneById(Mockito.eq(Financeira.class), Mockito.anyLong(), Mockito.eq(null))).thenReturn(financeira);
	var response = controler.delete(1L);
	assertNotNull(response);
	assertEquals(1L, response.getBody().getData().getId());
    }

    @Test
    @DisplayName(value = "delete financeira, não encontrou")
    void deleteNaoEncontrouTest() {
	when(financeiraRepository.deleteById(Mockito.eq(Financeira.class), Mockito.anyLong(), Mockito.eq(null))).thenReturn(null);
	var response = controler.delete(1L);
	assertNotNull(response);
	assertEquals(null, response.getBody().getData());
    }

    private FinanceiraUpdateDTO criarEmpresaUpdate() {
	var financeira = new FinanceiraUpdateDTO();
	return financeira;
    }

    private FinanceiraRequestDTO criarEmpresaRequest() {
	var financeira = new FinanceiraRequestDTO();
	financeira.setNome("Teste 01");
	return financeira;
    }

    private Financeira criarEmpresa() {
	var financeira01 = new Financeira(1L);
	financeira01.setNome("Teste 01");
	return financeira01;
    }
}