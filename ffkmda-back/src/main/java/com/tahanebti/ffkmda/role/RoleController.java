package com.tahanebti.ffkmda.role;

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
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1/roles")
public class RoleController {

	private final RoleService roleService;
	private final RoleMapper roleMapper;	
	private final SpecificationsBuilder<Role> spec;
	
	@GetMapping
	public List<RoleResponse> find(@RequestParam(required = false) String query) {
		return roleService.find(spec.with(query)).stream().map(roleMapper::toResponse).collect(Collectors.toList());
	}

	
	@GetMapping("/search")
	public Map<String, Object> search(@RequestParam(required = false) String query,
			@RequestParam(required = false, defaultValue = "1") Integer _offset,
			@RequestParam(required = false, defaultValue = "10") Integer _limit,
			@RequestParam(required = false, defaultValue = "id") String _sort) throws JsonProcessingException {
		Page<Role> pageEntity = roleService.search(spec.with(query), _limit, _offset, _sort);

		Map<String, Object> response = new HashMap<>();
		response.put("payload",
				pageEntity.getContent().stream().map(roleMapper::toResponse).collect(Collectors.toList()));

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
	public RoleResponse getRole(@PathVariable Long id) {
		Role role = roleService.validateAndGetById(id);
		return roleMapper.toResponse(role);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public RoleResponse addRole(@Valid @RequestBody CreateRoleRequest request) {
		Role role = roleMapper.toCreate(request);

		role = roleService.save(role);
		return roleMapper.toResponse(role);
	}
	
	@PatchMapping("/{id}")
	public RoleResponse updateRole(@PathVariable Long id, @Valid @RequestBody UpdateRoleRequest updateRoleRequest) {
		Role role = roleService.validateAndGetById(id);

		// Optional.ofNullable(Role.getName()).ifPresent(Role::setName);

		return roleMapper.toResponse(role);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRole(@PathVariable Long id) {
		Role role = roleService.validateAndGetById(id);
		roleService.delete(role);

		return ResponseEntity.ok(true);
	}
	
	   @GetMapping("/{userId}/")
	   public List<RoleResponse> getRolesWithUserId(@PathVariable("userId") Long id) {
		   return roleService.getRolesByUserId(id).stream().map(roleMapper::toResponse).collect(Collectors.toList());
	   }
}
