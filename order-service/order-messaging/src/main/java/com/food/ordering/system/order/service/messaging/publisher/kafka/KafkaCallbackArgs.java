package com.food.ordering.system.order.service.messaging.publisher.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaCallbackArgs<T> {
	private String responseTopicName;
	private String orderId;
	private T avroModel;
	private String requestAvroModelName;
}
