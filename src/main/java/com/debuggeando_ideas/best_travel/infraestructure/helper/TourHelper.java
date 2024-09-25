package com.debuggeando_ideas.best_travel.infraestructure.helper;

import com.debuggeando_ideas.best_travel.domain.entities.*;
import com.debuggeando_ideas.best_travel.domain.repositories.*;
import com.debuggeando_ideas.best_travel.util.BestTravelUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Transactional
@Component
@Slf4j
@AllArgsConstructor
public class TourHelper {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;


    public Set<TicketEntity> createTickets(Set<FlyEntity> flights, CustomerEntity customer){
        var response = new HashSet<TicketEntity>(flights.size());

        flights.forEach(fly ->{
            var ticketToPersist = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .fly(fly)
                    .customer(customer)
                    .price(fly.getPrice().add(fly.getPrice().multiply(BigDecimal.valueOf(Math.random()))))
                    .purchaseDate(LocalDate.now())
                    .arrivalDate(BestTravelUtil.getRandomLatter())
                    .departureDate(BestTravelUtil.getRandomSoon())
                    .build();

            response.add(this.ticketRepository.save(ticketToPersist));
        });

        return response;
    }


    public Set<ReservationEntity> createReservations(HashMap<HotelEntity, Integer> hotels, CustomerEntity customer){

        var response = new HashSet<ReservationEntity>(hotels.size());

       hotels.forEach((hotel, totalDays) -> {

           var reservationToPersist = ReservationEntity.builder()
                   .id(UUID.randomUUID())
                   .hotel(hotel)
                   .customer(customer)
                   .totalDays(totalDays)
                   .dateTimeReservation(LocalDateTime.now())
                   .dateStart(LocalDate.now())
                   .dateEnd(LocalDate.now().plusDays(totalDays))
                   .price(hotel.getPrice().add(hotel.getPrice().multiply(BigDecimal.valueOf(Math.random()))))
                   .build();

           response.add(this.reservationRepository.save(reservationToPersist));
       });

        return response;
    }

}
