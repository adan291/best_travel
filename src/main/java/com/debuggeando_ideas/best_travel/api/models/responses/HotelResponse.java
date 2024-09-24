package com.debuggeando_ideas.best_travel.api.models.responses;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class HotelResponse {

    private Long id;
    private String name;
    private String address;
    private Integer rating;
    private BigDecimal price;

}
