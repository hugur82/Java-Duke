package com.supabank.bankmanagementsystem.controller;

import com.supabank.bankmanagementsystem.dto.CustomerRequestDTO;
import com.supabank.bankmanagementsystem.dto.CustomerResponseDTO;
import com.supabank.bankmanagementsystem.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public List<CustomerResponseDTO> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/customers/{id}")
    public CustomerResponseDTO findById(@PathVariable Long id){
        return customerService.findCustomerById(id);
    }

    @PostMapping("/customers")
    public CustomerResponseDTO addCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        return customerService.createCustomer(customerRequestDTO);
    }

    @PutMapping("/customers/{id}")
    public CustomerResponseDTO updateCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO, @PathVariable("id") Long id) {
        return customerService.updateById(id, customerRequestDTO);
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long id) {
       customerService.deleteById(id);
    }
}
