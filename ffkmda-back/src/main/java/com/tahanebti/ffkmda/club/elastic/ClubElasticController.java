package com.tahanebti.ffkmda.club.elastic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tahanebti.ffkmda.base.PageRequestBuilder;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/elastic/clubs")
public class ClubElasticController {


	private final ClubElasticRepository repository;
	private final ClubIndexSearchService clubIndexSearchService;
	
	@Operation(summary = "Return list of clubs via Elasticsearch")
	@GetMapping
	public ResponseEntity<?> findAllViaElastic(){
		List<ClubModel> products = repository.findAll().getContent();
		
		return ResponseEntity.ok(products);
	}
	
	@Operation(summary = "Return filtred page of clubs via Elasticsearch")
	@GetMapping("/search")
	public ResponseEntity<?> searchByPage(
			@RequestParam(required = false) String nom,
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String etat,
			@RequestParam(required = false) String type,
			@RequestParam(required = false, defaultValue = "1") Integer _offset,
			@RequestParam(required = false, defaultValue = "10") Integer _limit,
			@RequestParam(required = false, defaultValue = "id") String _sort
			){
		
		PageRequest page = PageRequestBuilder.getPageRequest( _limit, _offset, _sort);

		Page<ClubModel> clubs = repository.findByNomOrCodeOrEtatOrType(nom, code, etat, type, page);
				
		Map<String, Object> response = new HashMap<>();

		response.put("data", clubs.getContent());
		response.put("totalElements", clubs.getTotalElements());
		response.put("last", clubs.isLast());
		response.put("first", clubs.isFirst());
		response.put("numberOfElements", clubs.getNumberOfElements());
		response.put("empty", clubs.isEmpty());
				
		return ResponseEntity.ok(clubs);
	}
	
	@Operation(summary = "full code")
	@GetMapping("/search/{query}")
	public ResponseEntity<?> searchPhrase(@RequestParam("query") String query,
	                                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit,
	                                                 @RequestParam(value = "offset", defaultValue = "0") Integer offset){
		return ResponseEntity.ok(clubIndexSearchService.searchMatchFullCode(query, offset, limit));
	}
	
	@Operation(summary = "nom, code, etat, type")
	@GetMapping("/search/multi")
	public ResponseEntity<?> searchMulti(@RequestParam("query") String query,
	                                                @RequestParam(value = "limit", defaultValue = "10") Integer limit,
	                                                @RequestParam(value = "offset", defaultValue = "0") Integer offset,
	                                                @RequestParam(value = "prefix_phrase_enabled", defaultValue = "false") Boolean prefixPhraseEnabled) throws Exception {
		return ResponseEntity.ok(clubIndexSearchService.multiSearchQuery(query, offset, limit, prefixPhraseEnabled));
	}
	
	
}
