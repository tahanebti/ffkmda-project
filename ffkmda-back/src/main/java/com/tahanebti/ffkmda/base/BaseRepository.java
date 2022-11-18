package com.tahanebti.ffkmda.base;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;


@NoRepositoryBean 
public interface BaseRepository <E extends IdentifiableEntity<ID>, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {


}