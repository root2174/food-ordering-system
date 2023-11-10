package com.food.ordering.system.kafka.producer.system.order.service.domain.valueobject;

import com.food.ordering.system.kafka.producer.system.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
