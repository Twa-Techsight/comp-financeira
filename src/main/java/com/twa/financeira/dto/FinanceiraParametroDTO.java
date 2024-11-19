package com.twa.financeira.dto;

import org.springframework.transaction.annotation.Transactional;

import com.persistence.model.TecFilterParameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class FinanceiraParametroDTO extends TecFilterParameter {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long empresaId;

    private String nome;

    private String atendente;

    private Boolean finaceiraPadrao;

    private Boolean semJuros;

    private Boolean inativo;
    
}
