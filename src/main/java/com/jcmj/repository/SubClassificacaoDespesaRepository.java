package com.jcmj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcmj.domain.SubClassificacaoDespesa;

@Repository
public interface SubClassificacaoDespesaRepository extends JpaRepository<SubClassificacaoDespesa, Integer>{

}
