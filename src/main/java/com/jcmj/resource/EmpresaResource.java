package com.jcmj.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcmj.domain.Empresa;
import com.jcmj.service.EmpresaService;

@RestController
@RequestMapping(value = "/empresas")
public class EmpresaResource {
	
	@Autowired
	private EmpresaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Empresa> findById(@PathVariable Integer id){
		Empresa t =  service.findById(id);
		return ResponseEntity.ok().body(t);
		
	}
	@GetMapping
	public ResponseEntity<List<Empresa>> findAll(){
		
		List<Empresa> list = service.findAll();		
		return ResponseEntity.ok().body(list);
	}
	/*
	 * @PreAuthorize("hasAnyRole('ADMIN')")
	 * 
	 * @PostMapping public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody
	 * TecnicoDTO objDto){ Tecnico newObj = tecnicoService.create(objDto); URI uri =
	 * ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand
	 * (newObj.getId()).toUri(); return ResponseEntity.created(uri).build(); }
	 * 
	 * @PreAuthorize("hasAnyRole('ADMIN')")
	 * 
	 * @PutMapping(value = "/{id}") public ResponseEntity<TecnicoDTO>
	 * update(@PathVariable Integer id,@Valid @RequestBody TecnicoDTO objDto){
	 * 
	 * Tecnico oldObj = tecnicoService.update(id, objDto); return
	 * ResponseEntity.ok().body(new TecnicoDTO(oldObj));
	 * 
	 * }
	 * 
	 * @PreAuthorize("hasAnyRole('ADMIN')")
	 * 
	 * @DeleteMapping(value = "/{id}") public ResponseEntity<TecnicoDTO>
	 * delete(@PathVariable Integer id){ tecnicoService.delete(id);
	 * 
	 * return ResponseEntity.noContent().build(); }
	 */
	
	
	

}
