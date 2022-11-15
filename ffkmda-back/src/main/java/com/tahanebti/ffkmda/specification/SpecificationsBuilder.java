package com.tahanebti.ffkmda.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;



import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SpecificationsBuilder<T> {
	
	
	private  SpecificationFactory<T> specificationFactory;
     
    private List<SearchCriteria> params;
    
    @Autowired
	public SpecificationsBuilder(SpecificationFactory<T> specificationFactory, List<SearchCriteria> params) {
		this.specificationFactory = specificationFactory;
		this.params = params;
	}

	public SpecificationsBuilder() {
		// TODO Auto-generated constructor stub
	}
    
    public Specification<T> with(Map<String, Object> params) {
    	String query = params.entrySet().stream().map((entry) -> 
    		entry.getKey() + entry.getValue()
    	)
    	.collect(Collectors.joining(","));
    	return with(query);
    }
 
    public Specification<T> with(String query) {
    	System.out.println(" ------------> spec query" + query);
    	Optional<String> splitOperation = FilterOperation.determineSplitOperation(query);
        String predicateFlag = null;        
        
        boolean isOrPredicate = false;
		if(splitOperation.isPresent()) {
        	isOrPredicate = StringUtils.equals(splitOperation.get(), "&") || StringUtils.equals(splitOperation.get(), "|");
        	if(splitOperation.get().contains("&")) {
        		predicateFlag = "AND";
            }
        	if(splitOperation.get().contains("|")) {
        		predicateFlag = "OR";
            }
        	
        }
		
       
        String[] searchQueries = FilterOperation.splitSearchOperations(query, predicateFlag);
        List<SearchCriteria> params = parse(searchQueries, predicateFlag);
            	
        System.out.println(" ------------> spec searchQueries" + searchQueries);
        
        System.out.println(" ------------> spec params" + params);
        
        return build(params);
    }
    
    public List<SearchCriteria> parse(String[] search, String isOrPredicate) {
        ArrayList<SearchCriteria> listOfResults = new ArrayList<SearchCriteria>();
        for (String s : search) {
        	Pattern pattern = Pattern.compile("(\\w+\\\".*\\\"|[^&]*)(\\w*)=:([\\p{L}\\p{Digit}\\/\\p{Space}-:\\(]+?):(\\w*)");
            Matcher matcher = pattern.matcher(s + ",");
            
            if (matcher.find()) {
           	   SearchSection section = process(matcher);
             	section.setIsOrPredicate(isOrPredicate);
             	
                listOfResults.add(buildCriteriaFromSection(section));
            }
        }
        return listOfResults;
    }
    
    protected SearchSection process(Matcher matcher) {
        return SearchSection.builder()
        		.key(matcher.group(1))
        		.operation(matcher.group(3))
        		.value(matcher.group(4))
        		.build();
    }
    
    private SearchCriteria buildCriteriaFromSection(SearchSection section) {
        FilterOperation searchOperation = FilterOperation.getSimpleOperation(section.getOperation());
        return SearchCriteria.builder()
        		.key(section.getKey())
        		.operation(searchOperation)
        		.value(section.getValue())
        		.dataOption(section.getIsOrPredicate())
        		.joinEntity("siege")
        		.build();
    }
    
    
    
    
    public Specification<T> build(List<SearchCriteria> params) {
    	  if (params.isEmpty())
              return null;
    	
    	  
    	  System.out.println(" ------------> spec from params" + params);
    	  
    	  
        List<Specification<T>> specs = params.stream()
          .map(specificationFactory::getByCriteria)
          .collect(Collectors.toList());
        
         Specification<T> result = specs.get(0);

         for (int i = 1; i < params.size(); i++) {
             result = params.get(i)
               .getDataOption() == "OR"
                 ? Specification.where(result)
                   .or(specs.get(i))
                 : Specification.where(result)
                   .and(specs.get(i));
         }
         
        return result;
    }


    public Specification<T> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<T>> specs = params.stream()
          .map(specificationFactory::getByCriteria)
          .collect(Collectors.toList());
        
    
         Specification<T> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
              .getDataOption() == ConditionType.OR.name()
                ? Specification.where(result)
                  .or(specs.get(i))
                : Specification.where(result)
                  .and(specs.get(i));
        }       
        return result;
    }



}

