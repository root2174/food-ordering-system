package com.food.ordering.system.order.service.messaging.mapper;

import com.food.ordering.system.domain.valueobject.PaymentStatus;
import com.food.ordering.system.kafka.producer.system.kafka.order.avro.model.*;
import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

	public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {
		Order order = orderCreatedEvent.getOrder();

		return PaymentRequestAvroModel.newBuilder()
				.setId(UUID.randomUUID().toString())
				.setSagaId("")
				.setCustomerId(order.getCustomerId().getValue().toString())
				.setOrderId(order.getId().getValue().toString())
				.setPrice(order.getPrice().getAmount())
				.setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
				.setPaymentOrderStatus(PaymentOrderStatus.PENDING)
				.build();
	}

	public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent) {
		Order order = orderCancelledEvent.getOrder();

		return PaymentRequestAvroModel.newBuilder()
				.setId(UUID.randomUUID().toString())
				.setSagaId("")
				.setCustomerId(order.getCustomerId().getValue().toString())
				.setOrderId(order.getId().getValue().toString())
				.setPrice(order.getPrice().getAmount())
				.setCreatedAt(orderCancelledEvent.getCreatedAt().toInstant())
				.setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
				.build();
	}

	public RestaurantApprovalRequestAvroModel orderPaidEventToRestaurantApprovalRequestAvroModel(OrderPaidEvent domainEvent) {
		Order order = domainEvent.getOrder();

		return RestaurantApprovalRequestAvroModel.newBuilder()
				.setId(UUID.randomUUID().toString())
				.setSagaId("")
				.setOrderId(order.getId().getValue().toString())
				.setRestaurantId(order.getRestaurantId().getValue().toString())
				.setRestaurantOrderStatus(RestaurantOrderStatus.valueOf(order.getOrderStatus().name()))
				.setProducts(order.getItems().stream().map(orderItem ->
						Product.newBuilder()
								.setId(orderItem.getProduct().getId().toString())
								.setQuantity(orderItem.getQuantity())
						.build()).collect(Collectors.toList()))
				.setPrice(order.getPrice().getAmount())
				.setCreatedAt(domainEvent.getCreatedAt().toInstant())
				.build();
	}

	public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel paymentResponseAvroModel) {
		return PaymentResponse.builder()
				.orderId(paymentResponseAvroModel.getOrderId())
				.paymentId(paymentResponseAvroModel.getPaymentId())
				.sagaId(paymentResponseAvroModel.getSagaId())
				.customerId(paymentResponseAvroModel.getCustomerId())
				.id(paymentResponseAvroModel.getId())
				.price(paymentResponseAvroModel.getPrice())
				.createdAt(paymentResponseAvroModel.getCreatedAt())
				.paymentStatus(PaymentStatus.valueOf(paymentResponseAvroModel.getPaymentStatus().name()))
				.failureMessages(paymentResponseAvroModel.getFailureMessages())
				.build();
	}
}
