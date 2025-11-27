package com.palja.common.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PageResponse<T> {

	private final List<T> content;
	private final long totalElements;
	private final int totalPages;
	private final int currentPage;
	private final int pageSize;

	public static <T> PageResponse<T> from(Page<T> page) {
		return PageResponse.<T>builder()
			.content(page.getContent())
			.totalElements(page.getTotalElements())
			.totalPages(page.getTotalPages())
			.currentPage(page.getNumber())
			.pageSize(page.getSize())
			.build();
	}

}
