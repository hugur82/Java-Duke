package com.example.demo.service;

import com.example.demo.entities.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void creer(Client client){
        Client clientDansLaBDD = this.clientRepository.findByEmail(client.getEmail());
        if (clientDansLaBDD == null) {
            this.clientRepository.save(client);
        }
    }

    public List<Client> rechercher(){
        return this.clientRepository.findAll();
    }

    public Client lire(int id) {
         Optional<Client> optionalClient = this.clientRepository.findById(id);
        return optionalClient.orElse(null);

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
