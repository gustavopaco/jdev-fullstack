import dao.GenericDAO;
import model.TelefoneUsuario;
import model.Usuario;
import org.junit.jupiter.api.Test;
import util.HibernateConexaoUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class TesteBancoHibernate {

    @Test
    public void testHibernateUtil(){
        HibernateConexaoUtil.getEntityManager();
    }

    @Test
    public void testInsertUsuario(){
        GenericDAO<Usuario> genericDAO = new GenericDAO<>();

        Usuario usuario = new Usuario();
        usuario.setNome_usuario("Joao");
        usuario.setSobrenome_usuario("Barbosa");
        usuario.setEmail_usuario("joaobarbosa@gmail.com");
        usuario.setPassword_usuario("123");
        usuario.setIdade(40);

        genericDAO.insertEntity(usuario);
    }

    @Test
    public void selectUsuarioByObjectID(){
        GenericDAO<Usuario> genericDAO = new GenericDAO<>();
        Usuario usuario = new Usuario();
        usuario.setId_usuario(3L);

        usuario = genericDAO.getEntityByObject(usuario);

        System.out.println(usuario);
    }

    @Test
    public void selectUsuarioByLongID(){
        GenericDAO<Usuario> genericDAO = new GenericDAO<>();

        Usuario usuario = genericDAO.getEntityByLong(Usuario.class,1L);

        System.out.println(usuario);
    }

    @Test
    public void updateUsuario(){
        GenericDAO<Usuario> usuarioGenericDAO = new GenericDAO<>();
        Usuario usuario = new Usuario();
        usuario.setId_usuario(6L);

        usuario.setEmail_usuario("alexandreogrande@gmail.com");
        usuario.setNome_usuario("Alexandre");
        usuario.setSobrenome_usuario("O Grande");
        usuario.setPassword_usuario("123");
        usuario.setIdade(60);

        usuario = usuarioGenericDAO.updateEntity(usuario);

        System.out.println(usuario);

    }

    @Test
    public void removeUsuarioByObject(){
        GenericDAO<Usuario> usuarioGenericDAO = new GenericDAO<>();

        Usuario usuario = new Usuario();
        usuario.setId_usuario(5L);

        usuario = usuarioGenericDAO.getEntityByObject(usuario);

        usuarioGenericDAO.removeEntityByObject(usuario);
    }

    @Test
    public void removeUsuarioByID(){
        GenericDAO<Usuario> genericDAO = new GenericDAO<>();

        genericDAO.removeEntityByID(Usuario.class, 4L);
    }

    @Test
    public void getUsuariosByClass(){

        List<Usuario> usuarios = new GenericDAO<Usuario>().getEntities(Usuario.class);

        for (Usuario user : usuarios) {
            System.out.println(user);
        }

    }

    @Test
    public void getQueryList(){

        GenericDAO<Usuario> genericDAO = new GenericDAO<>();

        List<Usuario> usuarios = genericDAO.getEntityManager().createQuery("SELECT u FROM Usuario u where u.nome_usuario = 'Gustavo' ")
                .getResultList();

        for (Usuario user : usuarios) {
        System.out.println(user);
        }
    }

    @Test
    public void getQueryListOrderByMaxResults(){

        GenericDAO<Usuario> genericDAO = new GenericDAO<>();
        EntityManager entityManager = genericDAO.getEntityManager();

        List<Usuario> usuarios = entityManager.createQuery("select u FROM Usuario u ORDER BY u.id_usuario")
                .setMaxResults(3).getResultList();

        for (Usuario user : usuarios) {
            System.out.println(user);
        }
    }

    @Test
    public void getQueryListParameter(){
        GenericDAO<Usuario> genericDAO = new GenericDAO<>();

        List<Usuario> usuarios = genericDAO.getEntityManager().createQuery
                    ("select u from Usuario u where u.nome_usuario = :nome or u.sobrenome_usuario = :sobrenome ")
                .setParameter("nome","Raquel").setParameter("sobrenome","Paco").getResultList();

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    @Test
    public void getSUMIdades(){

        GenericDAO<Usuario> genericDAO = new GenericDAO<>();

        int total = Integer.parseInt(genericDAO.getEntityManager()
                .createQuery("select sum(u.idade) from Usuario u").getSingleResult().toString());

        System.out.println("Soma das idade eh: " + total);
    }

    @Test
    public void getNamedQuery1(){

        GenericDAO<Usuario> genericDAO = new GenericDAO<>();

        List<Usuario> usuarios = genericDAO.getEntityManager().createNamedQuery("Usuario.findAll").getResultList();

        for (Usuario user: usuarios) {
            System.out.println(user);
        }
    }

    @Test
    public void getNamedQuery2(){

        GenericDAO<Usuario> genericDAO = new GenericDAO<>();

        List<Usuario> usuarios = genericDAO.getEntityManager().createNamedQuery("Usuario.findByName").setParameter("nome","Gustavo").getResultList();

        for (Usuario user: usuarios) {
            System.out.println(user);
        }
    }

    @Test
    public void insertTelefone(){

        GenericDAO genericDAO = new GenericDAO<>();

        Usuario usuario = (Usuario) genericDAO.getEntityByLong(Usuario.class, 2L);

//       === Funciona passando somente um usuario com ID dentro dele e o restante null ===
//        Usuario usuario = new Usuario();
//        usuario.setId_usuario(2L);

        TelefoneUsuario telefoneUsuario = new TelefoneUsuario();
        telefoneUsuario.setNumero_telefone("33998056025");
        telefoneUsuario.setTipo_telefone("Celular");
        telefoneUsuario.setUsuario(usuario);

        genericDAO.insertEntity(telefoneUsuario);

    }

    @Test
    public void getInnerJoin(){
//    **IMPORTANTE** -> Se o atributo do objeto for outro objeto Pessoa:Telefone por exemplo, a Anotacao nos Objetos:
//          OneToMany,ManyToOne,ManyToMany,OneToOne tem um sub-atributo Fetch:
//    Caso: fetch = FetchType.Eager -> Eh realizado somente um SQL em Pessoa e ja filtrando o objeto filho Telefone
//    Caso: fetch = FetchType.Lazy(Padrao) -> Eh realizado um SQL para o Objeto Pessoa e SE o objeto Telefone for chamado,
//          pessoa.getTelefone em algum lugar do codigo, entao eh realizado outro SQL filtrando somente Telefones no momento da chamada

        GenericDAO genericDAO = new GenericDAO();

        Usuario usuario = (Usuario) genericDAO.getEntityByLong(Usuario.class, 2L);

        System.out.println("========================================================");
        System.out.println("Nome do Titular: " + usuario.getNome_usuario());
        System.out.println("========================================================");

        for (TelefoneUsuario telefoneUsuario : usuario.getTelefoneUsuarios()) {
            System.out.println(telefoneUsuario);
        }
    }
}
