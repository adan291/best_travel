package com.debuggeando_ideas.best_travel.infraestructure.services;

import com.debuggeando_ideas.best_travel.domain.repositories.mongo.AppUserRepository;
import com.debuggeando_ideas.best_travel.infraestructure.ModifyUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AppUserService implements ModifyUserService /*UserDetailsServiceAutoConfiguration*/ {

    private final AppUserRepository appUserRepository;

    @Override
    public Map<String, Boolean> enabled(String username) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow();
        user.setEnabled(!user.isEnabled());
        var userSaved = this.appUserRepository.save(user);
        return Collections.singletonMap(userSaved.getUsername(), userSaved.isEnabled());
    }

    @Override
    public Map<String, Set<String>> addRole(String username, String role) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow();
        user.getRole().getGrantedAuthorities().add(role);
        var userSaved = this.appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        return Collections.singletonMap(userSaved.getUsername(), authorities);
    }

    @Override
    public Map<String, Set<String>> removeRole(String username, String role) {
        var user = this.appUserRepository.findByUsername(username).orElseThrow();
        user.getRole().getGrantedAuthorities().remove(role);
        var userSaved = this.appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        return Collections.singletonMap(userSaved.getUsername(), authorities);
    }


    @Transactional(readOnly = true)
    private void loadByUserName(String username){
        var user = this.appUserRepository.findByUsername(username).orElseThrow();
    }
}
