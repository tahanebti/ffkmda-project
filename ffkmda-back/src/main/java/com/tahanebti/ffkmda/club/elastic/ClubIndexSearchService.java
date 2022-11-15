package com.tahanebti.ffkmda.club.elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tahanebti.ffkmda.club.elastic.utils.HighLevelRestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ClubIndexSearchService {

	private final HighLevelRestClient highLevelRestClient;
	private final ObjectMapper objectMapper;
	
	public List<ClubModel> searchMatchFullCode(String code, Integer offset, Integer limit) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("clubs");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchQuery("code", code));
		sourceBuilder.from(offset);
		sourceBuilder.size(limit);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = highLevelRestClient.postSearchQueries(searchRequest);
		return extractClubResponse(searchResponse);
	}
	

	
	public List<ClubModel> searchMatchfildes(String name, String code, String etat, String type, Integer offset, Integer limit) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("clubs");
	
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
	
		sourceBuilder.query(QueryBuilders.matchQuery("code", code));		
		sourceBuilder.from(offset);
		sourceBuilder.size(limit);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = highLevelRestClient.postSearchQueries(searchRequest);
		return extractClubResponse(searchResponse);
	}
	
	public List<ClubModel> multiSearchQuery(String query, Integer offset, Integer limit, Boolean prefixPhraseEnabled) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("clubs");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(offset);
		sourceBuilder.size(limit);
		MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(query, "nom", "type", "etat", "code");
		if (prefixPhraseEnabled) {
			multiMatchQuery.type(MultiMatchQueryBuilder.Type.PHRASE_PREFIX);
		}
		sourceBuilder.query(multiMatchQuery);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = highLevelRestClient.postSearchQueries(searchRequest);
		return extractClubResponse(searchResponse);
	}
	
	public List<ClubModel> wildcardSearch(String query, Integer offset, Integer limit) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("customers");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(offset);
		sourceBuilder.size(limit);
		BoolQueryBuilder queryBuilders = new BoolQueryBuilder();
		queryBuilders.should(QueryBuilders.wildcardQuery("nom", "*" + query + "*"));
		queryBuilders.should(QueryBuilders.wildcardQuery("code", "*" + query + "*"));
		queryBuilders.should(QueryBuilders.wildcardQuery("etat", "*" + query + "*"));
		queryBuilders.should(QueryBuilders.wildcardQuery("type", "*" + query + "*"));
		sourceBuilder.query(queryBuilders);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = highLevelRestClient.postSearchQueries(searchRequest);
		log.info("Search JSON query: {}", searchRequest.source().toString());
		return extractClubResponse(searchResponse);
	}
	
	public List<ClubModel> searchDateRange(String fromDate, String toDate, Integer offset, Integer limit) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("clubs");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(offset);
		sourceBuilder.size(limit);
		sourceBuilder.query(QueryBuilders.rangeQuery("createdAt").gte(fromDate).lte(toDate));
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = highLevelRestClient.postSearchQueries(searchRequest);
		log.info("Search JSON query: {}", searchRequest.source().toString());
		return extractClubResponse(searchResponse);
	}

	public List<ClubModel> queryGeographyPoint(Double lon, Double lat, Integer offset, Integer limit) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("customers");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(offset);
		sourceBuilder.size(limit);
		sourceBuilder.query(QueryBuilders.geoDistanceQuery("point").point(lat, lon).distance("1km"));
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = highLevelRestClient.postSearchQueries(searchRequest);
		log.info("Search JSON query: {}", searchRequest.source().toString());
		return extractClubResponse(searchResponse);
	}
	
	private List<ClubModel> extractClubResponse(SearchResponse searchResponse) {
		ClubModel response = new ClubModel();
		SearchHits searchHits = searchResponse.getHits();
		long totalRecords = searchHits.getTotalHits().value;
		SearchHit[] searchHitsArray = searchResponse.getHits().getHits();
		List<ClubModel> clubs = new ArrayList<>();
		for (SearchHit hit : searchHitsArray) {

			clubs.add(objectMapper.convertValue(hit.getSourceAsMap(), ClubModel.class));
		}
		
		return clubs;
	}
	
	
	
}
