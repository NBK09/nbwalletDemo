package com.NBWallet.layers.db.utils;

import com.NBWallet.common.config.ConfigurationManager;
import com.NBWallet.layers.db.entities.Account;
import com.NBWallet.layers.db.entities.AccountPlan;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        var cfg = ConfigurationManager.getDbConfig();

        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.connection.url", cfg.jdbcUrl());
        configuration.setProperty("hibernate.connection.username", cfg.username());
        configuration.setProperty("hibernate.connection.password", cfg.password());
        configuration.setProperty("hibernate.connection.driver_class", cfg.driverClassName());
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");

        configuration.addAnnotatedClass(AccountPlan.class);
        configuration.addAnnotatedClass(Account.class);

        return configuration.buildSessionFactory();
    }

}
