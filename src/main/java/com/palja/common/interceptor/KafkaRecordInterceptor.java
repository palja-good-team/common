package com.palja.common.interceptor;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.listener.RecordInterceptor;

import com.palja.common.auditor.AuditorContext;
import com.palja.common.vo.UserRole;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaRecordInterceptor<T> implements RecordInterceptor<String, T> {

	private final Tracer tracer;

	@Override
	public ConsumerRecord<String, T> intercept(ConsumerRecord<String, T> record, Consumer<String, T> consumer) {
		String loginId = new String(record.headers().lastHeader("X-USER-LOGIN-ID").value(), StandardCharsets.UTF_8);
		UserRole role = UserRole.valueOf(new String(record.headers().lastHeader("X-USER-ROLE").value(), StandardCharsets.UTF_8));

		AuditorContext.set(loginId, role);

		String traceId = new String(record.headers().lastHeader("X-TRACE-ID").value(), StandardCharsets.UTF_8);
		String spanId = new String(record.headers().lastHeader("X-SPAN-ID").value(), StandardCharsets.UTF_8);

		MDC.put("traceId", traceId);
		MDC.put("spanId", spanId);

		TraceContext traceContext = tracer.traceContextBuilder()
			.traceId(traceId)
			.spanId(spanId)
			.sampled(true)
			.build();

		Span span = tracer.spanBuilder()
			.setParent(traceContext)
			.name(record.topic())
			.start();

		tracer.withSpan(span);

		return record;
	}

	@Override
	public void afterRecord(ConsumerRecord<String, T> record, Consumer<String, T> consumer) {
		Span span = tracer.currentSpan();
		if (span != null) {
			span.end();
		}
		MDC.clear();
	}
	
}
