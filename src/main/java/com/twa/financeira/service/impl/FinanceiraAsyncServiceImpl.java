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
    public SysPayload<Financeira> findPagePayload(Class<Financeira> clazz, Long grupoEmpresaId, FinanceiraParametroDTO parametersConsult, String authorization, Integer page, Integer size) {
	SysPayload<Financeira> payload;
	if (usuarioTemAcessoAessaEmpresa(authorization)) {
	    final Map<String, SysCriterion> fields = this.getService().definindoParametros(parametersConsult.getEmpresaId(), parametersConsult);
	    fields.put("id", new SysCriterion("id", SortOrder.DESCENDING));

	    fields.put("grupoEmpresaId",new SysCriterion("grupoEmpresaId", Restrictions.eq("grupoEmpresaId", grupoEmpresaId)));

	    if (parametersConsult.getId() != null )
		fields.put("id", new SysCriterion("id",
			Restrictions.eq("id", parametersConsult.getId())));

	    if (parametersConsult.getEmpresaId() != null )
		fields.put("empresaId", new SysCriterion("empresaId",
			Restrictions.eq("empresaId", parametersConsult.getEmpresaId())));
	    
	    if (parametersConsult.getCpf() != null   && !parametersConsult.getCpf().isBlank())
		fields.put("cpf",
			new SysCriterion("cpf", Restrictions.eq("cpf", parametersConsult.getCpf())));

	    if (parametersConsult.getEmail() != null    && !parametersConsult.getEmail().isBlank())
		fields.put("email",
			new SysCriterion("email", Restrictions.eq("email", parametersConsult.getEmail())));

	    if (parametersConsult.getTelefoneCel() != null    && !parametersConsult.getTelefoneCel().isBlank())
		fields.put("telefone",
			new SysCriterion("telefoneCel", Restrictions.eq("telefoneCel", parametersConsult.getTelefoneCel())));


	    if (parametersConsult.getInclusao() != null) {
		fields.put("inclusao", new SysCriterion("inclusao",
			Restrictions.eq("inclusao", parametersConsult.getInclusao())));
	    }

	    if (parametersConsult.getTipo() != null) {
		fields.put("tipo", new SysCriterion("tipo",
			Restrictions.eq("tipo", parametersConsult.getTipo())));
	    }

	    if (parametersConsult.getNome() != null) {
		fields.put("nome", new SysCriterion("nome",
			Restrictions.ilike("nome", parametersConsult.getNome().toUpperCase(), MatchMode.ANYWHERE)));
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
