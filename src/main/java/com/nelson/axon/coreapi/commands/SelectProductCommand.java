package com.nelson.axon.coreapi.commands;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SelectProductCommand {
        private @TargetAggregateIdentifier UUID foodCartId;
        private UUID productId;
        private Integer quantity;
}
