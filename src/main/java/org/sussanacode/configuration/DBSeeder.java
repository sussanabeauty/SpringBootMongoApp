package org.sussanacode.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.sussanacode.Repository.HotelRepository;
import org.sussanacode.model.Address;
import org.sussanacode.model.Hotel;
import org.sussanacode.model.Review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DBSeeder implements CommandLineRunner {


    private final HotelRepository hotelRepo;

    @Autowired
    public DBSeeder(HotelRepository hotelRepo) {
        this.hotelRepo = hotelRepo;
    }


    @Override
    public void run(String... args) throws Exception {
        Hotel hilton = new Hotel(
                "Hilton",
                126,
                new Address("Mt.Pleasant", "Georgia"),
                Arrays.asList(
                        new Review("maryadams", 7, false),
                        new Review("andrewholmes", 9, true),
                        new Review("nannykays", 8, false)
                )
        );

        Hotel hyatt = new Hotel(
                "Hyatt",
                99,
                new Address("Iowa City", "Iowa"),
                Arrays.asList(
                        new Review("macjacobs", 8, false),
                        new Review("kellyqui", 6, false),
                        new Review("teddsean", 7, true)
                )
        );

        Hotel ramada = new Hotel(
                "Ramada",
                78,
                new Address("Fairfield", "Ohio"),
                Arrays.asList(
                        new Review("cassandrajones", 9, true),
                        new Review("bennkey", 7, false)

                )
        );

        Hotel marriot = new Hotel(
                "Marriot",
                135,
                new Address("Duluth", "Pennsylvania"),
                new ArrayList<>()
        );

        //drop all hotels
        this.hotelRepo.deleteAll();

        //add our new hotel records to the database
        List<Hotel> hotels = Arrays.asList(hilton, hyatt, ramada, marriot);
        this.hotelRepo.saveAll(hotels);

    }
}
