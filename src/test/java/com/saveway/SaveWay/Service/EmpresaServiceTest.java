package com.saveway.SaveWay.Service;

import com.saveway.SaveWay.exception.SaldoInsuficienteException;
import com.saveway.SaveWay.model.Empresa;
import com.saveway.SaveWay.model.TipoDeTaxa;
import com.saveway.SaveWay.service.EmpresaService;
import com.saveway.SaveWay.service.TaxaAdministrativaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmpresaServiceTest {
    private EmpresaService empresaService;
    private TaxaAdministrativaService taxaServiceMock;

    @BeforeEach
    public void setUp() {
        taxaServiceMock = mock(TaxaAdministrativaService.class);
        empresaService = new EmpresaService(taxaServiceMock);
    }

    @Test
    public void testRealizarDeposito() throws SaldoInsuficienteException {
        Empresa empresa = new Empresa("",0);
        empresa.setSaldo(100.0);

        // Configurando o comportamento do mock para retornar uma taxa de 0
        when(taxaServiceMock.calcularTaxa(TipoDeTaxa.PORCENTAGEM, 50.0)).thenReturn(0.0);

        empresaService.realizarDeposito(empresa, 50.0);

        // Verificando se o saldo da empresa foi incrementado corretamente
        assertEquals(150.0, empresa.getSaldo(), 0.01);

        // Verificando se o método do mock foi invocado corretamente
        verify(taxaServiceMock).calcularTaxa(TipoDeTaxa.PORCENTAGEM, 50.0);
    }

    @Test
    public void testRealizarSaqueComSaldoSuficiente() throws SaldoInsuficienteException {
        Empresa empresa = new Empresa("",0);
        empresa.setSaldo(100.0);

        // Configurando o comportamento do mock para retornar uma taxa de 0
        when(taxaServiceMock.calcularTaxa(TipoDeTaxa.TAXA_FIXA, 50.0)).thenReturn(0.0);

        empresaService.realizarSaque(empresa, 50.0);

        // Verificando se o saldo da empresa foi reduzido corretamente
        assertEquals(50.0, empresa.getSaldo(), 0.01);

        // Verificando se o método do mock foi invocado corretamente
        verify(taxaServiceMock).calcularTaxa(TipoDeTaxa.TAXA_FIXA, 50.0);
    }

    @Test
    public void testRealizarSaqueComSaldoInsuficiente() {
        String cnpj="";
        double valor = 0;

        Empresa empresa = new Empresa(cnpj,valor);
        empresa.setSaldo(50.0);

        // Tentando sacar um valor maior do que o saldo disponível
        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class, () -> {
            empresaService.realizarSaque(empresa, 100.0);
        });

        // Verificando se a exceção foi lançada corretamente
        assertEquals("Saldo insuficiente para realizar o saque", exception.getMessage());
    }
}
