package com.debuggeando_ideas.best_travel.domain.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.io.Serializable;
import java.util.UUID;

@Entity(name = "tour")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TourEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "tour"
    )
    private Set<ReservationEntity> reservations;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "tour"
    )
    private Set<TicketEntity> tickets;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private CustomerEntity customer;



    // todo será ejecutado cada vez que la entidad sea persistida o cargada
    @PostLoad
    @PrePersist
    public void initCollections() {
        if (Objects.isNull(this.reservations)) {
            this.reservations = new HashSet<>();
        }
        if (Objects.isNull(this.tickets)) {
            this.tickets = new HashSet<>();
        }
    }


    public void addReservation(ReservationEntity reservation){
        this.reservations.add(reservation);
    }

    public void removeReservation(UUID idReservation){
        this.reservations.removeIf(r -> r.getId().equals(idReservation));
    }

    public void updateReservation(){
        this.reservations.forEach(r -> r.setTour(this));
    }


    public void addTicket(TicketEntity ticket){
        this.tickets.add(ticket);
    }

    public void removeTicket(UUID id){
        this.tickets.removeIf(ticket -> ticket.getId().equals(id));
    }

    public void updateTicket(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
    }

}
