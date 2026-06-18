package br.mmtes.ticketApi.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Audited
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    
    @Column(name = "ticket_number", columnDefinition = "serial", insertable = false, updatable = false)
    @Generated
    private Integer TicketNumber;
    
    @NotBlank(message = "O campo é obrigatório.")
    private String Solicitante;

    @NotBlank(message = "O campo é obrigatório.")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "O telefone deve seguir o padrão internacional. (ex: +5531999999999)")
    @Column(name = "telefone_solicitante")
    private String TelefoneSolicitante;
    
    @NotBlank(message = "O campo é obrigatório.")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "O telefone deve seguir o padrão internacional. (ex: +5531999999999)")
    @Column(name = "telefone_supervisor")
    private String TelefoneSupervisor;


    @Enumerated(EnumType.STRING)
    private StatusTicket Status;

    private LocalDateTime DataAbertura;
    private LocalDateTime DataAtualizacao;

    @PrePersist
    protected void onCreate() {
        DataAbertura = LocalDateTime.now();
        DataAtualizacao = LocalDateTime.now();
    }
}