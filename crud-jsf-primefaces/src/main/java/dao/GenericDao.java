package dao;

import connection.JPAUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

@Named
public class GenericDao<E> implements Serializable {

    @Inject
    private JPAUtil jpaUtil;

    @Inject
    private EntityManager entityManager;

    public void insertEntity(E entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }

    @SuppressWarnings(value = "unchecked")
    public E getEntity(E entity) {
        Object obj = jpaUtil.getPrimaryKey(entity);
        return (E) entityManager.find(entity.getClass(),obj);
    }

    public E getEntityByID(Class<E> entity, Long id) {
        return entityManager.find(entity,id);
    }

    @SuppressWarnings(value = "unchecked")
    public List<E> getEntityList(Class<E> entity) {
        return entityManager.createQuery("select p from " + entity.getCanonicalName() + " p").getResultList();
    }

    public void updateEntity(E entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(entity);
        transaction.commit();
    }

    public void deleteEntityStraight(E entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entity);
        transaction.commit();
    }

    public void deleteByID(Class<E> entity, Long id) throws Exception {
        E e = getEntityByID(entity,id);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(e);
        transaction.commit();
    }
}
