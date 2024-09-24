package com.debuggeando_ideas.best_travel.api.models.responses;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TicketResponse implements Serializable {

    private UUID id;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalDate purchaseDate;
    private BigDecimal price;
    private FlyResponse fly;
}
