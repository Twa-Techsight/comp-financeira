package com.twa.financeira.service.impl;

import java.io.Serializable;
import java.util.Map;

import javax.swing.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.backend.security.bean.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.persistence.TecFilterBean;
import com.persistence.commun.component.SysCriterion;
import com.persistence.commun.service.component.SysGenericService;
import com.twa.financeira.dto.ItensTabelaJurosDTO;
import com.twa.financeira.dto.TabelaIdResponseDTO;
import com.twa.financeira.dto.TabelaJurosParametroDTO;
import com.twa.financeira.dto.TabelaJurosResponseDTO;
import com.twa.financeira.dto.TabelaJurosUpdateDTO;
import com.twa.financeira.entity.TabelaJuros;
import com.twa.financeira.entity.view.TabelaJurosView;
import com.twa.financeira.repository.TabelaJurosRepositorio;
import com.twa.financeira.service.TabelaJurosService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TabelaJurosServiceImpl extends SysGenericService implements TabelaJurosService {

    private static final long serialVersionUID = 1L;

    private final ObjectMapper mapper;
    
    @SuppressWarnings("unused")
    @Autowired
    private JwtUtil jwtUtil;

    public TabelaJurosServiceImpl(@Qualifier("tabelaJurosRepositorioImpl") final TabelaJurosRepositorio dao, JwtUtil jwtUtil,
	    ObjectMapper mapper) {
	super(dao);
	this.jwtUtil = jwtUtil;
	this.mapper = mapper;
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    
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

    
    private JdbcTemplate jdbcTemplate() {
	return jdbcTemplate;
    }    
    
    @Override
    public TabelaJurosView inactiveById(Long entityId) {
	var consult = this.findOneById(TabelaJurosView.class, entityId);
	if(consult!=null) {
	    Long financeiraId= consult.getFinanceiraId();
	    String tabela_juros = consult.getNome();
	    String sqlUpdate = "UPDATE tabela_juros set inativo = true  WHERE financeira_id = ? and nome = ? ;";
	    jdbcTemplate().update(sqlUpdate, new Object[] { financeiraId , tabela_juros  });
	}
	return consult;
    }
        
    @Override
    public Map<String, SysCriterion> definindoParametros(Long financeiraId, TabelaJurosParametroDTO parametersConsult) {
	var build = new TecFilterBean();
	if("ATIVO".equals(parametersConsult.getSituacao())) {
	    parametersConsult.setSituacao("INATIVO");
	}else if("INATIVO".equals(parametersConsult.getSituacao())) {
	    parametersConsult.setSituacao("ATIVO");
	}
	final Map<String, SysCriterion> fields = build.buildParams(SortOrder.ASCENDING, parametersConsult.getId(), parametersConsult);
	return fields;
    }

    @Override
    public TabelaIdResponseDTO getTabelaId(Long empresaId) {
	var tabReurn = new TabelaIdResponseDTO();
	Long tabelaId= this.getDao().getIdTabela(empresaId);
	tabReurn.setEmpresaId(empresaId);
	tabReurn.setTabelaId(tabelaId + 1);
	return tabReurn;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public TabelaJurosResponseDTO update(Long empresaId, Long financeiraId, Long tabelaId, TabelaJurosUpdateDTO dto) {
	
	
	
	
	String sqlDelte = "DELETE FROM tabela_juros WHERE tabela_id = ? AND empresa_id = ? AND financeira_id = ?";
	jdbcTemplate().update(sqlDelte, new Object[] { tabelaId , empresaId, financeiraId });
	
	var newTableId=tabelaId;
	if(dto.getEdit()==false) {
	    var MaxId= this.getTabelaId(empresaId);
	    newTableId= MaxId.getTabelaId();
	}
	
	
	for(ItensTabelaJurosDTO item : dto.getItens()) {
	    TabelaJuros tabela=new TabelaJuros();
	    tabela.setTabelaId(newTableId);
	    tabela.setEmpresaId(dto.getEmpresaId());
	    tabela.setFinanceiraId(dto.getFinanceiraId());
	    tabela.setNome(dto.getNome());
	    tabela.setValorTac(dto.getValorTac());
	    tabela.setDataVigor(dto.getDataVigor());
	    tabela.setMinparc(0);
	    tabela.setMaxparc(dto.getMaxparc());
	    tabela.setParcela(item.getParcela().intValue());
	    tabela.setTaxaJuro(item.getTaxaJuro());
	    tabela.setPrazo1(item.getPrazo1());
	    tabela.setPrazo2(item.getPrazo2());
	    tabela.setPrazo3(item.getPrazo3());
	    tabela.setPrazo4(item.getPrazo4());
	    tabela.setPrazo5(item.getPrazo5());
	    tabela.setPrazo6(item.getPrazo6());
	    tabela.setPrazo7(item.getPrazo7());
	    tabela.setPrazo8(item.getPrazo8());
	    tabela.setPrazo9(item.getPrazo9());
	    tabela.setPrazo10(item.getPrazo10());
	    super.save(tabela);
	}
	
	TabelaJurosResponseDTO tabelaRetorno = new TabelaJurosResponseDTO();
	tabelaRetorno= getObjectMapper().convertValue(dto, TabelaJurosResponseDTO.class);
	
	return tabelaRetorno;
    }      
    
    @Override
    public ObjectMapper getObjectMapper() {
        return this.mapper;
    }    

}
