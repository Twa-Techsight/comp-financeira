package com.twa.financeira.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TabelaJurosUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    private Long empresaId;

    private Long financeiraId;
    
    private Long tabelaId;

    private String nome;
    
    private Date dataVigor;

    private BigDecimal valorTac;

    private BigDecimal taxaJuro;

    private Integer minparc;
    
    private Integer maxparc;
    
    private List<ItensTabelaJurosDTO> itens;
    
    private Boolean edit=false;
    
}
