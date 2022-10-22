package com.query.spring.kafka.api.repository;

import com.commons.dto.AvailibleProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AvailiableProductsRepo extends MongoRepository<AvailibleProduct, String> {
}
