import connection.SingleHibernateEM;
import dao.GenericDAO;
import model.Framework;
import model.Usuario;
import org.junit.jupiter.api.Test;
import repository.IDaoUsuario;
import repository.IDaoUsuarioImpl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestScoped
@Named
public class TesteHibernate {


    @Test
    public void testConexaoHibernate(){
        SingleHibernateEM singleHibernateEM = new SingleHibernateEM();
        singleHibernateEM.getEM();
    }

    @Test
    public void testgetEntityByObject() {
        GenericDAO genericDAO = new GenericDAO();
        Usuario usuario = new Usuario();
        usuario.setId_usuario(4L);

       usuario = (Usuario) genericDAO.getEntityByObject(usuario);
        System.out.println(usuario);
    }

    @Test
    public void testgetEntityByID(){
        GenericDAO genericDAO = new GenericDAO<>();

        Usuario usuario = new Usuario();
        usuario.setId_usuario(23L);

        usuario = (Usuario) genericDAO.getEntityByID(usuario, usuario.getId_usuario());
        System.out.println(usuario);

        for (Framework f : usuario.getFrameworks()) {
            System.out.println(f);
        }
    }

    @Test
    public void testgetEntities(){
        GenericDAO genericDAO = new GenericDAO<>();
        List<Usuario> usuarios = (List<Usuario>) genericDAO.getEntities(Usuario.class);

        for (Usuario user : usuarios) {
            System.out.println(user);
        }
    }

    @Test // MALUQUISSE DESCONSIDERAR
    public void testManyToMany(){

        GenericDAO genericDAO = new GenericDAO<>();

        Usuario usuario = new Usuario();
        usuario.setId_usuario(61L);
        usuario.setNome_usuario("Garrincha");
        usuario.setEmail_usuario("garrincha@gmail.com");
        usuario.setPassword_usuario("123");
        usuario.setSexo_usuario("Masculino");
        usuario.setIdade_usuario(200);
        usuario.setDataNascimento_usuario(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

        List<Framework> frameworks = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();

        Framework framework1 = new Framework();
        framework1.setId_framework(70L);
        framework1.setNome_framework("Spring Boot");

        Framework framework2 = new Framework();
        framework2.setId_framework(71L);
        framework2.setNome_framework("JSF");

        frameworks.add(framework1);
        frameworks.add(framework2);

        usuario.setFrameworks(frameworks);

        usuarios.add(usuario);

        framework1.setUsuarios(usuarios);
        framework2.setUsuarios(usuarios);

        frameworks = new ArrayList<>();
        frameworks.add(framework1);
        frameworks.add(framework2);

        usuario.setFrameworks(frameworks);

        genericDAO.updateEntity(usuario);
    }

    @Test
    public void testIsEmailAlreadyTaken(){
        IDaoUsuario iDaoUsuario = new IDaoUsuarioImpl();
        Usuario usuario = new Usuario();
        usuario.setEmail_usuario("gustavopaco@gmail.com");

        boolean exists = iDaoUsuario.emailAlreadyTaken(usuario.getEmail_usuario());
        if (exists){
            System.out.println("Email ja existente");
        }else{
            System.out.println("Email disponivel");
        }
    }
}
