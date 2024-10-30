package com.twa.financeira.service;

import java.io.Serializable;

import com.persistence.SysInterfaceGenericAsync;
import com.persistence.model.SysPayload;
import com.twa.financeira.dto.FinanceiraParametroDTO;
import com.twa.financeira.entity.Financeira;

public interface FinanceiraAsyncService extends SysInterfaceGenericAsync {

    public <T extends Serializable> SysPayload<T> findPagePayload(Class<T> clazz, int page, int size, Long idEmpresa,
	    String authorization);

    public SysPayload<Financeira> findPagePayload(Class<Financeira> clazz, Long idEmpresa, FinanceiraParametroDTO parametersConsult,
	    String authorization, Integer page, Integer size);

}
