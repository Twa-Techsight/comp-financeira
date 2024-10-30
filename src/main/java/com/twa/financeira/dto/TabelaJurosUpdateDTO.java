package com.twa.financeira.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TabelaJurosUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long empresaId;

    private Long financeiraId;

    private String nome;
    
    private Date dataVigor;

    private BigDecimal valorTac;

    private BigDecimal taxaJuro;

    private Integer parcela;

    private Integer minparc;
    
    private Integer maxparc;
    
    private BigDecimal prazo1;
    
    private BigDecimal prazo2;
    
    private BigDecimal prazo3;
    
    private BigDecimal prazo4;
    
    private BigDecimal prazo5;
    
    private BigDecimal prazo6;
    
    private BigDecimal prazo7;
    
    private BigDecimal prazo8;
    
    private BigDecimal prazo9;
    
    private BigDecimal prazo10;
    
    private BigDecimal prazo11;
    
    private BigDecimal prazo12;
    
    private Boolean comentrada;
}
