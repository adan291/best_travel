package com.debuggeando_ideas.best_travel.api.models.request;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ReservationRequest {


    @Size(min = 18, max = 20, message = "The size have to lenght between 18 and 20")
    @NotBlank(message = "Id client is mandatory")
    private String idClient;
    @Positive
    @NotNull(message = "Id hotel is mandatory")
    private Long idHotel;
    @Min(value = 1, message = "Min one days to make reservation")
    private Integer totalDays;
    //@Pattern(regexp = "^(.+)@(.+)$")
    @Email
    private String email;
}
