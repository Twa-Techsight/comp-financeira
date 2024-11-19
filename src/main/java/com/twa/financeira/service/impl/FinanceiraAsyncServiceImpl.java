package com.twa.financeira.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SortOrder;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.persistence.commun.component.SysCriterion;
import com.persistence.commun.service.component.SysAsyncService;
import com.persistence.model.SysPayload;
import com.twa.financeira.dto.FinanceiraParametroDTO;
import com.twa.financeira.entity.Financeira;
import com.twa.financeira.service.FinanceiraAsyncService;
import com.twa.financeira.service.FinanceiraService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FinanceiraAsyncServiceImpl extends SysAsyncService implements FinanceiraAsyncService {

    public FinanceiraAsyncServiceImpl(@Qualifier("financeiraServiceImpl") FinanceiraService financeiraService) {
	super(financeiraService);
    }

    @PostConstruct
    public void init() {
	log.info("created financeiraServiceImpl ...");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> SysPayload<T> findPagePayload(Class<T> clazz, int page, int size, Long idEmpresa,
	    String authorization) {

	SysPayload<Financeira> payload;
	if (usuarioTemAcessoAessaEmpresa(authorization)) {
	    final Map<String, SysCriterion> fields = new HashMap<>();
	    fields.put("id", new SysCriterion("id", SortOrder.DESCENDING));
	    fields.put("financeiraId", new SysCriterion("financeiraId", Restrictions.eq("financeiraId", idEmpresa)));
	    fields.put("destinatario", new SysCriterion("destinatario", Restrictions.eq("destinatario", false)));
	    payload = super.findPagePayload(Financeira.class, fields, page, size);

	} else {
	    payload = new SysPayload<Financeira>();
	}

	return (SysPayload<T>) payload;
    }

    @Override
    public SysPayload<Financeira> findPagePayload(Class<Financeira> clazz, Long empresaId,
	    FinanceiraParametroDTO parametersConsult, String authorization, Integer page, Integer size) {
	SysPayload<Financeira> payload;
	if (usuarioTemAcessoAessaEmpresa(authorization)) {
	    final Map<String, SysCriterion> fields = this.getService().definindoParametros(empresaId, parametersConsult);
	    fields.put("id", new SysCriterion("id", SortOrder.DESCENDING));

	    if (parametersConsult.getId() != null)
		fields.put("id", new SysCriterion("id", Restrictions.eq("id", parametersConsult.getId())));

	    fields.put("empresaId", new SysCriterion("empresaId", Restrictions.eq("empresaId", empresaId)));

	    if (parametersConsult.getNome() != null) {
		fields.put("nome", new SysCriterion("nome",
			Restrictions.ilike("nome", parametersConsult.getNome().toUpperCase(), MatchMode.ANYWHERE)));
	    }

	    if (parametersConsult.getAtendente() != null && !parametersConsult.getAtendente().isBlank())
		fields.put("atendente",
			new SysCriterion("", Restrictions.eq("atendente", parametersConsult.getAtendente())));

	    if (parametersConsult.getFinaceiraPadrao() != null)
		if (parametersConsult.getFinaceiraPadrao()) {
		    fields.put("finaceiraPadrao",
			    new SysCriterion("finaceiraPadrao", Restrictions.eq("finaceiraPadrao", true)));
		} else {
		    fields.put("finaceiraPadrao",
			    new SysCriterion("finaceiraPadrao", Restrictions.eq("finaceiraPadrao", false)));
		}

	    if (parametersConsult.getSemJuros() != null) {
		fields.put("semJuros",
			new SysCriterion("semJuros", Restrictions.eq("semJuros", parametersConsult.getSemJuros())));
	    }

	    payload = super.findPagePayload(Financeira.class, fields, page, size);
	} else {
	    payload = new SysPayload<Financeira>();
	}

	return payload;
    }

    @Override
    public FinanceiraService getService() {
	return (FinanceiraService) super.getService();
    }

    private Boolean usuarioTemAcessoAessaEmpresa(String authorization/* , Empresa financeiraRequerida */) {
	// extrai do hash ou do id do banco do hash quais financeiras tal usuário tem
	// acesso
	return true;
    }

}
