package br.mmtes.ticketApi.service;

import br.mmtes.ticketApi.model.Circuito;
import br.mmtes.ticketApi.model.SeguimentoTicket;
import br.mmtes.ticketApi.model.TipoNotificacao;
import br.mmtes.ticketApi.notification.NotificacaoEmail;
import br.mmtes.ticketApi.notification.NotificacaoStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private NotificacaoStrategy notificacaoStrategy;

    @InjectMocks
    private EmailService emailService;

    @Test
    void deveEnviarUmEmailPorCliente() {
        Circuito c1 = criarCircuito("C-001", "cliente@empresa.com");
        Circuito c2 = criarCircuito("C-002", "cliente@empresa.com");
        Circuito c3 = criarCircuito("C-003", "outro@empresa.com");

        SeguimentoTicket ticket = new SeguimentoTicket();
        ticket.setTicketNumber(1);
        ticket.setDescricaoAtividade("Manutenção preventiva");
        ticket.setCircuitos(List.of(c1, c2, c3));

        emailService.notificarClientes(ticket, TipoNotificacao.INICIO_EXECUCAO);

        verify(notificacaoStrategy, times(2)).enviar(any(NotificacaoEmail.class));
    }

    @Test
    void deveAgruparCircuitosPorEmail() {
        Circuito c1 = criarCircuito("C-001", "cliente@empresa.com");
        Circuito c2 = criarCircuito("C-002", "cliente@empresa.com");

        SeguimentoTicket ticket = new SeguimentoTicket();
        ticket.setTicketNumber(1);
        ticket.setDescricaoAtividade("Remanejamento");
        ticket.setCircuitos(List.of(c1, c2));

        emailService.notificarClientes(ticket, TipoNotificacao.ABERTURA);

        ArgumentCaptor<NotificacaoEmail> captor = ArgumentCaptor.forClass(NotificacaoEmail.class);
        verify(notificacaoStrategy).enviar(captor.capture());

        NotificacaoEmail msg = captor.getValue();
        assertEquals(2, msg.getCircuitosAfetados().size());
        assertTrue(msg.getCircuitosAfetados().contains("C-001"));
        assertTrue(msg.getCircuitosAfetados().contains("C-002"));
    }

    @Test
    void deveIgnorarCircuitosSemEmail() {
        Circuito semEmail = criarCircuito("C-010", null);
        Circuito comEmail = criarCircuito("C-011", "contato@empresa.com");

        SeguimentoTicket ticket = new SeguimentoTicket();
        ticket.setTicketNumber(2);
        ticket.setCircuitos(List.of(semEmail, comEmail));

        emailService.notificarClientes(ticket, TipoNotificacao.ENCERRAMENTO);

        verify(notificacaoStrategy, times(1)).enviar(any(NotificacaoEmail.class));
    }

    @Test
    void naoDeveEnviarSeNaoHouverCircuitos() {
        SeguimentoTicket ticket = new SeguimentoTicket();
        ticket.setTicketNumber(3);
        ticket.setCircuitos(List.of());

        emailService.notificarClientes(ticket, TipoNotificacao.ABERTURA);

        verifyNoInteractions(notificacaoStrategy);
    }

    private Circuito criarCircuito(String designacao, String email) {
        Circuito c = new Circuito();
        c.setDesignacao(designacao);
        c.setEmailContato(email);
        return c;
    }
}
