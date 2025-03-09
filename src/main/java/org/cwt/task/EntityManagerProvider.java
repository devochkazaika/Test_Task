package org.cwt.task;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerProvider {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
