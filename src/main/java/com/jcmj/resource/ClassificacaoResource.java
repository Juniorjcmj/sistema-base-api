package com.jcmj.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jcmj.domain.ClassificacaoDespesa;
import com.jcmj.service.ClassificacaoService;

@RestController
@RequestMapping(value = "/classificacao")
public class ClassificacaoResource {
	
	@Autowired
	private ClassificacaoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClassificacaoDespesa> findById(@PathVariable Integer id){
		ClassificacaoDespesa result =  service.findById(id);
		return ResponseEntity.ok().body(result);
		
	}
	
	@GetMapping
	public ResponseEntity<List<ClassificacaoDespesa>> findAll(){
		
		List<ClassificacaoDespesa> result = service.findAll();
		//List<ClienteDTO> result = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(result);
	}
	@PostMapping
	public ResponseEntity<ClassificacaoDespesa> create(@Valid @RequestBody ClassificacaoDespesa objDto){
		ClassificacaoDespesa newObj = service.create(objDto);
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClassificacaoDespesa> update(@PathVariable Integer id,
			                                           @Valid @RequestBody ClassificacaoDespesa objDto){
		
		ClassificacaoDespesa oldObj = service.update(id, objDto);
		return ResponseEntity.ok().body(oldObj);
		
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClassificacaoDespesa> delete(@PathVariable Integer id){
		service.delete(id);		
		return ResponseEntity.noContent().build();
	}
	
	

}
