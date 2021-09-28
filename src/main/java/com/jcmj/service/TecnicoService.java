package com.jcmj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcmj.domain.Pessoa;
import com.jcmj.domain.Tecnico;
import com.jcmj.domain.dto.TecnicoDTO;
import com.jcmj.repository.PessoaRepository;
import com.jcmj.repository.TecnicoRepository;
import com.jcmj.service.exception.DataIntegrityViolationException;
import com.jcmj.service.exception.ObjectnotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto Não encontrado!  id: " + id));
	}

	public List<Tecnico> findAll() {		
		return tecnicoRepository.findAll();
	}
	
	public Tecnico create (TecnicoDTO objDto) {
		objDto.setId(null);
		validaPorCpfEEmail(objDto);
		Tecnico newObj = new Tecnico(objDto);
		return tecnicoRepository.save(newObj);
	}

	private void validaPorCpfEEmail(TecnicoDTO objDto) {
		
	   Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
	   
	   if(obj.isPresent() && obj.get().getId() != objDto.getId()) {
		   throw new DataIntegrityViolationException("CPF Já cadastrado no Sistema");
	   }
	   
	   
	   obj = pessoaRepository.findByEmail(objDto.getEmail());
	   if(obj.isPresent() && obj.get().getId() != objDto.getId()) {
		   throw new DataIntegrityViolationException("E-mail Já cadastrado no Sistema");
	   }
		
	}

}
