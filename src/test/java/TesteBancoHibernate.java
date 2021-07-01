import org.junit.jupiter.api.Test;
import util.HibernateConexaoUtil;

public class TesteBancoHibernate {

    @Test
    public void testHibernateUtil(){
        HibernateConexaoUtil.getEntityManager();
    }
}
