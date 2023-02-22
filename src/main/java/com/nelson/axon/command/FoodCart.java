package com.nelson.axon.command;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.nelson.axon.coreapi.commands.CreateFoodCartCommand;
import com.nelson.axon.coreapi.commands.DeselectProductCommand;
import com.nelson.axon.coreapi.commands.SelectProductCommand;
import com.nelson.axon.coreapi.events.FoodCartCreatedEvent;
import com.nelson.axon.coreapi.events.ProductDeselectedEvent;
import com.nelson.axon.coreapi.events.ProductSelectedEvent;
import com.nelson.axon.coreapi.exceptions.ProductDeselectionException;

import lombok.extern.log4j.Log4j2;

@Aggregate
@Log4j2
public class FoodCart {

    @AggregateIdentifier
    private UUID foodCartId;
    private Map<UUID, Integer> selectedProducts;

    public FoodCart() {
        // default constructor required by axon
    }

    @CommandHandler
    public FoodCart(CreateFoodCartCommand command) {
        log.info("CommandHandler, CreateFoodCartCommand");
        UUID aggregateID = UUID.randomUUID();
        log.info(aggregateID);
        AggregateLifecycle.apply(new FoodCartCreatedEvent(aggregateID));
    }

    @CommandHandler
    public void handle(SelectProductCommand command) {
        log.info("Sending new Product", command.getProductId());
        AggregateLifecycle.apply(new ProductSelectedEvent(foodCartId, command.getProductId(), command.getQuantity()));
    }

    @CommandHandler
    public void handle(DeselectProductCommand command) throws ProductDeselectionException {
        UUID productId = command.getProductId();
        if (!selectedProducts.containsKey(productId)) {
            throw new ProductDeselectionException();
        }
        AggregateLifecycle.apply(new ProductDeselectedEvent(foodCartId, command.getProductId(), command.getQuantity()));
    }

    @EventSourcingHandler
    public void on(FoodCartCreatedEvent event) {
        log.info("EventSourcingHandler");
        foodCartId = event.getFoodCartId();
        log.info(foodCartId);
        selectedProducts = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(ProductSelectedEvent event) {
        selectedProducts.merge(event.getProductId(), event.getQuantity(), (v1, v2) -> v1 + v2);
    }

}
