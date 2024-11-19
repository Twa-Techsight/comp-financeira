package com.twa.financeira.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.persistence.commun.component.SysGenericRepository;
import com.twa.financeira.entity.TabelaJuros;
import com.twa.financeira.repository.TabelaJurosRepositorio;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TabelaJurosRepositorioImpl extends SysGenericRepository implements TabelaJurosRepositorio {

    private static final long serialVersionUID = 1L;

    public TabelaJurosRepositorioImpl() {
    }

    @PostConstruct
    public void init() {
	log.info("Create TabelaJurosRepositorioImpl");
    }
    
    
    
    @Override
    public Long getIdTabela(Long empresaId) {
	var criteria= DetachedCriteria.forClass(TabelaJuros.class);
	criteria.setProjection(Projections.projectionList().add(Projections.max("tabelaId")));
	final Criteria session = criteria.getExecutableCriteria(getCurrentSession());
	Long tabelaId = (Long) session.uniqueResult();	
	return tabelaId;
    }

    @Override
    public void forceFlush() {
	this.getCurrentSession().flush();
    }    

   
}
