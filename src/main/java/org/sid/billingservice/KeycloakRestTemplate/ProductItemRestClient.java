package org.sid.billingservice.KeycloakRestTemplate;




import net.minidev.json.JSONObject;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.sid.billingservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin("http://localhost:4200")
@Controller
public class ProductItemRestClient {
    @Autowired
    private KeycloakRestTemplate keycloakRestTemplate;

    public JSONObject pageProducts(){
        JSONObject products = keycloakRestTemplate.getForObject("http://localhost:8283/products/",JSONObject.class);

        return products;
    };

    public Product getProduct(@PathVariable(name = "id") Long id){
        Product product = keycloakRestTemplate.getForObject("http://localhost:8283/products/"+id,Product.class);
        return product;
    }
}
