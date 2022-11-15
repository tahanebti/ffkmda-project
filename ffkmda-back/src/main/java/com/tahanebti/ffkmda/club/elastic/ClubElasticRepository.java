package com.tahanebti.ffkmda.club.elastic;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface ClubElasticRepository extends ElasticsearchRepository<ClubModel, String> {

	Page<ClubModel> findAll();
	
	
	
	  @Query("{\"match\": {\"nom\": {\"query\": \"?0\"},"
	  		+ "{\"code\": {\"query\": \"?1\"}"
	  		+ "{\"etat\": {\"query\": \"?2\"}"
	  		+ "{\"type\": {\"query\": \"?3\"}"
	  		+ "}}")
	  Page<ClubModel> findByNomOrCodeOrEtatOrType(String nom, String code, String etat, String type, Pageable pageable);

}
