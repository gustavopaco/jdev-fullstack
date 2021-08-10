package br.com.webmvnspringbootthymeleaf.model;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Table
@Entity(name = "Pessoa")
public class Pessoa implements Serializable {

    @Id
    @SequenceGenerator(name = "pessoa_sequence", sequenceName = "pessoa_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_sequence")
    @Column(updatable = false)
    private Long id;

    @NotEmpty(message = "Nome nao pode ser vazio")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotEmpty(message = "Sobrenome nao pode ser vazio")
    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;

    @NotNull(message = "Data de nascimento deve ser selecionada")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "dta", nullable = false)
    private LocalDate dta;

    @NotBlank(message = "Selecione o sexo da pessoa")
    @Column(name = "sexo")
    private String sexo;

    @Transient
    private Integer idade;

    @OneToMany(mappedBy = "pessoa", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Telefone> telefones;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "pessoa_endereco",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            foreignKey = @ForeignKey(name = "pessoa_id", value = ConstraintMode.CONSTRAINT),
            inverseJoinColumns = @JoinColumn(name = "endereco_id"),
            inverseForeignKey = @ForeignKey(name = "endereco_id", value = ConstraintMode.CONSTRAINT))
    @Valid
    private List<Endereco> enderecos;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "profissao_id", foreignKey = @ForeignKey(name = "profissao_id", value = ConstraintMode.CONSTRAINT))
    private Profissao profissao;

    @Enumerated(value = EnumType.STRING)
    private Cargo cargo;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "curriculo")
    private byte[] curriculo;

    private String fileContentType;

    private String fileName;

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

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public byte[] getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(byte[] curriculo) {
        this.curriculo = curriculo;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;

        Pessoa pessoa = (Pessoa) o;

        return id != null ? id.equals(pessoa.id) : pessoa.id == null;
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
                ", sexo='" + sexo + '\'' +
                ", idade=" + idade +
                ", telefones=" + telefones +
                ", enderecos=" + enderecos +
                ", profissao=" + profissao +
                ", cargo=" + cargo +
                ", fileContentType='" + fileContentType + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
