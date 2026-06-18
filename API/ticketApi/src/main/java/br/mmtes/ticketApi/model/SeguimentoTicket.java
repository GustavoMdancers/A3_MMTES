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
public class SeguimentoTicket {

    //ID do ticket, gerado automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    
    // Numero do ticket, gerado automaticamente e sequencial
    @Column(name = "ticket_number", columnDefinition = "serial", insertable = false, updatable = false)
    @Generated
    private Integer TicketNumber;
    
    // Dados da solicitação
    // Nome do solicitante, obrigatório
    @NotBlank(message = "O campo é obrigatório.")
    @Column(name = "nome_solicitante")
    private String Solicitante;

    // Número do solicitante, obrigatório
    @NotBlank(message = "O campo é obrigatório.")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "O telefone deve seguir o padrão internacional. (ex: +5531999999999)")
    @Column(name = "telefone_solicitante")
    private String TelefoneSolicitante;

    // Nome do supervisor, obrigatório
    @NotBlank(message = "O campo é obrigatório.")
    @Column(name = "nome_supervisor")
    private String Supervisor;

    // Número do supervisor, obrigatório
    @NotBlank(message = "O campo é obrigatório.")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "O telefone deve seguir o padrão internacional. (ex: +5531999999999)")
    @Column(name = "telefone_supervisor")
    private String TelefoneSupervisor;

    // Status do ticket, obrigatório, com os seguintes valores: Aberto, Em Andamento, Fechado
    @Enumerated(EnumType.STRING)
    @Column(name = "status_ticket")
    private StatusTicket Status;

    // Data de abertura e última alteração do ticket, gerada automaticamente
    @Column(name = "data_abertura", updatable = false, nullable = false)
    private LocalDateTime DataAbertura;
    
    @Column(name = "data_atualizacao",nullable=false)
    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime DataAtualizacao;

    @PrePersist
    protected void onCreate() {
        DataAbertura = LocalDateTime.now();
        DataAtualizacao = LocalDateTime.now();
    }

    // Seguimento do ticket, obrigatório, com os seguintes valores: Manutenção, Operação, Ativação
    @Enumerated(EnumType.STRING)
    private SeguimentoAtividade Seguimento;

    // Empresa do supervisor, obrigatório, com os seguintes valores: Claro, Vivo, TIM, Oi
    @Enumerated(EnumType.STRING)
    private EmpresaSupervisor EmpresaSupervisor;

    // Local 
    // Local do atendimento, obrigatório, com os seguintes valores: Estação, Rede Externa, Site
    @Enumerated(EnumType.STRING)
    private tipoLocal LocalAtendimento;

    @Column (name = "Pais")
    private String Pais;
    @Column (name = "Estado")
    private String Estado;
    @Column (name = "Cidade")
    private String Cidade;

    //Informações da atividade

    @Column(name = "equipamento")
    private String Equipamento;


}