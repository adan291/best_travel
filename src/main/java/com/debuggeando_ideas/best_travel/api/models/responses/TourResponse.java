package com.debuggeando_ideas.best_travel.api.models.responses;

import com.debuggeando_ideas.best_travel.util.AeroLine;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
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
