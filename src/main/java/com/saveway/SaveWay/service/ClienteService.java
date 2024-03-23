package com.saveway.SaveWay.service;

import com.saveway.SaveWay.exception.ClienteNotFoundException;
import com.saveway.SaveWay.exception.SaldoInsuficienteException;
import com.saveway.SaveWay.model.Cliente;
import com.saveway.SaveWay.model.Empresa;
import com.saveway.SaveWay.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente obterClientePorCpf(String cpf) {
        Optional<Cliente> ClienteOptional = clienteRepository.findByCpf(cpf);
        return ClienteOptional.orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrada com o CNPJ: "));
    }

    @Autowired
    public ClienteService(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }


    public void realizarDeposito(Empresa empresa, Cliente cliente, double valorDeposito) throws SaldoInsuficienteException {
        // Garantir que o valor do depósito seja positivo
        if (valorDeposito <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo");
        }

        // Realizar um depósito para um cliente em uma empresa
        empresaService.realizarDeposito(empresa, valorDeposito);
    }

    public void realizarSaque(Empresa empresa, Cliente cliente, double valorSaque) throws SaldoInsuficienteException {

        //Garantir que o valor do saque seja positivo
        if (valorSaque <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo");
        }

        // Realizar um saque para um cliente em uma empresa
        empresaService.realizarSaque(empresa, valorSaque);
    }
}
