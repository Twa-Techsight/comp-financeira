package com.twa.financeira.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FinanceiraRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long empresaId;
    
    private String nome;

    private String atendente;
    
    private String telefone;

    private String site;

    private String usuario;

    private String senha;

    private String prazosCarencia;
    
    private boolean finaceiraPadrao;

    private boolean semJuros;

    private Boolean inativo;

}
