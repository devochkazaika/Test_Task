package org.cwt.task.config;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.ext.Provider;
import org.cwt.task.repository.AnalyticRepository;
import org.cwt.task.repository.BookRentRepository;
import org.cwt.task.repository.BookRepository;
import org.cwt.task.repository.UserRepository;
import org.cwt.task.repository.impl.AnalyticRepositoryImpl;
import org.cwt.task.repository.impl.BookRentRepositoryImpl;
import org.cwt.task.repository.impl.BookRepositoryImpl;
import org.cwt.task.repository.impl.UserRepositoryImpl;
import org.cwt.task.service.AnalyticService;
import org.cwt.task.service.BookService;
import org.cwt.task.service.RentService;
import org.cwt.task.service.UserService;
import org.cwt.task.service.impl.AnalyticServiceImpl;
import org.cwt.task.service.impl.BookServiceImpl;
import org.cwt.task.service.impl.RentServiceImpl;
import org.cwt.task.service.impl.UserServiceImpl;
import org.cwt.task.utils.BookMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.modelmapper.ModelMapper;


@Provider
public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindFactory(EntityManagerFactoryProvider.class)
                .to(EntityManager.class)
                .in(Singleton.class);

        // Бины

        // DAO
        bind(BookRepositoryImpl.class).to(BookRepository.class).in(Singleton.class);
        bind(BookRentRepositoryImpl.class).to(BookRentRepository.class).in(Singleton.class);
        bind(UserRepositoryImpl.class).to(UserRepository.class).in(Singleton.class);
        bind(AnalyticRepositoryImpl.class).to(AnalyticRepository.class).in(Singleton.class);

        // Services
        bind(RentServiceImpl.class).to(RentService.class).in(Singleton.class);
        bind(BookServiceImpl.class).to(BookService.class).in(Singleton.class);
        bind(UserServiceImpl.class).to(UserService.class).in(Singleton.class);
        bind(AnalyticServiceImpl.class).to(AnalyticService.class).in(Singleton.class);

        // Utils
        bind(BookMapper.class).to(ModelMapper.class).in(Singleton.class);

    }
}

