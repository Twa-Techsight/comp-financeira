package com.twa.financeira.repository.impl;

import org.springframework.stereotype.Repository;

import com.persistence.commun.component.SysGenericRepository;
import com.twa.financeira.repository.ResolucaoTrapezioRepositorio;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ResolucaoTrapezioRepositorioImpl extends SysGenericRepository implements ResolucaoTrapezioRepositorio {

    private static final long serialVersionUID = 1L;

    public ResolucaoTrapezioRepositorioImpl() {
    }

    @PostConstruct
    public void init() {
	log.info("Create ResolucaoTrapezioRepositorioImpl");
    }

   
}
