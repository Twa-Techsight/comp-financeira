package com.twa.financeira.service.impl;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.persistence.commun.component.SysCriterion;
import com.persistence.commun.service.component.SysGenericService;
import com.twa.financeira.repository.TabelaJurosRepositorio;
import com.twa.financeira.service.TabelaJurosService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TabelaJurosServiceImpl extends SysGenericService implements TabelaJurosService {

    private static final long serialVersionUID = 1L;

    public TabelaJurosServiceImpl(@Qualifier("tabelaJurosRepositorioImpl") final TabelaJurosRepositorio dao) {
        super(dao);
    }

    @PostConstruct
    public void init() {
        log.info("created TabelaJurosRepositorioImpl ...");
    }

    @Override
    public TabelaJurosRepositorio getDao() {
        return (TabelaJurosRepositorio) super.getDao();
    }

    @Override
    public <E extends Serializable> E deleteById(final Class<E> clazz, final Long entityId, final Map<String, SysCriterion> conditions) throws RuntimeException {
        return this.getDao().deleteById(clazz, entityId, conditions);
    }
}
