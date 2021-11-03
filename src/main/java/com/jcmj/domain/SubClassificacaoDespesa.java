package com.jcmj.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class SubClassificacaoDespesa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String descricao;	
	
	@OneToOne   
    private ClassificacaoDespesa classificacaoDespesa;
	
	public SubClassificacaoDespesa() {
		// TODO Auto-generated constructor stub
	}
	
	public SubClassificacaoDespesa(Integer id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
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

	public ClassificacaoDespesa getClassificacaoDespesa() {
		return classificacaoDespesa;
	}

	public void setClassificacaoDespesa(ClassificacaoDespesa classificacaoDespesa) {
		this.classificacaoDespesa = classificacaoDespesa;
	}
	
	
	
	
	
}
