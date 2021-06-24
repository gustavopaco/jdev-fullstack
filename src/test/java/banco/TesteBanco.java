package banco;


import com.curso.praticaweb.connection.SingleConnection;
import com.curso.praticaweb.dao.ProjetosDAO;
import com.curso.praticaweb.models.Projetos;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TesteBanco {

    @Test
    public void testeBancoDados(){
        SingleConnection.getConnection();
    }

    @Test
    public void testeOneToMany(){
        List<Projetos> projetosList = new ProjetosDAO().getProjetos();

        System.out.println(projetosList);
    }
}
