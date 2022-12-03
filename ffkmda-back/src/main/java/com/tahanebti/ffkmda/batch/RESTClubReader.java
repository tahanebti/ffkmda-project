package com.tahanebti.ffkmda.batch;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tahanebti.ffkmda.address.Address;
import com.tahanebti.ffkmda.club.Club;
import com.tahanebti.ffkmda.phone.Phone;
import com.tahanebti.ffkmda.siege.Siege;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@RequiredArgsConstructor
public class RESTClubReader implements ItemReader<Club> {

	private  String apiUrl;
	private RestTemplate restTemplate;
	
    private int nextClubIndex;	
    private List<Club> clubData;
    
    
    RESTClubReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
        nextClubIndex = 0;
    }
    
	@Override
	public Club read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        
		log.info("Reading the information of the next club");

		   if (clubDataIsNotInitialized()) {
	            clubData = fetchDataFromAPI();
	        }
		   
		   Club nextClub = null;
	        if (nextClubIndex < clubData.size()) {
	            nextClub =  clubData.get(nextClubIndex);
	            nextClubIndex++;
	        }
	        else {
	            nextClubIndex = 0;
	            clubData = null;
	        }
	        
	        log.info("Found club: {}", nextClub);

        
		return nextClub;
	}

	private List<Club> fetchDataFromAPI() throws IOException {
		String token = getAccessTokenFromExtranetFfkmda();
	    System.out.println("token" + token);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    headers.add("Authorization", "Bearer "+ token );

	    HttpEntity<String> httpEntity = new HttpEntity<>(headers);
	    //String accessUrl = "https://extranet.ffkmda.fr/api/v1/structures";
	    

	    URL url = new URL(apiUrl);
	    HttpURLConnection http = (HttpURLConnection)url.openConnection();
	    http.setRequestProperty("Authorization", "Bearer" + token);
	    System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
	    if(http.getResponseCode() == 200) {
	    	
	   
	         ResponseEntity<Object> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, httpEntity, Object.class);
	    	
	         ObjectMapper mapper = new ObjectMapper();
	 		String json = mapper.writeValueAsString(responseEntity.getBody());
	 		Map jsonMap = mapper.readValue(json, Map.class);
	 		
	        List list = (List)jsonMap.get("data");
    
	        List<Club> target = new ArrayList<>();
	        List<Address> address_list = new ArrayList<>();
	        for(Object item : list) {
	        	 Map<String,Object> ind = (Map<String,Object> ) item;
	        	 Map<String,Object>  listSiege = (Map<String,Object> )ind.get("siege");

	        		 Club club = Club.builder()
	        				 	.code(ind.get("code") == null ? null : ind.get("code").toString())
	 		        	 		.etat(ind.get("etat") == null ? null : ind.get("etat").toString())
	 		        	 		.nom(ind.get("nom") == null ? null : ind.get("nom").toString())
	 		        	 		.nom_court(ind.get("nom_court") == null ? null : ind.get("nom_court").toString())
	 		        	 		.type(ind.get("type") == null ? null : ind.get("type").toString())
	 		        	 		.code_region(ind.get("code_region") == null ? null : ind.get("code_region").toString())
	 		        	 		.siege(Siege.builder()
	 		        	 					.nom_voie(listSiege.get("nom_voie") == null ? null : listSiege.get("nom_voie").toString())
		 			        	 			.num_voie(listSiege.get("num_voie") == null ? null : listSiege.get("num_voie").toString())
		 			        	 			.type_voie(listSiege.get("type_voie") == null ? null : listSiege.get("type_voie").toString())
		 			        	 			.code_postal_fr(listSiege.get("code_postal_fr") == null ? null : listSiege.get("code_postal_fr").toString())
		 			        	 			.code_postal_et(listSiege.get("code_postal_et") == null ? null : listSiege.get("code_postal_et").toString())
		 			        	 			.commune(listSiege.get("commune") == null ? null : listSiege.get("commune").toString())
		 			        	 			.pays_code(listSiege.get("pays_code") == null ? null : listSiege.get("pays_code").toString())
		 			        	 			.adresse_complete(listSiege.get("adresse_complete") == null ? null : listSiege.get("adresse_complete").toString())
		 		        	 				.code_insee_departement(listSiege.get("code_insee_departement") == null ? null : listSiege.get("code_insee_departement").toString())
		 			        	 			.code_insee_region(listSiege.get("code_insee_region") == null ? null : listSiege.get("code_insee_region").toString())
		 			        	 			.latitude(listSiege.get("latitude") == null ? null : listSiege.get("latitude").toString())
		 			        	 			.longitude(listSiege.get("longitude") == null ? null : listSiege.get("longitude").toString())
		 			        	 			.tel(listSiege.get("tel") == null ? null : listSiege.get("tel").toString())
 		 			        	 			.tel2(listSiege.get("tel2") == null ? null : listSiege.get("tel2").toString())
 		 			        	 			.tel_pro(listSiege.get("tel_pro") == null ? null : listSiege.get("tel_pro").toString())
 		 			        	 			.mobile(listSiege.get("mobile") == null ? null : listSiege.get("mobile").toString())
 		 			        	 			.mobile2(listSiege.get("mobile2") == null ? null : listSiege.get("mobile2").toString())
 		 			        	 			.mobile_pro(listSiege.get("mobile_pro") == null ? null : listSiege.get("mobile_pro").toString())		    
 		 			        	 			.fax(listSiege.get("fax") == null ? null : listSiege.get("fax").toString())
 		 			        	 			.fax_pro(listSiege.get("fax_pro") == null ? null : listSiege.get("fax_pro").toString())
	 		        	 				
	 			        	 			 
	 			        	 			
	 		        	 				
	 		        	 				
	 			        	 			.batiment(listSiege.get("batiment") == null ? null : listSiege.get("batiment").toString())
	 			        	 			.escalier(listSiege.get("escalier") == null ? null : listSiege.get("escalier").toString())
	 			        	 			.mail(listSiege.get("mail") == null ? null : listSiege.get("mail").toString())		 			        	 					 			        	 			
	 			        	 			.facebook(listSiege.get("facebook") == null ? null : listSiege.get("facebook").toString())
	 			        	 			.twitter(listSiege.get("twitter") == null ? null : listSiege.get("twitter").toString())
	 			        	 			.web(listSiege.get("web") == null ? null : listSiege.get("web").toString())
	 		        	 				.build())
	        				 .build();
	        	 
	        		System.out.println(" ------------> club : " + club);
	        		 target.add(club);
	        	
	        	 
	        	 
	        }
	        	
	        return target;
	    }
	    
	    return null;
	}
	
	public String getAccessTokenFromExtranetFfkmda() {
		  String accessUrl = "https://extranet.ffkmda.fr/api/v1/login";
		  HttpHeaders headers = new HttpHeaders();
	        headers.add("Accept", "application/json");
	        
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("username", "SiteInternet");
	        params.put("password", "$elic!Europea#");
	        
	        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
	        String response = this.restTemplate.postForObject(accessUrl, request, String.class);
	        try {
	            return new ObjectMapper().readTree(response).get("success").get("token").asText();
	        } catch (JsonProcessingException e) {
	            throw new RuntimeException("Error parsing response while requesting token from ffkmda");
	        }
	    }
	  

	private boolean clubDataIsNotInitialized() {
        return this.clubData == null;
	}


}
