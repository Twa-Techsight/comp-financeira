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
import com.twa.financeira.dto.TabelaJurosParametroDTO;
import com.twa.financeira.dto.TabelaJurosRequestDTO;
import com.twa.financeira.dto.TabelaJurosResponseDTO;
import com.twa.financeira.dto.TabelaJurosUpdateDTO;
import com.twa.financeira.dto.TabelaJurosViewResponseDTO;
import com.twa.financeira.entity.TabelaJuros;
import com.twa.financeira.entity.view.TabelaJurosView;
import com.twa.financeira.service.TabelaJurosAsyncService;
import com.twa.financeira.service.TabelaJurosService;
import com.webservices.TecControllerRest;
import com.webservices.resouces.TabelaJurosResource;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tabelaJuros")
@Tag(name = "tabelaJuros", description = "tabelaJuros")
@Slf4j
public class TabelaJurosControler extends TecControllerRest implements TabelaJurosResource {

    private final TabelaJurosService service;

    private final TabelaJurosAsyncService tabelaJurosAsyncService;
    
    private final ObjectMapper mapper;

    public TabelaJurosControler(@Qualifier("tabelaJurosServiceImpl") TabelaJurosService service, 
	    			ObjectMapper mapper,
	    			@Qualifier("tabelaJurosAsyncServiceImpl") TabelaJurosAsyncService tabelaJurosAsyncService) {
	this.service = service;
	this.mapper = mapper;
	this.tabelaJurosAsyncService = tabelaJurosAsyncService;
    }

    @PostConstruct
    public void init() {
        log.info("created TabelaJurosControler..");
    }

    @Override
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> save(TabelaJurosRequestDTO dto, HttpServletResponse response) {
        var tabelaJuros = this.service.save(getObjectMapper().convertValue(dto, TabelaJuros.class));

        return new ResponseEntity<>(converterTabelaJuros(tabelaJuros), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> update(Long id, TabelaJurosUpdateDTO dto) {
        var tabelaJuros = this.service.update(id, getObjectMapper().convertValue(dto, TabelaJuros.class));

        return new ResponseEntity<>(converterTabelaJuros(tabelaJuros), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SysPayload<TabelaJurosViewResponseDTO>> listByParam(Long financeiraId, Integer page, Integer size, String authorization, TabelaJurosParametroDTO parametersConsult) {
	
	var tabelaJuros = tabelaJurosAsyncService.findPagePayload(TabelaJurosView.class, financeiraId, parametersConsult, authorization, page, size);
	
	var payload = converterTabelasJuros(tabelaJuros.getCollection(), tabelaJuros.getSize());

	
	return new ResponseEntity<>(payload, HttpStatus.OK);
    }    
        
    private SysPayload<TabelaJurosViewResponseDTO> converterTabelasJuros(List<TabelaJurosView> tabelaJuros, Integer rows) {
        var payload = newPayload();
        payload.setSize(rows);

        var collection = tabelaJuros.stream().map(tabelaJuro -> getObjectMapper().convertValue(tabelaJuro, TabelaJurosViewResponseDTO.class)).collect(Collectors.toList());
        payload.setCollection(collection);

        return payload;
    }

    private SysPayload<TabelaJurosResponseDTO> converterTabelaJuros(TabelaJuros tabelaJuros) {
        var payload = newPayloadFull();

        var dto = getObjectMapper().convertValue(tabelaJuros, TabelaJurosResponseDTO.class);
        payload.setData(dto);

        return payload;
    }

    @Override
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> get(Long id) {
        final Map<String, SysCriterion> fields = new HashMap<>();

        var tabelaJuros = this.service.findOneById(TabelaJuros.class, id, fields);

        var payload = new SysPayload<TabelaJurosResponseDTO>();
        var dto = getObjectMapper().convertValue(tabelaJuros, TabelaJurosResponseDTO.class);
        payload.setData(dto);

        return new ResponseEntity<>(payload, HttpStatus.OK);
    }
    
    
    @Override
    public ObjectMapper getObjectMapper() {
        return this.mapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SysPayload<TabelaJurosViewResponseDTO> newPayload() {
        return new SysPayload<TabelaJurosViewResponseDTO>();
    }
    
    public SysPayload<TabelaJurosResponseDTO> newPayloadFull() {
	return new SysPayload<TabelaJurosResponseDTO>();
    }
    
}
