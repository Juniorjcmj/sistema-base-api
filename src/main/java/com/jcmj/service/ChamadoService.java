package com.jcmj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcmj.domain.Chamado;
import com.jcmj.repository.ChamadoRepository;
import com.jcmj.service.exception.ObjectnotFoundException;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = chamadoRepository.findById(id);
		return obj.orElseThrow(()-> new ObjectnotFoundException("Chamado não encontrado!" + id));
	}

	public List<Chamado> findAll() {
		
		return chamadoRepository.findAll();
	}

}
