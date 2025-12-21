package com.example.demo.controller;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entities.Client;
import com.example.demo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "client")
public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ResponseStatus(value= HttpStatus.CREATED)
    @PostMapping(consumes=APPLICATION_JSON_VALUE)
    public void creer(@RequestBody Client client){
        this.clientService.creer(client);

    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Stream<ClientDTO> rechercher(){
        return this.clientService.rechercher();
    }

    @GetMapping(path="{id}",produces = APPLICATION_JSON_VALUE)
    public Client lire(@PathVariable int id){
           return this.clientService.lire(id);


    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping(path="{id}",consumes = APPLICATION_JSON_VALUE)
    public void modifier(@PathVariable int id,@RequestBody Client client){
        this.clientService.modifier(id, client);
    }

}
