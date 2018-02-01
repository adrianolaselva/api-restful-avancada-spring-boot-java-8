package com.laselva.pontointeligente.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.laselva.pontointeligente.api.entities.Empresa;

/**
 * 
 * @author adriano
 *
 */
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	
	@Transactional(readOnly = true)
	Empresa findByCnpj(String cnpj);
	
}
