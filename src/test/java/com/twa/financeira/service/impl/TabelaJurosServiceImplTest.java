package com.twa.financeira.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.ContextWebApplicationJUnit;
import com.twa.financeira.entity.TabelaJuros;
import com.twa.financeira.repository.TabelaJurosRepositorio;
import com.twa.financeira.repository.impl.TabelaJurosRepositorioImpl;
import com.twa.financeira.service.TabelaJurosService;

@SpringBootTest
public class TabelaJurosServiceImplTest extends ContextWebApplicationJUnit {

    @Autowired
    @Qualifier("tabelaJurosServiceImpl")
    private TabelaJurosService tabelaJurosService;


    private TabelaJurosRepositorio TabelaJurosRepositorio;

    @BeforeEach
    public void initMocks() {
	TabelaJurosRepositorio = Mockito.mock(TabelaJurosRepositorioImpl.class);
	ReflectionTestUtils.setField(tabelaJurosService, "dao", TabelaJurosRepositorio);
    }

    @Test
    @DisplayName(value = "save user")
    void saveTest() {
	
	var TabelaJuros = contruirTabelaJuros();
	when(TabelaJurosRepositorio.save(TabelaJuros)).thenReturn(TabelaJuros);
	var userDetail = tabelaJurosService.save(TabelaJuros);
	
	assertNotNull(userDetail);
	assertEquals(1L, userDetail.getId());
    }

    private TabelaJuros contruirTabelaJuros() {
	var tabelaJuros = new TabelaJuros(1l);
	tabelaJuros.setId(1L);
	return tabelaJuros;
    }
}