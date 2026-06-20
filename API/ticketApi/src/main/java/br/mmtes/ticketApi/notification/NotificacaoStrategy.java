package br.mmtes.ticketApi.notification;

public interface NotificacaoStrategy {
    void enviar(NotificacaoEmail notificacao);
}
