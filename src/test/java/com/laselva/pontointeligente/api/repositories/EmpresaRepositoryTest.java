package com.laselva.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.laselva.pontointeligente.api.entities.Empresa;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static String CNPJ = "82198127000121";
	
	@Before
	public void setUp() {
		Empresa empresa = new Empresa();
		empresa.setCnpj(CNPJ);
		empresa.setRazaoSocial("Empresa teste");
		this.empresaRepository.save(empresa);
	}
	
	@After
	public void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testBuscarPorCnpj() {
		Empresa empresa = this.empresaRepository.findByCnpj(this.CNPJ);
		
		assertEquals(CNPJ, empresa.getCnpj());
	}
}
