package com.supabank.bankmanagementsystem.controller;

import com.supabank.bankmanagementsystem.entity.CustomerEntity;
import com.supabank.bankmanagementsystem.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<CustomerEntity> findAll() {
        return customerService.findAll();
    }

    @PostMapping("/addCustomer")
    public CustomerEntity addCustomer(@RequestBody @Valid CustomerEntity customerEntity) {
        return customerService.createCustomer(customerEntity);
    }

}
