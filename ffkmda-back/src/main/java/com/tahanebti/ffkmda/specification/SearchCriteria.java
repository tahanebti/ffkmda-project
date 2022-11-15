package com.tahanebti.ffkmda.specification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data @Builder
@AllArgsConstructor
public class SearchCriteria {
	
	private String key;
    private FilterOperation operation;
    private Object value;
    private String dataOption;
    private String joinEntity;
 
}
