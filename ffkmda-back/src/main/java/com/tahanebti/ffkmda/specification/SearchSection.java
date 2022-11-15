package com.tahanebti.ffkmda.specification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter @Builder
@AllArgsConstructor
public class SearchSection {

    private final String key;
    private final String operation;
    private final Object value;
    private final String prefix;
    private final String suffix;
    private String isOrPredicate;
	private String joinEntity;

	
  

}

