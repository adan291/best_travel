package com.debuggeando_ideas.best_travel.domain.entities.documents;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Role {


    private List<String> grantedAuthorities;

}
