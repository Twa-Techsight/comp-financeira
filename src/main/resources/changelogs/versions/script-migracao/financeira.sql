select 	f.id,
		f.empresa_id,
		f.nome,
		f.atendente,
		f.telefone,
		f.site,
		f.usuario,
		f.senha,
		f.prazos_carencia,
		f.finaceira_padrao,
		false as inativo 
	from financeira f 
		inner join empresa e on(e.id = f.empresa_id)
	where e.status='ATIVA'	
	order by id

select 	tj.id,
		e.id as empresa_id,
		tj.financeira_id,
		tj.codigo as nome,
		tj.data_vigor,
		tj.valor_tac,
		tj.taxa_juro,
		tj.parcela,
		tj.minparc,
		tj.maxparc,
		tj.prazo_1,
		tj.prazo_2,
		tj.prazo_3,
		tj.prazo_4,
		tj.prazo_5,
		tj.prazo_6,
		tj.prazo_7,
		tj.prazo_8,
		tj.prazo_9,
		tj.prazo_10,
		tj.prazo_11,
		tj.prazo_12,
		tj.comentrada 
	from tabelas_juros tj
	inner join financeira f on(f.id = tj.financeira_id)
	inner join empresa e on(e.id = f.empresa_id)
	where e.status='ATIVA'	
order by id