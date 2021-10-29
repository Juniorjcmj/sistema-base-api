package com.jcmj.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jcmj.domain.ContasPagar;
import com.jcmj.domain.Empresa;

@Repository
public interface ContasPagarRepository extends JpaRepository<ContasPagar, Integer>{
	
	
	    @Transactional(readOnly=true)
	    List<ContasPagar> findByFornecedor(String parametro);
	    @Transactional(readOnly=true)
	    List<ContasPagar> findByformaPagamento(String parametro);
	    List<ContasPagar> findByTipoDespesaOrderByDataPagamento(String parametro);
	   
	    List<ContasPagar> findByEmpresaAndDataPagamentoBetweenAndTipoDespesaAndStatusPagamentoOrderByDataPagamentoDesc(Empresa empresa,
			Date dataInicial, Date dataFinal, String tipoDespesa, String situacao);

	    @Query(value="select * from contas_pagar c where c.data_vencimento between ?1 and ?1 order by c.data_pagamento, c.data_vencimento ASC ", nativeQuery=true)
	    List<ContasPagar> findByDataPagamento(Date data , Date data2);

	    @Query(value="SELECT * FROM contas_pagar c WHERE c.empresa_id LIKE %?1% AND c.tipo_despesa LIKE %?4% AND c.status_pagamento LIKE %?5%  AND c.tipo_despesa LIKE %?6% AND c.data_vencimento BETWEEN ?2 AND ?3 order by c.data_pagamento, c.data_vencimento ASC  ", nativeQuery=true)
	    List<ContasPagar> listaAvancada(Integer id, Date dataInicial, Date dataFinal, String tipoDespesa, String situacao, String forma);
	    @Query(value="SELECT * FROM contas_pagar c WHERE c.empresa_id LIKE %?1% AND c.data_vencimento BETWEEN ?2 AND ?3  order by c.data_pagamento, c.data_vencimento ASC ", nativeQuery=true)
		  List<ContasPagar> listaAvancadaEmpresaAndData(Integer id, Date dataFormatada1, Date dataFormatada2);
	    @Query(value = "SELECT * from contas_pagar  WHERE MONTH(`data_vencimento`) = MONTH(CURRENT_DATE())  order by data_vencimento, data_pagamento ASC ", nativeQuery = true)
	  	List<ContasPagar> buscandoContasDoMesAtual();

	    @Query(value="SELECT * FROM contas_pagar c WHERE c.data_vencimento BETWEEN ?1 AND ?2 order by c.data_vencimento ASC", nativeQuery=true)
	  	List<ContasPagar> listaAvancadaPriodo(Date dataFormatada1, Date dataFormatada2);
	    
	    @Query(value="SELECT * FROM contas_pagar c WHERE c.data_pagamento BETWEEN ?1 AND ?2 order by c.data_pagamento ASC", nativeQuery=true)
	  	List<ContasPagar> listaAvancadaDataPagamento(Date dataFormatada1, Date dataFormatada2);

	    List<ContasPagar> findByDataVencimentoBetween(Date dataInicio, Date dataFim);

	    @Query(value="SELECT * FROM contas_pagar c WHERE c.nd = ?1  ", nativeQuery=true)
	    List<ContasPagar> findByAllNd(String numeroDocumento);
	    
