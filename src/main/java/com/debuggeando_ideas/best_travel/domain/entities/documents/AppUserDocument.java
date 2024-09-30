package com.debuggeando_ideas.best_travel.domain.entities.documents;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "app_users")
public class AppUserDocument implements Serializable {

    @Id
    private String id;
    private String dni;
    private String username;
    private boolean enabled;
    private String password;
    private Role role;

}