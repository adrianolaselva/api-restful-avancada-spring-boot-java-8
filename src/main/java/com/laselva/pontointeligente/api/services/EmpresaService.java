package com.laselva.pontointeligente.api.services;

import java.util.Optional;

import com.laselva.pontointeligente.api.entities.Empresa;

public interface EmpresaService {
	
	/**
	 * Retorna empresa dado um CNPJ
	 * 
	 * @param cnpj
	 * @return
	 */
	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	/**
	 * Cadastr uma nova empresa na base de daodos
	 * 
	 * @param empresa
	 * @return
	 */
	Empresa persistir(Empresa empresa);
	
}
