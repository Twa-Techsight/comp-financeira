package com.twa.financeira.service;

import java.util.Map;

import com.persistence.SysInterfaceGenericService;
import com.persistence.commun.component.SysCriterion;
import com.twa.financeira.dto.TabelaJurosParametroDTO;
import com.twa.financeira.repository.TabelaJurosRepositorio;


public interface TabelaJurosService extends SysInterfaceGenericService, TabelaJurosRepositorio{
    public Map<String, SysCriterion> definindoParametros(Long empresaId, TabelaJurosParametroDTO parametersConsult);

}
