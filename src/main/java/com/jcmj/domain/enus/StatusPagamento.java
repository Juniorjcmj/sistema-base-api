package com.jcmj.domain.enus;

public enum StatusPagamento {
	
	
	PAGO(0,"PAGO"), PENDENTE(1, "PENDENTE");
	
	private Integer codigo;
	private String descricao;

	
	private StatusPagamento(Integer codigo,String descricao) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public static StatusPagamento toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(StatusPagamento x : StatusPagamento.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Status Inválido");
	}
	
	

}
