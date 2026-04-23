package com.hospedagem.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
@ConditionalOnProperty(name = "app.seed-data", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class SeedDataRunner implements ApplicationRunner {

    private final SeedDataService seedDataService;

    @Override
    public void run(ApplicationArguments args) {
        if (seedDataService.seedIfEmpty()) {
            log.info("Database seeded with demo residência, clientes, quartos e aluguéis.");
        } else {
            log.debug("Seed skipped: database already contains residências.");
        }
    }
}
