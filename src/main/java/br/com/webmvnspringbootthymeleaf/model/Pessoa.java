package br.com.webmvnspringbootthymeleaf.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@Table
@Entity(name = "Pessoa")
public class Pessoa implements Serializable {

    @Id
    @SequenceGenerator(name = "pessoa_sequence", sequenceName = "pessoa_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_sequence")
    @Column(updatable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dta", nullable = false)
    private LocalDate dta;

    @Transient
    private Integer idade;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.MERGE)
    private List<Telefone> telefones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public LocalDate getDta() {
        return dta;
    }

    public void setDta(LocalDate dta) {
        this.dta = dta;
    }

    public Integer getIdade() {
        return Period.between(this.dta,LocalDate.now()).getYears();
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa pessoa)) return false;

        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", dta=" + dta +
                ", idade=" + idade +
                ", telefones=" + telefones +
                '}';
    }
}
