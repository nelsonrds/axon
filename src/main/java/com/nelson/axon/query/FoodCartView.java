package com.nelson.axon.query;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodCartView {
    private @Id UUID foodCartId;
    private @ElementCollection(fetch = FetchType.EAGER) Map<UUID, Integer> products = new HashMap<>();
}
