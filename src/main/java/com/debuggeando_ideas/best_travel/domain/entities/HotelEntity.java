package com.debuggeando_ideas.best_travel.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "hotel")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HotelEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String address;
    private Integer rating;
    private BigDecimal price;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "hotel"
    )
    private Set<ReservationEntity> reservations;
}
