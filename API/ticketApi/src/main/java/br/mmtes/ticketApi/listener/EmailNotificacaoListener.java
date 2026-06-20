package br.mmtes.ticketApi.listener;

import br.mmtes.ticketApi.event.StatusAlteradoEvent;
import br.mmtes.ticketApi.model.SeguimentoTicket;
import br.mmtes.ticketApi.model.StatusTicket;
import br.mmtes.ticketApi.model.TipoNotificacao;
import br.mmtes.ticketApi.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailNotificacaoListener {

    private static final int DIAS_ALERTA_ANTECIPADO = 10;

    private final EmailService emailService;

    public EmailNotificacaoListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void onStatusAlterado(StatusAlteradoEvent event) {
        StatusTicket novoStatus = event.getNovoStatus();
        SeguimentoTicket ticket = event.getTicket();

        if (novoStatus == StatusTicket.AGUARDANDO_EXECUÇÃO) {
            if (dentroDoAlertaAntecipado(ticket)) {
                emailService.notificarClientes(ticket, TipoNotificacao.ABERTURA);
            }
        } else if (novoStatus == StatusTicket.EM_EXECUÇÃO) {
            emailService.notificarClientes(ticket, TipoNotificacao.INICIO_EXECUCAO);
        } else if (novoStatus == StatusTicket.FECHADO) {
            emailService.notificarClientes(ticket, TipoNotificacao.ENCERRAMENTO);
        }
    }

    private boolean dentroDoAlertaAntecipado(SeguimentoTicket ticket) {
        if (ticket.getDataInicioExecucao() == null) {
            return false;
        }
        LocalDateTime limite = LocalDateTime.now().plusDays(DIAS_ALERTA_ANTECIPADO);
        return ticket.getDataInicioExecucao().isBefore(limite);
    }
}
