package com.laselva.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laselva.pontointeligente.api.entities.Empresa;
import com.laselva.pontointeligente.api.repositories.EmpresaRepository;
import com.laselva.pontointeligente.api.services.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {
	
	private static Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
		
	@Autowired
	private EmpresaRepository EmpresaRepository;
	
	@Override
	public Optional<Empresa> buscarPorCnpj(String cnpj) {
		log.info("Buscando empresa por cnpj {}", cnpj);
		return Optional.ofNullable(this.EmpresaRepository.findByCnpj(cnpj));
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		log.info("Persistindo empresa {}", empresa);
		return this.EmpresaRepository.save(empresa);
	}
	
	
	
}
