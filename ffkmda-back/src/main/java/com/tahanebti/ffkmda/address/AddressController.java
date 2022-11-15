package com.tahanebti.ffkmda.address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

//@RestController
//@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
	
	private  final AddressService addressService;
	private final AddressMapper addressMapper;
	private final SpecificationsBuilder<Address> spec;


	@GetMapping
	public List<AddressResponse> find(@RequestParam(required = false) String query) {
		return addressService.find(spec.with(query)).stream().map(addressMapper::toResponse).collect(Collectors.toList());
	}

	
	@GetMapping("/search")
	public Map<String, Object> search(@RequestParam(required = false) String query,
			@RequestParam(required = false, defaultValue = "1") Integer _offset,
			@RequestParam(required = false, defaultValue = "10") Integer _limit,
			@RequestParam(required = false, defaultValue = "id") String _sort) throws JsonProcessingException {
		Page<Address> pageEntity = addressService.search(spec.with(query), _limit, _offset, _sort);

		Map<String, Object> response = new HashMap<>();
		response.put("payload",
				pageEntity.getContent().stream().map(addressMapper::toResponse).collect(Collectors.toList()));

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
	public AddressResponse getPhone(@PathVariable Long id) {
		Address address = addressService.validateAndGetById(id);
		return addressMapper.toResponse(address);
	}
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public AddressResponse addAddress(@Valid @RequestBody CreateAddressRequest addAddressRequest) {
	    Address address = addressMapper.toCreate(addAddressRequest);
	    address = addressService.save(address);
	    return addressMapper.toResponse(address);
	}

	@PatchMapping("/{id}")
	public AddressResponse updateAddress(@PathVariable Long id, @Valid @RequestBody UpdateAddressRequest updateAddressRequest) {
	    Address address = addressService.validateAndGetById(id);
	    addressMapper.updateFromRequest(updateAddressRequest, address);
	    address = addressService.save(address);
	    return addressMapper.toResponse(address);
	}

	@DeleteMapping("/{id}")
	public AddressResponse deleteAddress(@PathVariable Long id) {
	    Address address = addressService.validateAndGetById(id);
	    addressService.delete(address);
	    return addressMapper.toResponse(address);
	}
}
