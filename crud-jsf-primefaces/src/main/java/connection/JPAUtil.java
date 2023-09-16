package connection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JPAUtil {
    private EntityManagerFactory factory = null;

    public JPAUtil() {
       init();
    }

    private void init() {
        try {
            if (factory == null){
                factory = Persistence.createEntityManagerFactory("web-mvn-jpa-hb-cdi-jsf");
                System.out.println("Conectou ao banco de Dados....");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Produces
    @RequestScoped
    public EntityManager getEM(){
        return factory.createEntityManager();
    }

    public EntityManagerFactory getFactory() {return factory;}

    public Object getPrimaryKey(Object entity) {
        return factory.getPersistenceUnitUtil().getIdentifier(entity);
    }
}
