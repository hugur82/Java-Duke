package com.supabank.bankmanagementsystem.service;

import com.supabank.bankmanagementsystem.dto.CustomerRequestDTO;
import com.supabank.bankmanagementsystem.dto.CustomerRequestPasswordUpdateDTO;
import com.supabank.bankmanagementsystem.dto.CustomerRequestUpdateDTO;
import com.supabank.bankmanagementsystem.dto.CustomerResponseDTO;
import com.supabank.bankmanagementsystem.entity.CustomerEntity;
import com.supabank.bankmanagementsystem.exception.CustomerDeletionNotAllowedException;
import com.supabank.bankmanagementsystem.exception.CustomerNotFoundException;
import com.supabank.bankmanagementsystem.repository.AccountRepository;
import com.supabank.bankmanagementsystem.repository.CustomerRepository;
import com.supabank.bankmanagementsystem.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository,
                           AccountRepository accountRepository,
                           TransactionRepository transactionRepository,
                           PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerResponseDTO> findAll() {
         return customerRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setFirstName(customerRequestDTO.getFirstName());
        customerEntity.setLastName(customerRequestDTO.getLastName());
        customerEntity.setBirthDate(customerRequestDTO.getBirthDate());
        customerEntity.setPhone(customerRequestDTO.getPhone());
        customerEntity.setEmail(customerRequestDTO.getEmail());
        customerEntity.setPassword(
                passwordEncoder.encode(customerRequestDTO.getPassword())
        );
        customerEntity.setAddress(customerRequestDTO.getAddress());
        customerEntity.setPostalCode(customerRequestDTO.getPostalCode());
        customerEntity.setCity(customerRequestDTO.getCity());
        customerEntity.setStatus(customerRequestDTO.getStatus());

        CustomerEntity savedCustomer = customerRepository.save(customerEntity);

        return toResponseDTO(savedCustomer);
    }

    private CustomerResponseDTO toResponseDTO(CustomerEntity customer) {
        return new CustomerResponseDTO(customer.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getBirthDate(), customer.getAddress(), customer.getPostalCode(), customer.getCity(), customer.getStatus());
    }

    public CustomerResponseDTO updateById(
            Long id,
            CustomerRequestUpdateDTO customerRequestUpdateDTO) {

        CustomerEntity customerEntity = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id " + id));

        customerEntity.setFirstName(customerRequestUpdateDTO.getFirstName());
        customerEntity.setLastName(customerRequestUpdateDTO.getLastName());
        customerEntity.setBirthDate(customerRequestUpdateDTO.getBirthDate());
        customerEntity.setPhone(customerRequestUpdateDTO.getPhone());
        customerEntity.setEmail(customerRequestUpdateDTO.getEmail());
        customerEntity.setAddress(customerRequestUpdateDTO.getAddress());
        customerEntity.setPostalCode(customerRequestUpdateDTO.getPostalCode());
        customerEntity.setCity(customerRequestUpdateDTO.getCity());
        customerEntity.setStatus(customerRequestUpdateDTO.getStatus());

        CustomerEntity savedCustomer = customerRepository.save(customerEntity);

        return toResponseDTO(savedCustomer);
    }

    @Transactional
    public void deleteById(Long id) {

        CustomerEntity customerEntity = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Id " + id + " not found for delete"));

        if (transactionRepository.existsByCustomerId(id)) {
            throw new CustomerDeletionNotAllowedException(
                    "Customer cannot be deleted because transactions exist."
            );
        }

        accountRepository.deleteByCustomerCustomerId(id);

        customerRepository.delete(customerEntity);
    }
    public CustomerResponseDTO findCustomerById(Long id) {
        CustomerEntity customerEntity = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer with id " + id + " not found"));

        return toResponseDTO(customerEntity);
    }
    public void updatePassword(
            Long id,
            CustomerRequestPasswordUpdateDTO passwordUpdateDTO) {

        CustomerEntity customerEntity = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer with id " + id + " not found"));

        if (!passwordEncoder.matches(
                passwordUpdateDTO.getCurrentPassword(),
                customerEntity.getPassword())) {

            throw new IllegalArgumentException("Current password is incorrect");
        }

        String encodedPassword = passwordEncoder.encode(
                passwordUpdateDTO.getNewPassword()
        );

        customerEntity.setPassword(encodedPassword);

        customerRepository.save(customerEntity);
    }

}
