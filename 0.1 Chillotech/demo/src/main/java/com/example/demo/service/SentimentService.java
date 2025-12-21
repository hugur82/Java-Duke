package com.example.demo.service;

import com.example.demo.entities.Client;
import com.example.demo.entities.Sentiment;
import com.example.demo.enums.TypeSentiment;
import com.example.demo.repository.SentimentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentimentService {
    private  ClientService clientService;
    private SentimentRepository sentimentRepository;

    public SentimentService(ClientService clientService, SentimentRepository sentimentRepository) {
        this.clientService = clientService;
        this.sentimentRepository = sentimentRepository;
    }

    public List<Sentiment> rechercher(TypeSentiment type){
        if (type==null)
            return this.sentimentRepository.findAll();
        else
            return this.sentimentRepository.findByType(type);

    }

    public void creer(Sentiment sentiment){
        Client client = this.clientService.lireOuCreer(sentiment.getClient());
        sentiment.setClient(client);

        sentiment.setType(TypeSentiment.POSITIF);
        if(sentiment.getTexte().contains("pas")){
            sentiment.setType(TypeSentiment.NEGATIF);
        }

        this.sentimentRepository.save(sentiment);
    }


    public void supprimer(int id) {
        this.sentimentRepository.deleteById(id);
    }

}
