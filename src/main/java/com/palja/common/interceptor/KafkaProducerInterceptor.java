package com.palja.common.interceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.palja.common.auditor.CurrentUser;

import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaProducerInterceptor<T> implements ProducerInterceptor<String, T> {

	private final Tracer tracer;

	@Override
	public ProducerRecord<String, T> onSend(ProducerRecord<String, T> producerRecord) {
		producerRecord.headers().add("X-USER-LOGIN-ID", CurrentUser.getLoginId().getBytes(StandardCharsets.UTF_8));
		producerRecord.headers().add("X-USER-ROLE", CurrentUser.getRole().name().getBytes(StandardCharsets.UTF_8));

		if (tracer.currentSpan() != null) {
			producerRecord.headers().add("X-TRACE-ID", Objects.requireNonNull(tracer.currentSpan()).context().traceId().getBytes(StandardCharsets.UTF_8));
			producerRecord.headers().add("X-SPAN-ID", Objects.requireNonNull(tracer.currentSpan()).context().spanId().getBytes(StandardCharsets.UTF_8));
		}

		return producerRecord;
	}

	@Override
	public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
	}

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> map) {
	}

}
