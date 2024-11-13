package com.twa.financeira.service;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.persistence.SysInterfaceGenericService;
import com.persistence.commun.component.SysCriterion;
import com.twa.financeira.dto.TabelaIdResponseDTO;
import com.twa.financeira.dto.TabelaJurosParametroDTO;
import com.twa.financeira.dto.TabelaJurosResponseDTO;
import com.twa.financeira.dto.TabelaJurosUpdateDTO;
import com.twa.financeira.entity.view.TabelaJurosView;


public interface TabelaJurosService extends SysInterfaceGenericService{
    public Map<String, SysCriterion> definindoParametros(Long empresaId, TabelaJurosParametroDTO parametersConsult);

    TabelaJurosView inactiveById(Long entityId);

    public TabelaIdResponseDTO getTabelaId(Long empresaId);

    public ObjectMapper getObjectMapper();

    public TabelaJurosResponseDTO update(Long empresaId, Long financeiraId, Long tabelaId, TabelaJurosUpdateDTO dto);

}
