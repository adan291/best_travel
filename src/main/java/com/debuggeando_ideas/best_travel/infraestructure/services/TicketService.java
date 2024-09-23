package com.debuggeando_ideas.best_travel.infraestructure.services;

import com.debuggeando_ideas.best_travel.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel.api.models.responses.FlyResponse;
import com.debuggeando_ideas.best_travel.api.models.responses.TicketResponse;
import com.debuggeando_ideas.best_travel.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.*;
import com.debuggeando_ideas.best_travel.infraestructure.abstract_services.ITicketService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.parsing.BeanEntry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {

    private final CustomerRepository customerRepository;
    private final FlyRepository flyRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse create(TicketRequest request) {
       var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
       var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

       var ticketToPersist = TicketEntity.builder()
               .id(UUID.randomUUID())
               .fly(fly)
               .customer(customer)
               .price(fly.getPrice().multiply(BigDecimal.valueOf(Math.random())))
               .purchaseDate(LocalDate.now())
               .arrivalDate(LocalDateTime.now())
               .departureDate(LocalDateTime.now())
               .build();

       var ticketPersisted = this.ticketRepository.save(ticketToPersist);

       log.info("Ticket saved with id {}", ticketPersisted.getId());

       return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        return null;
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID uuid) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    private TicketResponse entityToResponse(TicketEntity entity){
        var ticketResponse = new TicketResponse();
        BeanUtils.copyProperties(entity, ticketResponse);

        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);

        ticketResponse.setFly(flyResponse);

        return ticketResponse;
    }

}
