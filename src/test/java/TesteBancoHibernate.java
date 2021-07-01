import dao.GenericDAO;
import model.Usuario;
import org.junit.jupiter.api.Test;
import util.HibernateConexaoUtil;

public class TesteBancoHibernate {

    @Test
    public void testHibernateUtil(){
        HibernateConexaoUtil.getEntityManager();
    }

    @Test
    public void testInsertUsuario(){
        GenericDAO<Usuario> genericDAO = new GenericDAO<>();

        Usuario usuario = new Usuario();
        usuario.setNome_usuario("Joaquim");
        usuario.setSobrenome_usuario("Ramos");
        usuario.setEmail_usuario("joaquimramos@gmail.com");
        usuario.setPassword_usuario("123");
        usuario.setIdade(35);

        genericDAO.insertUsuario(usuario);
    }
}
