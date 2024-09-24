package com.debuggeando_ideas.best_travel.api.models.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ReservationRequest {

    private String idClient;
    private Long idHotel;
    private Integer totalDays;
}
