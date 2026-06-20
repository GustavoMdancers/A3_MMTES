package br.mmtes.ticketApi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "circuito")
public class Circuito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designacao", unique = true, nullable = false)
    private String designacao;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "email_contato")
    private String emailContato;

    @Column(name = "telefone_contato")
    private String telefoneContato;

    @ManyToMany(mappedBy = "circuitos")
    private List<SeguimentoTicket> tickets = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDesignacao() { return designacao; }
    public void setDesignacao(String designacao) { this.designacao = designacao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getEmailContato() { return emailContato; }
    public void setEmailContato(String emailContato) { this.emailContato = emailContato; }

    public String getTelefoneContato() { return telefoneContato; }
    public void setTelefoneContato(String telefoneContato) { this.telefoneContato = telefoneContato; }

    public List<SeguimentoTicket> getTickets() { return tickets; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Circuito other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
