package com.twa.financeira.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TabelaJurosViewResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long empresaId;

    private Long financeiraId;
    
    private String nome;
    
    private Integer minparc;
    
    private Integer maxparc;

    private Date dataVigor;

    private BigDecimal valorTac;

    private BigDecimal taxaMinima;
    
    private BigDecimal taxaMaxima;

    private Boolean comentrada;

    private Boolean inativo;

}
