package com.webservices.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persistence.commun.component.SysCriterion;
import com.persistence.model.SysPayload;
import com.twa.financeira.dto.FinanceiraParametroDTO;
import com.twa.financeira.dto.FinanceiraRequestDTO;
import com.twa.financeira.dto.FinanceiraResponseDTO;
import com.twa.financeira.dto.FinanceiraUpdateDTO;
import com.twa.financeira.entity.Financeira;
import com.twa.financeira.service.FinanceiraAsyncService;
import com.twa.financeira.service.FinanceiraService;
import com.webservices.TecControllerRest;
import com.webservices.resouces.FinanceiraResource;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/financeira")
@Tag(name = "financeira", description = "financeira")
@Slf4j
public class FinanceiraControler extends TecControllerRest implements FinanceiraResource {

    private final FinanceiraService service;

    private final FinanceiraAsyncService financeiraAsyncService;
    
    private final ObjectMapper mapper;

    public FinanceiraControler(@Qualifier("financeiraServiceImpl") FinanceiraService service, 
	    			ObjectMapper mapper,
	    			@Qualifier("financeiraAsyncServiceImpl") FinanceiraAsyncService financeiraAsyncService) {
	this.service = service;
	this.mapper = mapper;
	this.financeiraAsyncService = financeiraAsyncService;
    }

    @PostConstruct
    public void init() {
        log.info("created FinanceiraControler..");
    }

    @Override
    public ResponseEntity<SysPayload<FinanceiraResponseDTO>> save(FinanceiraRequestDTO dto, HttpServletResponse response) {
        var financeira = this.service.save(getObjectMapper().convertValue(dto, Financeira.class));

        return new ResponseEntity<>(converterFinanceira(financeira), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SysPayload<FinanceiraResponseDTO>> update(Long id, FinanceiraUpdateDTO dto) {
        var financeira = this.service.update(id, getObjectMapper().convertValue(dto, Financeira.class));

        return new ResponseEntity<>(converterFinanceira(financeira), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SysPayload<FinanceiraResponseDTO>> listByParam(Long grupoEmpresaId, Integer page, Integer size, String authorization, FinanceiraParametroDTO parametersConsult) {
	var Financeiraes = financeiraAsyncService.findPagePayload(Financeira.class, grupoEmpresaId, parametersConsult, authorization, page, size);
	var payload = converterFinanceiraes(Financeiraes.getCollection(), Financeiraes.getSize());
	return new ResponseEntity<>(payload, HttpStatus.OK);
    }    
        
    @Override
    public ResponseEntity<SysPayload<FinanceiraResponseDTO>> delete(Long id) {
        var financeira = this.service.inactiveById(id);
        return new ResponseEntity<>(converterFinanceira(financeira), HttpStatus.OK);
    }

    private SysPayload<FinanceiraResponseDTO> converterFinanceiraes(List<Financeira> financeiraes, Integer rows) {
        var payload = newPayload();
        payload.setSize(rows);

        var collection = financeiraes.stream().map(financeira -> getObjectMapper().convertValue(financeira, FinanceiraResponseDTO.class)).collect(Collectors.toList());
        payload.setCollection(collection);

        return payload;
    }

    private SysPayload<FinanceiraResponseDTO> converterFinanceira(Financeira financeira) {
        var payload = newPayload();

        var dto = getObjectMapper().convertValue(financeira, FinanceiraResponseDTO.class);
        payload.setData(dto);

        return payload;
    }

    @Override
    public ResponseEntity<SysPayload<FinanceiraResponseDTO>> get(Long id) {
        final Map<String, SysCriterion> fields = new HashMap<>();

        var financeira = this.service.findOneById(Financeira.class, id, fields);

        var payload = new SysPayload<FinanceiraResponseDTO>();
        var dto = getObjectMapper().convertValue(financeira, FinanceiraResponseDTO.class);
        payload.setData(dto);

        return new ResponseEntity<>(payload, HttpStatus.OK);
    }
    
    
    @Override
    public ObjectMapper getObjectMapper() {
        return this.mapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SysPayload<FinanceiraResponseDTO> newPayload() {
        return new SysPayload<FinanceiraResponseDTO>();
    }
    
}
