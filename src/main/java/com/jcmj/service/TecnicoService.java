package com.jcmj.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcmj.domain.Tecnico;
import com.jcmj.repository.TecnicoRepository;
import com.jcmj.service.exception.ObjectnotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto Não encontrado!  id: " + id));
	}

}
