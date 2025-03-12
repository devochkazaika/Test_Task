package org.cwt.task.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.glassfish.hk2.api.Factory;

import java.util.HashMap;
import java.util.Map;


@Singleton
public class EntityManagerFactoryProvider implements Factory<EntityManager> {

    private final EntityManagerFactory emf;

    @PostConstruct
    public void init() {
        EntityManager em = provide();
        em.close();
    }

    public EntityManagerFactoryProvider() {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
        properties.put("jakarta.persistence.jdbc.url", System.getenv("DATABASE_URL"));
        properties.put("jakarta.persistence.jdbc.user",  System.getenv("DATABASE_USER"));
        properties.put("jakarta.persistence.jdbc.password",  System.getenv("DATABASE_PASSWORD"));

        properties.put("jakarta.persistence.schema-generation.database.action", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
//        properties.put("hibernate.use_sql_comments", "true");

        // Настройки HikariCP
        properties.put("hibernate.hikari.minimumIdle", "5");
        properties.put("hibernate.hikari.maximumPoolSize", "10");
        properties.put("hibernate.hikari.idleTimeout", "30000");
        properties.put("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");


        this.emf = Persistence.createEntityManagerFactory("library", properties);
    }
    @Override
    public EntityManager provide() {
        return emf.createEntityManager();
    }

    @Override
    public void dispose(EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
    @PreDestroy
    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }

}

