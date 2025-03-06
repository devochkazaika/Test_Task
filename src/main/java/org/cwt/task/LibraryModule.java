//package org.cwt.task;
//
//import com.google.inject.AbstractModule;
//import com.google.inject.Provides;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import javax.sql.DataSource;
//
//public class LibraryModule extends AbstractModule {
//    @Provides
//    public DataSource provideDataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl("jdbc:postgresql://localhost:5432/library");
//        config.setUsername("postgres");
//        config.setPassword("password");
//        config.setMaximumPoolSize(10);
//        return new HikariDataSource(config);
//    }
//}
