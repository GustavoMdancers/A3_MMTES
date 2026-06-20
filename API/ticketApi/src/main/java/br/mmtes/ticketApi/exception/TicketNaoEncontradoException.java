package br.mmtes.ticketApi.exception;

public class TicketNaoEncontradoException extends RuntimeException {

    public TicketNaoEncontradoException(Long id) {
        super("Ticket não encontrado: " + id);
    }
}
