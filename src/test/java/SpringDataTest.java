import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring-config.xml"})
public class SpringDataTest {

    @Test
    public void testConexao() {
        System.out.println("Iniciou spring com sucesso");
    }

}
