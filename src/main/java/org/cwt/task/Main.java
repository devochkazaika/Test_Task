package org.cwt.task;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
@ApplicationPath("api")
public class Main extends ResourceConfig {
    public static final String BASE_URI = "http://localhost:8080/api/";

    public Main() {
        packages("org.cwt.task");
        register(new ApplicationBinder()); // Добавляем поддержку CDI
    }

    public static HttpServer startServer() {
        final ResourceConfig resourceConfig = new Main();
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("Jersey app started at " + BASE_URI);
        System.in.read();
        server.stop();
    }
}


