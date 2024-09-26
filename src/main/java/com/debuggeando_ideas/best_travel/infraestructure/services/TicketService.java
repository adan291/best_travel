package com.debuggeando_ideas.best_travel.infraestructure.services;

import com.debuggeando_ideas.best_travel.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel.api.models.responses.FlyResponse;
import com.debuggeando_ideas.best_travel.api.models.responses.TicketResponse;
import com.debuggeando_ideas.best_travel.domain.entities.TicketEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.*;
import com.debuggeando_ideas.best_travel.infraestructure.abstract_services.ITicketService;
import com.debuggeando_ideas.best_travel.infraestructure.helper.BlackListHelper;
import com.debuggeando_ideas.best_travel.infraestructure.helper.CustomerHelper;
import com.debuggeando_ideas.best_travel.util.BestTravelUtil;
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
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;

    private static final BigDecimal charger_price_porcentage = BigDecimal.valueOf(0.25);

    @Override
    public TicketResponse create(TicketRequest request) {

       blackListHelper.isInBlackListCustomer(request.getIdClient());
       var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
       var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

       var ticketToPersist = TicketEntity.builder()
               .id(UUID.randomUUID())
               .fly(fly)
               .customer(customer)
               .price(fly.getPrice().add(fly.getPrice().multiply(BigDecimal.valueOf(Math.random()))))
               .purchaseDate(LocalDate.now())
               .arrivalDate(BestTravelUtil.getRandomLatter())
               .departureDate(BestTravelUtil.getRandomSoon())
               .build();

       var ticketPersisted = this.ticketRepository.save(ticketToPersist);

       log.info("Ticket saved with id {}", ticketPersisted.getId());

       this.customerHelper.incrase(customer.getDni(), TicketService.class);
       return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID uuid) {
        var ticketFromDB = this.ticketRepository.findById(uuid).orElseThrow();
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(UUID id, TicketRequest request) {

        var ticketToUpdate = this.ticketRepository.findById(id).orElseThrow();
        var fly = this.flyRepository.findById(request.getIdFly()).orElseThrow();

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(BigDecimal.valueOf(Math.random()))));
        ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLatter());
        ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);

        log.info("Ticket updated with id {}", ticketUpdated.getId());

        return this.entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {

        var ticketToDelete = ticketRepository.findById(id).orElseThrow();
        this.ticketRepository.delete(ticketToDelete);
    }

    private TicketResponse entityToResponse(TicketEntity entity){
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity, response);

        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);

        response.setFly(flyResponse);

        return response;
    }

    @Override
    public BigDecimal findPrice(Long idFly) {
        var flyPrice = this.flyRepository.findById(idFly).orElseThrow();
        return flyPrice.getPrice().add(flyPrice.getPrice().multiply(BigDecimal.valueOf(Math.random())));
    }
}
