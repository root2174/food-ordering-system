package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.producer.system.kafka.order.avro.model.PaymentRequestAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class OrderKafkaMessageHelper {
	public <T> ListenableFutureCallback<SendResult<String, T>> getKafkaCallback(
			KafkaCallbackArgs<T> args) {
		return new ListenableFutureCallback<>() {
			@Override
			public void onFailure(Throwable throwable) {
				log.error("Error while sending {} message {} to topic {}",
						args.getRequestAvroModelName(),
						args.getAvroModel().toString(),
						args.getResponseTopicName(),
						throwable);
			}

			@Override
			public void onSuccess(SendResult<String, T> result) {
				RecordMetadata metadata = result.getRecordMetadata();
				log.info("Received successful response from Kafka for order id: {}, topic: {}, partition: {}, offset: {}, timestamp: {}",
						args.getOrderId(),
						metadata.topic(),
						metadata.partition(),
						metadata.offset(),
						metadata.timestamp());
			}
		};
	}
}
