package com.jcmj.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.jcmj.domain.ContasPagar;

public class ContasPagarDTO  implements Serializable{

	
	private static final long serialVersionUID = 1L;

	 
    private Integer id;

    @NotNull(message = "A data de vencimento é obrigatória.")   
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataVencimento;

    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataPagamento;
    
    @NotNull(message = "O valor da duplicata é obrigatória.")
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###,##0.00")    
    private BigDecimal valorDuplicata;

    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###,##0.00")  
    private BigDecimal jurosMulta;

   
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###,##0.00")   
    private BigDecimal valorPago; 
    
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###,##0.00")   
    private BigDecimal desconto; 

    @Size(max = 255,min = 3 , message = "Nome deve conter no mínimo 3 caracteres.")
    @NotEmpty(message = "o Nome do fornecedor é obrigatório.")    
    private String fornecedor;   

    
    private String localPagamento;   
    private int formaPagamento;   
    private int tipoDespesa;      
    private int statusPagamento;
    private String observacao;   
    private Boolean isAprovado;   
    private Boolean isPedirBoleto;   
    private String parcela;

    @NotNull(message = "Quantidade de parcelas é obrigatória.")  
    private Integer numeroParcelas;
   
    @NotNull(message = "Empresa é obrigatória.")   
    private Integer empresa;
    
    private String nd;
    
	public ContasPagarDTO(ContasPagar c) {
		super();
		this.id = c.getId();
		this.dataVencimento = c.getDataVencimento();
		this.dataPagamento = c.getDataPagamento();
		this.valorDuplicata = c.getValorDuplicata();
		this.jurosMulta = c.getJurosMulta();
		this.valorPago = c.getValorPago();
		this.desconto = c.getDesconto();
		this.fornecedor = c.getFornecedor();
		this.localPagamento = c.getLocalPagamento();
		this.formaPagamento = c.getFormaPagamento().getCodigo();
		this.tipoDespesa = c.getTipoDespesa().getCodigo();
		this.statusPagamento = c.getStatusPagamento().getCodigo();
		this.observacao = c.getObservacao();
		this.isAprovado = c.getIsAprovado();
		this.isPedirBoleto = c.getIsPedirBoleto();
		this.parcela = c.getParcela();
		this.numeroParcelas = c.getNumeroParcelas();
		this.empresa = c.getEmpresa().getId();
		this.nd = c.getNd();
	}
	
	public ContasPagarDTO() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public BigDecimal getValorDuplicata() {
		return valorDuplicata;
	}
	public void setValorDuplicata(BigDecimal valorDuplicata) {
		this.valorDuplicata = valorDuplicata;
	}
	public BigDecimal getJurosMulta() {
		this.jurosMulta = this.jurosMulta != null ? this.jurosMulta : BigDecimal.ZERO ;
		return jurosMulta;
	}
	public void setJurosMulta(BigDecimal jurosMulta) {
		this.jurosMulta = jurosMulta;
	}
	public BigDecimal getValorPago() {
		return valorPago;
	}
	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}
	public BigDecimal getDesconto() {
		this.desconto = this.desconto != null ? this.desconto : BigDecimal.ZERO ;
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getLocalPagamento() {
		return localPagamento;
	}
	public void setLocalPagamento(String localPagamento) {
		this.localPagamento = localPagamento;
	}
	public int getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(int formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public int getTipoDespesa() {
		return tipoDespesa;
	}
	public void setTipoDespesa(int tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}
	public int getStatusPagamento() {
		return statusPagamento;
	}
	public void setStatusPagamento(int statusPagamento) {
		this.statusPagamento = statusPagamento;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Boolean getIsAprovado() {
		return isAprovado;
	}
	public void setIsAprovado(Boolean isAprovado) {
		this.isAprovado = isAprovado;
	}
	public Boolean getIsPedirBoleto() {
		return isPedirBoleto;
	}
	public void setIsPedirBoleto(Boolean isPedirBoleto) {
		this.isPedirBoleto = isPedirBoleto;
	}
	public String getParcela() {
		return parcela;
	}
	public void setParcela(String parcela) {
		this.parcela = parcela;
	}
	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}
	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}
	public Integer getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}
	public String getNd() {
		return nd;
	}
	public void setNd(String nd) {
		this.nd = nd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ContasPagarDTO [id=" + id + ", dataVencimento=" + dataVencimento + ", dataPagamento=" + dataPagamento
				+ ", valorDuplicata=" + valorDuplicata + ", jurosMulta=" + jurosMulta + ", valorPago=" + valorPago
				+ ", desconto=" + desconto + ", fornecedor=" + fornecedor + ", localPagamento=" + localPagamento
				+ ", formaPagamento=" + formaPagamento + ", tipoDespesa=" + tipoDespesa + ", statusPagamento="
				+ statusPagamento + ", observacao=" + observacao + ", isAprovado=" + isAprovado + ", isPedirBoleto="
				+ isPedirBoleto + ", parcela=" + parcela + ", numeroParcelas=" + numeroParcelas + ", empresa=" + empresa
				+ ", nd=" + nd + "]";
	}
	
	

    
    
    
	
	
	
	

}
