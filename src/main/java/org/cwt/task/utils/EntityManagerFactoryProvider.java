package org.cwt.task.utils;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.glassfish.hk2.api.Factory;


@Singleton
public class EntityManagerFactoryProvider implements Factory<EntityManager> {

    private final EntityManagerFactory emf;

    public EntityManagerFactoryProvider() {
//        Map<String, String> properties = new HashMap<>();
//        properties.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/library");
//        properties.put("jakarta.persistence.jdbc.user", "user");
//        properties.put("jakarta.persistence.jdbc.password", "password");

        this.emf = Persistence.createEntityManagerFactory("library");
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
}

