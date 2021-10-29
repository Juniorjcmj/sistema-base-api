package com.jcmj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jcmj.domain.SequenciaNumeroDoc;

@Repository
public interface SequenciaNumDocRepository extends JpaRepository<SequenciaNumeroDoc, Integer>{
	
	@Query(value ="SELECT * FROM sequencia_numero_documento ORDER BY numero_documento DESC LIMIT 1", nativeQuery=true)
	SequenciaNumeroDoc findByUltimoRegistro();

}
