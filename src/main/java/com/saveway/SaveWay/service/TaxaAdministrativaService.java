package com.saveway.SaveWay.service;

import com.saveway.SaveWay.model.TipoDeTaxa;
import org.springframework.stereotype.Service;

@Service
public class TaxaAdministrativaService {
    public double calcularTaxa(TipoDeTaxa tipoTaxa, double valorTransacao) {
        if (tipoTaxa == TipoDeTaxa.TAXA_FIXA) {
            return 2.0; //Taxa Fixa
        } else {
            return valorTransacao * 0.05; // Taxa Porcentagem
        }
    }
}
