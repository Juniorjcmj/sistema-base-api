package com.jcmj.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jcmj.domain.enus.FormaPagamento;
import com.jcmj.domain.enus.StatusPagamento;
import com.jcmj.domain.enus.TipoDespesa;

@Entity
@Table(name = "contas_pagar")
public class ContasPagar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "A data de vencimento é obrigatória.")
    @Column(name="data_vencimento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataVencimento;

    @Column(name="data_pagamento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataPagamento;
    
    @NotNull(message = "O valor da duplicata é obrigatória.")
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###,##0.00")
    @Column(name = "valor_duplicata", nullable = false, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00" )
    private BigDecimal valorDuplicata;

    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###,##0.00")
    @Column(name="juros_multa", nullable = false, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    private BigDecimal jurosMulta;

   
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###,##0.00")
    @Column(name = "valor_pago", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    private BigDecimal valorPago; 
    
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###,##0.00")
    @Column(name = "desconto", nullable = true, columnDefinition = "DECIMAL(9,2) DEFAULT 0.00")
    private BigDecimal desconto; 

    @Size(max = 255,min = 3 , message = "Nome deve conter no mínimo 3 caracteres.")
    @NotEmpty(message = "o Nome do fornecedor é obrigatório.")
    @Column(name = "fornecedor")
    private String fornecedor;   

    @Column(name="local_pagamento")
    private String localPagamento;

    @Column(name="forma_pagamento")
    private FormaPagamento formaPagamento;

    @NotNull(message = "Tipo de despesa deve ser preenchido.")
    @Column(name="tipo_despesa")
    private TipoDespesa tipoDespesa;   

    @Column(name = "status_pagamento", columnDefinition="varchar(50) default 'PENDENTE'")
    private StatusPagamento statusPagamento;

    private String observacao;

    @Column(name="is_aprovado")
    private Boolean isAprovado;

    @Column(name="is_pedir_boleto")
    private Boolean isPedirBoleto;    

    @NotNull(message = "Quantidade de parcelas é obrigatória.")
    @Column(name="numero_parcelas")
    private Integer numeroParcelas;
   
    @NotNull(message = "Empresa é obrigatória.")
    @OneToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    @NotNull(message = "N/D  é obrigatório.")
    private String nd;

    private String parcela;

    public ContasPagar(){ }

    public ContasPagar(Integer id, Date dataPagamento, BigDecimal valorPago
                      ,String fornecedor, TipoDespesa tipoDespesa, String observacao
                      ,Boolean isAprovado ){

        this.id = id;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
        this.fornecedor = fornecedor;
        this.tipoDespesa = tipoDespesa;
        this.observacao = observacao;
        this.isAprovado = isAprovado;

    }

    public Integer getNumeroParcelas() {        
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }
    
    public Boolean getIsPedirBoleto() {
        if(isPedirBoleto == null){
            return false;
        }
        return isPedirBoleto;
    }
    public void setIsPedirBoleto(Boolean isPedirBoleto) {
        this.isPedirBoleto = isPedirBoleto;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataVencimento() {
        return this.dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return this.dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValorDuplicata() {
        if(this.valorDuplicata ==  null){
            return BigDecimal.ZERO;
        }
        return this.valorDuplicata;
    }

    public void setValorDuplicata(BigDecimal valorDuplicata) {
        this.valorDuplicata = valorDuplicata;
    }

    public BigDecimal getJurosMulta() {
    	
    	//this.jurosMulta = this.jurosMulta == null ? BigDecimal.ZERO :  this.jurosMulta;
        return this.jurosMulta;
    }

    public void setJurosMulta(BigDecimal jurosMulta) {
        this.jurosMulta = jurosMulta;
    }

    public BigDecimal getValorPago() {
        if(this.valorPago ==  null){
            return BigDecimal.ZERO;
        }
        
        return this.valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getFornecedor() {
        return this.fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getNd() {
        return this.nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public String getParcela() {    	
        return this.parcela;
    }

    

	public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getLocalPagamento() {
        return this.localPagamento;
    }

    public void setLocalPagamento(String localPagamento) {
        this.localPagamento = localPagamento;
    }

    public FormaPagamento getFormaPagamento() {
    	return this.formaPagamento;
        
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public TipoDespesa getTipoDespesa() {
        return this.tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa ) {
        this.tipoDespesa = tipoDespesa;
    }

    public StatusPagamento getStatusPagamento() {
        return this.statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento situacao) {
        this.statusPagamento = situacao;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    public Boolean getIsAprovado() {
        if(isAprovado == null){
            return false;
        }
        return isAprovado;
    }
    public void setIsAprovado(Boolean isAprovado) {
        this.isAprovado = isAprovado;
    }

	public BigDecimal getDesconto() {
		//this.desconto = this.desconto != null ? this.desconto : BigDecimal.ZERO ;
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	@Override
	public String toString() {
		
		String autori = SecurityContextHolder.getContext().getAuthentication().getName();
		return "Contas a Pagar: "+"Responsável pela Operação  : "+ autori +"Data alteração : "+new Date()+", Fornecedor=" + "id=" + id + ", Data Vencimento=" + dataVencimento + ", Data do Pagamento=" + dataPagamento
				+ ", Valor da Duplicata=" + valorDuplicata + ", Juros ou Multa=" + jurosMulta + ", Valor Pago=" + valorPago
				+ ", Desconto=" + desconto +  fornecedor + ", ND=" + nd + ", Parcela=" + parcela
				+ ", Local de Pagamento=" + localPagamento + ", Forma Pagamento=" + formaPagamento + ", Tipo de Despesa="
				+ tipoDespesa + ", Situacao=" + statusPagamento + ", Observacao=" + observacao + ",Aprovado para Pagamento ?=" + isAprovado
				+ ", Pedir Boleto=" + isPedirBoleto + ",  Numero Parcelas=" + numeroParcelas
				+ ", Empresa=" + empresa;
	}
	
	    
    
}
