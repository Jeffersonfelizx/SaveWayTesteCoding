package com.saveway.SaveWay;

import com.saveway.SaveWay.exception.SaldoInsuficienteException;
import com.saveway.SaveWay.model.Cliente;
import com.saveway.SaveWay.model.Empresa;
import com.saveway.SaveWay.service.ClienteService;
import com.saveway.SaveWay.service.EmpresaService;
import com.saveway.SaveWay.service.TaxaAdministrativaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SaveWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaveWayApplication.class, args);

		TaxaAdministrativaService taxaService = new TaxaAdministrativaService();
		EmpresaService empresaService = new EmpresaService(taxaService);
		ClienteService clienteService = new ClienteService(empresaService);

		Empresa empresa = new Empresa("",0);
		empresa.setCnpj("12345678901234");
		empresa.setSaldo(1000.0);


		Cliente cliente = new Cliente();
		cliente.setCpf("98765432109");

		try {
			clienteService.realizarDeposito(empresa, cliente, 1500.0);
			clienteService.realizarSaque(empresa, cliente, 200.0);
		} catch (SaldoInsuficienteException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

}
