package com.nelson.axon.coreapi.events;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderConfirmedEvent {
    private UUID foodCartId;
}
