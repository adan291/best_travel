package com.debuggeando_ideas.best_travel.infraestructure.abstract_services;

import com.debuggeando_ideas.best_travel.api.models.request.TourRequest;
import com.debuggeando_ideas.best_travel.api.models.responses.TourResponse;

import java.util.UUID;

public interface ITourService extends SimpleCrudServices<TourRequest, TourResponse, Long> {

    void delete(Long id);

    void removeTicket(UUID ticketId, Long tourId);
    UUID addTicket(Long flyId, Long tourId);

    void removeReservation(UUID reservationId, Long tourId);
    UUID addReservation(Long reservationId, Long tourId);

}
