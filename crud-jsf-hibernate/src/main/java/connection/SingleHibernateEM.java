package connection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class SingleHibernateEM {
    private EntityManagerFactory factory = null;

    public SingleHibernateEM() {
       init();
    }

    private void init() {
        try {
            if (factory == null) {
                factory = Persistence.createEntityManagerFactory("crud-jsf-hibernate");
                System.out.println("Conectado ao banco");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Produces
    @RequestScoped
    public EntityManager getEM() {
        return factory.createEntityManager();
    }

    public Object getPrimaryKey(Object object){
        return factory.getPersistenceUnitUtil().getIdentifier(object);
    }

    public EntityManagerFactory getFactory(){return factory;}
}
