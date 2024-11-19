package com.twa.financeira.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItensTabelaJurosDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long tabelaJurosId;

    private Long parcela;
    
    private BigDecimal taxaJuro;
    
    private Long financeiraId;
    
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
    
}
