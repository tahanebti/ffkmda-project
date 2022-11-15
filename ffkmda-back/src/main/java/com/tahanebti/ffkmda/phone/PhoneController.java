package com.tahanebti.ffkmda.phone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1/phones")
public class PhoneController {
	
	private final PhoneService phoneService;
	private final PhoneMapper phoneMapper;	
	private final SpecificationsBuilder<Phone> spec;
	
	@GetMapping
	public List<PhoneResponse> find(@RequestParam(required = false) String query) {
		return phoneService.find(spec.with(query)).stream().map(phoneMapper::toResponse).collect(Collectors.toList());
	}

	
	@GetMapping("/search")
	public Map<String, Object> search(@RequestParam(required = false) String query,
			@RequestParam(required = false, defaultValue = "1") Integer _offset,
			@RequestParam(required = false, defaultValue = "10") Integer _limit,
			@RequestParam(required = false, defaultValue = "id") String _sort) throws JsonProcessingException {
		Page<Phone> pageEntity = phoneService.search(spec.with(query), _limit, _offset, _sort);

		Map<String, Object> response = new HashMap<>();
		response.put("payload",
				pageEntity.getContent().stream().map(phoneMapper::toResponse).collect(Collectors.toList()));

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(PageRequestBuilder.getPageRequest(_offset, _limit, _sort));
		Map<String, Object> map = mapper.readValue(json, Map.class);

		response.put("page", map);
		response.put("totalElements", pageEntity.getTotalElements());
		response.put("last", pageEntity.isLast());
		response.put("first", pageEntity.isFirst());
		response.put("numberOfElements", pageEntity.getNumberOfElements());
		response.put("empty", pageEntity.isEmpty());
		return response;
	}
	
	
	@GetMapping("/{id}")
	public PhoneResponse getPhone(@PathVariable Long id) {
		Phone Phone = phoneService.validateAndGetById(id);
		return phoneMapper.toResponse(Phone);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public PhoneResponse addPhone(@Valid @RequestBody CreatePhoneRequest request) {
		Phone Phone = phoneMapper.toCreate(request);

		Phone = phoneService.save(Phone);
		return phoneMapper.toResponse(Phone);
	}
	
	@PatchMapping("/{id}")
	public PhoneResponse updatePhone(@PathVariable Long id, @Valid @RequestBody UpdatePhoneRequest updatePhoneRequest) {
		Phone Phone = phoneService.validateAndGetById(id);

		// Optional.ofNullable(Phone.getName()).ifPresent(Phone::setName);

		return phoneMapper.toResponse(Phone);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePhone(@PathVariable Long id) {
		Phone Phone = phoneService.validateAndGetById(id);
		phoneService.delete(Phone);

		return ResponseEntity.ok(true);
	}
	
	   @GetMapping("/{userId}/")
	   public List<PhoneResponse> getPhonesWithUserId(@PathVariable("userId") Long id) {
		   return phoneService.getPhonesByUserId(id).stream().map(phoneMapper::toResponse).collect(Collectors.toList());
	   }

}
