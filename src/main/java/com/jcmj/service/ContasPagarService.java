package com.jcmj.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jcmj.domain.ContasPagar;
import com.jcmj.domain.Empresa;
import com.jcmj.domain.SequenciaNumeroDoc;
import com.jcmj.domain.dto.ContasPagarDTO;
import com.jcmj.domain.enus.FormaPagamento;
import com.jcmj.domain.enus.StatusPagamento;
import com.jcmj.domain.enus.TipoDespesa;
import com.jcmj.repository.ContasPagarRepository;
import com.jcmj.repository.SequenciaNumDocRepository;

@Service
public class ContasPagarService {
	
	@Autowired
    ContasPagarRepository repository;
	@Autowired
	EmpresaService empresaService;
	
	 @Autowired
	 SequenciaNumDocRepository numDocrepo;
	
	 /**
	   * Método salva conta a pagar no banco de dados.   
	   * @param ContasPagar  instancia de contas a pagar   
	   * @author José Carlos
	   * @since 1.0
	   * @version 1.0
	   * @return {ContasPagar} instance
		*/
	    @Transactional
	    public ContasPagar insert(ContasPagar conta) {	    	   	
	        return repository.save(conta);
	    }
	    
	    /**
	     * Método salva conta a pagar no banco de dados.   
	     * @param ContasPagar  instancia de contas a pagar   
	     * @author José Carlos
	     * @since 1.0
	     * @version 1.0
	     * @return {ContasPagar} instance
	  	*/
	       public List<ContasPagar> insertComParcelas(ContasPagarDTO contaDTO) {               
	          int qontosVouInserir = contaDTO.getId() == null ? contaDTO.getNumeroParcelas() : 1 ; 	          
	          contaDTO.setNd(contaDTO.getNd().isEmpty() ? numeroDocumento() : contaDTO.getNd());
	          
	          List<ContasPagar> contasSalvas = new ArrayList<>();
	          
	          if(qontosVouInserir == 1) {
	        	  ContasPagar c  = find(contaDTO.getId()); 
                  contasSalvas.add(insert(extractedInsert(contaDTO, 1, c)));
	          }else {
	        	   for(int i = 0; i < qontosVouInserir -1; i ++){
	            	  ContasPagar c = new ContasPagar(); 
	                   contasSalvas.add(insert(extractedInsert(contaDTO, i+1, c)));
	                 
	                }
	          }
	          
	             
	          return contasSalvas;
	      }
	       
	       private ContasPagar extractedInsert(ContasPagarDTO conta, int i, ContasPagar c) {
	           
	    	   if(conta.getId() != null) {
	    		   c = find(conta.getId());
	    	   }
	    	   Empresa e = empresaService.findById(conta.getEmpresa());
	    	   
	           c.setEmpresa(e);
	           System.out.println(c.toString());
	           c.setDataPagamento(conta.getDataPagamento());
	           c.setFornecedor(conta.getFornecedor());       
	           c.setValorDuplicata(conta.getNumeroParcelas() > 1 && conta.getId() == null ? conta.getValorDuplicata().divide(new BigDecimal(conta.getNumeroParcelas()),3, RoundingMode.CEILING) : conta.getValorDuplicata());       
	           
	           c.setFormaPagamento(FormaPagamento.toEnum(conta.getFormaPagamento()));
	           
	           c.setDesconto(conta.getNumeroParcelas() > 1 && conta.getId() == null ? conta.getDesconto().divide(new BigDecimal(conta.getNumeroParcelas()),3, RoundingMode.CEILING) : conta.getDesconto()); 
	           c.setJurosMulta(conta.getNumeroParcelas() > 1 && conta.getId() == null ? conta.getJurosMulta().divide(new BigDecimal(conta.getNumeroParcelas()),3, RoundingMode.CEILING) : conta.getJurosMulta()); 
	           c.setNd(conta.getNd());
	           c.setLocalPagamento(conta.getLocalPagamento());
	           c.setNumeroParcelas(conta.getNumeroParcelas());
	           c.setObservacao(conta.getObservacao());
	           
	           if(conta.getId() == null && !conta.getParcela().isEmpty()) {
	           	c.setParcela((Integer.parseInt(conta.getParcela()) + i)+ "/"+ (conta.getNumeroParcelas()+ (Integer.parseInt(conta.getParcela())-1)));
	           }
	          
	           
	           c.setStatusPagamento(StatusPagamento.toEnum(conta.getStatusPagamento()).getDescricao().isEmpty()  
	        		                ? StatusPagamento.PENDENTE : StatusPagamento.toEnum(conta.getStatusPagamento()));
	           c.setTipoDespesa(TipoDespesa.toEnum(conta.getTipoDespesa()));
	           
	           if(conta.getId() == null) {
	           	c.setValorPago(conta.getValorDuplicata().divide(new BigDecimal(conta.getNumeroParcelas()),3, RoundingMode.CEILING)
	               		                            .add(conta.getNumeroParcelas() > 1 && conta.getId() == null ? conta.getJurosMulta().divide(new BigDecimal(conta.getNumeroParcelas()),3, RoundingMode.CEILING) : conta.getJurosMulta())
	               		                            .subtract(conta.getNumeroParcelas() > 1 && conta.getId() == null ? conta.getDesconto().divide(new BigDecimal(conta.getNumeroParcelas()),3, RoundingMode.CEILING) : conta.getDesconto())); 
	           	
	           }
	                      
	           c.setDataVencimento(somaMes(conta.getDataVencimento(), i));
	           return c;
	       }
	       
