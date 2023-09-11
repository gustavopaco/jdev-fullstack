import connection.JPAUtil;
import org.junit.jupiter.api.Test;

public class TesteBanco {

    @Test
    public void testeBanco() {
        JPAUtil jpaUtil = new JPAUtil();
        jpaUtil.getEM();
    }
}
