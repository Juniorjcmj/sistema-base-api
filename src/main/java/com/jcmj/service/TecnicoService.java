package com.jcmj.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
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
		objDto.setSenha(encoder.encode(objDto.getSenha()));
		Tecnico newObj = new Tecnico(objDto);
		return tecnicoRepository.save(newObj);
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDto) {
		objDto.setId(id);
		Tecnico oldObj = findById(id);
		objDto.setSenha(encoder.encode(objDto.getSenha()));
		validaPorCpfEEmail(objDto);
		oldObj = new Tecnico(objDto);
		
		return  tecnicoRepository.save(oldObj);
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

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui OS e não pode ser deletado!");
		}
		tecnicoRepository.deleteById(id);
		
	}

	
}
