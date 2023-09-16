package dao;

import model.Usuario;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface IUsuarioDaoRepository extends CrudRepository<Usuario, Long> {

    @Query(value = "select u from Usuario u where lower(u.nome) like %?1%")
    List<Usuario> findByName(String nome);

    @Lock(LockModeType.READ) /* Bloqueia o objeto para nao deixar que alguem consulte e atualize o mesmo objeto ao mesmo tempo */
    @Query(value = "select u from Usuario u where lower(u.nome) like %:username%")
    List<Usuario> findByNameParam(@Param(value = "username") String nomeUsuario);

    @Modifying /* Notacao que define que a Query executava ira alterar os dados no banco */
    @Transactional /* Notacao que define que ao executar a Query eh preciso abrir uma transacao */
    @Query(value = "delete from Usuario u where u.email = ?1")
    void deleteByEmail(String email);

    @Modifying /* Notacao que define que a Query executava ira alterar os dados no banco */
    @Transactional /* Notacao que define que ao executar a Query eh preciso abrir uma transacao */
    @Query(value = "update Usuario u set u.email = ?1 where u.nome = ?2")
    void updateEmailByName(String email, String nome);

//    Eh possivel sobrescrever metodos ja existente.... e depois so fazer a chamada do metodo salvarUsuarioApos
    default <S extends Usuario> S salvarUsuarioAposProcesso(S s) {
//        Processa aqui toda a informacao antes de salvar caso tenha necessidade => Alterar algo antes, Pesquisar algo antes, qualquer coisa...
        return save(s);
    }
}
