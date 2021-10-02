package com.jcmj.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcmj.domain.Chamado;
import com.jcmj.domain.dto.ChamdoDTO;
import com.jcmj.service.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
	
	@Autowired
	private ChamadoService chamadoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamdoDTO> findById(@PathVariable Integer id){
		Chamado c =  chamadoService.findById(id);
		return ResponseEntity.ok().body(new ChamdoDTO(c));
		
	}

}
