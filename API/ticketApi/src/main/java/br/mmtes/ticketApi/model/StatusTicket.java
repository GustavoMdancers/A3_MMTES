package br.mmtes.ticketApi.model;

public enum StatusTicket {
    ABERTO,
    AGUARDANDO_ANALISE,
    EM_ANALISE,
    NOTIFICADO,
    AGUARDANDO_EXECUÇÃO,
    EM_EXECUÇÃO,
    PARALISADO,
    CANCELADO,
    FECHADO;
}