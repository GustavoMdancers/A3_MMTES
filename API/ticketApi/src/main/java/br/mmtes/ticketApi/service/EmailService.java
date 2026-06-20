package br.mmtes.ticketApi.service;

import br.mmtes.ticketApi.model.Circuito;
import br.mmtes.ticketApi.model.SeguimentoTicket;
import br.mmtes.ticketApi.model.TipoNotificacao;
import br.mmtes.ticketApi.notification.NotificacaoEmail;
import br.mmtes.ticketApi.notification.NotificacaoStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailService {

    private final NotificacaoStrategy notificacaoStrategy;

    public EmailService(NotificacaoStrategy notificacaoStrategy) {
        this.notificacaoStrategy = notificacaoStrategy;
    }

    public void notificarClientes(SeguimentoTicket ticket, TipoNotificacao tipo) {
        if (ticket.getCircuitos() == null || ticket.getCircuitos().isEmpty()) {
            return;
        }

        Map<String, List<Circuito>> porEmail = ticket.getCircuitos().stream()
            .filter(c -> c.getEmailContato() != null && !c.getEmailContato().isBlank())
            .collect(Collectors.groupingBy(Circuito::getEmailContato));

        porEmail.forEach((email, circuitos) -> {
            List<String> designacoes = circuitos.stream()
                .map(Circuito::getDesignacao)
                .collect(Collectors.toList());

            NotificacaoEmail msg = NotificacaoEmail.builder()
                .destinatario(email)
                .assunto(gerarAssunto(tipo, ticket.getTicketNumber()))
                .numeroAtividade(ticket.getTicketNumber())
                .localAtividade(ticket.getLocalAtendimento() != null
                    ? ticket.getLocalAtendimento().name() : "")
                .descricaoAtividade(ticket.getDescricaoAtividade())
                .dataInicioExecucao(ticket.getDataInicioExecucao())
                .dataTerminoExecucao(ticket.getDataTerminoExecucao())
                .circuitosAfetados(designacoes)
                .tipoNotificacao(tipo)
                .build();

            notificacaoStrategy.enviar(msg);
        });
    }

    private String gerarAssunto(TipoNotificacao tipo, Integer numero) {
        String prefixo = switch (tipo) {
            case ABERTURA -> "Atividade Programada";
            case INICIO_EXECUCAO -> "Atividade Iniciada";
            case ENCERRAMENTO -> "Atividade Encerrada";
            case REAGENDAMENTO -> "Reagendamento de Atividade";
            case ATRASO -> "Atividade em Andamento - Imprevisto";
        };
        return prefixo + " - Ticket #" + numero;
    }
}
