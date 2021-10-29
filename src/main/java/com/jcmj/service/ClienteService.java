package com.jcmj.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcmj.domain.Cliente;
import com.jcmj.domain.Pessoa;
import com.jcmj.domain.dto.ClienteDTO;
import com.jcmj.repository.ClienteRepository;
import com.jcmj.repository.PessoaRepository;
import com.jcmj.service.exception.DataIntegrityViolationException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElse(null);
	}
	
	
	public List<Cliente> findAll() {		
		return repository.findAll();
	}
	
	public Cliente create (ClienteDTO objDto) {
		objDto.setId(null);
		validaPorCpfEEmail(objDto);
		objDto.setSenha(encoder.encode(objDto.getSenha()));
		Cliente newObj = new Cliente(objDto);
		return repository.save(newObj);
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDto) {
		objDto.setId(id);
		Cliente oldObj = findById(id);
		validaPorCpfEEmail(objDto);
		if(objDto.getSenha().equals(oldObj.getSenha())) {
			objDto.setSenha(encoder.encode(objDto.getSenha()));
		}
		oldObj = new Cliente(objDto);
		
		return  repository.save(oldObj);
	}


	private void validaPorCpfEEmail(ClienteDTO objDto) {
		
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
		Cliente obj = findById(id);
		
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui Chamado e não pode ser deletado!");
		}
		repository.deleteById(id);
		
	}

}
