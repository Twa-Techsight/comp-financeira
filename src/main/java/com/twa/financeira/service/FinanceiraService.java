package com.twa.financeira.service;

import java.util.Map;

import com.persistence.SysInterfaceGenericService;
import com.persistence.commun.component.SysCriterion;
import com.twa.financeira.dto.FinanceiraParametroDTO;
import com.twa.financeira.entity.Financeira;
import com.twa.financeira.repository.FinanceiraRepositorio;


public interface FinanceiraService extends SysInterfaceGenericService, FinanceiraRepositorio{
    public Map<String, SysCriterion> definindoParametros(Long empresaId, FinanceiraParametroDTO parametersConsult);

    public Financeira inactiveById(Long id);

}
