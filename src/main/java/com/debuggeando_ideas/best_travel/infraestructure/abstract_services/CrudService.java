package com.debuggeando_ideas.best_travel.infraestructure.abstract_services;

public interface CrudService <RQ, RS, ID>{

    RS create(RQ request);

    RS read(ID id);

    RS update(ID id, RQ request);

    void delete(ID id);

}
