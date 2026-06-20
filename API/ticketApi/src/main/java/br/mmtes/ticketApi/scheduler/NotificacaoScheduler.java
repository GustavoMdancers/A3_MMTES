package br.mmtes.ticketApi.scheduler;

import br.mmtes.ticketApi.model.SeguimentoTicket;
import br.mmtes.ticketApi.model.StatusTicket;
import br.mmtes.ticketApi.model.TipoNotificacao;
import br.mmtes.ticketApi.repository.TicketRepository;
import br.mmtes.ticketApi.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificacaoScheduler {

    private final TicketRepository ticketRepository;
    private final EmailService emailService;

    public NotificacaoScheduler(TicketRepository ticketRepository, EmailService emailService) {
        this.ticketRepository = ticketRepository;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 3600000)
    public void verificarAtividadesEmAtraso() {
        LocalDateTime agora = LocalDateTime.now();

        List<SeguimentoTicket> emExecucao = ticketRepository
            .findByStatus(StatusTicket.EM_EXECUÇÃO);

        for (SeguimentoTicket ticket : emExecucao) {
            if (ticket.getDataTerminoExecucao() != null
                    && ticket.getDataTerminoExecucao().plusHours(1).isBefore(agora)) {
                emailService.notificarClientes(ticket, TipoNotificacao.ATRASO);
            }
        }
    }
}