	       public Date somaMes(Date data, int mes) {
	           Calendar cal = new GregorianCalendar();
	           cal.setTime(data);
	           cal.add(Calendar.MONTH, mes);
	           return cal.getTime();
	     }
	       
	       /**
	        * Método gera um numero de documento .          
	        * @author José Carlos
	        * @since 1.0
	        * @version 1.0
	        * @return {String} instance
	     	*/
	       private String numeroDocumento() {
	       	
	         	SequenciaNumeroDoc s = numDocrepo.findByUltimoRegistro() == null ? new SequenciaNumeroDoc() : numDocrepo.findByUltimoRegistro() ;
	         	if(s.getId() == null) {
	         		s.setNumeroDocumento(888888888);
	         		numDocrepo.save(s);
	         	}
	         	
	         	Integer novoNumero = s.getNumeroDocumento();
	       	s.setNumeroDocumento(++novoNumero);
	       	numDocrepo.save(s);    	
	       	return s.getNumeroDocumento().toString();
	       	
	       }
	       /**
	        * Método de busca de a pagar por periodo.
	        * Você deve utiliza-lo para buscar a pagar passando sort.   
	        * @param Sort     
	        * @author José Carlos
	        * @since 1.0
	        * @version 1.0
	        * @return {lis} instance
	     	*/
	         public List<ContasPagar> findAll(Sort sort) {
	             return repository.findAll(sort);
	         }
	         public List<ContasPagar> findAll() {
	             return repository.findAll();
	         }
	              /**
	        * Método que busca um a pagar .  
	        * @param Integer id 
	        * @author José Carlos
	        * @since 1.0
	        * @version 1.0
	        * @return {ContasPagar} instance
	     	*/
	         public ContasPagar find(Integer id) throws ObjectNotFoundException {

	             Optional<ContasPagar> obj = repository.findById(id);
	             return obj.orElseThrow(() -> new ObjectNotFoundException(
	                     "Objeto não encontrado!:" + id + ", Tipo : " + ContasPagar.class.getName(), null));

	         }
	         
