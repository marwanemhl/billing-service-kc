package org.sid.billingservice.web;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;



import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;


@RestController

public class BillingRestController {



    ProductItemRepository productItemRepository;
    ProductItemRestClient productItemRestClient;

    public BillingRestController(ProductItemRepository productItemRepository, ProductItemRestClient productItemRestClient, BillRepository billRepository, CustomerRestClient customerRestClient) {
        this.productItemRepository = productItemRepository;
        this.productItemRestClient = productItemRestClient;
        this.billRepository = billRepository;
        this.customerRestClient = customerRestClient;
    }

    BillRepository billRepository;
    CustomerRestClient customerRestClient;







    @CrossOrigin("http://localhost:4200")
    @GetMapping(path = "/initbills")
    public void initBills() throws ParseException {
        Customer customer = customerRestClient.getCustomer(1L);

        Bill bill1=billRepository.save(new Bill(null,new Date(),null,null,customer.getId()));


        JSONObject productPagedModel=productItemRestClient.pageProducts();


        JSONObject json = (JSONObject)new JSONParser().parse(productPagedModel.toJSONString());
        JSONObject results = (JSONObject) json.get("_embedded");
        JSONArray products = (JSONArray ) results.get("products");

        products.forEach(kk->{
            System.out.printf(kk.toString());
            JSONObject jj = (JSONObject) kk;
            try {
                JSONObject json2 = (JSONObject)new JSONParser().parse(jj.toJSONString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String price =  jj.get("price").toString();
            String id = jj.get("id").toString();

            ProductItem productItem=new ProductItem();
            productItem.setPrice(Double.parseDouble(price));
            productItem.setQuantity(1+new Random().nextInt(100));
            productItem.setBill(bill1);
            productItem.setProductID(Long.parseLong(id));
            productItemRepository.save(productItem);
        });













    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable Long id){

        Bill bill=billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomer(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi->{
          Product product = productItemRestClient.getProduct(pi.getProductID());
          pi.setProduct(product);
        });

        return bill;
    }

}
