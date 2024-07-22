package com.pack.card.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pack.card.model.Card;

@Repository
public interface CardRepository extends MongoRepository<Card, String>{

}
