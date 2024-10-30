package com.twa.financeira.repository.impl;

import org.springframework.stereotype.Repository;

import com.persistence.commun.component.SysGenericRepository;
import com.twa.financeira.repository.FinanceiraRepositorio;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class FinanceiraRepositorioImpl extends SysGenericRepository implements FinanceiraRepositorio {

    private static final long serialVersionUID = 1L;

    public FinanceiraRepositorioImpl() {
    }

    @PostConstruct
    public void init() {
	log.info("Create FinanceiraRepositorioImpl");
    }

   
}
