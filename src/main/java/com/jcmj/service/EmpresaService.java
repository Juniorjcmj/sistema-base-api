package com.jcmj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcmj.domain.Empresa;
import com.jcmj.repository.EmpresaRepository;
import com.jcmj.service.exception.ObjectnotFoundException;

@Service
public class EmpresaService {
	
	@Autowired
	EmpresaRepository repository;
	
	public Empresa findById(Integer id) {
		Optional<Empresa> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectnotFoundException(
						"Objeto Não encontrado!  id: " +
				id));
	}
	
	public List<Empresa> findAll() {
        return repository.findAll();
    }
       

	
	
}
