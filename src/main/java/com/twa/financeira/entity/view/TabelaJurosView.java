package com.twa.financeira.entity.view;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sys.persistence.model.TecObjectGenericTwa;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "v_tabela_juros_veiw")
@Setter
@Getter
public class TabelaJurosView extends TecObjectGenericTwa<TabelaJurosView> {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "financeira_id")
    private Long financeiraId;

    @Column(name = "empresa_id")
    private Long empresaId;

    @Column(name = "nome")
    private String nome;
    
    @Column(name = "minparc")
    private Integer minparc;
    
    @Column(name = "maxparc")
    private Integer maxparc;

    @Column(name = "data_vigor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVigor;

    @Column(name = "valor_tac")
    private BigDecimal valorTac;

    @Column(name = "taxa_minima")
    private BigDecimal taxaMinima;
    
    @Column(name = "taxa_maxima")
    private BigDecimal taxaMaxima;

    @Column(name = "comentrada")
    private Boolean comentrada;

    @Column(name = "inativo")
    private Boolean inativo;
    
    public TabelaJurosView() {
	super(TabelaJurosView.class);

    }
}
