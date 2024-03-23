package com.saveway.SaveWay.controller;

import com.saveway.SaveWay.exception.SaldoInsuficienteException;
import com.saveway.SaveWay.model.Cliente;
import com.saveway.SaveWay.model.Empresa;
import com.saveway.SaveWay.model.Transacao;
import com.saveway.SaveWay.service.ClienteService;
import com.saveway.SaveWay.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private EmpresaService empresaService;


    @PostMapping("/empresa/{empresacnpj}/cliente/{clientecpf}/deposito")
    public void realizarDeposito(@PathVariable String empresaCnpj, @PathVariable String clienteCpf, @Valid @RequestBody double valorDeposito) throws SaldoInsuficienteException {
        Empresa empresa = empresaService.obterEmpresaPorCnpj(empresaCnpj);
        Cliente cliente = clienteService.obterClientePorCpf(clienteCpf);
        clienteService.realizarDeposito(empresa, cliente, valorDeposito);
    }

    @PostMapping("/empresa/{empresacnpj}/cliente/{clientecpf}/saque")
    public void realizarSaque(@PathVariable String empresaCnpj, @PathVariable String clienteCpf, @RequestBody double valorSaque) throws SaldoInsuficienteException {
        Empresa empresa = empresaService.obterEmpresaPorCnpj(empresaCnpj);
        Cliente cliente = clienteService.obterClientePorCpf(clienteCpf);
        clienteService.realizarSaque(empresa, cliente, valorSaque);
    }

    @PostMapping("/callback/empresa/{empresaCnpj}")
    public void callback(@PathVariable String empresaCnpj, @Valid @RequestBody Transacao transacao) {
        Empresa empresa = empresaService.obterEmpresaPorCnpj(empresaCnpj);
        empresaService.enviarCallback(empresa, transacao);
    }
}
