package com.twa.financeira.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sys.persistence.model.TecObjectGenericTwa;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "financeira")
@Setter
@Getter
@SequenceGenerator(name = "SEQ_ID", sequenceName = "financeira_id_seq", allocationSize = 1)
public class Financeira extends TecObjectGenericTwa<Financeira> {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tabela_juros")
    @SequenceGenerator(name = "tabelaJuros", sequenceName = "tabela_juros_id_seq", allocationSize = 1)
    private Long id;
    
    @JsonProperty(value = "empresa_id")
    @Column(name = "empresa_id")
    private Long empresaId;
    
    @Column(length = 70, nullable = false)
    private String nome;

    @Column(length = 70)
    private String atendente;
    
    @Column(length = 20 )
    private String telefone;

    @Column(length = 150)
    private String site;

    @Column(length = 70)
    private String usuario;

    @Column(length = 70)
    private String senha;

    @Column(name = "prazos_carencia", length = 70)
    private String prazosCarencia;
    
    @Column(name = "finaceira_padrao", nullable = false)
    private boolean finaceiraPadrao;

    @Column(name = "sem_juros", nullable = false)
    private boolean semJuros;

    @Column(length = 70)
    private Boolean inativo;

    public Financeira() {
	super(Financeira.class);

    }

    public Financeira(final Long id) {
	super(Financeira.class);
	this.setId(id);

    }

}
