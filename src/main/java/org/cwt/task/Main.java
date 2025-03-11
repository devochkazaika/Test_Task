package org.cwt.task;


import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.ws.rs.ApplicationPath;
import org.cwt.task.utils.ApplicationBinder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;
import org.glassfish.jersey.servlet.ServletContainer;

@ApplicationPath("api")
public class Main extends ResourceConfig {
    public static final String BASE_URI = "http://localhost:8080/api/";

    public Main() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info().title("Library API")
                        .version("1.0")
                        .description("Автоматически сгенерированное API для управления библиотекой"));

        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(openAPI)
                .prettyPrint(true);

        System.out.println(oasConfig.);

//        register(new OpenApiResource().openApiConfiguration(oasConfig));
        packages("org.cwt.task");
        register(ValidationFeature.class);
        register(new ApplicationBinder()); // CDI
    }

    public static Server startServer() {
        // Создаем сервер Jetty
        Server server = new Server(8080);

        // Настроим обработчик сервлетов
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");

        // Добавляем сервлет Jersey
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(new Main()));
        context.addServlet(jerseyServlet, "/api/*");

        // Настроим сервер
        server.setHandler(context);
        return server;
    }

    public static void main(String[] args) throws Exception {
        final Server server = startServer();
        server.start();
        System.out.println("Jersey app started at " + BASE_URI);
        server.join(); // Ждем, пока сервер работает
    }
}
