package com.food.ordering.system.order.service.messaging.listener.kafka;

import com.food.ordering.system.kafka.consumer.KafkaConsumer;
import com.food.ordering.system.kafka.producer.system.kafka.order.avro.model.OrderApprovalStatus;
import com.food.ordering.system.kafka.producer.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import com.food.ordering.system.order.service.domain.RestaurantApprovalResponseMessageListenerImpl;
import com.food.ordering.system.order.service.domain.ports.input.listener.restaurantapproval.RestaurantApprovalMessageListener;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RestaurantApprovalResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseAvroModel> {

	private final RestaurantApprovalMessageListener restaurantApprovalResponseMessageListener;
	private final OrderMessagingDataMapper orderMessagingDataMapper;

	public RestaurantApprovalResponseKafkaListener(RestaurantApprovalMessageListener restaurantApprovalResponseMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
		this.restaurantApprovalResponseMessageListener = restaurantApprovalResponseMessageListener;
		this.orderMessagingDataMapper = orderMessagingDataMapper;
	}

	@Override
	@KafkaListener(
			id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
			topics = "${order-service.restaurant-approval-response-topic-name}"
	)
	public void receive(
			@Payload List<RestaurantApprovalResponseAvroModel> messages,
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
			@Header(KafkaHeaders.OFFSET) List<Long> offsets) {
		log.info("{} number of restaurant approval responses received with keys: {}, partitions: {}, offsets: {}",
				messages.size(), keys, partitions, offsets);

		messages.forEach(model -> {
			if (model.getOrderApprovalStatus() == OrderApprovalStatus.APPROVED) {
				log.info(("Processing approved order for order id: {}"), model.getOrderId());
				restaurantApprovalResponseMessageListener.orderApproved(
						orderMessagingDataMapper.approvalResponseAvroModelToApprovalResponse(model));
			} else if (model.getOrderApprovalStatus() == OrderApprovalStatus.REJECTED) {
				log.info(("Processing rejected order for order id: {}"), model.getOrderId());
				restaurantApprovalResponseMessageListener.orderRejected(
						orderMessagingDataMapper.approvalResponseAvroModelToApprovalResponse(model));
			}
		});
	}
}
