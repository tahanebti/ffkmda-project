package com.tahanebti.ffkmda.specification;


import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;


import lombok.extern.slf4j.Slf4j;

import static com.tahanebti.ffkmda.specification.Constants.*;

@Slf4j
public enum FilterOperation {
	  CONTAINS, DOES_NOT_CONTAIN, EQUAL, NOT_EQUAL, BEGINS_WITH, DOES_NOT_BEGIN_WITH, ENDS_WITH,
	    DOES_NOT_END_WITH, NUL, NOT_NULL, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN, LESS_THAN_EQUAL,
	    ANY, ALL, STARTS_WITH;
	
	
	   public static final String[] SIMPLE_OPERATION_SET = {"cn", "nc", "eq", "ne", "bw", "bn", "ew",
               "en", "nu", "nn", "gt", "ge", "lt", "le"};



	    public static FilterOperation getDataOption(final String dataOption){
	        switch(dataOption){
	            case "all": return ALL;
	            case "any": return ANY;
	            default: return null;
	        }
	    }


	    public static FilterOperation getSimpleOperation(final String input) {
	        switch (input){
	            case "cn": return CONTAINS;
	            case "nc": return DOES_NOT_CONTAIN;
	            case "eq": return EQUAL;
	            case "ne": return NOT_EQUAL;
	            case "bw": return BEGINS_WITH;
	            case "bn": return DOES_NOT_BEGIN_WITH;
	            case "ew": return ENDS_WITH;
	            case "en": return DOES_NOT_END_WITH;
	            case "nu": return NUL;
	            case "nn": return NOT_NULL;
	            case "gt": return GREATER_THAN;
	            case "ge": return GREATER_THAN_EQUAL;
	            case "lt": return LESS_THAN;
	            case "le": return LESS_THAN_EQUAL;

	            default: return null;
	        }
	    }    
	    

	
    public static Optional<String> determineSplitOperation(String search) {
        if (StringUtils.contains(search, COMA))
            return Optional.of(COMA);
        if (StringUtils.contains(search, AND))
            return Optional.of(AND);
        if (StringUtils.contains(search, OR))
            return Optional.of(OR);
        
        return Optional.empty();
    }
    


    public static String[] splitSearchOperations(String search, String operator) {
        if (operator == null)
            return new String[] {search};
        return StringUtils.split(search, operator == "AND" ? "&" : "|");
    }
    
    public static String[] splitJoinOperations(String search, String operator) {
        if (operator == null)
            return new String[] {search};
        return StringUtils.split(search, operator);
    }
    
    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }

    public static boolean isValueNullKey(String search) {
        return StringUtils.equals(search, "null");
    }

 

    public static boolean isKeyCompound(String key) {
        return StringUtils.contains(key, DOT);
    }

    public static String[] getCompoundKeys(String key) {
        return StringUtils.split(key, DOT, 2);
    }


    

	
	

}
