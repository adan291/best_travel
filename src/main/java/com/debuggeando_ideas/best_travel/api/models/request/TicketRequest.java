package com.debuggeando_ideas.best_travel.api.models.request;

import jakarta.validation.constraints.Email;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TicketRequest implements Serializable {

    private String idClient;
    private Long idFly;
    @Email
    private String email;

}
