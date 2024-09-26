package com.debuggeando_ideas.best_travel.infraestructure.helper;

import com.debuggeando_ideas.best_travel.util.exceptions.ForbiddenCustomerException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class BlackListHelper {

    public void isInBlackListCustomer(String customerId){
        if(customerId.equals("GORWASDF")){
            throw new ForbiddenCustomerException();
        }
    }
}
