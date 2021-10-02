package com.jcmj.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcmj.domain.Chamado;
import com.jcmj.domain.Cliente;
import com.jcmj.domain.Tecnico;
import com.jcmj.domain.dto.ChamadoDTO;
import com.jcmj.domain.enus.Prioridade;
import com.jcmj.domain.enus.Status;
import com.jcmj.repository.ChamadoRepository;
import com.jcmj.service.exception.ObjectnotFoundException;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = chamadoRepository.findById(id);
		return obj.orElseThrow(()-> new ObjectnotFoundException("Chamado não encontrado!" + id));
	}

	public List<Chamado> findAll() {		
		return chamadoRepository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO objDto) {
		
		return chamadoRepository.save(newChamado(objDto));
	}
	
	public Chamado newChamado(ChamadoDTO obj) {
		Cliente c = clienteService.findById(obj.getCliente());
		Tecnico t = tecnicoService.findById(obj.getTecnico());
		
		Chamado chamado = new Chamado();
		if(obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
		chamado.setCliente(c);
		chamado.setTecnico(t);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacao(obj.getObservacao());
		
		return chamado;
		
	}

}
