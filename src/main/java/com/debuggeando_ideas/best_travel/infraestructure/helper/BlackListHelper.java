package com.debuggeando_ideas.best_travel.infraestructure.helper;

import com.debuggeando_ideas.best_travel.util.exceptions.ForbiddenCustomerException;

public class BlackListHelper {

    public void isInBlackListCustomer(String customerId){
        if(customerId.equals("GORWASDF")){
            throw new ForbiddenCustomerException();
        }
    }
}
