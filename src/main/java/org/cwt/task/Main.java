package org.cwt.task;


import jakarta.ws.rs.ApplicationPath;
import org.cwt.task.config.ApplicationBinder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;
import org.glassfish.jersey.servlet.ServletContainer;

@ApplicationPath("api")
public class Main extends ResourceConfig {
    public static final String BASE_URI = "http://localhost:" + System.getenv("LIBRARY_PORT") + "/api/";

    public Main() {

        packages("org.cwt.task");
        register(ValidationFeature.class);
        register(new ApplicationBinder()); // CDI

    }

    public static Server startServer() {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");

        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(new Main()));
        context.addServlet(jerseyServlet, "/api/*");
        jerseyServlet.setInitOrder(1);

        server.setHandler(context);
        return server;
    }

    public static void main(String[] args) throws Exception {
        final Server server = startServer();
        server.start();
        System.out.println("Jersey app started at " + BASE_URI);
        server.join();
    }
}
