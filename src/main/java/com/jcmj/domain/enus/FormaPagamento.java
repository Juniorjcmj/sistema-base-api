package com.jcmj.domain.enus;

public enum FormaPagamento {
	
	ESPECIE(0,"ESPÉCIE"), BOLETO(1, "BOLETO"), CHUEQUE(2,"CHEQUE"),TRANSFERENCIA(3,"TRANSFERÊNCIA")
	,DEBITO(4,"DÉBITO"), PEDIRBOLETO(5,"PEDIR BOLETO"), NAOINFORMADO(6,"NÃO INFORMADO");
	
	
	private Integer codigo;
	private String descricao;

	
	private FormaPagamento(Integer codigo,String descricao) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public static FormaPagamento toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(FormaPagamento x : FormaPagamento.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Status Inválido");
	}
	
	public static FormaPagamento toGetDescricao(String desc) {
		if(desc == null) {
			return FormaPagamento.NAOINFORMADO;
		}
		
		for(FormaPagamento x : FormaPagamento.values()) {
			if(desc.equals(x.getDescricao())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Status Inválido");
	}
	
	

}
