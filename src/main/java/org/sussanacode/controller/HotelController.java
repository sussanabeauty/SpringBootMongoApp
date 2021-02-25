package org.sussanacode.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sussanacode.Repository.HotelRepository;
import org.sussanacode.model.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelRepository hotelRepo;

    @Autowired
    public HotelController(HotelRepository hotelRepo) {
        this.hotelRepo = hotelRepo;
    }

    //display all hotels
    @GetMapping("/all")
    public List<Hotel> getAll(){
        List<Hotel> hotels = this.hotelRepo.findAll();

        return hotels;
    }

    //find by hotel id
    @GetMapping("/{id}")
    public Optional<Hotel> getById(@PathVariable("id") String id){

        Optional<Hotel> hotel = this.hotelRepo.findById(id) ;

        return hotel;

    }

    //find by hotel id
    @GetMapping("/price/{maxPrice}")
    public List<Hotel> getByPricePerNight(@PathVariable("maxPrice") int maxPrice){
        List<Hotel> hotels = this.hotelRepo.findByPricePerNightLessThan(maxPrice);

        return hotels;

    }

    //find hotel by city
    @GetMapping("/address/{city}")
    public List<Hotel> getByAddress(@PathVariable("city") String city){
        List<Hotel> hotels = this.hotelRepo.findByCity(city);

        return hotels;

    }

    //find hotel by country
    @GetMapping("/country/{country}")
    public List<Hotel> getByCountry(@PathVariable("country") String country){
        //create a query class(QHotel)
        QHotel qHotel = new QHotel("hotel");

        //using the query class we create to filter through our class
        BooleanExpression filterByCountry = qHotel.address.country.eq(country);

        //we can then pass the filter to the findAll method.
        List<Hotel> hotels = (List<Hotel>) this.hotelRepo.findAll(filterByCountry);

        return hotels;

    }

    //find hotel by max-price and reviews
    @GetMapping("/recommended")
    public List<Hotel> getRecommended(){
        final int maxPrice = 100;
        final int minRating = 8;

        //create a query class(QHotel)
        QHotel qHotel = new QHotel("hotel");

        //using the query class we create to filter through our class
        BooleanExpression filterByPrice = qHotel.pricePerNight.lt(maxPrice);
        BooleanExpression filterByRating = qHotel.reviews.any().rating.gt(minRating);

        //we can then pass the filter to the findAll method.
        List<Hotel> hotels = (List<Hotel>) this.hotelRepo.findAll(filterByPrice.and(filterByRating));

        return hotels;

    }



    //create new  hotel record in the database
    @PostMapping
    public Hotel insertNewHotel(@RequestBody Hotel hotel){
        this.hotelRepo.save(hotel);

        return hotel;
    }


    //update new hotel into record
    @PutMapping("/{id}")
    public Hotel updateHotel(@PathVariable("id") String id, @RequestBody Hotel hotel){
        hotel.setId(id);
        this.hotelRepo.save(hotel);
        //this.hotelRepo.insert(hotel);

        return hotel;

    }

    //delete hotel record from the database
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable("id") String id){
        this.hotelRepo.deleteById(id);
    }


}
