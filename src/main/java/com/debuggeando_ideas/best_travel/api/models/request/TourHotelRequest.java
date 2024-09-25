package com.debuggeando_ideas.best_travel.api.models.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TourHotelRequest {

    private Long id;
    private Integer totalDays;
}
