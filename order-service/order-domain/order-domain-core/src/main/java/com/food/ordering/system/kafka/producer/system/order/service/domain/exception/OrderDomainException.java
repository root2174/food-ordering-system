package com.food.ordering.system.kafka.producer.system.order.service.domain.exception;

import com.food.ordering.system.kafka.producer.system.domain.exception.DomainException;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
