package dao;

import util.HibernateConexaoUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class GenericDAO<E> {
    private EntityManager entityManager = HibernateConexaoUtil.getEntityManager();

    public void insertUsuario(E object) {

        // Obtem a transacao do entityManager
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Grava no Banco o Usuario || EntityManager => preparedstatement.executeUpdate()
        entityManager.persist(object);

        // Comita as alteracoes no banco || EntityTransaction => connection.commit() ou connect.rollback()
        transaction.commit();
    }
}
