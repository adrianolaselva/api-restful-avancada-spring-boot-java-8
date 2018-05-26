package com.laselva.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.laselva.pontointeligente.api.entities.Lancamento;
import com.laselva.pontointeligente.api.repositories.LancamentoRepository;
import com.laselva.pontointeligente.api.services.LancamentoService;

@Service
public class LancamentoServiceImp implements LancamentoService {

	private static Logger log = LoggerFactory.getLogger(LancamentoServiceImp.class);
	
	@Autowired
	private LancamentoRepository LancamentoRepository;
	
	@Override
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		log.info("busca lancamentos por funcionarioId {}", funcionarioId);
		return this.LancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Override
	public Optional<Lancamento> buscarPorId(Long id) {
		log.info("buscar por id {}", id);
		return Optional.ofNullable(this.LancamentoRepository.findOne(id));
	}

	@Override
	public Lancamento persistir(Lancamento lancamento) {
		log.info("persistir lancamento {}", lancamento);
		return this.LancamentoRepository.save(lancamento);
	}

	@Override
	public void remover(Long id) {
		log.info("remove por id {}", id);
		this.LancamentoRepository.delete(id);
	}
	
	
	
}
