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
import com.twa.financeira.entity.Financeira;
import com.twa.financeira.repository.FinanceiraRepositorio;
import com.twa.financeira.repository.impl.FinanceiraRepositorioImpl;
import com.twa.financeira.service.FinanceiraService;

@SpringBootTest
public class FinanceiraServiceImplTest extends ContextWebApplicationJUnit {

    @Autowired
    @Qualifier("financeiraServiceImpl")
    private FinanceiraService financeiraService;


    private FinanceiraRepositorio financeiraRepositorio;

    @BeforeEach
    public void initMocks() {
	financeiraRepositorio = Mockito.mock(FinanceiraRepositorioImpl.class);
	ReflectionTestUtils.setField(financeiraService, "dao", financeiraRepositorio);
    }

    @Test
    @DisplayName(value = "save user")
    void saveTest() {
	
	var financeira = construirFinanceira();
	when(financeiraRepositorio.save(financeira)).thenReturn(financeira);
	var userDetail = financeiraService.save(financeira);
	
	assertNotNull(userDetail);
	assertEquals(1L, userDetail.getId());
    }

    private Financeira construirFinanceira() {
	var financeira = new Financeira();
	financeira.setId(1L);
	return financeira;
    }
}