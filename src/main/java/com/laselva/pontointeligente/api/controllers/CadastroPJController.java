package com.laselva.pontointeligente.api.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laselva.pontointeligente.api.dtos.PessoaPJDto;
import com.laselva.pontointeligente.api.entities.Empresa;
import com.laselva.pontointeligente.api.entities.Funcionario;
import com.laselva.pontointeligente.api.enums.PerfilEnum;
import com.laselva.pontointeligente.api.response.Response;
import com.laselva.pontointeligente.api.services.EmpresaService;
import com.laselva.pontointeligente.api.services.FuncionarioService;
import com.laselva.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

	private static Logger log = LoggerFactory.getLogger(CadastroPJController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	public CadastroPJController() {
		
	}
	
	/**
	 * 
	 * @param cadastroPJDto
	 * @param result
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Response<PessoaPJDto>> cadastrar(@Valid @RequestBody PessoaPJDto cadastroPJDto, BindingResult result) {
		log.info("Cadastrando pj", cadastroPJDto.toString());
		Response<PessoaPJDto> response = new Response<>();
		
		this.validarDadosExistentes(cadastroPJDto, result);
		
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto, result);
		
		if(result.hasErrors()) {
			log.error("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);
		
		response.setData(this.converterCadastroPJDto(funcionario));
		
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * @param funcionario
	 * @return
	 */
	private PessoaPJDto converterCadastroPJDto(Funcionario funcionario) {
		PessoaPJDto pessoaDto = new PessoaPJDto();
		pessoaDto.setId(funcionario.getId());
		pessoaDto.setNome(funcionario.getNome());
		pessoaDto.setEmail(funcionario.getEmail());
		pessoaDto.setCpf(funcionario.getCpf());
		pessoaDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		pessoaDto.setCnpj(funcionario.getEmpresa().getCnpj());
		
		
		return pessoaDto;
	}
	
	/**
	 * 
	 * @param cadastroPJDto
	 * @param result
	 * @return
	 */
	private Funcionario converterDtoParaFuncionario(PessoaPJDto cadastroPJDto, BindingResult result) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroPJDto.getNome());
		funcionario.setEmail(cadastroPJDto.getEmail());
		funcionario.setCpf(cadastroPJDto.getCpf());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));
		
		return funcionario;
	}
	
	/**
	 * 
	 * @param cadastroPJDto
	 * @return
	 */
	private Empresa converterDtoParaEmpresa(PessoaPJDto cadastroPJDto) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPJDto.getCnpj());
		empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
		
		return empresa;
	}
	
	private void validarDadosExistentes(PessoaPJDto cadastroPJDto, BindingResult result) {
		this.empresaService.buscarPorCnpj(cadastroPJDto.getCnpj())
			.ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente")));
		
		this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf())
			.ifPresent(emp -> result.addError(new ObjectError("funcionario", "CPF já existente")));
		
		this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail())
			.ifPresent(emp -> result.addError(new ObjectError("funcionario", "Email já existente")));
		
	}
	
}
