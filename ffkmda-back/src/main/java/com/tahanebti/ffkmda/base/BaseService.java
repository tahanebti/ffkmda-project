package com.tahanebti.ffkmda.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;


public interface BaseService<E extends IdentifiableEntity<ID>, ID extends Serializable> {
	
	public List<E> find();
	public List<E> find(Specification<E> query);
	public Page<E> search(Specification<E> query, Integer _limit, Integer _offset, String _sort);

	public E validateAndGetById(ID id);
	
	public E save(E source);
	public void saveAll(List<E> es);
	
	public void delete(E e);
	public void deleteAll(List<ID> ids);
	
	public void clear();
	
	
	
}

