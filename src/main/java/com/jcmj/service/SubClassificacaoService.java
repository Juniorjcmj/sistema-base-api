package com.jcmj.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcmj.domain.Pessoa;
import com.jcmj.domain.SubClassificacaoDespesa;
import com.jcmj.domain.Tecnico;
import com.jcmj.domain.dto.TecnicoDTO;
import com.jcmj.repository.ClassificacaoDespesaRepository;
import com.jcmj.repository.ContasPagarRepository;
import com.jcmj.repository.PessoaRepository;
import com.jcmj.repository.SubClassificacaoDespesaRepository;
import com.jcmj.repository.TecnicoRepository;
import com.jcmj.service.exception.DataIntegrityViolationException;
import com.jcmj.service.exception.ObjectnotFoundException;

@Service
public class SubClassificacaoService {
	
	@Autowired
	private SubClassificacaoDespesaRepository repo;
	@Autowired
	private ClassificacaoDespesaRepository classRepo;
	
	@Autowired
	private ContasPagarRepository contaRepo;
	
	
	
	public SubClassificacaoDespesa findById(Integer id) {
		Optional<SubClassificacaoDespesa> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto Não encontrado!  id: " + id));
	}

	public List<SubClassificacaoDespesa> findAll() {		
		return repo.findAll();
	}
	
	public SubClassificacaoDespesa create (SubClassificacaoDespesa objDto) {
		objDto.setId(null);
		return repo.save(objDto);
	}
	
	public SubClassificacaoDespesa update(Integer id, @Valid SubClassificacaoDespesa objDto) {
		objDto.setId(id);
		SubClassificacaoDespesa oldObj = findById(id);		
		return  repo.save(oldObj);
	}
	

	public void delete(Integer id) {		

		if(contaRepo.finAllClassificacaoDespesa(id).size() > 0  || contaRepo.finAllSubClassificacaoDespesa(id).size() > 0) {
			throw new DataIntegrityViolationException("Objeto  possui a pagar e não pode ser deletada!");
		}
		repo.deleteById(id);
		
	}

	
}
