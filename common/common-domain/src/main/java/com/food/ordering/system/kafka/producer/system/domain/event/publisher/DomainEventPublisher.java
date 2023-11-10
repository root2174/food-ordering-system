package com.food.ordering.system.kafka.producer.system.domain.event.publisher;

import com.food.ordering.system.kafka.producer.system.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
