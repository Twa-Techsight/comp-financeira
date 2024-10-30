package com.webservices.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.SortOrder;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persistence.commun.component.SysCriterion;
import com.persistence.model.SysPayload;
import com.twa.financeira.dto.TabelaJurosRequestDTO;
import com.twa.financeira.dto.TabelaJurosResponseDTO;
import com.twa.financeira.dto.TabelaJurosUpdateDTO;
import com.twa.financeira.entity.TabelaJuros;
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

    private final ObjectMapper mapper;

    public TabelaJurosControler(@Qualifier("tabelaJurosServiceImpl") TabelaJurosService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @PostConstruct
    public void init() {
        log.info("created TabelaJurosControler..");
    }

    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> list(Long idEmpresa, Integer page, Integer size) {
        final Map<String, SysCriterion> fields = new HashMap<>();
        fields.put("id", new SysCriterion("id", SortOrder.DESCENDING));
        fields.put("empresaId", new SysCriterion("empresaId", Restrictions.eq("empresaId", idEmpresa)));

        var tabelaJuross = this.service.findAll(TabelaJuros.class, fields, page, size);
        var rows = this.service.count(TabelaJuros.class, fields);
        var payload = converterTabelaJuroses(tabelaJuross, rows);

        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> save(TabelaJurosRequestDTO dto, HttpServletResponse response) {
        var TabelaJuros = this.service.save(getObjectMapper().convertValue(dto, TabelaJuros.class));

        return new ResponseEntity<>(converterTabelaJuros(TabelaJuros), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> edit(Long id, TabelaJurosUpdateDTO dto) {
        var tabelaJuros = this.service.update(id, getObjectMapper().convertValue(dto, TabelaJuros.class));

        return new ResponseEntity<>(converterTabelaJuros(tabelaJuros), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SysPayload<TabelaJurosResponseDTO>> delete(Long id) {
        final Map<String, SysCriterion> fields = new HashMap<>();
        fields.put("cargo", new SysCriterion("cargo", FetchMode.JOIN));

        var tabelaJuros = this.service.deleteById(TabelaJuros.class, id, fields);

        return new ResponseEntity<>(converterTabelaJuros(tabelaJuros), HttpStatus.OK);
    }

    private SysPayload<TabelaJurosResponseDTO> converterTabelaJuroses(List<TabelaJuros> tabelaJuross, Integer rows) {
        var payload = newPayload();
        payload.setSize(rows);

        var collection = tabelaJuross.stream().map(tabelaJuros -> getObjectMapper().convertValue(tabelaJuros, TabelaJurosResponseDTO.class)).collect(Collectors.toList());
        payload.setCollection(collection);

        return payload;
    }

    private SysPayload<TabelaJurosResponseDTO> converterTabelaJuros(TabelaJuros tabelaJuros) {
        var payload = newPayload();

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
    public SysPayload<TabelaJurosResponseDTO> newPayload() {
        return new SysPayload<TabelaJurosResponseDTO>();
    }
}
