package com.NBWallet.layers.db.repositories;

import com.NBWallet.layers.db.entities.AccountPlan;
import com.NBWallet.layers.db.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class AccountPlanRepository extends RepositoryBaseImpl<AccountPlan, Integer> {
    public AccountPlanRepository() {
        super(AccountPlan.class);
    }


    public AccountPlan getByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

//            Query<AccountPlan> query = session
//                    .createQuery("from AccountPlan as p where p.name = :name", AccountPlan.class);
//            query.setParameter("name", name);

            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(AccountPlan.class);
            var root = criteriaQuery.from(AccountPlan.class);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get("name"), name));

            return session.createQuery(criteriaQuery).getSingleResult();
        }
    }

    public List<AccountPlan> getListByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

//            Query<AccountPlan> query = session
//                    .createQuery("from AccountPlan as p where p.name = :name", AccountPlan.class);
//            query.setParameter("name", name);

            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(AccountPlan.class);
            var root = criteriaQuery.from(AccountPlan.class);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get("name"), name));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
