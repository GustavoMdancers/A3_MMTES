package br.mmtes.ticketApi.dto;

import java.util.List;

public class BulkImportDTO {

    private List<String> designacoes;

    public List<String> getDesignacoes() { return designacoes; }
    public void setDesignacoes(List<String> designacoes) { this.designacoes = designacoes; }
}
