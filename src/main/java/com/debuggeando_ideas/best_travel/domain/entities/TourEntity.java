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
@RequiredArgsConstructor
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

    @Override
    public final boolean equals(Object o) {
        // Si los objetos son el mismo, devolver true (son iguales)
        if (this == o) return true;

        // Si el otro objeto es nulo, devolver false (no pueden ser iguales)
        if (o == null) return false;

        // Obtener la clase efectiva del objeto actual (this)
        // Si es un proxy de Hibernate, obtener la clase de la entidad real
        Class<?> thisEffectiveClass = (this instanceof HibernateProxy)
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        // Obtener la clase efectiva del otro objeto (o)
        // Si es un proxy de Hibernate, obtener la clase de la entidad real
        Class<?> oEffectiveClass = (o instanceof HibernateProxy)
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        // Comparar si las clases efectivas no son iguales
        // Si las clases no coinciden, los objetos no son iguales
        if (thisEffectiveClass != oEffectiveClass) return false;

        // Hacer el casting del objeto 'o' a TourEntity
        TourEntity other = (TourEntity) o;

        // Comparar los IDs de las entidades
        // Si ambos tienen un ID y los IDs son iguales, los objetos son iguales
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public final int hashCode() {
        // Si el objeto es un proxy de Hibernate, usar la clase de la entidad real para el hash
        // Si no, usar la clase actual
        return (this instanceof HibernateProxy)
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

}
