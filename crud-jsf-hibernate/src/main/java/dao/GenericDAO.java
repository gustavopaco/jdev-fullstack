package dao;

import connection.SingleHibernateEM;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

@Named
public class GenericDAO<E> implements Serializable {

    @Inject
    private EntityManager entityManager;

    @Inject
    private SingleHibernateEM singleHibernateEM;

//    public GenericDAO(){
//
//    }

//    public GenericDAO(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    public void insertEntity(Object entity) {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(entity);

        transaction.commit();
    }

    public Object getEntityByObject(E entity) {

        Object id = singleHibernateEM.getPrimaryKey(entity);

        Object e = entityManager.find(entity.getClass(), id);

        return e;
    }

    public Object getEntityByID(E entity, Long id) {

        Object e =  entityManager.find(entity.getClass(), id);

        return e;
    }


    public List<?> getEntities(Class<E> entitiy) {

        return entityManager.createQuery(" select u from " + entitiy.getName() + " u ", entitiy).getResultList();
    }

    public void updateEntity(E entity) {

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(entity);

        transaction.commit();
    }

    public void deleteEntityByObject(E entity) {

        Object e = getEntityByObject(entity);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.remove(e);

        transaction.commit();
    }

    public void deleteEntityByID(E entity, Long id) {

        Object e = getEntityByID(entity, id);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.remove(e);

        transaction.commit();
    }

    public void deleteStraight(E entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.remove(entity);

        transaction.commit();
    }
}
