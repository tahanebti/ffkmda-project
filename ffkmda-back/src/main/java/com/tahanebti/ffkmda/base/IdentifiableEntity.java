package com.tahanebti.ffkmda.base;

import java.io.Serializable;

public interface IdentifiableEntity<ID extends Serializable> extends Serializable {
	ID getId();
}

