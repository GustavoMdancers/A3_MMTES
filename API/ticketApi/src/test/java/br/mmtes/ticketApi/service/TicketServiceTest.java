package br.mmtes.ticketApi.service;

import br.mmtes.ticketApi.dto.TicketRequestDTO;
import br.mmtes.ticketApi.dto.TicketResponseDTO;
import br.mmtes.ticketApi.event.StatusAlteradoEvent;
import br.mmtes.ticketApi.exception.TicketNaoEncontradoException;
import br.mmtes.ticketApi.model.SeguimentoTicket;
import br.mmtes.ticketApi.model.StatusTicket;
import br.mmtes.ticketApi.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TicketService ticketService;

    private TicketRequestDTO dto;

    @BeforeEach
    void setup() {
        dto = new TicketRequestDTO();
        dto.setSolicitante("João");
        dto.setTelefoneSolicitante("+5531999999999");
        dto.setSupervisor("Maria");
        dto.setTelefoneSupervisor("+5531888888888");
    }

    @Test
    void deveCriarTicketComStatusAberto() {
        SeguimentoTicket salvo = new SeguimentoTicket();
        salvo.setId(1L);
        salvo.setStatus(StatusTicket.ABERTO);

        when(ticketRepository.save(any(SeguimentoTicket.class))).thenReturn(salvo);

        TicketResponseDTO resultado = ticketService.criar(dto);

        assertEquals(StatusTicket.ABERTO, resultado.getStatus());
        verify(ticketRepository).save(any(SeguimentoTicket.class));
    }

    @Test
    void deveAlterarStatusComTransicaoValida() {
        SeguimentoTicket ticket = new SeguimentoTicket();
        ticket.setId(1L);
        ticket.setStatus(StatusTicket.ABERTO);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any())).thenReturn(ticket);

        ticketService.alterarStatus(1L, StatusTicket.AGUARDANDO_ANALISE);

        assertEquals(StatusTicket.AGUARDANDO_ANALISE, ticket.getStatus());
        verify(eventPublisher).publishEvent(any(StatusAlteradoEvent.class));
    }

    @Test
    void deveLancarExcecaoEmTransicaoInvalida() {
        SeguimentoTicket ticket = new SeguimentoTicket();
        ticket.setId(1L);
        ticket.setStatus(StatusTicket.ABERTO);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        assertThrows(IllegalStateException.class,
            () -> ticketService.alterarStatus(1L, StatusTicket.FECHADO));
    }

    @Test
    void deveLancarExcecaoQuandoTicketNaoEncontrado() {
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TicketNaoEncontradoException.class,
            () -> ticketService.buscarPorId(99L));
    }

    @Test
    void devePublicarEventoAoAlterarStatus() {
        SeguimentoTicket ticket = new SeguimentoTicket();
        ticket.setId(1L);
        ticket.setStatus(StatusTicket.ABERTO);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any())).thenReturn(ticket);

        ticketService.alterarStatus(1L, StatusTicket.AGUARDANDO_ANALISE);

        ArgumentCaptor<StatusAlteradoEvent> captor = ArgumentCaptor.forClass(StatusAlteradoEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());

        StatusAlteradoEvent evento = captor.getValue();
        assertEquals(StatusTicket.ABERTO, evento.getStatusAnterior());
        assertEquals(StatusTicket.AGUARDANDO_ANALISE, evento.getNovoStatus());
    }
}