	         /**
	          * Método que exclui um a pagar .  
	          * @param Integer id 
	          * @author José Carlos
	          * @since 1.0
	          * @version 1.0         
	 	*/
	     public void delete(Integer id) {
	         //ContasPagar c = find(id);	        
	         repository.deleteById(id);
	     }
	     /**
	         * Método que calcula o total de valor pago na lista que foi passada .
	         * @param List<ContasPagar>                   
	         * @author José Carlos
	         * @since 1.0
	         * @version 1.0
	         * @return {Double}
	         
		*/
	    public BigDecimal calculandoTotalPago(List<ContasPagar> contas) {
	        BigDecimal result = contas.stream()
	        .filter(conta -> conta.getDataPagamento() != null)
	        .map(conta -> conta.getValorDuplicata().add(conta.getJurosMulta()).subtract(conta.getDesconto()))
	        .reduce(BigDecimal.ZERO,BigDecimal::add);
	 
	         return result;
	     }
	        /**
	         * Método que calcula o valor total de duplicatas na lista que foi passada .
	         * @param List<ContasPagar>                   
	         * @author José Carlos
	         * @since 1.0
	         * @version 1.0
	         * @return {Double}         
		*/
	     public BigDecimal calcularValorTotalDuplicata(List<ContasPagar> contas) {
	        BigDecimal result = contas.stream()        
	        .map(conta -> conta.getValorDuplicata()
	        		           .add(conta.getJurosMulta())
	        		           .subtract(conta.getDesconto()))
	        .reduce(BigDecimal.ZERO,BigDecimal::add);
	 
	         return result;
	     }
		 /**
	         * Método que calcula o valor do mês .
	         * @param List<ContasPagar>                   
	         * @author José Carlos
	         * @since 1.0
	         * @version 1.0
	         * @return {Double}         
		*/
	     public BigDecimal calcularValorTotalDoMes(List<ContasPagar> contas) {
	        BigDecimal result = contas.stream()         
	         .map(conta -> conta.getValorDuplicata().add(conta.getJurosMulta()).subtract(conta.getDesconto()))
	         .reduce(BigDecimal.ZERO,BigDecimal::add);
			return result;
		}
	     /**
	         * Método que calcula o valor total pago no dia .
	         * @param List<ContasPagar>                   
	         * @author José Carlos
	         * @since 1.0
	         * @version 1.0
	         * @return {Double}         
		*/
		public BigDecimal calculandoTotalPagoDoDia(List<ContasPagar> contas) {
	        BigDecimal result = contas.stream()
	         .filter(conta -> (verificandoMesmoDiaDaSemana(conta.getDataVencimento(), new Date())) == true)
	         .map(conta -> conta.getValorPago())
	         .reduce(BigDecimal.ZERO,BigDecimal::add);
			return result;
		}
	     /**
	         * Método que calcula o valor total a pagar na lista que foi passada .
	         * @param List<ContasPagar>                   
	         * @author José Carlos
	         * @since 1.0
	         * @version 1.0
	         * @return {Double}         
		*/
	    public BigDecimal calcularValorTotalDoDia()  {     

	        List<ContasPagar> contas = repository.findByDataPagamento(new Date(), new Date());
	        BigDecimal result = contas.stream()
	         .filter(conta -> (verificandoMesmoDiaDaSemana(conta.getDataVencimento(), new Date())) == true)
	         .map(conta -> conta.getValorDuplicata().add(conta.getJurosMulta()).subtract(conta.getDesconto()))
	         .reduce(BigDecimal.ZERO,BigDecimal::add); 
	           return result;
	       }
	           /**
	         * Método que calcula o valor total de duplicatas que no dia na lista que foi passada .
	         * @param List<ContasPagar>                   
	         * @author José Carlos
	         * @since 1.0
	         * @version 1.0
	         * @return {Double}         
		     */
	    public BigDecimal calcularValorTotalDoDia(List<ContasPagar> contas) {
	        BigDecimal result = contas.stream()
	        .filter(conta -> (verificandoMesmoDiaDaSemana(conta.getDataVencimento(), new Date())) == true)      
	        .map(conta ->  conta.getValorDuplicata().add(conta.getJurosMulta()).subtract(conta.getDesconto()))
	        .reduce(BigDecimal.ZERO,BigDecimal::add); 
	           return result;
	       }
	        /**
	         * Método que calcula o valor total de duplicatas da semana na lista que foi passada .
	         * @param List<ContasPagar>                   
	         * @author José Carlos
	         * @since 1.0
	         * @version 1.0
	         * @return {Double}         
		*/
		public BigDecimal calculandoApagarSemana(List<ContasPagar> contas) {
	        BigDecimal result = contas.stream()
	         .filter(conta -> (pegandoASemanaDoAno(conta.getDataVencimento(), new Date())) == true)
	         .map(conta -> conta.getValorDuplicata().add(conta.getJurosMulta()).subtract(conta.getDesconto()))
	         .reduce(BigDecimal.ZERO,BigDecimal::add); 
			return result;
		}
	     /**
	         * Método que calcula o valor total pago na semana lista que foi passada .
	         * @param List<ContasPagar>                   
	         * @author José Carlos
	         * @since 1.0
	         * @version 1.0
	         * @return {Double}         
		*/
		public BigDecimal calcularTotalPagoSemana(List<ContasPagar> contas) {
	        BigDecimal result = contas.stream()
	         .filter(conta -> conta.getDataPagamento() != null)
	         .filter(conta -> (pegandoASemanaDoAno(conta.getDataVencimento(), new Date())) == true)
	         .map(conta -> conta.getValorDuplicata().add(conta.getJurosMulta()).subtract(conta.getDesconto()))
	         .reduce(BigDecimal.ZERO,BigDecimal::add); 
			return result;
		}
	      /**
	         * Método que através de duas datas passadas verifica se são da mesma semana .
	         * @param Date  data dovencimento
	         * @param Date data de hoje                   
	         * @author José Carlos
	         * @since 1.0
	         * @version 1.0
	         * @return {Boolean}         
		*/
	    public Boolean pegandoASemanaDoAno (Date dataVencimento, Date dataDeHoje){

	        Calendar semanaVencimento = new GregorianCalendar();
	        semanaVencimento.setTime(dataVencimento);
	        Integer semanaVenc = semanaVencimento.get(Calendar.WEEK_OF_MONTH);

	        Calendar semanaAtual = new GregorianCalendar();
	        semanaAtual.setTime(dataDeHoje);
	        Integer semanaDeHoje = semanaAtual.get(Calendar.WEEK_OF_MONTH);

	        if(semanaVencimento.get(Calendar.YEAR) != semanaAtual.get(Calendar.YEAR)){
	            return false;
	        }
	        return semanaVenc == semanaDeHoje;    
	    }
	    /**
	         * Método que através de duas datas passadas verifica se são da mesma semana .
	         * @param List<ContasPagar>                   
	         * @author José Carlos
	         * @since 1.0
	         * @version 1.0
	         * @return {Boolean}         
		*/
	    public Boolean verificandoMesmoDiaDaSemana(Date dataVencimento, Date dataDeHoje){
	       
	    	Calendar diaVencimento = new GregorianCalendar();
	        diaVencimento.setTime(dataVencimento);
	        Integer vencimento = diaVencimento.get(Calendar.DAY_OF_MONTH);
	        
	        Calendar diaHoje = new GregorianCalendar();
	        diaHoje.setTime(dataDeHoje);
	        Integer hoje = diaHoje.get(Calendar.DAY_OF_MONTH);

	        if(diaVencimento.get(Calendar.YEAR) != diaHoje.get(Calendar.YEAR)){
	            return false;
	        }
	        return vencimento == hoje;
	    }
	    public List<ContasPagar> findByNumeroDocumento(String numeroDocumento) {
	    	
	        return (numeroDocumento.isEmpty() ? repository.buscarPorNumeroDoUltimoRegistro() : repository.findByAllNd(numeroDocumento));
	    }  
	    /**
	     * Método que cadastra fields através de uma chamada ajax para o controlador .
	     * @param Integer id(ContasPagar), String parametro(valor a ser inderido), String tipo(qual campo deverá ser modificado)                   
	     * @author José Carlos
	     * @since 1.0
	     * @version 1.0
	             
	*/
		public void cadastroUtil(Integer id, String parametro, String tipo) throws ParseException {
			ContasPagar conta = find(id);
			if (!(parametro.isEmpty())) {
				if (tipo.equals("data")) {
					SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
					Date dataFormatada = formato.parse(parametro.replace("-", "/"));
					conta.setDataPagamento(dataFormatada);
					conta.setValorPago(conta.getValorDuplicata().add(conta.getJurosMulta()).subtract(conta.getDesconto()));
					conta.setStatusPagamento(StatusPagamento.PAGO);
				} else if (tipo.contains("valor")) {
					conta.setValorPago(new BigDecimal(parametro.replace(".", "").replace(",", ".")));
					conta.setStatusPagamento(StatusPagamento.PAGO);
				} else if (tipo.contains("forma")) {
					conta.setFormaPagamento(FormaPagamento.toGetDescricao(parametro));
					conta.setIsPedirBoleto(false);
				} else if (tipo.equals("multa") || tipo.equals("desconto")) {
					if(tipo.equals("multa")) {
						conta.setJurosMulta(new BigDecimal(parametro.replace(".", "").replace(",", ".")));
						conta.setValorPago(conta.getValorDuplicata().add(conta.getJurosMulta()).subtract(conta.getDesconto()));
					}else {
						conta.setDesconto(new BigDecimal(parametro.replace(".", "").replace(",", ".")));
						conta.setValorPago(conta.getValorDuplicata().subtract(conta.getDesconto()).add(conta.getJurosMulta()));
					}	
				} else if (tipo.contains("duplicata")) {
					conta.setValorDuplicata(new BigDecimal(parametro.replace(".", "").replace(",", ".")));
					conta.setValorPago(conta.getValorDuplicata().add(conta.getJurosMulta()).subtract(conta.getDesconto()));
				} else if (tipo.contains("local")) {
					conta.setLocalPagamento(parametro);
				} else if (tipo.contains("observacao")) {
					conta.setObservacao(parametro);
				} else if (tipo.equals("dataVencimento")) {
					SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
					Date dataFormatada = formato.parse(parametro.replace("-", "/"));
					conta.setDataVencimento(dataFormatada);
				}
				conta.setNumeroParcelas(conta.getNumeroParcelas() == null ? 1 : conta.getNumeroParcelas());
				insert(conta);
			}
		}

	

}
