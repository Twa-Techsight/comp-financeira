package com.twa.financeira.service;

import java.io.Serializable;

import com.persistence.SysInterfaceGenericAsync;
import com.persistence.model.SysPayload;
import com.twa.financeira.dto.TabelaJurosParametroDTO;
import com.twa.financeira.entity.view.TabelaJurosView;
public interface TabelaJurosAsyncService extends SysInterfaceGenericAsync {

    public <T extends Serializable> SysPayload<T> findPagePayload(Class<T> clazz, int page, int size, Long idEmpresa, String authorization);

    public SysPayload<TabelaJurosView> findPagePayload(Class<TabelaJurosView> clazz, Long financeiraId,TabelaJurosParametroDTO parametersConsult, String authorization, Integer page, Integer size);

    
}
