package com.example.bankApp.domain.enums;

public enum TipoOperazione {

    TRANSAZIONE("transazione"),
    VERSAMENTO("versamento"),
    PRELIEVO("prelievo");

    private String nomeOperazione;

    TipoOperazione(String nomeOperazione) {
        this.nomeOperazione = nomeOperazione;
    }

    public String getNomeOperazione() {
        return nomeOperazione;
    }
}
