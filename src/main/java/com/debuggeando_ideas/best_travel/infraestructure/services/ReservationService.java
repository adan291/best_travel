package com.debuggeando_ideas.best_travel.infraestructure.services;

import com.debuggeando_ideas.best_travel.api.models.request.ReservationRequest;
import com.debuggeando_ideas.best_travel.api.models.responses.HotelResponse;
import com.debuggeando_ideas.best_travel.api.models.responses.ReservationResponse;
import com.debuggeando_ideas.best_travel.domain.entities.ReservationEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.*;
import com.debuggeando_ideas.best_travel.infraestructure.abstract_services.IReservationService;
import com.debuggeando_ideas.best_travel.infraestructure.helper.ApiCurrencyConnectorHelper;
import com.debuggeando_ideas.best_travel.infraestructure.helper.BlackListHelper;
import com.debuggeando_ideas.best_travel.infraestructure.helper.CustomerHelper;
import com.debuggeando_ideas.best_travel.util.exceptions.IdNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;

    private final ApiCurrencyConnectorHelper currencyConnectorHelper;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        blackListHelper.isInBlackListCustomer(request.getIdClient());
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow(() -> new IdNotFoundException("hotel"));
        var customer = this.customerRepository.findById(request.getIdClient()).orElseThrow(() -> new IdNotFoundException("reservation"));

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .totalDays(request.getTotalDays())
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .price(hotel.getPrice().add(hotel.getPrice().multiply(BigDecimal.valueOf(Math.random()))))
                .build();

        var reservationPersisted = this.reservationRepository.save(reservationToPersist);
        this.customerHelper.incrase(customer.getDni(), ReservationService.class);

        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID uuid) {
        var reservationFromDB = this.reservationRepository.findById(uuid).orElseThrow();
        return this.entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(UUID id, ReservationRequest request) {

        var reservationToUpdate = this.reservationRepository.findById(id).orElseThrow();
        var customer = this.customerRepository.findById(request.getIdClient()).orElseThrow();
        var hotel = this.hotelRepository.findById(request.getIdHotel()).orElseThrow();

        reservationToUpdate.setCustomer(customer);
        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now());
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(BigDecimal.valueOf(Math.random()))));

        var reservationUpdated = this.reservationRepository.save(reservationToUpdate);

        log.info("Reservation updated with id {}", reservationUpdated.getId());

        return this.entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID id) {
        var reservationToDelete = reservationRepository.findById(id).orElseThrow();
        this.reservationRepository.delete(reservationToDelete);
    }


    private ReservationResponse entityToResponse(ReservationEntity entity){
        var response = new ReservationResponse();
        BeanUtils.copyProperties(entity, response);

        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);

        response.setHotel(hotelResponse);

        return response;
    }

    @Override
    public BigDecimal findPrice(Long hotelId) {
        var hotelPrice = this.hotelRepository.findById(hotelId).orElseThrow();
        return hotelPrice.getPrice().add(hotelPrice.getPrice().multiply(BigDecimal.valueOf(Math.random())));
    }

    @Override
    public BigDecimal findPrice(Long hotelId, Currency currency) {
        var hotelPrice = this.hotelRepository.findById(hotelId).orElseThrow();
        var priceInDollars = hotelPrice.getPrice().add(hotelPrice.getPrice().add
                (hotelPrice.getPrice().multiply(BigDecimal.valueOf(Math.random()))));
        if(currency.equals(Currency.getInstance("USD")))return priceInDollars;

        var currencyDTO = this.currencyConnectorHelper.getCurrency(currency);

        log.info("API CURRENCY in {}, response: {}", currencyDTO.getExchangeDate().toString(), currencyDTO.getRates());

        return priceInDollars.multiply(currencyDTO.getRates().get(currency));
    }
}
