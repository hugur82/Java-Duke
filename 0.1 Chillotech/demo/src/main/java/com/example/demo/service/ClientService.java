package com.example.demo.service;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entities.Client;
import com.example.demo.mapper.ClientDTOMapper;
import com.example.demo.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ClientService {

    private ClientDTOMapper clientDTOMapper;
    private final ClientRepository clientRepository;


    public ClientService(ClientRepository clientRepository, ClientDTOMapper clientDTOMapper) {
        this.clientRepository = clientRepository;
        this.clientDTOMapper = clientDTOMapper;
    }

    public void creer(Client client){
        Client clientDansLaBDD = this.clientRepository.findByEmail(client.getEmail());
        if (clientDansLaBDD == null) {
            this.clientRepository.save(client);
        }
        else{
            throw new RuntimeException("Cet email existe déjà");
        }
    }

    public Stream<ClientDTO> rechercher(){

        return this.clientRepository.findAll()
                .stream()
                .map(clientDTOMapper);
    }

    public Client lire(int id) {
         Optional<Client> optionalClient = this.clientRepository.findById(id);
        return optionalClient.orElseThrow(
                ()-> new EntityNotFoundException("Aucun client retrouvé"));

    }

    public Client lireOuCreer(Client clientAcreer){
        Client clientDansLaBDD = this.clientRepository.findByEmail(clientAcreer.getEmail());
        if (clientDansLaBDD == null) {
            clientDansLaBDD = this.clientRepository.save(clientAcreer);
        }
        return clientDansLaBDD;
    }

    public void modifier(int id, Client client) {
        Client clientDansLaBDD = this.lire(id);
        if(clientDansLaBDD.getId() == client.getId()) {
            clientDansLaBDD.setTelephone(client.getTelephone());
            clientDansLaBDD.setEmail(client.getEmail());
            this.clientRepository.save(clientDansLaBDD);
        }
    }
}
