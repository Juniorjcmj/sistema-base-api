package com.jcmj;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jcmj.domain.Chamado;
import com.jcmj.domain.Cliente;
import com.jcmj.domain.Tecnico;
import com.jcmj.domain.enus.Perfil;
import com.jcmj.domain.enus.Prioridade;
import com.jcmj.domain.enus.Status;
import com.jcmj.repository.ChamadoRepository;
import com.jcmj.repository.ClienteRepository;
import com.jcmj.repository.TecnicoRepository;

@SpringBootApplication
public class SistemaBaseApplication implements CommandLineRunner {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(SistemaBaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Tecnico tec1 = new Tecnico(null, "Sergio Barbosa", "716.417.840-24", "sergio@mail.com", "sergio@2020");
		tec1.addPerfis(Perfil.ADMIN);
		
		
		Cliente cli1 = new Cliente(null, "Linux Trovald", "559.406.800-70", "linux@mail.com", "linux@2020");
		
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);
		
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

}
