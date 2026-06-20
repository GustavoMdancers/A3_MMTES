package br.mmtes.ticketApi.repository;

import br.mmtes.ticketApi.model.SeguimentoTicket;
import br.mmtes.ticketApi.model.StatusTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<SeguimentoTicket, Long> {

    List<SeguimentoTicket> findByStatus(StatusTicket status);

    List<SeguimentoTicket> findByDataInicioExecucaoBetween(LocalDateTime inicio, LocalDateTime fim);
}
