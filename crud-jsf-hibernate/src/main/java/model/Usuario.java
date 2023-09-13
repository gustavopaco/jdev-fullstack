package model;


import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.type.descriptor.java.PrimitiveByteArrayTypeDescriptor;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.constraints.br.TituloEleitoral;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@TypeDef(name = "bytea", typeClass = PrimitiveByteArrayTypeDescriptor.class)
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_usuario;

    @Column(nullable = false)
    private String nome_usuario;

    @Column(nullable = false, unique = true)
    private String email_usuario;

    @Column(nullable = false)
    private String password_usuario;

    @DecimalMax(value = "60", message = "Age must be under 60 years old")
    @DecimalMin(value = "18", message = "Age must be over 18 years old")
    @Column(nullable = false)
    private int idade_usuario;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataNascimento_usuario;

    private String sexo_usuario;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "usuario_framework",
            joinColumns = {@JoinColumn(name = "usuario_id")},
            inverseJoinColumns = {@JoinColumn(name = "framework_id")})
    private List<Framework> frameworks = new ArrayList<>();

    @Type(type = "bytea")
    @Column(columnDefinition = "bytea")
    private String[] linguagens;

    private Boolean isSignedContract;

    private String status;

    @Type(type = "bytea")
    @Column(columnDefinition = "bytea")
    private Integer[] linguas;

    private String cep;

    private String logradouro;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    private String ibge;

    private String gia;

    private String ddd;

    private String siafi;

    @Transient // Nao Grava no banco, fica so na memoria
    private Estados estado;

    @ManyToOne
    private Cidades cidade;

    @Column(columnDefinition = "text") /* Grava arquivos em Base 64 */
    private String fileIconBase64;

    private String fileType; /* Tipo de arquivo, png, jpg, pdf */

    @Lob /* Tag mapear objetos e/ou propriedades/atributos muito grandes, normalmente utilizados com byte[] ou character Type = String */
    @Basic(fetch = FetchType.LAZY)
    private byte[] fileMainBase64;

    @CPF(message = "Invalid CPF")
    private String cpf;

    @TituloEleitoral(message = "Invalid Voter Title")
    private String tituloEleitor;

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getPassword_usuario() {
        return password_usuario;
    }

    public void setPassword_usuario(String password_usuario) {
        this.password_usuario = password_usuario;
    }

    public int getIdade_usuario() {
        return idade_usuario;
    }

    public void setIdade_usuario(int idade_usuario) {
        this.idade_usuario = idade_usuario;
    }

    public Date getDataNascimento_usuario() {
        return dataNascimento_usuario;
    }

    public void setDataNascimento_usuario(Date dataNascimento_usuario) {
        this.dataNascimento_usuario = dataNascimento_usuario;
    }

    public String getSexo_usuario() {
        return sexo_usuario;
    }

    public void setSexo_usuario(String sexo_usuario) {
        this.sexo_usuario = sexo_usuario;
    }

    public List<Framework> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(List<Framework> frameworks) {
        this.frameworks = frameworks;
    }

    public String[] getLinguagens() {
        return linguagens;
    }

    public void setLinguagens(String[] linguagens) {
        this.linguagens = linguagens;
    }

    public Boolean getSignedContract() {
        return isSignedContract;
    }

    public void setSignedContract(Boolean signedContract) {
        isSignedContract = signedContract;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer[] getLinguas() {
        return linguas;
    }

    public void setLinguas(Integer[] linguas) {
        this.linguas = linguas;
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

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public Cidades getCidade() {
        return cidade;
    }

    public void setCidade(Cidades cidade) {
        this.cidade = cidade;
    }

    public String getFileIconBase64() {
        return fileIconBase64;
    }

    public void setFileIconBase64(String fileIconBase64) {
        this.fileIconBase64 = fileIconBase64;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileMainBase64() {
        return fileMainBase64;
    }

    public void setFileMainBase64(byte[] fileMainBase64) {
        this.fileMainBase64 = fileMainBase64;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTituloEleitor() {
        return tituloEleitor;
    }

    public void setTituloEleitor(String tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id_usuario, usuario.id_usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_usuario);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", nome_usuario='" + nome_usuario + '\'' +
                ", email_usuario='" + email_usuario + '\'' +
                ", password_usuario='" + password_usuario + '\'' +
                ", idade_usuario=" + idade_usuario +
                ", dataNascimento_usuario=" + dataNascimento_usuario +
                ", sexo_usuario='" + sexo_usuario + '\'' +
                ", frameworks=" + frameworks +
                ", linguagens=" + Arrays.toString(linguagens) +
                ", isSignedContract=" + isSignedContract +
                ", status='" + status + '\'' +
                ", linguas=" + Arrays.toString(linguas) +
                ", cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                ", ibge='" + ibge + '\'' +
                ", gia='" + gia + '\'' +
                ", ddd='" + ddd + '\'' +
                ", siafi='" + siafi + '\'' +
                ", estado=" + estado +
                ", cidade=" + cidade +
                ", fileIconBase64='" + fileIconBase64 + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileMainBase64=" + Arrays.toString(fileMainBase64) +
                ", cpf='" + cpf + '\'' +
                ", tituloEleitor='" + tituloEleitor + '\'' +
                '}';
    }
}
