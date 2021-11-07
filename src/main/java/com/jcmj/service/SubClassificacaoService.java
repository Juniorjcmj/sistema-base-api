package com.jcmj.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcmj.domain.SubClassificacaoDespesa;
import com.jcmj.domain.dto.SubClassificacaoDespesaDTO;
import com.jcmj.repository.ContasPagarRepository;
import com.jcmj.repository.SubClassificacaoDespesaRepository;
import com.jcmj.service.exception.DataIntegrityViolationException;
import com.jcmj.service.exception.ObjectnotFoundException;

@Service
public class SubClassificacaoService {
	
	@Autowired
	private SubClassificacaoDespesaRepository repo;
	@Autowired
	ClassificacaoService classificacaoService;	
	@Autowired
	private ContasPagarRepository contaRepo;	
	
	public SubClassificacaoDespesa findById(Integer id) {
		Optional<SubClassificacaoDespesa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto Não encontrado!  id: " + id));
	}

	public List<SubClassificacaoDespesa> findAll() {		
		return repo.findAll();
	}
	
	public SubClassificacaoDespesa create (SubClassificacaoDespesaDTO objDto) {
		objDto.setId(null);
		SubClassificacaoDespesa result = new SubClassificacaoDespesa();
		
		result.setDescricao(objDto.getDescricao());
		return repo.save(result);
	}
	
	public SubClassificacaoDespesa update(Integer id, @Valid SubClassificacaoDespesaDTO objDto) {
		objDto.setId(id);
		SubClassificacaoDespesa result = findById(id);		
		result.setDescricao(objDto.getDescricao());
		return repo.save(result);
	}
	

	public void delete(Integer id) {		

		if(contaRepo.finAllClassificacaoDespesa(id).size() > 0  || contaRepo.finAllSubClassificacaoDespesa(id).size() > 0) {
			throw new DataIntegrityViolationException("Objeto  possui a pagar e não pode ser deletada!");
		}
		repo.deleteById(id);
		
	}

	
}
