package br.mmtes.ticketApi.service;

import br.mmtes.ticketApi.dto.TicketRequestDTO;
import br.mmtes.ticketApi.dto.TicketResponseDTO;
import br.mmtes.ticketApi.event.StatusAlteradoEvent;
import br.mmtes.ticketApi.exception.TicketNaoEncontradoException;
import br.mmtes.ticketApi.model.SeguimentoTicket;
import br.mmtes.ticketApi.model.StatusTicket;
import br.mmtes.ticketApi.repository.TicketRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private static final Map<StatusTicket, List<StatusTicket>> TRANSICOES_VALIDAS = new HashMap<>();

    static {
        TRANSICOES_VALIDAS.put(StatusTicket.ABERTO,
            List.of(StatusTicket.AGUARDANDO_ANALISE, StatusTicket.CANCELADO));
        TRANSICOES_VALIDAS.put(StatusTicket.AGUARDANDO_ANALISE,
            List.of(StatusTicket.EM_ANALISE, StatusTicket.CANCELADO));
        TRANSICOES_VALIDAS.put(StatusTicket.EM_ANALISE,
            List.of(StatusTicket.NOTIFICADO, StatusTicket.CANCELADO));
        TRANSICOES_VALIDAS.put(StatusTicket.NOTIFICADO,
            List.of(StatusTicket.AGUARDANDO_EXECUÇÃO, StatusTicket.CANCELADO));
        TRANSICOES_VALIDAS.put(StatusTicket.AGUARDANDO_EXECUÇÃO,
            List.of(StatusTicket.EM_EXECUÇÃO, StatusTicket.PARALISADO, StatusTicket.CANCELADO));
        TRANSICOES_VALIDAS.put(StatusTicket.EM_EXECUÇÃO,
            List.of(StatusTicket.FECHADO, StatusTicket.PARALISADO));
        TRANSICOES_VALIDAS.put(StatusTicket.PARALISADO,
            List.of(StatusTicket.AGUARDANDO_EXECUÇÃO, StatusTicket.CANCELADO));
        TRANSICOES_VALIDAS.put(StatusTicket.CANCELADO, List.of());
        TRANSICOES_VALIDAS.put(StatusTicket.FECHADO, List.of());
    }

    private final TicketRepository ticketRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TicketService(TicketRepository ticketRepository, ApplicationEventPublisher eventPublisher) {
        this.ticketRepository = ticketRepository;
        this.eventPublisher = eventPublisher;
    }

    public TicketResponseDTO criar(TicketRequestDTO dto) {
        SeguimentoTicket ticket = new SeguimentoTicket();
        preencherCampos(ticket, dto);
        ticket.setStatus(StatusTicket.ABERTO);
        return toDTO(ticketRepository.save(ticket));
    }

    public TicketResponseDTO buscarPorId(Long id) {
        return toDTO(buscarEntidade(id));
    }

    public List<TicketResponseDTO> listar() {
        return ticketRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public TicketResponseDTO atualizar(Long id, TicketRequestDTO dto) {
        SeguimentoTicket ticket = buscarEntidade(id);
        preencherCampos(ticket, dto);
        return toDTO(ticketRepository.save(ticket));
    }

    public TicketResponseDTO alterarStatus(Long id, StatusTicket novoStatus) {
        SeguimentoTicket ticket = buscarEntidade(id);
        validarTransicao(ticket.getStatus(), novoStatus);

        StatusTicket statusAnterior = ticket.getStatus();
        ticket.setStatus(novoStatus);
        ticketRepository.save(ticket);

        eventPublisher.publishEvent(new StatusAlteradoEvent(this, ticket, statusAnterior, novoStatus));
        return toDTO(ticket);
    }

    private SeguimentoTicket buscarEntidade(Long id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new TicketNaoEncontradoException(id));
    }

    private void validarTransicao(StatusTicket atual, StatusTicket novo) {
        List<StatusTicket> permitidos = TRANSICOES_VALIDAS.getOrDefault(atual, List.of());
        if (!permitidos.contains(novo)) {
            throw new IllegalStateException("Transição inválida: " + atual + " -> " + novo);
        }
    }

    private void preencherCampos(SeguimentoTicket ticket, TicketRequestDTO dto) {
        ticket.setSolicitante(dto.getSolicitante());
        ticket.setTelefoneSolicitante(dto.getTelefoneSolicitante());
        ticket.setSupervisor(dto.getSupervisor());
        ticket.setTelefoneSupervisor(dto.getTelefoneSupervisor());
        ticket.setSeguimento(dto.getSeguimento());
        ticket.setEmpresaSupervisor(dto.getEmpresaSupervisor());
        ticket.setLocalAtendimento(dto.getLocalAtendimento());
        ticket.setPais(dto.getPais());
        ticket.setEstado(dto.getEstado());
        ticket.setCidade(dto.getCidade());
        ticket.setEquipamento(dto.getEquipamento());
        ticket.setCondicao(dto.getCondicao());
        ticket.setTipoAtividade(dto.getTipoAtividade());
        ticket.setDescricaoAtividade(dto.getDescricaoAtividade());
        ticket.setClassificacao(dto.getClassificacao());
        ticket.setDataInicioExecucao(dto.getDataInicioExecucao());
        ticket.setDataTerminoExecucao(dto.getDataTerminoExecucao());
        ticket.setDataInicioInterrupcao(dto.getDataInicioInterrupcao());
        ticket.setDataTerminoInterrupcao(dto.getDataTerminoInterrupcao());
    }

    public TicketResponseDTO toDTO(SeguimentoTicket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setTicketNumber(ticket.getTicketNumber());
        dto.setSolicitante(ticket.getSolicitante());
        dto.setTelefoneSolicitante(ticket.getTelefoneSolicitante());
        dto.setSupervisor(ticket.getSupervisor());
        dto.setTelefoneSupervisor(ticket.getTelefoneSupervisor());
        dto.setStatus(ticket.getStatus());
        dto.setSeguimento(ticket.getSeguimento());
        dto.setEmpresaSupervisor(ticket.getEmpresaSupervisor());
        dto.setLocalAtendimento(ticket.getLocalAtendimento());
        dto.setPais(ticket.getPais());
        dto.setEstado(ticket.getEstado());
        dto.setCidade(ticket.getCidade());
        dto.setEquipamento(ticket.getEquipamento());
        dto.setCondicao(ticket.getCondicao());
        dto.setTipoAtividade(ticket.getTipoAtividade());
        dto.setDescricaoAtividade(ticket.getDescricaoAtividade());
        dto.setClassificacao(ticket.getClassificacao());
        dto.setDataInicioExecucao(ticket.getDataInicioExecucao());
        dto.setDataTerminoExecucao(ticket.getDataTerminoExecucao());
        dto.setDataInicioInterrupcao(ticket.getDataInicioInterrupcao());
        dto.setDataTerminoInterrupcao(ticket.getDataTerminoInterrupcao());
        dto.setDataAbertura(ticket.getDataAbertura());
        dto.setDataAtualizacao(ticket.getDataAtualizacao());
        return dto;
    }
}
