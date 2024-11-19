package com.twa.financeira.repository;

import com.persistence.SysInterfaceGenericRepository;

public interface TabelaJurosRepositorio extends  SysInterfaceGenericRepository  {

    public void forceFlush();

    public Long getIdTabela(Long empresaId);
}