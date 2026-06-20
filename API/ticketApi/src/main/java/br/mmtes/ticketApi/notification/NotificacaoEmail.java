package br.mmtes.ticketApi.notification;

import br.mmtes.ticketApi.model.TipoNotificacao;

import java.time.LocalDateTime;
import java.util.List;

public class NotificacaoEmail {

    private String destinatario;
    private String assunto;
    private Integer numeroAtividade;
    private String localAtividade;
    private String descricaoAtividade;
    private LocalDateTime dataInicioExecucao;
    private LocalDateTime dataTerminoExecucao;
    private List<String> circuitosAfetados;
    private TipoNotificacao tipoNotificacao;

    private NotificacaoEmail() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final NotificacaoEmail notificacao = new NotificacaoEmail();

        public Builder destinatario(String destinatario) {
            notificacao.destinatario = destinatario;
            return this;
        }

        public Builder assunto(String assunto) {
            notificacao.assunto = assunto;
            return this;
        }

        public Builder numeroAtividade(Integer numeroAtividade) {
            notificacao.numeroAtividade = numeroAtividade;
            return this;
        }

        public Builder localAtividade(String localAtividade) {
            notificacao.localAtividade = localAtividade;
            return this;
        }

        public Builder descricaoAtividade(String descricaoAtividade) {
            notificacao.descricaoAtividade = descricaoAtividade;
            return this;
        }

        public Builder dataInicioExecucao(LocalDateTime data) {
            notificacao.dataInicioExecucao = data;
            return this;
        }

        public Builder dataTerminoExecucao(LocalDateTime data) {
            notificacao.dataTerminoExecucao = data;
            return this;
        }

        public Builder circuitosAfetados(List<String> circuitos) {
            notificacao.circuitosAfetados = circuitos;
            return this;
        }

        public Builder tipoNotificacao(TipoNotificacao tipo) {
            notificacao.tipoNotificacao = tipo;
            return this;
        }

        public NotificacaoEmail build() {
            return notificacao;
        }
    }

    public String getDestinatario() { return destinatario; }
    public String getAssunto() { return assunto; }
    public Integer getNumeroAtividade() { return numeroAtividade; }
    public String getLocalAtividade() { return localAtividade; }
    public String getDescricaoAtividade() { return descricaoAtividade; }
    public LocalDateTime getDataInicioExecucao() { return dataInicioExecucao; }
    public LocalDateTime getDataTerminoExecucao() { return dataTerminoExecucao; }
    public List<String> getCircuitosAfetados() { return circuitosAfetados; }
    public TipoNotificacao getTipoNotificacao() { return tipoNotificacao; }
}
