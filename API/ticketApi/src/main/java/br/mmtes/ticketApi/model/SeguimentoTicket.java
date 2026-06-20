package br.mmtes.ticketApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Generated;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Table(name = "ticket")
public class SeguimentoTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_number", columnDefinition = "serial", insertable = false, updatable = false)
    @Generated
    private Integer ticketNumber;

    @NotBlank(message = "O campo é obrigatório.")
    @Column(name = "nome_solicitante")
    private String solicitante;

    @NotBlank(message = "O campo é obrigatório.")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Telefone no formato internacional. (ex: +5531999999999)")
    @Column(name = "telefone_solicitante")
    private String telefoneSolicitante;

    @NotBlank(message = "O campo é obrigatório.")
    @Column(name = "nome_supervisor")
    private String supervisor;

    @NotBlank(message = "O campo é obrigatório.")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Telefone no formato internacional. (ex: +5531999999999)")
    @Column(name = "telefone_supervisor")
    private String telefoneSupervisor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_ticket")
    private StatusTicket status;

    @Enumerated(EnumType.STRING)
    private SeguimentoAtividade seguimento;

    @Enumerated(EnumType.STRING)
    private EmpresaSupervisor empresaSupervisor;

    @Enumerated(EnumType.STRING)
    private TipoLocal localAtendimento;

    @Column(name = "pais")
    private String pais;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "equipamento")
    private String equipamento;

    @Enumerated(EnumType.STRING)
    private CondicaoEquipamento condicao;

    @Enumerated(EnumType.STRING)
    private TipoAtividade tipoAtividade;

    @Column(name = "descricao_atividade", columnDefinition = "TEXT")
    private String descricaoAtividade;

    @Enumerated(EnumType.STRING)
    private ClassificacaoAtividade classificacao;

    @Column(name = "data_inicio_execucao")
    private LocalDateTime dataInicioExecucao;

    @Column(name = "data_termino_execucao")
    private LocalDateTime dataTerminoExecucao;

    @Column(name = "data_inicio_interrupcao")
    private LocalDateTime dataInicioInterrupcao;

    @Column(name = "data_termino_interrupcao")
    private LocalDateTime dataTerminoInterrupcao;

    @Column(name = "data_abertura", updatable = false, nullable = false)
    private LocalDateTime dataAbertura;

    @Column(name = "data_atualizacao", nullable = false)
    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(
        name = "ticket_circuito",
        joinColumns = @JoinColumn(name = "ticket_id"),
        inverseJoinColumns = @JoinColumn(name = "circuito_id")
    )
    private List<Circuito> circuitos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        dataAbertura = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(Integer ticketNumber) { this.ticketNumber = ticketNumber; }

    public String getSolicitante() { return solicitante; }
    public void setSolicitante(String solicitante) { this.solicitante = solicitante; }

    public String getTelefoneSolicitante() { return telefoneSolicitante; }
    public void setTelefoneSolicitante(String t) { this.telefoneSolicitante = t; }

    public String getSupervisor() { return supervisor; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }

    public String getTelefoneSupervisor() { return telefoneSupervisor; }
    public void setTelefoneSupervisor(String t) { this.telefoneSupervisor = t; }

    public StatusTicket getStatus() { return status; }
    public void setStatus(StatusTicket status) { this.status = status; }

    public SeguimentoAtividade getSeguimento() { return seguimento; }
    public void setSeguimento(SeguimentoAtividade seguimento) { this.seguimento = seguimento; }

    public EmpresaSupervisor getEmpresaSupervisor() { return empresaSupervisor; }
    public void setEmpresaSupervisor(EmpresaSupervisor e) { this.empresaSupervisor = e; }

    public TipoLocal getLocalAtendimento() { return localAtendimento; }
    public void setLocalAtendimento(TipoLocal localAtendimento) { this.localAtendimento = localAtendimento; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEquipamento() { return equipamento; }
    public void setEquipamento(String equipamento) { this.equipamento = equipamento; }

    public CondicaoEquipamento getCondicao() { return condicao; }
    public void setCondicao(CondicaoEquipamento condicao) { this.condicao = condicao; }

    public TipoAtividade getTipoAtividade() { return tipoAtividade; }
    public void setTipoAtividade(TipoAtividade tipoAtividade) { this.tipoAtividade = tipoAtividade; }

    public String getDescricaoAtividade() { return descricaoAtividade; }
    public void setDescricaoAtividade(String descricaoAtividade) { this.descricaoAtividade = descricaoAtividade; }

    public ClassificacaoAtividade getClassificacao() { return classificacao; }
    public void setClassificacao(ClassificacaoAtividade classificacao) { this.classificacao = classificacao; }

    public LocalDateTime getDataInicioExecucao() { return dataInicioExecucao; }
    public void setDataInicioExecucao(LocalDateTime d) { this.dataInicioExecucao = d; }

    public LocalDateTime getDataTerminoExecucao() { return dataTerminoExecucao; }
    public void setDataTerminoExecucao(LocalDateTime d) { this.dataTerminoExecucao = d; }

    public LocalDateTime getDataInicioInterrupcao() { return dataInicioInterrupcao; }
    public void setDataInicioInterrupcao(LocalDateTime d) { this.dataInicioInterrupcao = d; }

    public LocalDateTime getDataTerminoInterrupcao() { return dataTerminoInterrupcao; }
    public void setDataTerminoInterrupcao(LocalDateTime d) { this.dataTerminoInterrupcao = d; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }

    public List<Circuito> getCircuitos() { return circuitos; }
    public void setCircuitos(List<Circuito> circuitos) { this.circuitos = circuitos; }
}
