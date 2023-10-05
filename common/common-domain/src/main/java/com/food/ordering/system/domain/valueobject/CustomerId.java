package com.food.ordering.system.domain.valueobject;

import java.util.UUID;

public class CustomerId extends BaseId<UUID> {
    private CustomerId(UUID value) {
        super(value);
    }
}
