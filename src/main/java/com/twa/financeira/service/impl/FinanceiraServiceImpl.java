package com.twa.financeira.service.impl;

import java.util.Map;

import javax.swing.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.persistence.TecFilterBean;
import com.persistence.commun.component.SysCriterion;
import com.persistence.commun.service.component.SysGenericService;
import com.twa.financeira.dto.FinanceiraParametroDTO;
import com.twa.financeira.entity.Financeira;
import com.twa.financeira.repository.FinanceiraRepositorio;
import com.twa.financeira.service.FinanceiraService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class FinanceiraServiceImpl extends SysGenericService implements FinanceiraService {

    private static final long serialVersionUID = 1L;

    public FinanceiraServiceImpl(@Qualifier("financeiraRepositorioImpl") final FinanceiraRepositorio dao) {
        super(dao);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @PostConstruct
    public void init() {
        log.info("created FinanceiraRepositorioImpl ...");
    }

    @Override
    public FinanceiraRepositorio getDao() {
        return (FinanceiraRepositorio) super.getDao();
    }

    private JdbcTemplate jdbcTemplate() {
	return jdbcTemplate;
    }    
    
    @Override
    public Financeira inactiveById(Long entityId) {
	String sqlUpdate = "UPDATE financeira set inativo = true  WHERE id = ? ;";
	jdbcTemplate().update(sqlUpdate, new Object[] { entityId  });
	var financeira = this.getDao().findOneById(Financeira.class, entityId, null);
	
	return financeira;
    }
    
   

    @Override
    public Map<String, SysCriterion> definindoParametros(Long empresaId, FinanceiraParametroDTO parametersConsult) {
	var build = new TecFilterBean();
	if("ATIVO".equals(parametersConsult.getSituacao())) {
	    parametersConsult.setSituacao("INATIVO");
	}else if("INATIVO".equals(parametersConsult.getSituacao())) {
	    parametersConsult.setSituacao("ATIVO");
	}
	final Map<String, SysCriterion> fields = build.buildParams(SortOrder.ASCENDING, empresaId, parametersConsult);
	return fields;
    }
}
