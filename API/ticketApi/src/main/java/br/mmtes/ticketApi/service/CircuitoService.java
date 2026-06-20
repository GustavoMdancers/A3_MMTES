package br.mmtes.ticketApi.service;

import br.mmtes.ticketApi.dto.CircuitoDTO;
import br.mmtes.ticketApi.exception.TicketNaoEncontradoException;
import br.mmtes.ticketApi.model.Circuito;
import br.mmtes.ticketApi.model.SeguimentoTicket;
import br.mmtes.ticketApi.repository.CircuitoRepository;
import br.mmtes.ticketApi.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CircuitoService {

    private final CircuitoRepository circuitoRepository;
    private final TicketRepository ticketRepository;

    public CircuitoService(CircuitoRepository circuitoRepository, TicketRepository ticketRepository) {
        this.circuitoRepository = circuitoRepository;
        this.ticketRepository = ticketRepository;
    }

    public void associar(Long ticketId, CircuitoDTO dto) {
        SeguimentoTicket ticket = buscarTicket(ticketId);

        Circuito circuito = circuitoRepository.findByDesignacao(dto.getDesignacao())
            .orElseGet(() -> {
                Circuito novo = new Circuito();
                novo.setDesignacao(dto.getDesignacao());
                novo.setDescricao(dto.getDescricao());
                novo.setEmailContato(dto.getEmailContato());
                novo.setTelefoneContato(dto.getTelefoneContato());
                return circuitoRepository.save(novo);
            });

        if (!ticket.getCircuitos().contains(circuito)) {
            ticket.getCircuitos().add(circuito);
            ticketRepository.save(ticket);
        }
    }

    public void importarEmMassa(Long ticketId, List<String> designacoes) {
        SeguimentoTicket ticket = buscarTicket(ticketId);

        for (String designacao : designacoes) {
            circuitoRepository.findByDesignacao(designacao).ifPresent(circuito -> {
                if (!ticket.getCircuitos().contains(circuito)) {
                    ticket.getCircuitos().add(circuito);
                }
            });
        }

        ticketRepository.save(ticket);
    }

    public void desassociar(Long ticketId, Long circuitoId) {
        SeguimentoTicket ticket = buscarTicket(ticketId);
        ticket.getCircuitos().removeIf(c -> c.getId().equals(circuitoId));
        ticketRepository.save(ticket);
    }

    public List<Circuito> listarPorTicket(Long ticketId) {
        return buscarTicket(ticketId).getCircuitos();
    }

    private SeguimentoTicket buscarTicket(Long ticketId) {
        return ticketRepository.findById(ticketId)
            .orElseThrow(() -> new TicketNaoEncontradoException(ticketId));
    }
}
