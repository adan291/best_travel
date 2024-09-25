package com.debuggeando_ideas.best_travel.api.controllers;

import com.debuggeando_ideas.best_travel.api.models.responses.HotelResponse;
import com.debuggeando_ideas.best_travel.infraestructure.abstract_services.IHotelService;
import com.debuggeando_ideas.best_travel.util.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path = "hotel")
@AllArgsConstructor
public class HotelController {

    private final IHotelService hotelService;


    public ResponseEntity<Page<HotelResponse>> getAll
            (@RequestParam Integer page, @RequestParam Integer size, @RequestHeader(required = false) SortType sortType){

        if(Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.hotelService.readAll(page, size, sortType);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping(path = "lessPrice")
    public ResponseEntity<Set<HotelResponse>> getLessPrice
            (@RequestParam BigDecimal price){

        var response = this.hotelService.readLessPrice(price);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }


    @GetMapping(path = "betweenPrice")
    public ResponseEntity<Set<HotelResponse>> getBetweenprice
            (@RequestParam BigDecimal min, @RequestParam BigDecimal max){

        var response = this.hotelService.readBetweenPrice(min, max);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }


    @GetMapping(path = "rating")
    public ResponseEntity<Set<HotelResponse>> getByRating
            (@RequestParam Integer rating){

        rating = Math.max(1, Math.min(rating, 4));
        var response = this.hotelService.readByRating(rating);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}
