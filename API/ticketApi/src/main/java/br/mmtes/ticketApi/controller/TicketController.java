package br.mmtes.ticketApi.controller;

import br.mmtes.ticketApi.dto.BulkImportDTO;
import br.mmtes.ticketApi.dto.CircuitoDTO;
import br.mmtes.ticketApi.dto.TicketRequestDTO;
import br.mmtes.ticketApi.dto.TicketResponseDTO;
import br.mmtes.ticketApi.model.Circuito;
import br.mmtes.ticketApi.model.StatusTicket;
import br.mmtes.ticketApi.service.CircuitoService;
import br.mmtes.ticketApi.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final CircuitoService circuitoService;

    public TicketController(TicketService ticketService, CircuitoService circuitoService) {
        this.ticketService = ticketService;
        this.circuitoService = circuitoService;
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> criar(@RequestBody @Valid TicketRequestDTO dto) {
        return ResponseEntity.ok(ticketService.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> listar() {
        return ResponseEntity.ok(ticketService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid TicketRequestDTO dto) {
        return ResponseEntity.ok(ticketService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponseDTO> alterarStatus(@PathVariable Long id,
                                                            @RequestParam StatusTicket status) {
        return ResponseEntity.ok(ticketService.alterarStatus(id, status));
    }

    @PostMapping("/{id}/circuitos")
    public ResponseEntity<Void> associarCircuito(@PathVariable Long id,
                                                  @RequestBody @Valid CircuitoDTO dto) {
        circuitoService.associar(id, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/circuitos/importar")
    public ResponseEntity<Void> importarCircuitos(@PathVariable Long id,
                                                   @RequestBody @Valid BulkImportDTO dto) {
        circuitoService.importarEmMassa(id, dto.getDesignacoes());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/circuitos/{circuitoId}")
    public ResponseEntity<Void> desassociarCircuito(@PathVariable Long id,
                                                     @PathVariable Long circuitoId) {
        circuitoService.desassociar(id, circuitoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/circuitos")
    public ResponseEntity<List<Circuito>> listarCircuitos(@PathVariable Long id) {
        return ResponseEntity.ok(circuitoService.listarPorTicket(id));
    }
}
