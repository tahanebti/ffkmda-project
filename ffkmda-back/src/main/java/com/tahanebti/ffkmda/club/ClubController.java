package com.tahanebti.ffkmda.club;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tahanebti.ffkmda.base.PageRequestBuilder;
import com.tahanebti.ffkmda.role.Role;
import com.tahanebti.ffkmda.role.RoleMapper;
import com.tahanebti.ffkmda.role.RoleService;
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clubs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClubController {

	private final ClubService clubService;
	private final ClubMapper clubMapper;	
	private final SpecificationsBuilder<Club> spec;
	
	@GetMapping
	public List<ClubResponse> find(@RequestParam(required = false) String query) {
		return clubService.find(spec.with(query)).stream().map(clubMapper::toResponse).collect(Collectors.toList());
	}

	
	@GetMapping("/search")
	public Map<String, Object> search(
			@Parameter(description = "native query search {} -- Example : code=:eq:ffkmda&siege.commune=:cn:bagnolet") @RequestParam(required = false) String query,
			@RequestParam(required = false, defaultValue = "1") Integer _offset,
			@RequestParam(required = false, defaultValue = "10") Integer _limit,
			@RequestParam(required = false, defaultValue = "id") String _sort
			
			) throws JsonProcessingException {
		Page<Club> pageEntity = clubService.search(spec.with(query), _limit, _offset, _sort);

		Map<String, Object> response = new HashMap<>();
		response.put("payload",
				pageEntity.getContent().stream().map(clubMapper::toResponse).collect(Collectors.toList()));

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(PageRequestBuilder.getPageRequest(_offset, _limit, _sort));
		Map<String, Object> map = mapper.readValue(json, Map.class);

		response.put("pageNumber", pageEntity.getNumber());
		response.put("pageSize", pageEntity.getSize());
		response.put("totalElements", pageEntity.getTotalElements());
		response.put("last", pageEntity.isLast());
		response.put("first", pageEntity.isFirst());
		response.put("numberOfElements", pageEntity.getNumberOfElements());
		response.put("empty", pageEntity.isEmpty());
		return response;
	}
	
	
	@GetMapping("/{id}")
	public ClubResponse getClub(@PathVariable Long id) {
		Club Club = clubService.validateAndGetById(id);
		return clubMapper.toResponse(Club);
	}
	
	@GetMapping("/code/{code}")
	public ClubResponse getClubByCode(
		@Parameter(description = "find club by code {} -- Example : ffkmda") @PathVariable String code) {
		Club Club = clubService.validateAndGetByCode(code);
		return clubMapper.toResponse(Club);
	}
	
	@GetMapping("/etat/{etat}")
	public List<ClubResponse> getClubByEtat(
			@Parameter(description = "return list of clubs by etat {} --- Example : A") @PathVariable String etat) {
		List<Club> clubs = clubService.validateAndGetByEtat(etat);
		return clubs.stream().map(clubMapper::toResponse).collect(Collectors.toList());
	}
	
	@GetMapping("/type/{type}")
	public List<ClubResponse> getClubByType(
			@Parameter(description = "return list of clubs by type {} --- Example : CLU, DEP, LIG etc ....")
			@PathVariable String type) {
		List<Club> clubs = clubService.validateAndGetByType(type);
		return clubs.stream().map(clubMapper::toResponse).collect(Collectors.toList());
	}
	
	@GetMapping("/address")
	public Map<String, Object> findClubsBySiegeAddressCommune(
			@Parameter(description = "return list of clubs by commune {} --- Example : bagnolet ....") @RequestParam(required = false) String commune, 
			@Parameter(description = "return list of clubs by zip {} --- Example : 35000 ....") @RequestParam(required = false) String code_postal_fr,
			@Parameter(description = "return list of clubs by street name {} --- Example : Malmaison ....") @RequestParam(required = false) String nom_voie,
			@Parameter(description = "return list of clubs by street type {} --- Example : Rue ....") @RequestParam(required = false) String type_voie,
			@Parameter(description = "return list of clubs by code deparement {} --- Example : 75 ....") @RequestParam(required = false) String code_insee_departement,
			@PageableDefault(page = 0, size = 10) Pageable pageable
			) {
		List<Club> clubs = clubService.findClubsBySiegeAddress(commune, code_postal_fr, nom_voie, type_voie, code_insee_departement);
		
	    
		//Integer  size = clubs.size() < 1 ? 1 : pageable.getPageSize();
	
		int pageSize = pageable.getPageSize();
		long pageOffset = pageable.getOffset();
		long total = pageOffset + clubs.size() + (clubs.size() == pageSize ? pageSize : 0);
		
		//List<Club> userSubList = clubs.subList((pageable.getPageNumber()-1)*size, (pageable.getPageNumber()*size)-1);

			
		final Page<Club> pageEntity = new PageImpl<>(clubs, pageable, total);
	
		Map<String, Object> response = new HashMap<>();
		response.put("data", pageEntity.getContent().stream().map(clubMapper::toResponse).collect(Collectors.toList()));			
		response.put("offset", pageEntity.getPageable().getOffset());
		response.put("current", pageEntity.getPageable());
		response.put("next", pageEntity.getPageable().next());
		response.put("previous", pageEntity.getPageable().previousOrFirst());
		response.put("totalElements", pageEntity.getTotalElements());
		response.put("last", pageEntity.isLast());
		response.put("first", pageEntity.isFirst());
		response.put("numberOfElements", pageEntity.getNumberOfElements());
		response.put("empty", pageEntity.isEmpty());
		return response;
	}
	
	
	
	@GetMapping("/phone")
	public List<ClubResponse> findClubsBySiegePhone(
			@Parameter(description = "return list of clubs by tel type {} --- Example : tel....") @RequestParam(required = false) String tel, 
			@Parameter(description = "return list of clubs by mobile type {} --- Example : mobile=0640270263 ....") @RequestParam(required = false) String mobile,
			@Parameter(description = "return list of clubs by street fax {} --- Example : fax....") @RequestParam(required = false) String fax

			) {
		List<Club> clubs = clubService.findClubsBySiegePhone(tel, mobile, fax);
		return clubs.stream().map(clubMapper::toResponse).collect(Collectors.toList());
	}
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ClubResponse addClub(@Valid @RequestBody CreateClubRequest request) {
		Club Club = clubMapper.toCreate(request);

		Club = clubService.save(Club);
		return clubMapper.toResponse(Club);
	}
	
	@PatchMapping("/{id}")
	public ClubResponse updateClub(@PathVariable Long id, @Valid @RequestBody UpdateClubRequest updateClubRequest) {
		Club Club = clubService.validateAndGetById(id);

		// Optional.ofNullable(Club.getName()).ifPresent(Club::setName);

		return clubMapper.toResponse(Club);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteClub(@PathVariable Long id) {
		Club Club = clubService.validateAndGetById(id);
		clubService.delete(Club);

		return ResponseEntity.ok(true);
	}
	

}
