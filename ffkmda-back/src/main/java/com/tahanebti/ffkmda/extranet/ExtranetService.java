package com.tahanebti.ffkmda.extranet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tahanebti.ffkmda.address.Address;
import com.tahanebti.ffkmda.address.AddressRepository;
import com.tahanebti.ffkmda.annotation.Timed;
import com.tahanebti.ffkmda.base.PageRequestBuilder;
import com.tahanebti.ffkmda.club.Club;
import com.tahanebti.ffkmda.club.ClubRepository;
import com.tahanebti.ffkmda.phone.Phone;
import com.tahanebti.ffkmda.siege.Siege;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExtranetService {
	
    private static final String accessUrl = "https://extranet.ffkmda.fr/api/v1/login";

    private final RestTemplate restTemplate;
    
    private final ClubRepository clubRepository;
    
    private final AddressRepository addressRepository;
    
	public String getAccessTokenFromExtranetFfkmda() {
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
	  
	
	    private HttpHeaders attachHeaders(String token) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Accept", "application/json");
	        if (!StringUtils.isEmpty(token)) {
	            headers.set("Authorization", "Bearer " + token);
	        }
	        return headers;
	    }
	    
	    
	    public List<Object> findAll() throws IOException{
	    	return null;
	    }
	
	    
	    @Timed
	    public Map<String, Object> fetchStructures(
	    		String include,
	    		String type_structure, 
	    		String parent_code,
	    		String code_postal,
	    		String commune,
	    		String latitude,
	    		String longitude,
	    		String rayon,
	    		String discipines_code,
	    		String discipline,
	    		String affilie,
	    		Integer _limit, Integer _offset, String _sort, String direction) throws IOException {
	    	
	    	String token = getAccessTokenFromExtranetFfkmda();
		    System.out.println("token" + token);
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		    headers.add("Authorization", "Bearer "+ token );

		    HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		    String accessUrl = "https://extranet.ffkmda.fr/api/v1/structures";
		    

		    URL url = new URL("https://extranet.ffkmda.fr/api/v1/structures");
		    HttpURLConnection http = (HttpURLConnection)url.openConnection();
		    http.setRequestProperty("Authorization", "Bearer" + token);
		    System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
		    if(http.getResponseCode() == 200) {
		    	
		    

		    	  UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(accessUrl)
		    			        		 .queryParam("type_structure", type_structure)
		    			        		 .queryParam("include", include)
		    			        		 .queryParam("parent_code", parent_code)
		    			        		 .queryParam("code_postal", code_postal)
		    			        		 .queryParam("commune", commune)
		    			        		 .queryParam("latitude", latitude)
		    			        		 .queryParam("longitude", longitude)
		    			        		 .queryParam("rayon", rayon)
		    			        		 .queryParam("discipines_code", discipines_code)
		    			        		 .queryParam("discipline", discipline)
		    			        		 .queryParam("affilie", affilie);
		    			 		
		         ResponseEntity<Object> responseEntity = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, httpEntity, Object.class);
		    	
		         ObjectMapper mapper = new ObjectMapper();
		 		String json = mapper.writeValueAsString(responseEntity.getBody());
		 		Map jsonMap = mapper.readValue(json, Map.class);
		 		
		        List list = (List)jsonMap.get("data");
		        
		     
		        
//		        JsonParser jsonParser = mapper.getFactory().createParser(json);
//	            Long dataCount = 0L;
//	            List<Object> list2 = new ArrayList<Object>();
//	            if(jsonParser.nextToken() == JsonToken.START_OBJECT) {
//	                if(jsonParser.nextFieldName() == "data") {
//	                    if(jsonParser.nextToken() == JsonToken.START_ARRAY) {
//	                        while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
//	                            Object data = jsonParser.readValueAs(Object.class);
//	                           // String nom =  ((JSONObject) data).get("nom").toString();
//	                           // System.out.println(" ------------> nom" + nom);
//	                            list2.add(data);
//	                            dataCount++;
//	                        }
//	                    }
//	                }
//	            }
		
		        

		     //   clubRepository.saveAll(target);
		         PageRequest page = PageRequestBuilder.getPageRequest( _limit, _offset, _sort);

				
				int start =  (int) page.getOffset();
			    int end = Math.min((start + page.getPageSize()), list.size());

				
				final Page<Object> pageEntity = new PageImpl<>(list.subList(start, end), page, list.size());
				
				Map<String, Object> response = new HashMap<>();

				response.put("data", pageEntity.getContent());
				response.put("totalElements", pageEntity.getTotalElements());
				response.put("last", pageEntity.isLast());
				response.put("first", pageEntity.isFirst());
				response.put("numberOfElements", pageEntity.getNumberOfElements());
				response.put("empty", pageEntity.isEmpty());
				
			 //   System.out.println(" ------------> " + target);
			    return response;
		    }
		    
		  
           
            return null;
	    }
	    
	
	    @Timed
	    public Map fetchStructureCode(String code) throws IOException{
	    	String token = getAccessTokenFromExtranetFfkmda();
		    System.out.println("token " + token);
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		    headers.add("Authorization", "Bearer "+ token );

		    HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		    String accessUrl = "https://extranet.ffkmda.fr/api/v1/structure/{code}?include=instances,disciplines,derniere_affiliation";
		    
	 		
		    ResponseEntity<Object> responseEntity = restTemplate.exchange(accessUrl, 
             HttpMethod.GET, httpEntity, Object.class, code);
	
    	ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(responseEntity.getBody());
		 Map jsonMap = mapper.readValue(json, Map.class);
 
		// System.out.println(" ------------> " + jsonMap);
	    
		    return jsonMap;
	    }
	    
	    
	    public String getAccessWithAPERRIN() throws IOException {
	          HttpHeaders headers = new HttpHeaders();
	            headers.add("Accept", "application/json");
	            
	            String accessUrl = "https://extranet.ffkmda.fr/auth/login";

	            
	            URL url = new URL("https://extranet.ffkmda.fr/");

	            HttpURLConnection http = (HttpURLConnection)url.openConnection();
	            http.setRequestProperty("Cookies", "XSRF-TOKEN=eyJpdiI6IjJzN3Nid0JtK0hPMDdoMU1Tb1c2WUE9PSIsInZhbHVlIjoiQ0gzZWVuVlZYRFwvNVBjd1ZWV1c0cTR4OTZwTDZodisrREVSV0JLQWQ2NExvQktUdGZ5cVRJQkhZSU1TSGZIOHdZK1lQWDBNdk9Yek4xR3ZTcnVwaFNsZUNUWFkrRDlBSWVMU0FnWGZlM2dXNysxS0VlZ1NMcWcwbURhMlNxdDFTIiwibWFjIjoiNDhmY2MzYjM5NTVkNWMyMDUyZWJiZDk5OGZmMGJkMDhjYmU0MmRjOTZjYzVjYzdlNjQ2Y2Q1Y2ZhOTQ4MTI0NiJ9" );
	            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
	            if(http.getResponseCode() == 200) {
	                
	                Map<String, String> params = new HashMap<String, String>();
	                params.put("username", "APERRIN");
	                params.put("password", "Myonlyone190321");
	                
	                HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
	                String response = this.restTemplate.postForObject(accessUrl, request, String.class);
	                try {
	                    return new ObjectMapper().readTree(response).asText();
	                } catch (JsonProcessingException e) {
	                    throw new RuntimeException("Error parsing response while requesting token from ffkmda");
	                }
	            
	            }
                return null;
	            
	        
	        }
	    
	    
	    @Timed
        public Map fetchPersonCode(Integer _limit, Integer _offset, String _sort, String direction) throws IOException{
	        String token = getAccessTokenFromExtranetFfkmda();
            System.out.println("token" + token);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("Authorization", "Bearer "+ token );

            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            String accessUrl = "https://extranet.ffkmda.fr/api/v1/personnes/recherche";
            

            URL url = new URL("https://extranet.ffkmda.fr/api/v1/personnes");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Authorization", "Bearer" + token);
            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
            if(http.getResponseCode() == 200) {
                
            

                  
                                
                 ResponseEntity<Object> responseEntity = restTemplate.exchange(accessUrl, HttpMethod.GET, httpEntity, Object.class);
                
                 ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(responseEntity.getBody());
                Map jsonMap = mapper.readValue(json, Map.class);
                
                List list = (List)jsonMap.get("data");
                
             
                
//              JsonParser jsonParser = mapper.getFactory().createParser(json);
//              Long dataCount = 0L;
//              List<Object> list2 = new ArrayList<Object>();
//              if(jsonParser.nextToken() == JsonToken.START_OBJECT) {
//                  if(jsonParser.nextFieldName() == "data") {
//                      if(jsonParser.nextToken() == JsonToken.START_ARRAY) {
//                          while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
//                              Object data = jsonParser.readValueAs(Object.class);
//                             // String nom =  ((JSONObject) data).get("nom").toString();
//                             // System.out.println(" ------------> nom" + nom);
//                              list2.add(data);
//                              dataCount++;
//                          }
//                      }
//                  }
//              }
        
                

             //   clubRepository.saveAll(target);
                 PageRequest page = PageRequestBuilder.getPageRequest( _limit, _offset, _sort);

                
                int start =  (int) page.getOffset();
                int end = Math.min((start + page.getPageSize()), list.size());

                
                final Page<Object> pageEntity = new PageImpl<>(list.subList(start, end), page, list.size());
                
                Map<String, Object> response = new HashMap<>();

                response.put("data", pageEntity.getContent());
                response.put("totalElements", pageEntity.getTotalElements());
                response.put("last", pageEntity.isLast());
                response.put("first", pageEntity.isFirst());
                response.put("numberOfElements", pageEntity.getNumberOfElements());
                response.put("empty", pageEntity.isEmpty());
                
             //   System.out.println(" ------------> " + target);
                return response;
            }
            
          
           
            return null;
	    }
	    
	    
}
