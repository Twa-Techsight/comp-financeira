package com.twa.financeira.entity;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "tabela_juros")
@Setter
@Getter
@SequenceGenerator(name = "SEQ_ID", sequenceName = "tabela_juros_id_seq", allocationSize = 1)
public class TabelaJuros extends TecObjectGenericTwa<TabelaJuros> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tabela_juros")
    @SequenceGenerator(name = "tabelaJuros", sequenceName = "tabela_juros_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "empresa_id", nullable = false)
    @JsonProperty(value = "empresaId")
    private Long empresaId;

    @Column(name = "financeira_id", nullable = false)
    @JsonProperty(value = "financeiraId")
    private Long financeiraId;

    @Column(length = 70, nullable = false)
    private String nome;
    
    @Column(name = "data_vigor")
    private Date dataVigor;

    @Column(name = "valor_tac")
    private BigDecimal valorTac;

    @Column(name = "taxa_juro")
    private BigDecimal taxaJuro;

    @Column(name = "parcela")
    private Integer parcela;

    @Column(name = "minparc")
    private Integer minparc;
    
    @Column(name = "maxparc")
    private Integer maxparc;
    
    @Column(name = "prazo_1")
    private BigDecimal prazo1;
    
    @Column(name = "prazo_2")
    private BigDecimal prazo2;
    
    @Column(name = "prazo_3")
    private BigDecimal prazo3;
    
    @Column(name = "prazo_4")
    private BigDecimal prazo4;
    
    @Column(name = "prazo_5")
    private BigDecimal prazo5;
    
    @Column(name = "prazo_6")
    private BigDecimal prazo6;
    
    @Column(name = "prazo_7")
    private BigDecimal prazo7;
    
    @Column(name = "prazo_8")
    private BigDecimal prazo8;
    
    @Column(name = "prazo_9")
    private BigDecimal prazo9;
    
    @Column(name = "prazo_10")
    private BigDecimal prazo10;
    
    @Column(name = "prazo_11")
    private BigDecimal prazo11;
    
    @Column(name = "prazo_12")
    private BigDecimal prazo12;
    
    @Column(name = "comentrada")
    private Boolean comentrada;
    
    public TabelaJuros() {
	super(TabelaJuros.class);

    }

    public TabelaJuros(final Long id) {
	super(TabelaJuros.class);
	this.setId(id);

    }
}
