import dao.ITelefoneDaoRepository;
import dao.IUsuarioDaoRepository;
import model.Telefone;
import model.Usuario;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring-config.xml"})
public class SpringDataTest {

    @SuppressWarnings(value = "SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private IUsuarioDaoRepository iUsuarioDaoRepository;

    @SuppressWarnings(value = "SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ITelefoneDaoRepository iTelefoneDaoRepository;

    @Test
    public void testConexao() {
        System.out.println("Iniciou spring com sucesso");
    }

    @Test
    public void insertUsuario() {

        Usuario usuario = new Usuario();
        usuario.setNome("Bobssg");
        usuario.setEmail("bobssg@gmail.com");
        usuario.setSenha("123");
        usuario.setIdade(35);
        usuario.setLogin("bobssg");
        iUsuarioDaoRepository.save(usuario);
        System.out.println("Quantidade de usuarios no banco: " + iUsuarioDaoRepository.count());
    }

    @Test
    public void findUsuario() {
        Optional<Usuario> usuario = iUsuarioDaoRepository.findById(1L);
        System.out.println("Usuario Escolhido: " + (usuario.isPresent() ? usuario.get().getNome() : "Nenhum usuario encontrado"));

        usuario.ifPresent(user -> System.out.println("=== Usuario encontrado ==="));
        usuario.ifPresent(user -> System.out.println("Nome:" + user.getNome()));
        usuario.ifPresent(user -> System.out.println("Email: " + user.getEmail()));
        usuario.ifPresent(user -> user.getTelefones().forEach(System.out::println));
    }

    @Test
    public void listUsuarios() {

        Iterable<Usuario> usuarios = iUsuarioDaoRepository.findAll();

        for (Usuario user : usuarios) {
            System.out.println(user);
        }
        /* === Imprimindo lista de usuarios utilizando lambda Java 11 === */
//        usuarios.forEach(usuario -> System.out.println("Dados dos Usuario:\n" + usuario));
//        usuarios.forEach(System.out::println);
    }

    @Test
    public void updateUsuario() {

        Optional<Usuario> usuario = iUsuarioDaoRepository.findById(6L);

        Usuario user = usuario.orElse(null);
        assert user != null;
        user.setLogin("Patricia");

        iUsuarioDaoRepository.save(user);

//        Posso setar uma variavel do tipo do Objeto ao inves de ficar utilizando a classe Optional e ter que ficar utilizando usuario.get()
//        Usuario user = usuario.get();
    }

    @Test
    public void deleteUsuario() {

        /* Deletando por ID da Entidade */
//        iUsuarioDaoRepository.deleteById(7L);

        Optional<Usuario> user = iUsuarioDaoRepository.findById(1L);

        /* Deletando pelo Objeto inteiro */
        iUsuarioDaoRepository.delete(user.get());
    }

    @Test
    public void findByName() {
        List<Usuario> users =  iUsuarioDaoRepository.findByName("Gu".toLowerCase());
        for (Usuario user : users) {
            System.out.println(user);
        }

//        users.forEach(usuario -> System.out.println("Usuarios baseado no nome pesquisado: " + usuario));
//        users.forEach(System.out::println);
    }

    @Test
    public void findByNameParam() {
        List<Usuario> users =  iUsuarioDaoRepository.findByNameParam("g".toLowerCase());
        for (Usuario user : users) {
            System.out.println(user);
        }

//        users.forEach(usuario -> System.out.println("Usuarios baseado no nome pesquisado: " + usuario));
//        users.forEach(System.out::println);
    }

    @Test
    public void deleteByEmail() {
        iUsuarioDaoRepository.deleteByEmail("vagabalixo@gmail.com");
    }

    @Test
    public void updateEmailByName() {
        iUsuarioDaoRepository.updateEmailByName("barbarapaz@gmail.com","Barbara");
    }

    /*=================================== INICIO - Telefones ========================================================================*/

    @Test
    public void insertTelefone() {

        Optional<Usuario> usuario = iUsuarioDaoRepository.findById(2L);

        Telefone telefone = new Telefone();
        telefone.setNumero("3332789275");
        telefone.setTipo("Casa");
        telefone.setUsuario(usuario.get());

        iTelefoneDaoRepository.save(telefone);
    }
}
