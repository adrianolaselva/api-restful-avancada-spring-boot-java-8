package com.laselva.pontointeligente.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.laselva.pontointeligente.api.entities.Lancamento;

public interface LancamentoService {

	/**
	 * Buscar por funcionario Id
	 * 
	 * @param funcionario
	 * @param pageRequest
	 * @return
	 */
	Page<Lancamento> buscarPorFuncionarioId(Long funcionario, PageRequest pageRequest);
	
	/**
	 * Buscar por id
	 *
	 * @param id
	 * @return
	 */
	Optional<Lancamento> buscarPorId(Long id);
	
	/**
	 * Persisir lancamento
	 * 
	 * @param lancamento
	 * @return
	 */
	Lancamento persistir(Lancamento lancamento);
	
	/**
	 * Remover por id
	 * 
	 * @param id
	 */
	void remover(Long id);
	
}