	    @Query(value="SELECT * FROM contas_pagar c WHERE c.nd = (SELECT nd  FROM contas_pagar ORDER BY id DESC LIMIT 1)  ", nativeQuery=true)
		List<ContasPagar> buscarPorNumeroDoUltimoRegistro();
	    @Query(value="SELECT * FROM contas_pagar c WHERE c.forma_pagamento LIKE %?5% AND c.empresa_id LIKE %?1% AND c.status_pagamento LIKE %?4%  AND c.data_vencimento BETWEEN ?2 AND ?3 order by c.data_pagamento, c.data_vencimento ASC  ", nativeQuery=true)
	    List<ContasPagar> listaAvancadaSemTipoDespesa(Integer id, Date dataFormatada1, Date dataFormatada2,
				 String situacao, String forma);
	    @Query(value="SELECT * FROM contas_pagar c WHERE c.forma_pagamento LIKE %?5% AND c.tipo_despesa LIKE %?3% AND c.status_pagamento LIKE %?4%  AND c.data_vencimento BETWEEN ?1 AND ?2 order by c.data_pagamento, c.data_vencimento ASC  ", nativeQuery=true)
	    List<ContasPagar> listaAvancadaSemEmpresa(Date dataFormatada1, Date dataFormatada2, String tipoDespesa,
				String situacao, String forma);
	    
	    @Query(value="SELECT * FROM contas_pagar c WHERE  c.status_pagamento LIKE %?3%  AND c.data_vencimento BETWEEN ?1 AND ?2 LIMIT 1000 ", nativeQuery=true)
	    List<ContasPagar> listaAvancadaSemEmpresaSemTipoDespesa(Date dataFormatada1, Date dataFormatada2, String situacao);
	    
	    @Query(value="SELECT * FROM contas_pagar c WHERE  c.forma_pagamento LIKE %?5% OR c.tipo_despesa LIKE %?4% OR c.status_pagamento LIKE %?3%  AND c.data_vencimento BETWEEN ?1 AND ?2 order by c.data_pagamento, c.data_vencimento ASC  ", nativeQuery=true)
	    List<ContasPagar> listaAvancadaGenerica(Date dataFormatada1, Date dataFormatada2, String situacao, Integer id, String tipo, String forma);
	    
	    @Query(value="SELECT * FROM contas_pagar c WHERE c.empresa_id LIKE %?1% AND  c.tipo_despesa LIKE %?5% and c.status_pagamento LIKE %?4%   AND c.data_vencimento BETWEEN ?2 AND ?3 order by c.data_pagamento, c.data_vencimento ASC  ", nativeQuery=true)
	      List<ContasPagar> listaAvancadaComEmpresaEtipo(Integer id, Date dataFormatada1, Date dataFormatada2,
				String situacao, String tipoDespesa);
	    
	    @Query(value="SELECT * FROM contas_pagar c WHERE c.empresa_id LIKE %?1% and c.status_pagamento LIKE %?4%   AND c.data_vencimento BETWEEN ?2 AND ?3 order by c.data_pagamento, c.data_vencimento ASC  ", nativeQuery=true)
		List<ContasPagar> listaAvancadaEmpresa(Integer id, Date dataFormatada1, Date dataFormatada2, String situacao);
	    @Query(value="SELECT * FROM contas_pagar c WHERE c.tipo_despesa LIKE %?1% and c.status_pagamento LIKE %?4%   AND c.data_vencimento BETWEEN ?2 AND ?3 order by c.data_pagamento, c.data_vencimento ASC  ", nativeQuery=true)
		List<ContasPagar> listaAvancadaTipoDespesa(String tipoDespesa, Date dataFormatada1, Date dataFormatada2,
				String situacao);
	    @Query(value="SELECT * FROM contas_pagar c WHERE c.forma_pagamento LIKE %?1% and c.status_pagamento LIKE %?4%   AND c.data_vencimento BETWEEN ?2 AND ?3 order by c.data_pagamento, c.data_vencimento ASC  ", nativeQuery=true)
		List<ContasPagar> listaAvancadaFormaPagamento(String forma, Date dataFormatada1, Date dataFormatada2,
				String situacao);
	    @Query(value="SELECT * FROM contas_pagar c WHERE c.fornecedor LIKE %?1%   AND c.data_vencimento BETWEEN ?2 AND ?3 order by c.data_pagamento, c.data_vencimento ASC   ", nativeQuery=true)
	    List<ContasPagar> findAllFornecedorPeriodo(String fornecedor, Date dataInicial, Date dataFinal);


}
