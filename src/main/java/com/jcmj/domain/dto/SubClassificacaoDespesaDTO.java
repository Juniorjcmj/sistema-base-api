package com.jcmj.domain.dto;

import com.jcmj.domain.SubClassificacaoDespesa;

public class SubClassificacaoDespesaDTO {

	
	private Integer id;
	private String descricao;	    
	
	public SubClassificacaoDespesaDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public SubClassificacaoDespesaDTO(Integer id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}
	
	public SubClassificacaoDespesaDTO(SubClassificacaoDespesa c) {
		super();
		this.id = c.getId();
		this.descricao = c.getDescricao();
		
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	

	
	
	
	
}
