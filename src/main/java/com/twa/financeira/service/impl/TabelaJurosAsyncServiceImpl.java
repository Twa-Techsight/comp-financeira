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
import com.twa.financeira.dto.TabelaJurosParametroDTO;
import com.twa.financeira.entity.TabelaJuros;
import com.twa.financeira.entity.view.TabelaJurosView;
import com.twa.financeira.service.TabelaJurosAsyncService;
import com.twa.financeira.service.TabelaJurosService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TabelaJurosAsyncServiceImpl extends SysAsyncService implements TabelaJurosAsyncService {

    public TabelaJurosAsyncServiceImpl(@Qualifier("tabelaJurosServiceImpl") TabelaJurosService tabelaJurosService) {
	super(tabelaJurosService);
    }

    @PostConstruct
    public void init() {
	log.info("created tabelaJurosServiceImpl ...");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> SysPayload<T> findPagePayload(Class<T> clazz, int page, int size, Long idEmpresa,
	    String authorization) {

	SysPayload<TabelaJuros> payload;
	if (usuarioTemAcessoAessaEmpresa(authorization)) {
	    final Map<String, SysCriterion> fields = new HashMap<>();
	    fields.put("id", new SysCriterion("id", SortOrder.DESCENDING));
	    fields.put("tabelaJurosId", new SysCriterion("tabelaJurosId", Restrictions.eq("tabelaJurosId", idEmpresa)));
	    fields.put("destinatario", new SysCriterion("destinatario", Restrictions.eq("destinatario", false)));
	    payload = super.findPagePayload(TabelaJuros.class, fields, page, size);

	} else {
	    payload = new SysPayload<TabelaJuros>();
	}

	return (SysPayload<T>) payload;
    }

    @Override
    public SysPayload<TabelaJurosView> findPagePayload(Class<TabelaJurosView> clazz, Long financeiraId, TabelaJurosParametroDTO parametersConsult, String authorization, Integer page, Integer size) {
	SysPayload<TabelaJurosView> payload;
	if (usuarioTemAcessoAessaEmpresa(authorization)) {
	    
	    final Map<String, SysCriterion> fields = this.getService().definindoParametros(parametersConsult.getEmpresaId(), parametersConsult);

	    fields.put("id", new SysCriterion("id", SortOrder.ASCENDING));

	    fields.put("financeiraId", new SysCriterion("financeiraId", Restrictions.eq("financeiraId", financeiraId)));

	    if (parametersConsult.getNome() != null) {
		fields.put("nome", new SysCriterion("nome",
			Restrictions.ilike("nome", parametersConsult.getNome().toUpperCase(), MatchMode.ANYWHERE)));
	    }

	    payload = super.findPagePayload(TabelaJurosView.class, fields, page, size);
	} else {
	    payload = new SysPayload<TabelaJurosView>();
	}

	return payload;
    }

    @Override
    public TabelaJurosService getService() {
	return (TabelaJurosService) super.getService();
    }

    private Boolean usuarioTemAcessoAessaEmpresa(String authorization/* , Empresa tabelaJurosRequerida */) {
	// extrai do hash ou do id do banco do hash quais tabelaJuross tal usuário tem
	// acesso
	return true;
    }

}
