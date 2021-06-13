package models;

import java.time.LocalDate;
import java.util.Objects;

public class Usuario {

    private Long id;
    private String login;
    private String password;
    private String name;
    private LocalDate birthday;
    private String gender;
    private String cpf;
    private String profileImage;
    private String contentType;
    private String tempImageUsuario;
    private String curriculo;
    private String curriculoContentType;
    private String miniaturaprofile;
    private boolean updateImage = true;
    private boolean updatePDF = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTempImageUsuario() {
        tempImageUsuario = "data:" + contentType + ";base64," + profileImage;
        return tempImageUsuario;
    }

    public String getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(String curriculo) {
        this.curriculo = curriculo;
    }

    public String getCurriculoContentType() {
        return curriculoContentType;
    }

    public void setCurriculoContentType(String curriculoContentType) {
        this.curriculoContentType = curriculoContentType;
    }

    public String getMiniaturaprofile() {
        return miniaturaprofile;
    }

    public void setMiniaturaprofile(String miniaturaprofile) {
        this.miniaturaprofile = miniaturaprofile;
    }

    public boolean isUpdateImage() {
        return updateImage;
    }

    public void setUpdateImage(boolean updateImage) {
        this.updateImage = updateImage;
    }

    public boolean isUpdatePDF() {
        return updatePDF;
    }

    public void setUpdatePDF(boolean updatePDF) {
        this.updatePDF = updatePDF;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(login, usuario.login) && Objects.equals(password, usuario.password) && Objects.equals(name, usuario.name) && Objects.equals(birthday, usuario.birthday) && Objects.equals(gender, usuario.gender) && Objects.equals(cpf, usuario.cpf) && Objects.equals(profileImage, usuario.profileImage) && Objects.equals(contentType, usuario.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, name, birthday, gender, cpf, profileImage, contentType);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", cpf='" + cpf + '\'' +
                ", imagemPerfil='" + profileImage + '\'' +
                ", imagemContentType='" + contentType + '\'' +
                '}';
    }
}
