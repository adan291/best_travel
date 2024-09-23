package com.debuggeando_ideas.best_travel.api.models.request;


import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TicketRequest implements Serializable {

    private String idClient;
    private Long idFly;

}
