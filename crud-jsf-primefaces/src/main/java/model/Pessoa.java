package model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 3, message = "*Minimo 3 letras")
    @NotEmpty(message = "*Campo obrigatorio")
    private String nome;

    @NotEmpty(message = "*Campo obrigatorio")
    private String sobrenome;

    @Email(regexp = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]", message = "*Email invalido")
    @NotEmpty(message = "*Campo obrigatorio")
    private String email;

    @Size(min = 5, max = 20, message = "*Senha deve ter no minimo 5 e maximo de 20 caracteres")
    @NotEmpty(message = "*Campo obrigatorio")
    private String password;

    @Size(min = 3, message = "*Minimo de 3 letras")
    @NotEmpty(message = "*Campo obrigatorio")
    private String login;

    @Min(value = 18, message = "*Somente maiores de 18 anos")
    @NotNull(message = "*Campo obrigatorio")
    private Integer idade;

    private String sexo;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.MERGE)
    private List<TelefonePessoa> telefonePessoas = new ArrayList<>();

    @NotEmpty(message = "*Campo obrigatorio")
    @Size(min = 8, message = "Cep deve ter no minimo 8 caracteres")
    private String cep;

    @Transient
    private boolean isCepValid = true;

    @NotEmpty(message = "*Campo obrigatorio")
    private String logradouro;
    @NotEmpty(message = "*Campo obrigatorio")
    private String bairro;
    @NotEmpty(message = "*Campo obrigatorio")
    private String localidade;
    @NotEmpty(message = "*Campo obrigatorio")
    private String uf;

    private String complemento;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    @Column(columnDefinition = "text")
    private String perfilImagem64;

    private String perfilImagemType;

    private String perfilImagemNome;

    @Column(columnDefinition = "text")
    private String perfilImagemMiniatura64;

    @DecimalMin(value = "0.00", groups = Double.class, message = "*Campo deve ser um numero, exemplo: 0.01")
    private Double salario;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.MERGE)
    private List<CartaoCredito> cartoesCredito = new ArrayList<>();

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<TelefonePessoa> getTelefonePessoas() {
        return telefonePessoas;
    }

    public void setTelefonePessoas(List<TelefonePessoa> telefonePessoas) {
        this.telefonePessoas = telefonePessoas;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getSiafi() {
        return siafi;
    }

    public void setSiafi(String siafi) {
        this.siafi = siafi;
    }

    public boolean isCepValid() {
        return isCepValid;
    }

    public void setCepValid(boolean cepValid) {
        isCepValid = cepValid;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public List<CartaoCredito> getCartoesCredito() {
        return cartoesCredito;
    }

    public void setCartoesCredito(List<CartaoCredito> cartoesCredito) {
        this.cartoesCredito = cartoesCredito;
    }

    public String getPerfilImagem64() {
        return perfilImagem64;
    }

    public void setPerfilImagem64(String perfilImagem64) {
        this.perfilImagem64 = perfilImagem64;
    }

    public String getPerfilImagemType() {
        return perfilImagemType;
    }

    public void setPerfilImagemType(String perfilImagemType) {
        this.perfilImagemType = perfilImagemType;
    }

    public String getPerfilImagemNome() {
        return perfilImagemNome;
    }

    public void setPerfilImagemNome(String perfilImagemNome) {
        this.perfilImagemNome = perfilImagemNome;
    }

    public String getPerfilImagemMiniatura64() {
        return perfilImagemMiniatura64;
    }

    public void setPerfilImagemMiniatura64(String perfilImagemMiniatura64) {
        this.perfilImagemMiniatura64 = perfilImagemMiniatura64;
    }

    public String getPerfilImagemCompleta() {
        return (perfilImagem64 == null) ? null :  "data:" + perfilImagemType + ";base64," + perfilImagem64;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                ", idade=" + idade +
                ", sexo='" + sexo + '\'' +
                ", telefonePessoas=" + telefonePessoas +
                ", cep='" + cep + '\'' +
                ", isCepValid=" + isCepValid +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                ", complemento='" + complemento + '\'' +
                ", ibge='" + ibge + '\'' +
                ", gia='" + gia + '\'' +
                ", ddd='" + ddd + '\'' +
                ", siafi='" + siafi + '\'' +
                ", salario=" + salario +
                ", cartoesCredito=" + cartoesCredito +
                '}';
    }
}
