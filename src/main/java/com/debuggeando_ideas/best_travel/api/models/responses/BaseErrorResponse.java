package com.debuggeando_ideas.best_travel.api.models.responses;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class BaseErrorResponse implements Serializable {

    private String status;
    private Integer code;
}
