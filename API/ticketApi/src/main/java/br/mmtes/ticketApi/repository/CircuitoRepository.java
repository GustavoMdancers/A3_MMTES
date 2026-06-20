package br.mmtes.ticketApi.repository;

import br.mmtes.ticketApi.model.Circuito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CircuitoRepository extends JpaRepository<Circuito, Long> {

    Optional<Circuito> findByDesignacao(String designacao);
}
