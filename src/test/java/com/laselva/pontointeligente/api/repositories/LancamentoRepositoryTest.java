package com.laselva.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.laselva.pontointeligente.api.entities.Empresa;
import com.laselva.pontointeligente.api.entities.Funcionario;
import com.laselva.pontointeligente.api.entities.Lancamento;
import com.laselva.pontointeligente.api.enums.PerfilEnum;
import com.laselva.pontointeligente.api.enums.TipoEnum;
import com.laselva.pontointeligente.api.utils.PasswordUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private Long funcionarioId;
	
	@Before
	public void setUp() {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
		this.funcionarioId = funcionario.getId();
		
		this.lancamentoRepository.save(obterDadosLancamento(funcionario));
		this.lancamentoRepository.save(obterDadosLancamento(funcionario));
	}
	
	@After
	public final void tearDown() {
		this.lancamentoRepository.deleteAll();
	}
	
	@Test
	public void testBuscarLancamentoPorFuncionario() {
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(this.funcionarioId);
		
		assertEquals(2, lancamentos.size());
	}
	
	@Test
	public void testBuscarLancamentoPorFuncionarioPaginado() {
		Pageable pageable = new PageRequest(0, 10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(this.funcionarioId, pageable);
		
		assertEquals(2, lancamentos.getTotalElements());
	}
	
	public Lancamento obterDadosLancamento(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(Calendar.getInstance());
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setDescricao("teste");
		lancamento.setFuncionario(funcionario);
		return lancamento;
	}
	
	public Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Fulando de tal");
		funcionario.setCpf("36162266869");
		funcionario.setEmail("adrianolaselva@gmail.com");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setEmpresa(empresa);
		funcionario.setLancamentos(new ArrayList<Lancamento>());
		return funcionario;
	}
	
	public Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("99.999.999/0001-99");
		empresa.setRazaoSocial("Nome da empresa");
		return empresa;
	}
	
}
