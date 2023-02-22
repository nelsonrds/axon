package com.nelson.axon.coreapi.events;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductSelectedEvent {
        private UUID foodCartId;
        private UUID productId;
        private Integer quantity;
}
