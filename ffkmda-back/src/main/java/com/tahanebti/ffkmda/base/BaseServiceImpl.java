package com.tahanebti.ffkmda.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.tahanebti.ffkmda.annotation.Timed;
import com.tahanebti.ffkmda.exception.DataDeletionException;
import com.tahanebti.ffkmda.exception.DataNotFoundException;
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseServiceImpl<E extends IdentifiableEntity<ID>, ID extends Serializable>
implements BaseService<E, ID>  {
	
	protected final BaseRepository<E, ID> baseRepository;
    protected final  SpecificationsBuilder<E> spec;

    
    @Override
	public List<E> find() {
		return baseRepository.findAll();
	}
	
	@Override
    @Timed
	public List<E> find(Specification<E> query) {
	        return baseRepository.findAll(query);
	}
	
	
	@Override
    @Timed
	public Page<E> search(Specification<E> query, Integer _limit, Integer _offset, String _sort) {
		PageRequest page = PageRequestBuilder.getPageRequest(_limit, _offset, _sort);
	        return baseRepository.findAll(query, page);
	}

	@Override
	@Timed
	public E save(E e) {
		return baseRepository.save(e);
	}
	
	@Override
	@Timed
	public void saveAll(List<E> es) {
		baseRepository.saveAll(es);
	}

	@Override
	@Timed
	public void delete(E e) {
		try {
			baseRepository.delete(e);
		} catch (DataIntegrityViolationException ex) {
			 throw new DataDeletionException(e);
		}
	}

	@Override
	public E validateAndGetById(ID id) {
		return baseRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
	}


	@Override
	public void deleteAll(List<ID> ids) {
		baseRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void clear() {
		baseRepository.deleteAll();
	}
}	
		
	


