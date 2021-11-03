package com.jcmj.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcmj.domain.ClassificacaoDespesa;
import com.jcmj.repository.ClassificacaoDespesaRepository;
import com.jcmj.repository.ContasPagarRepository;
import com.jcmj.repository.SubClassificacaoDespesaRepository;
import com.jcmj.service.exception.DataIntegrityViolationException;
import com.jcmj.service.exception.ObjectnotFoundException;

@Service
public class ClassificacaoService {
	
	@Autowired
	private ClassificacaoDespesaRepository repo;
	@Autowired
	private SubClassificacaoDespesaRepository subRepo;
	
	@Autowired
	private ContasPagarRepository contaRepo;
	
	public ClassificacaoDespesa findById(Integer id) {
		Optional<ClassificacaoDespesa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto Não encontrado!  id: " + id));
	}

	public List<ClassificacaoDespesa> findAll() {		
		return repo.findAll();
	}
	
	public ClassificacaoDespesa create (ClassificacaoDespesa objDto) {
		objDto.setId(null);				
		return repo.save(objDto);
	}
	
	public ClassificacaoDespesa update(Integer id, @Valid ClassificacaoDespesa objDto) {
		objDto.setId(id);
		ClassificacaoDespesa oldObj = findById(id);		
		return  repo.save(oldObj);
	}

	public void delete(Integer id) {		
	
		if(contaRepo.finAllClassificacaoDespesa(id).size() > 0  || contaRepo.finAllSubClassificacaoDespesa(id).size() > 0) {
			throw new DataIntegrityViolationException("Objeto  possui a pagar e não pode ser deletada!");
		}
		
		repo.deleteById(id);
		
	}

	
}
