package repository;

import model.Usuario;

import javax.faces.model.SelectItem;
import java.util.List;

public interface IDaoUsuario {

    Usuario signIn(String email, String password);

    boolean emailAlreadyTaken(String email);

    Usuario findAddress(String componentUI, Usuario usuario) throws Exception;

    List<SelectItem> getStates();

    List<SelectItem> getCities(Long estado_id);

    List<Usuario> getUsersOrderByName();
}
