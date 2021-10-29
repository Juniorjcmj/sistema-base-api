package com.jcmj.domain.enus;

public enum TipoDespesa {
	
	VARIAVEL(0,"VARIÁVEL"), FIXA(1, "FIXA");
	
	
	private Integer codigo;
	private String descricao;

	
	private TipoDespesa(Integer codigo,String descricao) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public static TipoDespesa toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(TipoDespesa x : TipoDespesa.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Status Inválido");
	}
	
	

}
