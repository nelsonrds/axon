package com.nelson.axon.gui;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nelson.axon.coreapi.commands.CreateFoodCartCommand;
import com.nelson.axon.coreapi.commands.SelectProductCommand;
import com.nelson.axon.coreapi.queries.FindFoodCartQuery;
import com.nelson.axon.coreapi.queries.RetrieveProductOptionsQuery;
import com.nelson.axon.query.FoodCartView;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class FoodOrderingController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public FoodOrderingController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/create")
    public void handle() {
        commandGateway.send(new CreateFoodCartCommand());
    }

    @PostMapping("/foodcart/{foodCartId}/add/{productId}/{quantity}")
    public void addProduct(@PathVariable("foodCartId") final String foodCartId,
            @PathVariable("productId") final String productId, @PathVariable("quantity") final Integer quantity) {
        commandGateway
                .send(new SelectProductCommand(UUID.fromString(foodCartId), UUID.fromString(productId), quantity));
    }

    @GetMapping("/foodcart/{foodCartId}")
    public CompletableFuture<FoodCartView> handle(@PathVariable("foodCartId") String foodCartId) {
        log.info("controller attrib: " + foodCartId);
        return queryGateway.query(new FindFoodCartQuery(UUID.fromString(foodCartId)),
                ResponseTypes.instanceOf(FoodCartView.class));

    }

    @GetMapping("/foodcart")
    public CompletableFuture<List<FoodCartView>> handleList() {
        log.info("controller get all");
        return queryGateway.query(new RetrieveProductOptionsQuery(),
                ResponseTypes.multipleInstancesOf(FoodCartView.class));

    }
}
