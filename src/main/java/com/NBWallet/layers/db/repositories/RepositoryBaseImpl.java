package com.NBWallet.layers.db.repositories;

import com.NBWallet.layers.db.utils.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public class RepositoryBaseImpl<TEntity, ID extends Serializable> implements RepositoryBase<TEntity, ID> {

    private final Class<TEntity> entityClass;


    public RepositoryBaseImpl(Class<TEntity> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void create(TEntity tEntity) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(tEntity);
            transaction.commit();
        }
    }

    @Override
    public TEntity get(ID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(entityClass, id);
        }
    }

    @Override
    public List<TEntity> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TEntity> criteriaQuery = builder.createQuery(entityClass);
            Root<TEntity> root = criteriaQuery.from(entityClass);
            criteriaQuery.select(root);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public void update(TEntity tEntity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(tEntity);
            transaction.commit();
        }
    }

    @Override
    public void delete(TEntity tEntity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(tEntity);
            transaction.commit();
        }
    }
}
