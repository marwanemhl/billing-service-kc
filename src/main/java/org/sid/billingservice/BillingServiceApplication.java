package org.sid.billingservice;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.KeycloakRestTemplate.CustomerRestClient;
import org.sid.billingservice.KeycloakRestTemplate.ProductItemRestClient;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;


@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }



    @Bean
    CommandLineRunner start(
            BillRepository billRepository,
            ProductItemRepository productItemRepository,
            CustomerRestClient customerRestClient,
            ProductItemRestClient productItemRestClient, RepositoryRestConfiguration restConfiguration){
        restConfiguration.exposeIdsFor(Bill.class);
        return args -> {




        };
    }

}
