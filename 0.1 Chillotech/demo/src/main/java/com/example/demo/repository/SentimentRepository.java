package com.example.demo.repository;

import com.example.demo.entities.Sentiment;
import com.example.demo.enums.TypeSentiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SentimentRepository extends JpaRepository<Sentiment,Integer> {
    List<Sentiment> findByType(TypeSentiment type);
}
