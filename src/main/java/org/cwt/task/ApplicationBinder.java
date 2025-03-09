package org.cwt.task;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import org.cwt.task.repository.BookRepository;
import org.cwt.task.repository.impl.BookRepositoryImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindFactory(EntityManagerFactoryProvider.class)
                .to(EntityManager.class)
                .in(ApplicationScoped.class);
        bind(BookRepositoryImpl.class).to(BookRepository.class).in(Singleton.class);
    }
}
