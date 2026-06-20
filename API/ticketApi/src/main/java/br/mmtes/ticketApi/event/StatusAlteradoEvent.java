package br.mmtes.ticketApi.event;

import br.mmtes.ticketApi.model.SeguimentoTicket;
import br.mmtes.ticketApi.model.StatusTicket;
import org.springframework.context.ApplicationEvent;

public class StatusAlteradoEvent extends ApplicationEvent {

    private final SeguimentoTicket ticket;
    private final StatusTicket statusAnterior;
    private final StatusTicket novoStatus;

    public StatusAlteradoEvent(Object source, SeguimentoTicket ticket,
                                StatusTicket statusAnterior, StatusTicket novoStatus) {
        super(source);
        this.ticket = ticket;
        this.statusAnterior = statusAnterior;
        this.novoStatus = novoStatus;
    }

    public SeguimentoTicket getTicket() {
        return ticket;
    }

    public StatusTicket getStatusAnterior() {
        return statusAnterior;
    }

    public StatusTicket getNovoStatus() {
        return novoStatus;
    }
}
