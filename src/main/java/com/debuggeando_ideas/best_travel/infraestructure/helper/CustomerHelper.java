package com.debuggeando_ideas.best_travel.infraestructure.helper;

import com.debuggeando_ideas.best_travel.domain.entities.*;
import com.debuggeando_ideas.best_travel.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.ReservationRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TicketRepository;
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
public class CustomerHelper {

   private final CustomerRepository customerRepository;

   public void incrase(String customerId, Class<?> type){

       var customerToUpdate = this.customerRepository.findById(customerId).orElseThrow();
       switch (type.getSimpleName()){
           case "TourService" -> customerToUpdate.setTotalTours(customerToUpdate.getTotalTours() +1);
           case "TicketService" -> customerToUpdate.setTotalFlights(customerToUpdate.getTotalFlights() +1);
           case "ReservationService" -> customerToUpdate.setTotalLodgings(customerToUpdate.getTotalLodgings() +1);
       }
       this.customerRepository.save(customerToUpdate);
   }
}
