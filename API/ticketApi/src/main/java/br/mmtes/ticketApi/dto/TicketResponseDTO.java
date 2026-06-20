package br.mmtes.ticketApi.dto;

import br.mmtes.ticketApi.model.*;

import java.time.LocalDateTime;

public class TicketResponseDTO {

    private Long id;
    private Integer ticketNumber;
    private String solicitante;
    private String telefoneSolicitante;
    private String supervisor;
    private String telefoneSupervisor;
    private StatusTicket status;
    private SeguimentoAtividade seguimento;
    private EmpresaSupervisor empresaSupervisor;
    private TipoLocal localAtendimento;
    private String pais;
    private String estado;
    private String cidade;
    private String equipamento;
    private CondicaoEquipamento condicao;
    private TipoAtividade tipoAtividade;
    private String descricaoAtividade;
    private ClassificacaoAtividade classificacao;
    private LocalDateTime dataInicioExecucao;
    private LocalDateTime dataTerminoExecucao;
    private LocalDateTime dataInicioInterrupcao;
    private LocalDateTime dataTerminoInterrupcao;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataAtualizacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(Integer ticketNumber) { this.ticketNumber = ticketNumber; }

    public String getSolicitante() { return solicitante; }
    public void setSolicitante(String solicitante) { this.solicitante = solicitante; }

    public String getTelefoneSolicitante() { return telefoneSolicitante; }
    public void setTelefoneSolicitante(String telefoneSolicitante) { this.telefoneSolicitante = telefoneSolicitante; }

    public String getSupervisor() { return supervisor; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }

    public String getTelefoneSupervisor() { return telefoneSupervisor; }
    public void setTelefoneSupervisor(String telefoneSupervisor) { this.telefoneSupervisor = telefoneSupervisor; }

    public StatusTicket getStatus() { return status; }
    public void setStatus(StatusTicket status) { this.status = status; }

    public SeguimentoAtividade getSeguimento() { return seguimento; }
    public void setSeguimento(SeguimentoAtividade seguimento) { this.seguimento = seguimento; }

    public EmpresaSupervisor getEmpresaSupervisor() { return empresaSupervisor; }
    public void setEmpresaSupervisor(EmpresaSupervisor empresaSupervisor) { this.empresaSupervisor = empresaSupervisor; }

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
    public void setDataInicioExecucao(LocalDateTime dataInicioExecucao) { this.dataInicioExecucao = dataInicioExecucao; }

    public LocalDateTime getDataTerminoExecucao() { return dataTerminoExecucao; }
    public void setDataTerminoExecucao(LocalDateTime dataTerminoExecucao) { this.dataTerminoExecucao = dataTerminoExecucao; }

    public LocalDateTime getDataInicioInterrupcao() { return dataInicioInterrupcao; }
    public void setDataInicioInterrupcao(LocalDateTime dataInicioInterrupcao) { this.dataInicioInterrupcao = dataInicioInterrupcao; }

    public LocalDateTime getDataTerminoInterrupcao() { return dataTerminoInterrupcao; }
    public void setDataTerminoInterrupcao(LocalDateTime dataTerminoInterrupcao) { this.dataTerminoInterrupcao = dataTerminoInterrupcao; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
