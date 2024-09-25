package com.debuggeando_ideas.best_travel.api.models.responses;

import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TourResponse implements Serializable {

    private Long id;
    private Set<UUID> ticketsIds;
    private Set<UUID> reservationsIds;
}
