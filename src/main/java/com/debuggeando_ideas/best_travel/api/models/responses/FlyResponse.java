package com.debuggeando_ideas.best_travel.api.models.responses;

import com.debuggeando_ideas.best_travel.util.enums.AeroLine;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FlyResponse implements Serializable {

    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;
    private BigDecimal price;
    private String originName;
    private String destinyName;
    private AeroLine aeroLine;

}
