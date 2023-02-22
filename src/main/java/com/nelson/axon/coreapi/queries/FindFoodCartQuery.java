package com.nelson.axon.coreapi.queries;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindFoodCartQuery {
    private UUID foodCardId;
}
