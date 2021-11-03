package com.jcmj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcmj.domain.ClassificacaoDespesa;

@Repository
public interface ClassificacaoDespesaRepository extends JpaRepository<ClassificacaoDespesa, Integer>{

}
