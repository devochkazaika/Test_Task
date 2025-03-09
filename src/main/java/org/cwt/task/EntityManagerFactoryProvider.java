package org.cwt.task;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.glassfish.hk2.api.Factory;

@ApplicationScoped
public class EntityManagerFactoryProvider implements Factory<EntityManager> {

    private final EntityManagerFactory emf;

    public EntityManagerFactoryProvider() {
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
