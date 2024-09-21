package com.debuggeando_ideas.best_travel.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "ticket")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TicketEntity implements Serializable {

    @Id
    private UUID id;

    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalDate purchaseDate;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "fly_id")
    private FlyEntity fly;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private TourEntity tour;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
