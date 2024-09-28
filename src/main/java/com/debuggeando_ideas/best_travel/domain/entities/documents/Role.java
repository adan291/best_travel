package com.debuggeando_ideas.best_travel.domain.entities.documents;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Role {

    @Field(name = "granted_authorities")
    private List<String> grantedAuthorities;

}
