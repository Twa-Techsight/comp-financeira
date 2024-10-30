package com.twa.financeira.dto;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Long grupoEmpresaId;

    private String nome;

    private String tipo;

    private String telefoneRes;

    private String telefoneCom;

    private String telefoneCel;

    private String email;
    
    private String cpf;
    
    private Date dataNascimento;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Sao_Paulo")
    private Date inclusao;
    
    private Boolean inativo;
    
}
