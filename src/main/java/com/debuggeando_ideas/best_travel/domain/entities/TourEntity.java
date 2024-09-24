package com.debuggeando_ideas.best_travel.domain.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity(name = "tour")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
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


    @PreRemove
    public void update(){
        this.tickets.forEach(ticket -> ticket.setTour(this));
        this.reservations.forEach(r -> r.setTour(this));
    }

    public void addReservation(ReservationEntity reservation){
        if (Objects.isNull(this.reservations)) {
            this.reservations = new HashSet<>();
        }
        this.reservations.add(reservation);
    }

    public void removeReservation(UUID idReservation){
        if (Objects.isNull(this.reservations)) {
            this.reservations = new HashSet<>();
        }
        this.reservations.removeIf(r -> r.getId().equals(idReservation));
    }

    public void updateReservation(){
        if (Objects.isNull(this.reservations)) {
            this.reservations = new HashSet<>();
        }
        this.reservations.forEach(r -> r.setTour(this));
    }


    public void addTicket(TicketEntity ticket){
        if (Objects.isNull(this.tickets)) {
            this.tickets = new HashSet<>();
        }
        this.tickets.add(ticket);
    }

    public void removeTicket(UUID id){
        if (Objects.isNull(this.tickets)) {
            this.tickets = new HashSet<>();
        }
        this.tickets.removeIf(ticket -> ticket.getId().equals(id));
    }

    public void updateTicket(){
        if (Objects.isNull(this.tickets)) {
            this.tickets = new HashSet<>();
        }
        this.tickets.forEach(ticket -> ticket.setTour(this));
    }

}
