package com.twa.financeira.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SortOrder;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.error.HttpException;
import com.persistence.commun.component.SysCriterion;
import com.persistence.commun.service.component.SysAsyncService;
import com.persistence.model.ErrorClean;
import com.persistence.model.SysPayload;
import com.twa.financeira.dto.ItensTabelaJurosDTO;
import com.twa.financeira.dto.ResolucaoTrapezio;
import com.twa.financeira.dto.TabelaJurosParametroDTO;
import com.twa.financeira.dto.TabelaJurosResponseDTO;
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
	    payload = super.findPagePayload(TabelaJuros.class, fields, page, size);

	} else {
	    payload = new SysPayload<TabelaJuros>();
	}

	return (SysPayload<T>) payload;
    }

    @Override
    public Boolean validaNome(Long empresaId, Long financeiraId, String nomeTabela, Boolean onEdit) {
	Boolean retornoValidacao = false;
	var page = 1;
	var size = 999;
	if(!onEdit) {
	    final Map<String, SysCriterion> fields = new HashMap<>();
	    fields.put("empresaId", new SysCriterion("empresaId", Restrictions.eq("empresaId", empresaId)));
	    fields.put("financeiraId", new SysCriterion("financeiraId", Restrictions.eq("financeiraId", financeiraId)));
	    fields.put("nome", new SysCriterion("nome", Restrictions.eq("nome", nomeTabela)));
	    var payload = super.findPagePayload(TabelaJurosView.class, fields, page, size);
	    List<TabelaJurosView> tabelas = payload.getCollection();
	    if (tabelas.size() > 0) {
		var error = new ErrorClean(HttpStatus.BAD_REQUEST.value(),"Nome da TABELA DE JUROS 	já existe para esta FINANCEIRA!", "NOME_TABELA_INVALIDA");
		throw HttpException.error(error);
	    }
	}
	return retornoValidacao;
    }

    @Override
    public TabelaJurosResponseDTO getByTabelaId(Long empresaId, Long tabelaId) {
	SysPayload<TabelaJuros> payload;
	var page = 1;
	var size = 900;
	final Map<String, SysCriterion> fields = new HashMap<>();
	fields.put("parcela", new SysCriterion("parcela", SortOrder.ASCENDING));
	fields.put("empresaId", new SysCriterion("empresaId", Restrictions.eq("empresaId", empresaId)));
	fields.put("tabelaId", new SysCriterion("tabelaId", Restrictions.eq("tabelaId", tabelaId)));
	payload = super.findPagePayload(TabelaJuros.class, fields, page, size);
	List<TabelaJuros> tabelas = payload.getCollection();

	TabelaJurosResponseDTO tabelaResponse = new TabelaJurosResponseDTO();

	var i = 0;
	if (tabelas.size() > 0) {
	    List<ItensTabelaJurosDTO> itens = new ArrayList<ItensTabelaJurosDTO>();

	    for (TabelaJuros tabela : tabelas) {
		if (i == 0) {
		    tabelaResponse.setEmpresaId(tabela.getEmpresaId());
		    tabelaResponse.setFinanceiraId(tabela.getFinanceiraId());
		    tabelaResponse.setTabelaId(tabela.getTabelaId());
		    tabelaResponse.setNome(tabela.getNome());
		    tabelaResponse.setDataVigor(tabela.getDataVigor());
		    if (tabela.getMinparc() != null) {
			tabelaResponse.setMinparc(tabela.getMinparc().intValue());
		    }
		    if (tabela.getMaxparc() != null) {
			tabelaResponse.setMaxparc(tabela.getMinparc().intValue());
		    }
		}
		ItensTabelaJurosDTO item = new ItensTabelaJurosDTO();
		item.setFinanceiraId(tabela.getFinanceiraId());
		item.setTabelaJurosId(tabelaId);
		item.setParcela(tabela.getParcela().longValue());
		item.setTaxaJuro(tabela.getTaxaJuro());
		item.setPrazo1(tabela.getPrazo1());
		item.setPrazo2(tabela.getPrazo2());
		item.setPrazo3(tabela.getPrazo3());
		item.setPrazo4(tabela.getPrazo4());
		item.setPrazo5(tabela.getPrazo5());
		item.setPrazo6(tabela.getPrazo6());
		item.setPrazo7(tabela.getPrazo7());
		item.setPrazo8(tabela.getPrazo8());
		item.setPrazo9(tabela.getPrazo9());
		item.setPrazo10(tabela.getPrazo10());
		itens.add(item);
		i = i + 1;
	    }
	    tabelaResponse.setItens(itens);
	}
	return tabelaResponse;
    }

    @Override
    public TabelaJurosResponseDTO processaCoeficientes(TabelaJurosParametroDTO parametros) {
	var tabelaJurosResponse = new TabelaJurosResponseDTO();

	tabelaJurosResponse.setEmpresaId(parametros.getEmpresaId());
	tabelaJurosResponse.setFinanceiraId(parametros.getFinanceiraId());
	tabelaJurosResponse.setId(parametros.getId());
	tabelaJurosResponse.setNome(parametros.getNome());
	tabelaJurosResponse.setMinparc(parametros.getParcelaMinima());
	tabelaJurosResponse.setMaxparc(parametros.getParcelaMaxima());

	BigDecimal taxa = BigDecimal.ZERO;

	List<ItensTabelaJurosDTO> itensTabelaJuros = new ArrayList<ItensTabelaJurosDTO>();
	for (ItensTabelaJurosDTO item : parametros.getItens()) {
	    var itemTabela = new ItensTabelaJurosDTO();
	    itemTabela.setParcela(item.getParcela());
	    itemTabela.setTaxaJuro(item.getTaxaJuro());

	    if (parametros.getOnlyDay1()) {
		taxa = item.getTaxaJuro().divide(new BigDecimal(30), 8, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(5));
		itemTabela.setPrazo1(resolucaoCoeficiente(item.getParcela(), taxa));
	    } else {
		itemTabela.setPrazo1(BigDecimal.ZERO);
	    }
	    if (parametros.getOnlyDay2()) {
		taxa = item.getTaxaJuro().divide(new BigDecimal(30), 8, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(10));
		itemTabela.setPrazo2(resolucaoCoeficiente(item.getParcela(), taxa));
	    } else {
		itemTabela.setPrazo2(BigDecimal.ZERO);
	    }
	    if (parametros.getOnlyDay3()) {
		taxa = item.getTaxaJuro().divide(new BigDecimal(30), 8, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(15));
		itemTabela.setPrazo3(resolucaoCoeficiente(item.getParcela(), taxa));
	    } else {
		itemTabela.setPrazo3(BigDecimal.ZERO);
	    }
	    if (parametros.getOnlyDay4()) {
		taxa = item.getTaxaJuro().divide(new BigDecimal(30), 8, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(20));
		itemTabela.setPrazo4(resolucaoCoeficiente(item.getParcela(), taxa));
	    } else {
		itemTabela.setPrazo4(BigDecimal.ZERO);
	    }
	    if (parametros.getOnlyDay5()) {
		taxa = item.getTaxaJuro().divide(new BigDecimal(30), 8, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(25));
		itemTabela.setPrazo5(resolucaoCoeficiente(item.getParcela(), taxa));
	    } else {
		itemTabela.setPrazo5(BigDecimal.ZERO);
	    }
	    if (parametros.getOnlyDay6()) {
		taxa = item.getTaxaJuro().divide(new BigDecimal(30), 8, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(30));
		itemTabela.setPrazo6(resolucaoCoeficiente(item.getParcela(), taxa));
	    } else {
		itemTabela.setPrazo6(BigDecimal.ZERO);
	    }
	    if (parametros.getOnlyDay7()) {
		taxa = item.getTaxaJuro().divide(new BigDecimal(30), 8, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(45));
		itemTabela.setPrazo7(resolucaoCoeficiente(item.getParcela(), taxa));
	    } else {
		itemTabela.setPrazo7(BigDecimal.ZERO);
	    }
	    if (parametros.getOnlyDay8()) {
		taxa = item.getTaxaJuro().divide(new BigDecimal(30), 8, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(60));
		itemTabela.setPrazo8(resolucaoCoeficiente(item.getParcela(), taxa));
	    } else {
		itemTabela.setPrazo8(BigDecimal.ZERO);
	    }
	    if (parametros.getOnlyDay9()) {
		taxa = item.getTaxaJuro().divide(new BigDecimal(30), 8, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(90));
		itemTabela.setPrazo9(resolucaoCoeficiente(item.getParcela(), taxa));
	    } else {
		itemTabela.setPrazo9(BigDecimal.ZERO);
	    }
	    if (parametros.getOnlyDay10()) {
		taxa = item.getTaxaJuro().divide(new BigDecimal(30), 8, RoundingMode.HALF_UP)
			.multiply(new BigDecimal(120));
		itemTabela.setPrazo10(resolucaoCoeficiente(item.getParcela(), taxa));
	    } else {
		itemTabela.setPrazo10(BigDecimal.ZERO);
	    }
	    itensTabelaJuros.add(itemTabela);
	}

	tabelaJurosResponse.setItens(itensTabelaJuros);
	return tabelaJurosResponse;
    }

    private BigDecimal resolucaoCoeficiente(Long parcelaRecebida, BigDecimal taxaJuros) {
	Long parcelaValida;
	if (parcelaRecebida > 0) {
	    parcelaValida = parcelaRecebida.longValue();
	} else {
	    parcelaValida = 1l;
	}

	ResolucaoTrapezio resTrap = new ResolucaoTrapezio((p) -> {
	    double q0 = 1000;
	    double n = parcelaValida;
	    double j = taxaJuros.doubleValue() / 100;
	    return p * (1 - Math.pow(1 + j, -n)) / j - q0;
	}, -1.8, -1.5, 0.00000001, 1000);
	BigDecimal indiceParcela = BigDecimal.ZERO;
	if (taxaJuros.compareTo(BigDecimal.ZERO) == 0) {
	    indiceParcela = BigDecimal.ONE.divide(new BigDecimal(parcelaValida), 6, RoundingMode.UP);
	} else {
	    indiceParcela = new BigDecimal(resTrap.getRaiz()).divide(new BigDecimal(1000).setScale(6, RoundingMode.UP));
	}
	return indiceParcela;
    }

    @Override
    public SysPayload<TabelaJurosView> findPagePayload(Class<TabelaJurosView> clazz, Long financeiraId,
	    TabelaJurosParametroDTO parametersConsult, String authorization, Integer page, Integer size) {
	SysPayload<TabelaJurosView> payload;
	if (usuarioTemAcessoAessaEmpresa(authorization)) {

	    final Map<String, SysCriterion> fields = this.getService()
		    .definindoParametros(parametersConsult.getEmpresaId(), parametersConsult);

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
