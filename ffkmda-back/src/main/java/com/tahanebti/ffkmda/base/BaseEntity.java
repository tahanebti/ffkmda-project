package com.tahanebti.ffkmda.base;

import java.io.Serializable;



import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public abstract class BaseEntity<ID extends Serializable> implements IdentifiableEntity<ID> {
	
	 
}
