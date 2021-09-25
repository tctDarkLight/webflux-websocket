package com.example.websocketwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Documentation APIs v1.0"))*/
public class WebsocketWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketWebfluxApplication.class, args);
        //System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
