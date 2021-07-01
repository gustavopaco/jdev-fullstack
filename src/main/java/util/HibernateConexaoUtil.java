package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateConexaoUtil {
    public static EntityManagerFactory factory = null;

    static {
        init();
    }

    private static void init(){
        try{
            if (factory == null){
                factory = Persistence.createEntityManagerFactory("mvn-jpa-hibernate");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static EntityManager getEntityManager(){
        return factory.createEntityManager(); // Prove a parte de persistencia
    }
}
