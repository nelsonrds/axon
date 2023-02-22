package com.nelson.axon.coreapi.commands;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class DeselectProductCommand {
    private @TargetAggregateIdentifier UUID foodCartId;
    private UUID productId;
    private Integer quantity;
}
