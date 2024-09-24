package com.debuggeando_ideas.best_travel.infraestructure.abstract_services;

import com.debuggeando_ideas.best_travel.api.models.request.ReservationRequest;
import com.debuggeando_ideas.best_travel.api.models.responses.FlyResponse;
import com.debuggeando_ideas.best_travel.api.models.responses.ReservationResponse;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public interface IFlyService extends ICatalogService<FlyResponse> {


    Set<FlyResponse> readByOriginDestiny(String origen, String destiny);
}
