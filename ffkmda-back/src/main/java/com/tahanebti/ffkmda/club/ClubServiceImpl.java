package com.tahanebti.ffkmda.club;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tahanebti.ffkmda.address.Address;
import com.tahanebti.ffkmda.base.BaseRepository;
import com.tahanebti.ffkmda.base.BaseServiceImpl;
import com.tahanebti.ffkmda.base.PageRequestBuilder;
import com.tahanebti.ffkmda.exception.DataNotFoundException;
import com.tahanebti.ffkmda.siege.Siege;
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;

@Service
public class ClubServiceImpl extends BaseServiceImpl<Club, Long> implements ClubService{

	private ClubRepository clubRepository;
	
	public ClubServiceImpl(BaseRepository<Club, Long> baseRepository, SpecificationsBuilder<Club> spec, ClubRepository clubRepository) {
		super(baseRepository, spec);
		this.clubRepository = clubRepository;
	}

	@Override
	public Club validateAndGetByCode(String code) {
		return clubRepository.findByCode(code).orElseThrow(() -> new DataNotFoundException(code));
	}

	@Override
	public List<Club> validateAndGetByEtat(String etat) {
		return clubRepository.findByEtat(etat);
	}

	@Override
	public List<Club> validateAndGetByType(String type) {
		return clubRepository.findByType(type);
	}
	
	
	@Override
	public List<Club> findClubsBySiegeAddress(String code_postal_fr, String commune, String nom_voie, String type_voie, String code_insee_departement) {
		return clubRepository.findAll(adSpecial(code_postal_fr, commune, nom_voie, type_voie, code_insee_departement));	
	}
	

	@Override
	public Page<Club> searchByAddress(String commune, String code_postal_fr, String nom_voie, String type_voie,
			String code_insee_departement, Integer _limit, Integer _offset, String _sort) {
		
		

		
		PageRequest page = PageRequestBuilder.getPageRequest( _limit, _offset, _sort);
		
		return clubRepository.findAll(adSpecial(code_postal_fr, commune, nom_voie, type_voie, code_insee_departement), page);
	}

	
	//	// firstName=:eq:taha&address.state=:cn:kairouan

	public static Specification<Club> adSpecial(String commune, String code_postal_fr, String nom_voie, String type_voie, String code_insee_departement) {
		
	    return (root, query, criteriaBuilder) -> {
	       // ListJoin<Club, Siege> values = root.joinList("siege");
	    	Join<Club, Siege> joined = root.join("siege", JoinType.LEFT);
	    	  
	    	List<Predicate> predicates = new ArrayList<>();
	    	
	    	 if(commune != null && commune instanceof String){
                 return criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("commune")), "%" + commune + "%");
             }
	    	 
	    	 if(code_postal_fr != null && code_postal_fr instanceof String){
                 return criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("code_postal_fr")), "%" + code_postal_fr + "%");
             }
	    	 
	    	 if(nom_voie != null && nom_voie instanceof String){
                 return criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("nom_voie")), "%" + nom_voie + "%");
             }
	    	 
	    	 if(type_voie != null && nom_voie instanceof String){
                 return criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("type_voie")), "%" + type_voie + "%");
             }
             
	    	 if(code_insee_departement != null && code_insee_departement instanceof String){
                 return criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("code_insee_departement")), "%" + code_insee_departement + "%");
             }
	    	 
             
	    	 
	    	 return criteriaBuilder.like(criteriaBuilder.lower(root.get("commune")), "%" + commune + "%");
	            
	        
	    };
	}

	@Override
	public List<Club> findClubsBySiegePhone(String tel, String mobile, String fax) {
		return clubRepository.findAll(adSpecialPhone(tel, mobile, fax));	

	}
	
	


	public static Specification<Club> adSpecialPhone(String tel, String mobile, String fax) {
	    return (root, query, criteriaBuilder) -> {
		       // ListJoin<Club, Siege> values = root.joinList("siege");
		    	Join<Club, Siege> joined = root.join("siege", JoinType.LEFT);
		    	  
		    	List<Predicate> predicates = new ArrayList<>();
		    	
		    	 if(tel != null && tel instanceof String){
	                 return criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("tel")), "%" + tel + "%");
	             }
		    	 
		    	 if(mobile != null && mobile instanceof String){
	                 return criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("mobile")), "%" + mobile + "%");
	             }
		    	 
		    	 if(fax != null && fax instanceof String){
	                 return criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("nom_voie")), "%" + fax + "%");
	             }
		    	 
		    	 return criteriaBuilder.like(criteriaBuilder.lower(root.get("tel")), "%" + tel + "%");
		           
	    };
	}

}