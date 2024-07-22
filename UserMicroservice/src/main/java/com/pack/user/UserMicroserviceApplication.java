package com.pack.user;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexOptions;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@OpenAPIDefinition
@SpringBootApplication
public class UserMicroserviceApplication {
	
	@Autowired
	MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}
	 @PostConstruct
	    public void initIndexes() {
//	        IndexOperations indexOps = mongoTemplate.indexOps("User");
//	        IndexOptions indexOptions = new IndexOptions().unique();
//	        Index index = new Index().on("email", Direction.ASC).unique();
//	        indexOps.ensureIndex(index);
		 

			mongoTemplate.indexOps("User").ensureIndex(new Index("email", Direction.ASC).unique());
	    }
	
}
