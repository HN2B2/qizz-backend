package tech.qizz.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
//        Server devServer = new Server();
//        devServer.setUrl(devUrl);
//        devServer.setDescription("Server URL in Development environment");

//        Server prodServer = new Server();
//        prodServer.setUrl(prodUrl);
//        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
//        contact.setEmail("quydx.work@gmail.com");
//        contact.setName("LiusDev");
//        contact.setUrl("https://github.com/LiusDev");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info().title("Qizz App API")
                .description("This API provides service for Qizz")
                .version("0.1.0")
                .contact(contact)
                .license(mitLicense);

        return new OpenAPI().info(info);
//        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
