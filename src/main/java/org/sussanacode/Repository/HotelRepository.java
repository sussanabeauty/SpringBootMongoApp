package org.sussanacode.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.sussanacode.model.Hotel;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String>, QuerydslPredicateExecutor<Hotel> {

    Optional<Hotel> findById(String id);

    //find by price less than the provided less price
    List<Hotel> findByPricePerNightLessThan(int maxPrice);

    //find by address
    @Query(value = "{'address.city' : ?0}")
    List<Hotel> findByCity(String city);

}
