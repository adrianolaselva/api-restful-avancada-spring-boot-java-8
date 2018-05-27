package com.laselva.pontointeligente.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laselva.pontointeligente.api.dtos.EmpresaDto;
import com.laselva.pontointeligente.api.entities.Empresa;
import com.laselva.pontointeligente.api.response.Response;
import com.laselva.pontointeligente.api.services.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);
	
	@Autowired
	private EmpresaService empresaService;
	
	public EmpresaController() {
		
	}
	
	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
		log.info("Buscando empresas por cnpj: {} ", cnpj);
		Response<EmpresaDto> response = new Response<>();
		
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);
		
		if(!empresa.isPresent()) {
			log.info("Empresa não encontrada para o cnpj: {}", cnpj);
			response.getErrors().add("Empresa não encontrada para o cnpj: " + cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * @param empresa
	 * @return
	 */
	private EmpresaDto converterEmpresaDto(Empresa empresa) {
		EmpresaDto empresaDto = new EmpresaDto();
		empresaDto.setCnpj(empresa.getCnpj());
		empresaDto.setId(empresa.getId());
		empresaDto.setRazaoSocial(empresa.getRazaoSocial());
		
		return empresaDto;
	}
	
}
