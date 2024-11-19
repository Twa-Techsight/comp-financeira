package com.twa.financeira.dto;

import java.util.List;

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
public class TabelaJurosParametroDTO extends TecFilterParameter {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long empresaId;

    private Long financeiraId;

    private Long tabelaId;

    private String nome;
    
    private Integer parcelaMinima;
    
    private Integer parcelaMaxima;

    private Boolean onlyDay1;
    
    private Boolean onlyDay2;
    
    private Boolean onlyDay3;
        
    private Boolean onlyDay4;

    private Boolean onlyDay5;
    
    private Boolean onlyDay6;
    
    private Boolean onlyDay7;
    
    private Boolean onlyDay8;
    
    private Boolean onlyDay9;
    
    private Boolean onlyDay10;
    
    private Boolean onlyDay11;
    
    private Boolean onlyDay12;
    
    private List<ItensTabelaJurosDTO> itens;
    
}
