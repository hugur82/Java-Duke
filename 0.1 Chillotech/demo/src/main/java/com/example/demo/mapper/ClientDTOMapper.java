package com.example.demo.mapper;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entities.Client;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ClientDTOMapper implements Function<Client, ClientDTO> {

    @Override
    public ClientDTO apply(Client client) {
        return new ClientDTO(client.getId(), client.getEmail(), client.getTelephone());
    }
}
