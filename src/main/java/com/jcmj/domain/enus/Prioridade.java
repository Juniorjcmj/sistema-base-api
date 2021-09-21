package com.jcmj.domain.enus;

public enum Prioridade {
	
	BAIXA(0,"BAIXA"), MEDIA(1, "MEDIA"), ALTA(2,"ALTA");
	
	
	private Integer codigo;
	private String descricao;

	
	private Prioridade(Integer codigo,String descricao) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public static Prioridade toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Prioridade x : Prioridade.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Status Inválido");
	}
	
	

}
