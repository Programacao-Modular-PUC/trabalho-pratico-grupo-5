package com.hospedagem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class HospedagemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospedagemApplication.class, args);
    }
}
