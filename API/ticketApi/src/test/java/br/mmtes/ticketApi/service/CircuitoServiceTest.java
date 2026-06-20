package br.mmtes.ticketApi.service;

import br.mmtes.ticketApi.dto.CircuitoDTO;
import br.mmtes.ticketApi.exception.TicketNaoEncontradoException;
import br.mmtes.ticketApi.model.Circuito;
import br.mmtes.ticketApi.model.SeguimentoTicket;
import br.mmtes.ticketApi.repository.CircuitoRepository;
import br.mmtes.ticketApi.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CircuitoServiceTest {

    @Mock
    private CircuitoRepository circuitoRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private CircuitoService circuitoService;

    @Test
    void deveAssociarCircuitoExistente() {
        SeguimentoTicket ticket = ticketComId(1L);
        Circuito circuito = circuitoComDesignacao("C-001");

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(circuitoRepository.findByDesignacao("C-001")).thenReturn(Optional.of(circuito));

        CircuitoDTO dto = new CircuitoDTO();
        dto.setDesignacao("C-001");

        circuitoService.associar(1L, dto);

        assertEquals(1, ticket.getCircuitos().size());
        verify(circuitoRepository, never()).save(any());
    }

    @Test
    void deveCriarCircuitoSeNaoExistir() {
        SeguimentoTicket ticket = ticketComId(1L);
        Circuito novo = circuitoComDesignacao("C-999");

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(circuitoRepository.findByDesignacao("C-999")).thenReturn(Optional.empty());
        when(circuitoRepository.save(any(Circuito.class))).thenReturn(novo);

        CircuitoDTO dto = new CircuitoDTO();
        dto.setDesignacao("C-999");

        circuitoService.associar(1L, dto);

        verify(circuitoRepository).save(any(Circuito.class));
        assertEquals(1, ticket.getCircuitos().size());
    }

    @Test
    void deveIgnorarDesignacoesDesconhecidasNaImportacao() {
        SeguimentoTicket ticket = ticketComId(1L);
        Circuito conhecido = circuitoComDesignacao("C-001");

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(circuitoRepository.findByDesignacao("C-001")).thenReturn(Optional.of(conhecido));
        when(circuitoRepository.findByDesignacao("INEXISTENTE")).thenReturn(Optional.empty());

        circuitoService.importarEmMassa(1L, List.of("C-001", "INEXISTENTE"));

        assertEquals(1, ticket.getCircuitos().size());
    }

    @Test
    void naoDeveAssociarCircuitoDuplicado() {
        Circuito circuito = circuitoComDesignacao("C-001");
        SeguimentoTicket ticket = ticketComId(1L);
        ticket.getCircuitos().add(circuito);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(circuitoRepository.findByDesignacao("C-001")).thenReturn(Optional.of(circuito));

        circuitoService.importarEmMassa(1L, List.of("C-001"));

        assertEquals(1, ticket.getCircuitos().size());
    }

    @Test
    void deveLancarExcecaoQuandoTicketNaoEncontrado() {
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TicketNaoEncontradoException.class,
            () -> circuitoService.listarPorTicket(99L));
    }

    private SeguimentoTicket ticketComId(Long id) {
        SeguimentoTicket ticket = new SeguimentoTicket();
        ticket.setId(id);
        ticket.setCircuitos(new ArrayList<>());
        return ticket;
    }

    private Circuito circuitoComDesignacao(String designacao) {
        Circuito c = new Circuito();
        c.setDesignacao(designacao);
        return c;
    }
}
