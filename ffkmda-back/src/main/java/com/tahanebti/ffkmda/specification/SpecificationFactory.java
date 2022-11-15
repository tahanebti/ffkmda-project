package com.tahanebti.ffkmda.specification;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

@Component @Slf4j

public class SpecificationFactory<T> {
	
	private Map<FilterOperation, Function<SearchCriteria, Specification<T>>> specs;

	@PostConstruct
	private void init() {
		specs = new HashMap<>();	
		specs.put(FilterOperation.CONTAINS, this::getJoinSpecification);
		specs.put(FilterOperation.EQUAL, this::getEqualsSpecification);
		specs.put(FilterOperation.GREATER_THAN, this::getGreaterThanSpecification);
		specs.put(FilterOperation.LESS_THAN, this::getLessThanSpecification);
	}
	
	public Specification<T> getByCriteria(SearchCriteria criteria) {
		return specs.get(criteria.getOperation()).apply(criteria);
	}
	
	private Specification<T> getJoinSpecification(SearchCriteria criteria) {
		return (root, query, builder) -> {
			 String[] splitProperty = criteria.getKey().split("[\\p{Punct}\\s]+");

			  if(splitProperty[1] != null && splitProperty[1] instanceof String){
                  return builder.like(builder.lower(root.join(splitProperty[0], JoinType.LEFT).get(splitProperty[1])), "%" + criteria.getValue() + "%");
              }
              return builder.like(builder.lower(root.get(splitProperty[1])), "%" + criteria.getValue() + "%");
		};
	}
	

    public Specification<T> getEqualsSpecification(SearchCriteria criteria) {
    	System.out.println(" ------------> spec criteria factory" + criteria);
		return (root, query, builder) -> {
			if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                  root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
		};
	}
	
	private Specification<T> getGreaterThanSpecification(SearchCriteria criteria) {
		return (root, query, builder) -> {
			return builder
				.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
		};
	}
	
	private Specification<T> getLessThanSpecification(SearchCriteria criteria) {
		return (root, query, builder) -> {
			return builder
				.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
		};
	}
	   
	
}