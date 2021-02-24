package org.sid.billingservice.KeycloakRestTemplate;


import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.sid.billingservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;




@CrossOrigin("http://localhost:4200")
@Controller
public class CustomerRestClient {

    @Autowired
    private KeycloakRestTemplate keycloakRestTemplate;

    public Customer getCustomer(@PathVariable(name = "id") Long id){

        Customer customer = keycloakRestTemplate.getForObject("http://localhost:8282/customers/"+id,Customer.class);

        return customer;
    }

}
