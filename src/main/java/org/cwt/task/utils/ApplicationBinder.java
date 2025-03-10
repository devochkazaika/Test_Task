package org.cwt.task.utils;

import jakarta.persistence.EntityManager;
import org.cwt.task.repository.BookRentRepository;
import org.cwt.task.repository.BookRepository;
import org.cwt.task.repository.UserRepository;
import org.cwt.task.repository.impl.BookRentRepositoryImpl;
import org.cwt.task.repository.impl.BookRepositoryImpl;
import org.cwt.task.repository.impl.UserRepositoryImpl;
import org.cwt.task.service.BookService;
import org.cwt.task.service.RentService;
import org.cwt.task.service.UserService;
import org.cwt.task.service.impl.BookServiceImpl;
import org.cwt.task.service.impl.RentServiceImpl;
import org.cwt.task.service.impl.UserServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.modelmapper.ModelMapper;

import javax.inject.Singleton;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindFactory(EntityManagerFactoryProvider.class)
                .to(EntityManager.class)
                .in(Singleton.class);


        bind(RentServiceImpl.class).to(RentService.class).in(Singleton.class);
        bind(ModelMapper.class).to(ModelMapper.class).in(Singleton.class);
        bind(BookRepositoryImpl.class).to(BookRepository.class).in(Singleton.class);
        bind(BookRentRepositoryImpl.class).to(BookRentRepository.class).in(Singleton.class);
        bind(BookServiceImpl.class).to(BookService.class).in(Singleton.class);
        bind(UserServiceImpl.class).to(UserService.class).in(Singleton.class);
        bind(UserRepositoryImpl.class).to(UserRepository.class).in(Singleton.class);
    }
}

