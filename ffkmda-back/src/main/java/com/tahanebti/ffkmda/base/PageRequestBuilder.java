package com.tahanebti.ffkmda.base;


import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;


public class PageRequestBuilder  {

	private PageRequestBuilder() {
		
	}
	

	public static PageRequest getPageRequest(Integer pageSize, Integer pageNumber, String sortingCriteria) {

		Set<String> sortingFileds = new LinkedHashSet<>(
				Arrays.asList(StringUtils.split(StringUtils.defaultIfEmpty(sortingCriteria, ""), ",")));

		List<Order> sortingOrders = sortingFileds.stream().map(PageRequestBuilder::getOrder)
				.collect(Collectors.toList());

		Sort sort = sortingOrders.isEmpty() ? null : Sort.by(sortingOrders);

		if(sort != null) {
			return PageRequest.of(ObjectUtils.defaultIfNull(pageNumber, 1) - 1, ObjectUtils.defaultIfNull(pageSize, 20),sort);
		} else {
			return PageRequest.of(ObjectUtils.defaultIfNull(pageNumber, 1) - 1, ObjectUtils.defaultIfNull(pageSize, 20));
		}
	}


	private static Order getOrder(String value) {

		if (StringUtils.startsWith(value, "-")) {
			return new Order(Direction.DESC, StringUtils.substringAfter(value, "-"));
		} else if (StringUtils.startsWith(value, "+")) {
			return new Order(Direction.ASC, StringUtils.substringAfter(value, "+"));
		} else {
			// Sometimes '+' from query param can be replaced as ' '
			return new Order(Direction.ASC, StringUtils.trim(value));
		}

	}
	 
	 

	 
	 

}
