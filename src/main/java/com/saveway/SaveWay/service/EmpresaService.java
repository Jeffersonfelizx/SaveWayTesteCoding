package com.saveway.SaveWay.service;

import com.saveway.SaveWay.exception.EmpresaNotFoundException;
import com.saveway.SaveWay.exception.SaldoInsuficienteException;
import com.saveway.SaveWay.model.Empresa;
import com.saveway.SaveWay.model.TipoDeTaxa;
import com.saveway.SaveWay.model.Transacao;
import com.saveway.SaveWay.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private TaxaAdministrativaService taxaService;

    @Autowired
    private EmpresaRepository empresaRepository;

    private RestTemplate restTemplate;
    public EmpresaService(TaxaAdministrativaService taxaService) {
        this.taxaService = taxaService;
        this.restTemplate = restTemplate;
    }
    public Empresa obterEmpresaPorCnpj(String cnpj) {
        Optional<Empresa> empresaOptional = empresaRepository.findByCnpj(cnpj);
        return empresaOptional.orElseThrow(() -> new EmpresaNotFoundException("Empresa não encontrada com o CNPJ: " + cnpj));
    }

    public void realizarDeposito(Empresa empresa, double valorDeposito) throws SaldoInsuficienteException {

        //Garantir que o valor de deposito seja positivo
        if (valorDeposito <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo");
        }

        // Operação para remover a taxa de serviço
        double taxa = taxaService.calcularTaxa(TipoDeTaxa.PORCENTAGEM, valorDeposito);
        double valorLiquido = valorDeposito - taxa;

        empresa.setSaldo(empresa.getSaldo() + valorLiquido);

        //
    }

    public void realizarSaque(Empresa empresa, double valorSaque) throws SaldoInsuficienteException {
        // Validação adicional: garantir que o valor do saque seja positivo
        if (valorSaque <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo");
        }

        // Verificar se há saldo suficiente na empresa
        if (empresa.getSaldo() < valorSaque) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque");
        }

        // realizar um saque na empresa, aplicando a taxa de administração.
        double taxa = taxaService.calcularTaxa(TipoDeTaxa.TAXA_FIXA, valorSaque);
        double valorLiquido = valorSaque - taxa;

        empresa.setSaldo(empresa.getSaldo() - valorLiquido);
    }
    public void enviarCallback(Empresa empresa, Transacao transacao) {
        // callback para o webhook
        String webhookURL = "https://webhook.site/0c30a1fa-ec2c-4e78-87fd-3de4b8620ada";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transacao> request = new HttpEntity<>(transacao, headers);
        restTemplate.postForObject(webhookURL, request, String.class);

        try {
            restTemplate.postForObject(webhookURL, request, String.class);
            System.out.println("Callback enviado para a empresa " + empresa.getCnpj() + ": " + transacao.toString());
        } catch (Exception e) {
            System.err.println("Erro ao enviar callback para o webhook: " + e.getMessage());
        }
    }
}
