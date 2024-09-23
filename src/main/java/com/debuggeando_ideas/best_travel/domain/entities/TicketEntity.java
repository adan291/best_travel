package com.debuggeando_ideas.best_travel.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.UUID;

@Entity(name = "ticket")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TicketEntity implements Serializable {

    @Id
    private UUID id;

    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
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
