package com.nelson.axon.query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.nelson.axon.coreapi.events.FoodCartCreatedEvent;
import com.nelson.axon.coreapi.events.ProductSelectedEvent;
import com.nelson.axon.coreapi.queries.FindFoodCartQuery;
import com.nelson.axon.coreapi.queries.RetrieveProductOptionsQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class FoodCartProjector {
    private final FoodCartViewRepository repository;

    public FoodCartProjector(FoodCartViewRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(FoodCartCreatedEvent event) {
        log.info("EventHandler, FoodCartCreateEvent");
        log.info(event.getFoodCartId());
        FoodCartView foodCartView = new FoodCartView(event.getFoodCartId(), Collections.emptyMap());
        repository.save(foodCartView);
    }

    @EventHandler
    public void on(ProductSelectedEvent event) {
        Optional<FoodCartView> foodCartView = repository.findById(event.getFoodCartId());

        if (foodCartView.isPresent()) {
            final FoodCartView foodCartViewToUpdate = foodCartView.get();
            foodCartViewToUpdate.getProducts().put(event.getProductId(), event.getQuantity());
            repository.save(foodCartViewToUpdate);
        }
    }

    @QueryHandler
    public FoodCartView handle(FindFoodCartQuery query) {
        log.info("foodCardId: " + query.getFoodCardId());
        return repository.findById(query.getFoodCardId()).orElse(null);
    }

    @QueryHandler
    public List<FoodCartView> retrieveProducts(RetrieveProductOptionsQuery query) {
        log.info("Controller, RetrieveProducOptionsQuery");
        return repository.findAll();
    }

}
